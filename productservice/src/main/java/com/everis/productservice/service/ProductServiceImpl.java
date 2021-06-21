package com.everis.productservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.everis.productservice.entity.Product;
import com.everis.productservice.repository.IProductRepository;
import com.everis.productservice.exception.EntityNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements IProductService {

	/**
	 * 
	 */
	@Value("${msg.error.registro.notfound}")
	private String msgNotFound;
	
	@Autowired
	private IProductRepository productRep;
	

	@Override
	public Flux<Product> findAll() {
		return productRep.findAll();
	}

	@Override
	public Mono<Product> findEntityById(String id) {
		return productRep.findById(id);
	}

	@Override
	public Mono<Product> createEntity(Product entity) throws Exception {
		return productRep.insert(entity);
	}

	@Override
	public Mono<Product> updateEntity(Product entity) {
		return productRep.findById(entity.getId())
		 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
		 .flatMap(item-> productRep.save(entity));
	}

	@Override
	public Mono<Void> deleteEntity(String id) {
		return productRep.findById(id)
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> productRep.deleteById(id));
	}

	@Override
	public Mono<Product> getProductByIdProduct(String idProduct) {
		return productRep.findByIdProduct(idProduct);
	}
}
