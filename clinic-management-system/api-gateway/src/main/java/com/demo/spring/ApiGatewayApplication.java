package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args){
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	@Bean
    RestTemplate restTemplateOne() {
        return  new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

}
