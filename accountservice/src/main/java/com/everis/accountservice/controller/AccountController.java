package com.everis.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.accountservice.entity.Account;
import com.everis.accountservice.service.AccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/account")

public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@GetMapping
	public Flux<Account> getAccounts(){
		return accountService.getAccounts();
	}
	
	@GetMapping("/{id}")
	public Mono<Account> getAccount(@PathVariable String id){
		return accountService.getAccount(id);
	}
	
	@PostMapping
	public Mono<Account> saveProduct(@RequestBody Account accountMono){
		return accountService.saveAccount(accountMono);
	}
	
	@PutMapping
	public Mono<Account> updateTransaction(@RequestBody Account accountMono){
		return accountService.updateAccount(accountMono);
	}
		
	@DeleteMapping("/{id}")
	public Mono<Void> deleteTransaction(@PathVariable String id){
		return accountService.deleteAccount(id);
	}
}
