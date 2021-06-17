package com.everis.productservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.everis.productservice.entity.Product;

@Repository
public interface IProductRepository extends ReactiveMongoRepository<Product, String> {

}
