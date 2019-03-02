package com.ifood.nutritional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"com","ifood","nutritional"})
@EnableEurekaClient
@EnableFeignClients
@EnableJpaRepositories
public class DeliveryOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryOrderServiceApplication.class, args);
	}
    
}
