package com.everis.productservice.service;

import com.everis.productservice.entity.Product;

import reactor.core.publisher.Mono;

public interface IProductService extends IMaintenanceService<Product> {

	
	public Mono<Product> getProductByIdProduct(String idProduct);
}
