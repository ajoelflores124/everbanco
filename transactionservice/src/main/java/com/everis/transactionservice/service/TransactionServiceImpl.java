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
@PropertySource("classpath:application.properties")
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
		System.out.println(" tipo customer=>"+ customer.getTypeCustomer());
		System.out.println(" tipo product=>"+ product.getTypeProduct());
		transaction.setCustomer(customer);
		transaction.setProduct(product);
		
		if("P".equalsIgnoreCase(customer.getTypeCustomer()) || "Personal".equalsIgnoreCase(customer.getTypeCustomer())) {//Personal
			//Un cliente personal solo puede tener un máximo de una cuenta de ahorro, una cuenta 
			//corriente o cuentas a plazo fijo.
			//Buscar alguna de las cuentas: ahorros, cuenta corriente, plazo fijo del cliente pueda tener
			Query query= new Query( 
					Criteria.where("customer.numDoc").is(transaction.getCustomer().getNumDoc())
					.andOperator(
							Criteria.where("product.idProduct").is(transaction.getProduct().getIdProduct()),
							Criteria.where("product.typeProduct").is("Pasivo")
							)
					);
			
			long countAccounts = mongoTemplate.find(query,Transaction.class).count().share().block();
			System.out.println("count=>"+ countAccounts);
			if(countAccounts==0) {
				transaction.setRepresentatives(this.getRepresentativesByNumDocRep(transaction.getRepresentatives()));
				return transactionRep.insert(transaction);
			}else {
				throw new Exception(msgErrorCuentaClientePersonal+transaction.getProduct().getNameProduct());
			}
					
		}else {//Empresarial
			//Un cliente empresarial no puede tener una cuenta de ahorro o de plazo fijo pero sí 
			//múltiples cuentas corrientes
			if("100".equals(product.getIdProduct()) || "300".equals(product.getIdProduct())) { //ahorros (100) o plazo fijo (300)
				throw new Exception(msgErrorCuentaClienteEmpresarial);
			}else {
				//Validate Representatives
				if("200".equals(product.getIdProduct())){ //Cuenta corriente (bancaria)
					if(!this.validateRepresentatives(transaction.getRepresentatives())) {
						throw new Exception(msgErrorCuentaClienteEmpresarialRep);
					}else {
						//Se guarda los representante
						transaction.setRepresentatives(this.getRepresentativesByNumDocRep(transaction.getRepresentatives()));
					}
				}
				
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
				.filter(r-> r.getTypeRep().equalsIgnoreCase("T") || r.getTypeRep().equalsIgnoreCase("Titular"))
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
	
	
	
	
}
