// Patrick Neves - 301367126 COMP303 Assignment 4, December 4, 2025
package com.assignment4.manager.model.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.assignment4.manager.model.entity.Team;

public interface TeamRepository extends ReactiveMongoRepository<Team, ObjectId> {

}
