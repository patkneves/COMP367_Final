// Patrick Neves - 301367126 COMP303 Assignment 4, December 4, 2025
package com.assignment4.Gateway.client;

import com.assignment4.Gateway.model.Player;

import reactor.core.publisher.Flux;

public class PlayerClient extends BaseCrudClient<Player> {
	public PlayerClient(String baseUrl) {
		super(baseUrl, Player.class);
	}

	// Get all players that belong to a specific team by team ID
	public Flux<Player> getPlayersByTeam(String teamId) {
		return client.get()
				.uri("/team/{teamId}", teamId)
				.retrieve()
				.bodyToFlux(Player.class);
	}
}
