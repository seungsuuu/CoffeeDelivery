package com.sparta.coffeedeliveryproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CoffeeDeliveryProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeDeliveryProjectApplication.class, args);
	}

}
