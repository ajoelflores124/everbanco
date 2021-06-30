package com.everis.transactionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TransactionserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionserviceApplication.class, args);
	}

}
