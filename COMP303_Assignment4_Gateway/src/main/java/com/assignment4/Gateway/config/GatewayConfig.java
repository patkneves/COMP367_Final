// Patrick Neves - 301367126 COMP303 Assignment 4, December 4, 2025
package com.assignment4.Gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assignment4.Gateway.client.PlayerClient;
import com.assignment4.Gateway.client.TeamClient;

// Configure WebClient beans for Player and Team controllers
@Configuration
public class GatewayConfig {

	@Bean
	PlayerClient playerWebClient() {
		PlayerClient client = new PlayerClient("http://localhost:8084/db/player");
		return client;
	}

	@Bean
	TeamClient teamWebClient() {
		TeamClient client = new TeamClient("http://localhost:8084/db/team");
		return client;
	}
}
