// Patrick Neves - 301367126 COMP303 Assignment 4, December 4, 2025
package com.assignment4.Gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment4.Gateway.client.PlayerClient;
import com.assignment4.Gateway.model.Player;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/player")
@AllArgsConstructor
@RestController
public class PlayerController {

	@Autowired
	private PlayerClient playerClient;

	@GetMapping
	public Flux<Player> getAll() {
		return playerClient.getAll();
	}

	@GetMapping("/team/{teamId}")
	public Flux<Player> getPlayersByTeam(@PathVariable("teamId") String teamId) {
		return playerClient.getPlayersByTeam(teamId);
	}

	@GetMapping("{id}")
	public Mono<Player> getPlayer(@PathVariable("id") String id) {
		return playerClient.getById(id);
	}

	@PostMapping
	public Mono<Player> createPlayer(@RequestBody @Valid Player player) {
		return playerClient.create(player);
	}

	@PutMapping("{id}")
	public Mono<Player> updatePlayer(@PathVariable("id") String id, @RequestBody @Valid Player player) {
		return playerClient.update(id, player);
	}

	@DeleteMapping("{id}")
	public Mono<Void> deletePlayer(@PathVariable("id") String id) {
		return playerClient.delete(id);
	}
}