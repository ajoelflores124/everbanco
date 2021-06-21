package com.everis.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.productservice.entity.Product;
import com.everis.productservice.service.ProductService;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController 
@RequestMapping(path = "/product")

public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping
	public Flux<Product> getTransactions(){
		return productService.getTransactions();
	}
	
	@GetMapping("/{id}")
	public Mono<Product> getTransaction(@PathVariable String id){
		return productService.getTransacion(id);
	}
	
	@PostMapping
	public Mono<Product> saveProduct(@RequestBody Product transactionMono){
		return productService.saveTransaction(transactionMono);
	}
	
	@PutMapping
	public Mono<Product> updateTransaction(@RequestBody Product transactionMono){
		return productService.updateTransaction(transactionMono);
	}
	
	@DeleteMapping("/{id}")
	public Mono<Void> deleteTransaction(@PathVariable String id){
		return productService.deleteTransaction(id);
	}
	
	@GetMapping("/find-by-product/{idProduct}")
	public Mono<Product> getTransacionByIdProduct(@PathVariable String idProduct){
		return productService.getTransacionByIdProduct(idProduct);
	}
}
