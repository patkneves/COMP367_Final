// Patrick Neves - 301367126 COMP303 Assignment 4, December 4, 2025
package com.assignment4.manager.rest.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.assignment4.manager.model.entity.Player;
import com.assignment4.manager.rest.service.PlayerService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/db/player")
@AllArgsConstructor
@RestController
public class PlayerController {
	
	@Autowired
	private PlayerService players;
	
	@GetMapping
	public Flux<Player> getAll() {
		System.out.println("Getting all player info");
		return players.getAll();
	}
	
	@GetMapping("/")
	public Flux<Player> getAllSlash() {
		return getAll();
	}
	
	@GetMapping("{id}")
	public Mono<Player> getById(@PathVariable("id") final ObjectId id) {
		System.out.println("Getting player info for id=" + id.toHexString());
		return players.getById(id);
	}
	
	@GetMapping("/team/{id}")
	public Flux<Player> getByTeam(@PathVariable("id") final ObjectId id) {
		System.out.println("Getting player info for teamId=" + id.toHexString());
		return players.getByTeam(id);
	}

	@GetMapping("/search")
	public Flux<Player> searchByName(
			@RequestParam(required = false) final String firstName,
			@RequestParam(required = false) final String lastName) {
		return players.searchByName(firstName, lastName);
	}

	@GetMapping("/position/{position}")
	public Flux<Player> getByPosition(@PathVariable("position") final String position) {
		return players.getByPosition(position);
	}

	@GetMapping("/age-range")
	public Flux<Player> getByAgeRange(
			@RequestParam final int minAge,
			@RequestParam final int maxAge) {
		return players.getByAgeRange(minAge, maxAge);
	}
	
	@PutMapping("{id}")
	public Mono<Player> updateById(@PathVariable("id") final ObjectId id, @RequestBody @Valid final Player player) {
		System.out.println("Updating player info id=" + id.toHexString());
		return players.update(id, player);
	}
	
	@PostMapping
	public Mono<Player> save(@RequestBody @Valid final Player player) {
		System.out.println(String.format("Adding player Info %s - %s %s", player.getPlayerId(), player.getFirstName(), player.getLastName()));

		return players.save(player);
	}
	
	@PostMapping("all")
	public Flux<Player> saveAll(@RequestBody @Valid final List<Player> players) {
		System.out.println(String.format("Adding player info for %d players", players.size()));
		return this.players.saveAll(players);
	}

	@DeleteMapping("{id}")
	public Mono<Player> deleteById(@PathVariable("id") final ObjectId id) {
		System.out.println("Deleting player id=" + id.toHexString());
		return players.delete(id);
	}
}
