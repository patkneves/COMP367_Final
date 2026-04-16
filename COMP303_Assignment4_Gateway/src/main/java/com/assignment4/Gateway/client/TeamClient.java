// Patrick Neves - 301367126 COMP303 Assignment 4, December 4, 2025
package com.assignment4.Gateway.client;

import com.assignment4.Gateway.model.Team;

public class TeamClient extends BaseCrudClient<Team> {

    public TeamClient(String baseUrl) {
        super(baseUrl, Team.class);
    }
}
