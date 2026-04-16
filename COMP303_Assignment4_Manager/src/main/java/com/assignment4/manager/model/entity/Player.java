// Patrick Neves - 301367126 COMP303 Assignment 4, December 4, 2025
package com.assignment4.manager.model.entity;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "players")
@ToString
public class Player {
    @Id
    private ObjectId playerId;
	@NotNull
	@NotBlank
    private String firstName;
	@NotNull
	@NotBlank
    private String lastName;
	@NotNull
    private String position;
    private int jerseyNumber;
	@NotNull
    private String dateOfBirth;
	@NotNull
    private ObjectId teamId;
    
    public Player() {
    	super();
    }
    
	public String getPlayerId() {
		return playerId == null ? "" : playerId.toHexString();
	}
	
	public void setPlayerId(ObjectId playerId) {
		this.playerId = playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = new ObjectId(playerId == null ? "" : playerId);
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public int getJerseyNumber() {
		return jerseyNumber;
	}
	
	public void setJerseyNumber(int jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	// Calculate age based on dateOfBirth
	public int getAge() {
		int age;
		LocalDate now = LocalDate.now();
		LocalDate bdate;
		try {
			bdate = LocalDate.parse(dateOfBirth);
		} catch (DateTimeParseException e) {
			// Log error and return 0 if date parsing fails (invalid format)
			System.err.println("Could not parse date "
			 + e.getParsedString()
			  + " for player "
			   + firstName + " " + lastName
				+ " ID: " + getPlayerId());
			return 0;
		}
		age = Period.between(bdate, now).getYears();
		return age;
	}

	public String getTeamId() {
		return teamId == null ? "" : teamId.toHexString();
	}

	public void setTeamId(ObjectId teamId) {
		this.teamId = teamId;
	}
    
    public void setTeamId(@NotBlank String teamId) {
    	this.teamId = new ObjectId(teamId);
    }
}
