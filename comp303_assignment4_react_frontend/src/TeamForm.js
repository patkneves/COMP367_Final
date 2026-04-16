import React from 'react';
import './App.css';

class TeamForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      teamName: '',
      cityName: '',
      foundedYear: '',
      coachName: ''
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount() {
    if (this.props.teamId) {
      fetch('http://localhost:8085/api/team/' + this.props.teamId)
        .then(response => response.json())
        .then(data => {
          this.setState({
            teamName: data.teamName,
            cityName: data.cityName,
            foundedYear: data.foundedYear,
            coachName: data.coachName
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
    let url = 'http://localhost:8085/api/team';
    let method = 'POST';

    if (this.props.teamId) {
      url = 'http://localhost:8085/api/team/' + this.props.teamId;
      method = 'PUT';
    }

    fetch(url, {
      method: method,
      body: JSON.stringify({
        teamName: this.state.teamName,
        cityName: this.state.cityName,
        foundedYear: parseInt(this.state.foundedYear),
        coachName: this.state.coachName
      }),
      headers: { "Content-type": "application/json; charset=UTF-8" }
    }).then(response => {
      if (response.ok) {
        if (this.props.teamId) {
          alert("Team updated successfully");
        } else {
          alert("Team added successfully");
        }
        if (this.props.onSuccess) {
          this.props.onSuccess();
        }
      }
    });
  }

  render() {
    let heading = "Add Team";
    if (this.props.teamId) {
      heading = "Edit Team";
    }

    return (
      <div id="container">
        <h2>{heading}</h2>
        <form onSubmit={this.handleSubmit}>
          <p>
            <label>Team Name:</label>
            <input 
              type="text" 
              name="teamName" 
              value={this.state.teamName} 
              onChange={this.handleChange} 
            />
          </p>
          <p>
            <label>City:</label>
            <input 
              type="text" 
              name="cityName" 
              value={this.state.cityName} 
              onChange={this.handleChange} 
            />
          </p>
          <p>
            <label>Founded Year:</label>
            <input 
              type="number" 
              name="foundedYear" 
              value={this.state.foundedYear} 
              onChange={this.handleChange} 
            />
          </p>
          <p>
            <label>Coach Name:</label>
            <input 
              type="text" 
              name="coachName" 
              value={this.state.coachName} 
              onChange={this.handleChange} 
            />
          </p>
          <p>
            <input type="submit" value="Submit" />
          </p>
        </form>
      </div>
    );
  }
}

export default TeamForm;