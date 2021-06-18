package com.everis.accountservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.accountservice.entity.Account;

public interface IAccountRepository extends ReactiveMongoRepository<Account, String>{

}
