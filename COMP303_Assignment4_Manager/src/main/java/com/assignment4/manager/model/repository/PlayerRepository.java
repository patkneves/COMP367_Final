// Patrick Neves - 301367126 COMP303 Assignment 4, December 4, 2025
package com.assignment4.manager.model.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.assignment4.manager.model.entity.Player;

import reactor.core.publisher.Flux;

public interface PlayerRepository extends ReactiveMongoRepository<Player, ObjectId> {
//	https://docs.spring.io/spring-data/mongodb/reference/mongodb/repositories/query-methods.html
//	Apparently you can define a query like this and Spring just knows.
	public Flux<Player> findByTeamId(ObjectId teamId);
}
