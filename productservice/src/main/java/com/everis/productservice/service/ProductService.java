package com.everis.productservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.productservice.entity.Product;
import com.everis.productservice.repository.IProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

	@Autowired
	private IProductRepository productRep;
	
	public Flux<Product> getTransactions(){
		return productRep.findAll();
	}
	
	public Mono<Product> getTransacion(String id){
		return productRep.findById(id);
	}
	
	public Mono<Product> saveTransaction(Product transaction){
		return productRep.insert(transaction);
	}
	
	public Mono<Product> updateTransaction(Product transaction){
		return productRep.save(transaction);
	}

	public Mono<Void> deleteTransaction(String id){
		return productRep.deleteById(id);
	}
}
