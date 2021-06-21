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

import com.everis.transactionservice.entity.Customer;
import com.everis.transactionservice.entity.Product;
import com.everis.transactionservice.entity.Representative;
import com.everis.transactionservice.entity.Transaction;
import com.everis.transactionservice.exception.EntityNotFoundException;
import com.everis.transactionservice.repository.ITransactionRepository;
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
	
	@Autowired
	private ITransactionRepository transactionRep;
	private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public TransactionServiceImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
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
		Customer customer = this.getCustomerByNumDoc(transaction.getCustomer().getNumDoc());
		Product product= this.getProductByIdProduct(transaction.getProduct().getIdProduct());
		transaction.setCustomer(customer);
		transaction.setProduct(product);
		System.out.println(" type_customer=> " + customerTypePersonal);
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
	public Customer getCustomerByNumDoc(String numDoc) {
		WebClient webClient = WebClient.create(urlCustomerService);
		System.out.println("num_doc=>"+ numDoc);
	    return  webClient.get()
	    		.uri("/customer/find-by-numdoc/{numdoc}",numDoc)
	    		.retrieve()
	    		.bodyToMono(Customer.class)
	    		.switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
	    		.share().block();
	}

	@Override
	public Product getProductByIdProduct(String idProduct) {
		WebClient webClient = WebClient.create(urlProductService);
		System.out.println("idProduct=>"+ idProduct);
	    return  webClient.get()
	    		.uri("/product/find-by-product/{idProduct}",idProduct)
	    		.retrieve()
	    		.bodyToMono(Product.class)
	    		.switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
	    		.share().block();
	}
	
	@Override
	public Representative[] getRepresentativesByNumDocRep(Representative[] representatives) {
		List<Representative> listaRep= Arrays.asList(representatives);
		List<Representative> listaRepNueva= new ArrayList<>();
		listaRepNueva = listaRep.stream().map(r-> getDataRepresentative(r)).collect(Collectors.toList());
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
	public Representative getDataRepresentative(Representative rep) {
		WebClient webClient = WebClient.create(urlRepresentativeService);
		Representative represetante=  webClient.get()
	    		.uri("/representative/find-by-numdoc/{numDocRep}",rep.getNumDocRep())
	    		.retrieve()
	    		.bodyToMono(Representative.class)
	    		.switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
	    		.share().block();
		represetante.setTypeRep(rep.getTypeRep());
		
		return represetante;
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
	
	
	
}
