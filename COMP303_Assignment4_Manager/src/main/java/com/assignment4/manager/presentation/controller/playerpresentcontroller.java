package com.assignment4.manager.presentation.controller;

import com.assignment4.manager.model.entity.Player;
import com.assignment4.manager.model.repository.PlayerRepository;
import com.assignment4.manager.model.repository.TeamRepository;

import jakarta.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class playerpresentcontroller {

    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;

    public playerpresentcontroller(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping("/players")
    public Mono<String> listPlayers(Model model) {
        Flux<Player> players = playerRepository.findAll();
        model.addAttribute("players", players);
        return Mono.just("player");
    }

    @GetMapping("/players/new")
    public Mono<String> newPlayerForm(Model model) {
        model.addAttribute("player", new Player());
        model.addAttribute("teams", teamRepository.findAll());
        return Mono.just("player-view");
    }

    @PostMapping("/players")
    public Mono<String> addPlayer(@ModelAttribute @Valid Player player) {
        return playerRepository.save(player)
                .then(Mono.just("redirect:/players"));
    }

    @GetMapping("/players/edit/{id}")
    public Mono<String> editPlayerForm(@PathVariable("id") String id, Model model) {
        ObjectId objectId = new ObjectId(id);
        return playerRepository.findById(objectId)
                .flatMap(player -> {
                    model.addAttribute("player", player);
                    model.addAttribute("teams", teamRepository.findAll());
                    return Mono.just("player-view");
                });
    }

    @GetMapping("/players/delete/{id}")
    public Mono<String> deletePlayer(@PathVariable("id") String id) {
        ObjectId objectId = new ObjectId(id);
        return playerRepository.deleteById(objectId)
                .then(Mono.just("redirect:/players"));
    }

    @GetMapping("/players/team/{id}")
    public Mono<String> listPlayersByTeam(@PathVariable("id") ObjectId teamId, Model model) {
        Flux<Player> players = playerRepository.findByTeamId(teamId);
        model.addAttribute("players", players);
        return Mono.just("player");
    }
}
