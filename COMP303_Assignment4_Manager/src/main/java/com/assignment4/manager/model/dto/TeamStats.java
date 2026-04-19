package com.assignment4.manager.model.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TeamStats {
    private String teamId;
    private String teamName;
    private String cityName;
    private String coachName;
    private int playerCount;
    private double averagePlayerAge;
    private Map<String, Long> positionBreakdown;
}
