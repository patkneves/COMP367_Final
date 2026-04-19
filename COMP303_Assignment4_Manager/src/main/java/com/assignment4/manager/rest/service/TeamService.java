// Patrick Neves - 301367126 COMP303 Assignment 4, December 4, 2025
package com.assignment4.manager.rest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.assignment4.manager.model.dto.TeamStats;
import com.assignment4.manager.model.entity.Team;
import com.assignment4.manager.model.repository.PlayerRepository;
import com.assignment4.manager.model.repository.TeamRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
@AllArgsConstructor
public class TeamService {

	@Autowired
	private TeamRepository teams;

	@Autowired
	private PlayerRepository players;
	
	public Flux<Team> getAll() {
		return teams.findAll().switchIfEmpty(Flux.empty());
	}
	// Get a team by their unique ID
	// Throws 404 NOT FOUND if team does not exist
	public Mono<Team> getById(final ObjectId teamId) {
		return teams.findById(teamId)
		.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find team with id: " + teamId)));
	}

	public Mono<TeamStats> getTeamStats(final ObjectId teamId) {
		return getById(teamId)
			.flatMap(team -> players.findByTeamId(teamId)
				.collectList()
				.map(playerList -> {
					int count = playerList.size();
					double avgAge = playerList.stream()
						.mapToInt(p -> p.getAge())
						.average()
						.orElse(0.0);
					var positions = playerList.stream()
						.collect(Collectors.groupingBy(
							p -> p.getPosition(),
							Collectors.counting()));
					return TeamStats.builder()
						.teamId(team.getTeamId())
						.teamName(team.getTeamName())
						.cityName(team.getCityName())
						.coachName(team.getCoachName())
						.playerCount(count)
						.averagePlayerAge(avgAge)
						.positionBreakdown(positions)
						.build();
				}));
	}
	
	public Mono<Team> update(final ObjectId teamId, final Team team) {
		team.setTeamId(teamId);
		return teams.save(team);
	}
	
	public Mono<Team> save(final Team team) {
		return teams.save(team);
	}
	
	public Flux<Team> saveAll(final List<Team> teams) {
		return this.teams.saveAll(teams);
	}
	
	public Mono<Team> delete(final ObjectId id) {
		return getById(id)
			.flatMap(teamToDelete -> teams.delete(teamToDelete)
				.then(Mono.just(teamToDelete)));
	}
}
