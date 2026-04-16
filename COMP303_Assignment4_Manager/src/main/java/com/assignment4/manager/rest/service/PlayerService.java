// Patrick Neves - 301367126 COMP303 Assignment 4, December 4, 2025
package com.assignment4.manager.rest.service;

import java.util.List;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.assignment4.manager.model.entity.Player;
import com.assignment4.manager.model.repository.PlayerRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
@AllArgsConstructor
public class PlayerService {

	@Autowired
	private PlayerRepository players;
	
	public Flux<Player> getAll() {
		return players.findAll().switchIfEmpty(Flux.empty());
	}
	// Get a player by their unique ID
	// Throws 404 NOT FOUND if player does not exist
	public Mono<Player> getById(final ObjectId playerId) {
		return players.findById(playerId)
		.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find player with id: " + playerId)));
	}
	
	public Flux<Player> getByTeam(final ObjectId teamId) {
		return players.findByTeamId(teamId).switchIfEmpty(Flux.empty());
	}
	
	public Mono<Player> update(final ObjectId playerId, final Player player) {
		player.setPlayerId(playerId);
		return players.save(player);
	}
	
	public Mono<Player> save(final Player player) {
		return players.save(player);
	}
	// Useful as a manager to be able to add a list of players all at once. Also good for testing.
	public Flux<Player> saveAll(final List<Player> players) {
		return this.players.saveAll(players);
	}
	
	public Mono<Player> delete(final ObjectId id) {
		final Mono<Player> dbMatch = getById(id);
		if (Objects.isNull(dbMatch)) {
			return Mono.empty();
		}
		return getById(id)
				.switchIfEmpty(Mono.empty())
				.filter(Objects::nonNull)
				.flatMap(matchToBeDeleted -> players.delete(matchToBeDeleted).then(Mono.just(matchToBeDeleted)));
	}
}
