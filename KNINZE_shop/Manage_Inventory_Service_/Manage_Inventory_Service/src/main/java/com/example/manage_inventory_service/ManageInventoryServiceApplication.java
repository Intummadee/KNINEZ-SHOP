package com.example.manage_inventory_service;

import com.example.core.AxonXstreamConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;


@EnableDiscoveryClient
@SpringBootApplication
@Import({AxonXstreamConfig.class})
public class ManageInventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageInventoryServiceApplication.class, args);
		System.out.println("Manage Inventory Service is running");
	}

}
