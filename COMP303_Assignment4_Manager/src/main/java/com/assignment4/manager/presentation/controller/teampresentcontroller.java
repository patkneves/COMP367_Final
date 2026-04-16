package com.assignment4.manager.presentation.controller;

import com.assignment4.manager.model.entity.Team;
import com.assignment4.manager.model.repository.TeamRepository;

import jakarta.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class teampresentcontroller {

    private final TeamRepository teamRepository;

    public teampresentcontroller(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping("/teams")
    public Mono<String> listTeams(Model model) {
        Flux<Team> teams = teamRepository.findAll();
        model.addAttribute("teams", teams);
        return Mono.just("team");
    }

    @GetMapping("/teams/new")
    public Mono<String> newTeamForm(Model model) {
        model.addAttribute("team", new Team());
        return Mono.just("team-view");
    }

    @PostMapping("/teams")
    public Mono<String> addTeam(@ModelAttribute @Valid Team team) {
        return teamRepository.save(team).then(Mono.just("redirect:/teams"));
    }

    @GetMapping("/teams/edit/{id}")
    public Mono<String> editTeamForm(@PathVariable("id") String id, Model model) {
        ObjectId objectId = new ObjectId(id); // convert String to ObjectId
        return teamRepository.findById(objectId)
                .flatMap(team -> {
                    model.addAttribute("team", team);
                    return Mono.just("team-view");
                });
    }

    @GetMapping("/teams/delete/{id}")
    public Mono<String> deleteTeam(@PathVariable("id") String id) {
        ObjectId objectId = new ObjectId(id); // convert String to ObjectId
        return teamRepository.deleteById(objectId)
                .then(Mono.just("redirect:/teams"));
    }

}
