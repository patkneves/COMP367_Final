import React from 'react';
import './App.css';

class PlayerForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      firstName: '',
      lastName: '',
      position: '',
      jerseyNumber: '',
      dateOfBirth: '',
      teamId: '',
      teams: []
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount() {
    fetch('http://localhost:8085/api/team')
      .then(response => response.json())
      .then(data => {
        this.setState({ teams: data });
      });

    if (this.props.playerId) {
      fetch('http://localhost:8085/api/player/' + this.props.playerId)
        .then(response => response.json())
        .then(data => {
          this.setState({
            firstName: data.firstName,
            lastName: data.lastName,
            position: data.position,
            jerseyNumber: data.jerseyNumber,
            dateOfBirth: data.dateOfBirth,
            teamId: data.teamId
          });
        });
    }
  }

  handleChange(event) {
    const state = this.state;
    state[event.target.name] = event.target.value;
    this.setState(state);
  }

  handleSubmit(event) {
    event.preventDefault();
    let url = 'http://localhost:8085/api/player';
    let method = 'POST';

    if (this.props.playerId) {
      url = 'http://localhost:8085/api/player/' + this.props.playerId;
      method = 'PUT';
    }

    fetch(url, {
      method: method,
      body: JSON.stringify({
        firstName: this.state.firstName,
        lastName: this.state.lastName,
        position: this.state.position,
        jerseyNumber:parseInt(this.state.jerseyNumber),
        dateOfBirth: this.state.dateOfBirth,
        teamId: this.state.teamId
      }),
      headers: { "Content-type": "application/json; charset=UTF-8" }
    }).then(response => {
      if (response.ok) {
        if (this.props.playerId) {
          alert("Player updated successfully");
        } else {
          alert("Player added successfully");
        }
       if (this.props.onSuccess) {
          this.props.onSuccess();
        }
      }
    });
  }

  render() {
    let heading = "Add Player";
    if (this.props.playerId) {
      heading = "Edit Player";
    }

    return (
      <div id="container">
        <h2>{heading}</h2>
        <form onSubmit={this.handleSubmit}>
          <p>
            <label>First Name:</label>
            <input 
              type="text" 
              name="firstName" 
              value={this.state.firstName} 
              onChange={this.handleChange} 
            />
          </p>
          <p>
            <label>Last Name:</label>
            <input 
              type="text" 
              name="lastName" 
              value={this.state.lastName} 
              onChange={this.handleChange} 
            />
          </p>
          <p>
            <label>Position:</label>
            <input 
              type="text" 
              name="position" 
              value={this.state.position} 
              onChange={this.handleChange} 
            />
          </p>
          <p>
            <label>Jersey Number:</label>
            <input 
              type="text" 
              name="jerseyNumber" 
              value={this.state.jerseyNumber} 
              onChange={this.handleChange} 
            />
          </p>
          <p>
            <label>Date of Birth:</label>
            <input 
              type="date" 
              name="dateOfBirth" 
              value={this.state.dateOfBirth} 
              onChange={this.handleChange} 
            />
          </p>
          <p>
            <label>Team:</label>
            <select 
              name="teamId" 
              value={this.state.teamId} 
              onChange={this.handleChange}
            >
              <option value="">Select Team</option>
              {this.state.teams.map(function(team) {
                return (
                  <option key={team.teamId} value={team.teamId}>
                    {team.teamName}
                  </option>
                );
              })}
            </select>
          </p>
          <p>
            <input type="submit" value="Submit" />
          </p>
        </form>
      </div>
    );
  }
}

export default PlayerForm;
