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

import com.assignment4.Gateway.client.TeamClient;
import com.assignment4.Gateway.model.Team;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/team")
@AllArgsConstructor
@RestController
public class TeamController {

	@Autowired
	private TeamClient teamClient;

	@GetMapping
	public Flux<Team> getAll() {
		return teamClient.getAll();
	}

	@GetMapping("{id}")
	public Mono<Team> getTeam(@PathVariable("id") String id) {
		return teamClient.getById(id);
	}

	@PostMapping
	public Mono<Team> createTeam(@RequestBody @Valid Team team) {
		return teamClient.create(team);
	}

	@PutMapping("{id}")
	public Mono<Team> updateTeam(@PathVariable("id") String id, @RequestBody @Valid Team team) {
		return teamClient.update(id, team);
	}

	@DeleteMapping("{id}")
	public Mono<Void> deleteTeam(@PathVariable("id") String id) {
		return teamClient.delete(id);
	}
}