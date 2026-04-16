// Patrick Neves - 301367126 COMP303 Assignment 4, December 4, 2025
package com.assignment4.Gateway.client;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Base CRUD client providing common operations for entities
public abstract class BaseCrudClient<T> {
    // The type of entity this client manages
    private Class<T> type;

    protected WebClient client;

    // Constructor initializing the WebClient with the base URL and entity type
    public BaseCrudClient(String baseUrl, Class<T> clazz) {
        super();
        this.type = clazz;
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<T> getAll() {
        return client.get()
                .retrieve()
                .bodyToFlux(type);
    }

    // Get an entity by its unique ID
    // Throws 404 NOT FOUND if entity does not exist
    public Mono<T> getById(String id) {
        return client.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(c -> c.value() == 404, (r) -> {
                    return Mono
                            .error(new ResponseStatusException(r.statusCode(), "Could not find entity with id: " + id));
                })
                .bodyToMono(type);
    }

    // Create a new entity
    // Throws 400 BAD REQUEST if entity data is invalid
    public Mono<T> create(T entity) {
        return client.post()
                .body(Mono.just(entity), type)
                .retrieve()
                .onStatus(c -> c.value() == 400, r -> {
                    return Mono.error(new ResponseStatusException(r.statusCode(), "Invalid entity data provided"));
                })
                .bodyToMono(type);
    }

    // Update an existing entity by its ID
    // Throws 400 BAD REQUEST if entity data is invalid
    public Mono<T> update(String id, T entity) {
        return client.put()
                .uri("/{id}", id)
                .body(Mono.just(entity), type)
                .retrieve()
                .onStatus(c -> c.value() == 400, r -> {
                    return Mono.error(new ResponseStatusException(r.statusCode(), "Invalid entity data provided"));
                })
                .bodyToMono(type);
    }

    public Mono<Void> delete(String id) {
        return client.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
