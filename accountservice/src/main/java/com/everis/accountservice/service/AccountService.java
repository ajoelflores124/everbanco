package com.everis.accountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.everis.accountservice.entity.Account;
import com.everis.accountservice.exception.EntityNotFoundException;
import com.everis.accountservice.repository.IAccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@PropertySource("classpath:application.properties")
@Service
public class AccountService {
	
	private String msgNotFound;
	
	@Autowired
	private IAccountRepository accountRep;
	
	public Flux<Account> getAccounts(){
		return accountRep.findAll();
	}
	
	public Mono<Account> getAccount(String id){
		return accountRep.findById(id);
	}
	
	public Mono<Account> saveAccount(Account account){
		return accountRep.insert(account);
	}
	
	public Mono<Account> updateAccount(Account account){
		
		return  accountRep.findById(account.getId())
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> accountRep.save(account));
	}

	public Mono<Void> deleteAccount(String id){
		
		return  accountRep.findById(id)
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> accountRep.deleteById(id));
	}
}
