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
import org.springframework.web.bind.annotation.RestController;
import com.assignment4.manager.model.dto.TeamStats;
import com.assignment4.manager.model.entity.Team;
import com.assignment4.manager.rest.service.TeamService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/db/team")
@AllArgsConstructor
@RestController
public class TeamController {
	
	@Autowired
	private TeamService teams;
	
	@GetMapping
	public Flux<Team> getAll() {
		System.out.println("Getting all team info");
		return teams.getAll();
	}
	
	@GetMapping("/")
	public Flux<Team> getAllSlash() {
		return getAll();
	}
	
	@GetMapping("{id}")
	public Mono<Team> getById(@PathVariable("id") final ObjectId id) {
		System.out.println("Getting team info for id=" + id.toHexString());
		return teams.getById(id);
	}

	@GetMapping("{id}/stats")
	public Mono<TeamStats> getTeamStats(@PathVariable("id") final ObjectId id) {
		return teams.getTeamStats(id);
	}
	
	@PutMapping("{id}")
	public Mono<Team> updateById(@PathVariable("id") final ObjectId id, @RequestBody @Valid final Team team) {
		System.out.println("Updating team info id=" + id.toHexString());
		return teams.update(id, team);
	}
	
	@PostMapping
	public Mono<Team> save(@RequestBody @Valid final Team team) {
		System.out.println(String.format("Adding team Info %s", team.getTeamName()));
		return teams.save(team);
	}

	@PostMapping("all")
	public Flux<Team> saveAll(@RequestBody @Valid final List<Team> teams) {
		System.out.println(String.format("Adding team info for %d teams", teams.size()));
		return this.teams.saveAll(teams);
	}
	
	@DeleteMapping("{id}")
	public Mono<Team> deleteById(@PathVariable("id") final ObjectId id) {
		System.out.println("Deleting team id=" + id.toHexString());
		return teams.delete(id);
	}
}
