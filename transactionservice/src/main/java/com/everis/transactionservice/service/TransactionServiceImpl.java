package com.everis.transactionservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.transactionservice.dto.BalanceMainDTO;
import com.everis.transactionservice.dto.ResumeDTO;
import com.everis.transactionservice.entity.Customer;
import com.everis.transactionservice.entity.Product;
import com.everis.transactionservice.entity.Representative;
import com.everis.transactionservice.entity.Transaction;
import com.everis.transactionservice.exception.EntityNotFoundException;
import com.everis.transactionservice.repository.ITransactionRepository;
import com.everis.transactionservice.webclient.TransactionServiceClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Angel
 *
 */
//@PropertySource("classpath:application.properties")
@Service
public class TransactionServiceImpl implements ITransactionService {

	
	/**
	 * 
	 */
	@Value("${msg.error.registro.notfound}")
	private String msgNotFound;
	
	@Value("${url.customer.service}")
	private String urlCustomerService;
	
	@Value("${url.product.service}")
	private String urlProductService;
	
	@Value("${url.representative.service}")
	private String urlRepresentativeService;
	
	@Value("${msg.error.cuenta.cliente.personal}")
	private String msgErrorCuentaClientePersonal;
	
	@Value("${msg.error.cuenta.cliente.empresarial}")
	private String msgErrorCuentaClienteEmpresarial;
	
	@Value("${msg.error.cuenta.cliente.empresarial.rep}")
	private String msgErrorCuentaClienteEmpresarialRep;
	
	@Value("${id.product.cnta.ahorros}")
	private String idProductCntaAhorros;
	
	@Value("${id.product.cnta.corriente}")
	private String idProductCntaCorriente;
	
	@Value("${id.product.cnta.plazofijo}")
	private String idProductCntaPlazoFijo;
	
	@Value("${id.product.credit.personal}")
	private String idProductCreditPersonal;
	
	@Value("${id.product.credit.empresarial}")
	private String idProductCreditEmpresarial;
	
	@Value("${id.product.credit.tarjetacredit}")
	private String idProductCreditTarjetaCredit;
	
	@Value("${product.type.pasivo}")
	private String productTypePasivo;
	
	@Value("${product.type.activo}")
	private String productTypeActivo;
	
	@Value("${customer.type.personal}")
	private String customerTypePersonal;
	
	@Value("${customer.id.type.personal}")
	private String customerIdTypePersonal;
	
	@Value("${customer.type.empresarial}")
	private String customerTypeEmpresarial;
	
	@Value("${customer.id.type.empresarial}")
	private String customerIdTypeEmpresarial; 
	
	@Value("${repres.type.titular}")
	private String represTypeTitular;
	
	@Value("${repres.id.type.titular}")
	private String represIdTypeTitular;
	
	@Value("${repres.type.firmante}")
	private String represTypeFirmante;
	
	@Value("${repres.id.type.firmante}")
	private String represIdTypeFirmante;
	
	@Value("${url.apigateway.service}")
	private String urlApiGatewayService;
	
	@Autowired
	private ITransactionRepository transactionRep;
	@Autowired
	private IDebitAssociationService debitAssociationService;
	@Autowired
	private IResumeService resumenService;
	
	@Autowired
	private TransactionServiceClient transactionServiceClient;
	
	
	private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public TransactionServiceImpl(ReactiveMongoTemplate mongoTemplate, ITransactionRepository transactionRep) {
        this.mongoTemplate = mongoTemplate;
        this.transactionRep = transactionRep;
    }
    
    //WebClient webClient = WebClient.create(urlCustomerService);
    
	
	@Override
	public Flux<Transaction> findAll() {
		return transactionRep.findAll();
	}

	@Override
	public Mono<Transaction> findEntityById(String id) {
		return transactionRep.findById(id);
	}

	@Override
	public Mono<Transaction> createEntity(Transaction transaction) throws Exception {
		//Customer customer = this.getCustomerByNumDoc(transaction.getCustomer().getNumDoc());
		Customer customer = transactionServiceClient.getCustomerByNumDoc(transaction.getCustomer().getNumDoc());
		//Product product= this.getProductByIdProduct(transaction.getProduct().getIdProduct());
		Product product = transactionServiceClient.getProductByIdProduct(transaction.getProduct().getIdProduct());
		
		transaction.setCustomer(customer);
		transaction.setProduct(product);
		System.out.println(" type_customer=> " + customerTypePersonal);
		
		//validar si el cliente tiene una deuda vencida
		long countDbtExp= this.countDebtExpiredByCustomer(transaction.getCustomer().getNumDoc());
		System.out.println("cant deuda exp=>"+ countDbtExp);
		if(countDbtExp>0) {
			throw new Exception("El cliente tiene una deuda vencida, no puede adquirir nuevos producto");
		}
		
		
		if(customerIdTypePersonal.equalsIgnoreCase(customer.getTypeCustomer()) || customerTypePersonal.equalsIgnoreCase(customer.getTypeCustomer())) {//Personal
			//Un cliente personal solo puede tener un máximo de una de las cuentas bancarias.
			long  countAccounts= this.countAccountByCustomer(transaction);
			System.out.println("count=>"+ countAccounts);
			if(countAccounts==0) {
				transaction.setRepresentatives(this.getRepresentativesByNumDocRep(transaction.getRepresentatives()));
				return transactionRep.insert(transaction);
			}else {
				throw new Exception(msgErrorCuentaClientePersonal+transaction.getProduct().getNameProduct());
			}
					
		}else {//Empresarial
			//Un cliente empresarial solo puede tener múltiples cuentas corrientes las otras no. 
			if(idProductCntaAhorros.equals(product.getIdProduct()) || idProductCntaPlazoFijo.equals(product.getIdProduct())) { //ahorros (100) o plazo fijo (300)
				throw new Exception(msgErrorCuentaClienteEmpresarial);
			}else {
				//Validate Representatives
				if(idProductCntaCorriente.equals(product.getIdProduct())){ //Cuenta corriente (bancaria) //200
					if(!this.validateRepresentatives(transaction.getRepresentatives())) {
						throw new Exception(msgErrorCuentaClienteEmpresarialRep);
					}
				}
				
				//Se setea los representante
				transaction.setRepresentatives(this.getRepresentativesByNumDocRep(transaction.getRepresentatives()));
				return transactionRep.insert(transaction);
			}
		
		}
		
	}

	@Override
	public Mono<Transaction> updateEntity(Transaction transaction) {
		return  transactionRep.findById(transaction.getId())
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> transactionRep.save(transaction));
	}

	@Override
	public Mono<Void> deleteEntity(String id) {
		return  transactionRep.findById(id)
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> transactionRep.deleteById(id));
	}

	@Override
	public Representative[] getRepresentativesByNumDocRep(Representative[] representatives) {
		List<Representative> listaRep= Arrays.asList(representatives);
		List<Representative> listaRepNueva= new ArrayList<>();
		//listaRepNueva = listaRep.stream().map(r-> getDataRepresentative(r)).collect(Collectors.toList());
		listaRepNueva = listaRep.stream().map(r-> transactionServiceClient.getDataRepresentative(r)).collect(Collectors.toList());
		Representative[] rep_ar= new Representative[listaRepNueva.size()];
		return listaRepNueva.toArray(rep_ar);
	}
	
	@Override
	public boolean validateRepresentatives(Representative[] representatives) {
		List<Representative> listaRep= Arrays.asList(representatives);
		long count = listaRep.stream()
				.filter(r-> r.getTypeRep().equalsIgnoreCase(represIdTypeTitular)  || r.getTypeRep().equalsIgnoreCase(represTypeTitular)) // T or Titular
				.count();
		if(count>1)
			return false;
		
		return true;
	}
	
	@Override
	public long countAccountByCustomer(Transaction transaction) {
		Query query= new Query( 
				Criteria.where("customer.numDoc").is(transaction.getCustomer().getNumDoc())
				.andOperator(
						Criteria.where("product.idProduct").is(transaction.getProduct().getIdProduct()),
						Criteria.where("product.typeProduct").is(productTypePasivo)//Pasivo
						)
				);
		
		return mongoTemplate.find(query,Transaction.class).count().share().block();
	}

	@Override
	public long countDebtExpiredByCustomer(String numDoc) {
		Query query= new Query( 
				Criteria.where("customer.numDoc").is(numDoc)
				.andOperator(
						Criteria.where("status").is(1),
						Criteria.where("closedAccount").is(false),//no cerrada
						Criteria.where("debtStatus").is("vencida"),//vencida
						Criteria.where("product.typeProduct").is(productTypeActivo)
						)
				);
		return mongoTemplate.find(query,Transaction.class).count().share().block();
	}

	@Override
	public Mono<Transaction> findByNumAccount(String numAcc) {
		return transactionRep.findByNumAccount(numAcc);
	}

	@Override
	public Mono<Transaction> updateBalance(String numAcc, Double balance, String oper) {
		return transactionRep.findByNumAccount(numAcc)
				.doOnNext(e-> e.setBalance( oper.equals("+")?e.getBalance()+balance: e.getBalance()-balance))
				.flatMap(transactionRep::save);
	}

	@Override
	public Flux<ResumeDTO> updateBalanceAccountsByCardDebit(String cardDebit, Double balance) {
		//buscamos la cuenta principal asociada al debito
		Mono<Transaction> transaction= debitAssociationService.findAccountMainByCardDebit(cardDebit)
				.flatMap(e-> transactionRep.findByNumAccount(e.getNumAccAsoc())
						.doOnNext(t-> t.setBalance( t.getBalance() - balance )) );
		//se analiza la cuenta principal si es sufiente caso cantrario se aplicara a las demas cuentas bancarias
		Transaction tr = transaction.share().block();
		double b= tr.getBalance();
		if(b<0) {
			tr.setBalance(0);
			//analizar la demas cuentas
			this.updateBalanceAccounts(cardDebit, b);
		}
		transactionRep.save(tr).subscribe();

		return resumenService.resumeByCustomer( tr.getCustomer().getNumDoc() );
	}
	
	private void updateBalanceAccounts(String cardDebit, Double balance){
		Double b= balance;
		Flux<Transaction> lista=debitAssociationService.listAccountsByCardDebit(cardDebit)
				.flatMap(e-> transactionRep.findByNumAccount(e.getNumAccAsoc() ));

		List<Transaction> listaTransactions= lista.collectList().share().block();
		for (Transaction t : listaTransactions) {
			System.out.println(" balance accountsss "+ t.getBalance());
			b = t.getBalance() - (Math.abs(b));
			if(b < 0 ) {
				t.setBalance(0);
				transactionRep.save(t).subscribe();
			}else {
				t.setBalance(b);
				transactionRep.save(t).subscribe();
				break;
			}
		}		
	}

	@Override
	public Mono<BalanceMainDTO> getBalanceAccountMain(String cardDebit) {
		
		 Transaction transaction= debitAssociationService.findAccountMainByCardDebit(cardDebit)
				.flatMap(e-> transactionRep.findByNumAccount(e.getNumAccAsoc())).share().block();
		 
		 BalanceMainDTO b=new BalanceMainDTO();
		 b.setId(transaction.getId());
		 b.setBalance(transaction.getBalance());
		 b.setStatus(transaction.getStatus());
		 return Mono.just(b);
	}
	
	
}
