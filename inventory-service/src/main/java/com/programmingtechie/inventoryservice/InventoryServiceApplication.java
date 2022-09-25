package com.programmingtechie.inventoryservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	@Value( "${spring.application.name}" )
	private static String applicationName;
	public static void main(String[] args) {

		System.out.println(applicationName);
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

}
