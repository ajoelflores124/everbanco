package com.everis.productservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.everis.productservice.entity.Product;

import reactor.core.publisher.Mono;

@Repository
public interface IProductRepository extends ReactiveMongoRepository<Product, String> {
	Mono<Product> findByIdProduct(String idProduct);
}
