package com.assignment4.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Comp303Assignment4EurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(Comp303Assignment4EurekaApplication.class, args);
	}

}
