import React, { useEffect, useState } from "react"
import PlayerForm from './PlayerForm';
import TeamForm from "./TeamForm";

const App = () => {
  const [currentView, setCurrentView] = useState('teams')
  const [teams, setTeams] = useState([])
  const [players, setPlayers] = useState([])
  const [editingPlayerId, setEditingPlayerId] = useState(null)
  const [editingTeamId, setEditingTeamId] = useState(null)
  const fetchPlayersData = ()=> {
    fetch('http://localhost:8085/api/player')
      .then(response =>{return response.json()
      })
      .then(data => {
        console.log("fetched players data:", data);
        setPlayers(data)
      })
      .catch(error => {
        console.error('Error fetching players data:', error)
      }) 
  }
  

  const fetchTeamsData = ()=> {
    fetch('http://localhost:8085/api/team')
      .then(response =>{return response.json()
      })
      .then(data => {
        console.log("fetched teams data:", data);
        setTeams(data)
      })
      .catch(error => {
        console.error('Error fetching teams data:', error)
      })
    }

      const deleteTeam = (teamId) => {
    fetch('http://localhost:8085/api/team/' + teamId, {
      method: 'DELETE'
    })
    .then(response => {
      if (response.ok) {
        fetchTeamsData();
      }
    });
  }

  const deletePlayer = (playerId) => {
    fetch('http://localhost:8085/api/player/' + playerId, {
      method: 'DELETE'
    })
    .then(response => {
      if (response.ok) {
        fetchPlayersData();
      }
    });
  }


      useEffect(() => {
        fetchTeamsData()
      }, []);

      useEffect(() => {
      if (currentView === 'players') fetchPlayersData();
      }, [currentView]); 
      
  return (
    <div>
    <h1 style={{textAlign: 'center', color: '#333', marginBottom: '20px'}}>
  🏒 Team & Player Manager
</h1>
      <nav>
        <button onClick={() => setCurrentView('teams')}>Teams</button>
        <button onClick={() => setCurrentView('players')}>Players</button>
        </nav>
      
      
      
      {currentView === 'teams' && (
        <div>
          <h2>Teams List</h2>
           <button onClick={() => {
          setEditingTeamId(null)
          setCurrentView('addTeam')
           }}>Add Team</button>
          {teams.length> 0 &&(
            <ul>
              {teams.map((team) => (
                <li key={team.teamId}>{team.teamName} - {team.cityName}-{team.foundedYear}-{team.coachName}
                 <button onClick={() => {
                  setEditingTeamId(team.teamId)
                  setCurrentView('editTeam')
                  }} style={{marginLeft: '10px'}}>
              Edit
            </button>
            <button onClick={() => deleteTeam(team.teamId)} style={{marginLeft: '10px'}}>
              Delete
            </button>
                </li>
              ))}
            </ul>
          )}
        </div>
      )}

      {currentView === 'addTeam' && (
          <div>
            <TeamForm 
              teamId={null}
              onSuccess={() => {
                fetchTeamsData();
                setCurrentView('teams');
              }}
            />
            <button onClick={() => setCurrentView('teams')}>Back to Teams List</button>
          </div>
      )}

    {currentView === 'editTeam' && editingTeamId && (
        <div>
          <TeamForm 
            teamId={editingTeamId}
            onSuccess={() => {
              fetchTeamsData();
              setCurrentView('teams');
            }}
          />
          <button onClick={() => setCurrentView('teams')}>Back to Teams List</button>
        </div>
    )}
      
      {currentView === 'players' && (
        <div>
          <h2>Players List</h2>
          {players.length> 0 &&(
            <ul>
              {players.map((player) => (
                <li key={player.playerId}>{player.firstName} - {player.lastName}-{player.position}-{player.jerseyNumber}-{player.dateOfBirth}-{player.teamId}
                <button onClick={() => {
                setEditingPlayerId(player.playerId)
                setCurrentView('addPlayer')
                  }} style={{marginLeft: '10px'}}>
                Edit
              </button>
              <button onClick={() => deletePlayer(player.playerId)} style={{marginLeft: '10px'}}>
                Delete
              </button>
              </li>
              ))}
            </ul>
          )}
          <button onClick={() => {setEditingPlayerId(null)
            setCurrentView('addPlayer')}}>Add Player</button>
        </div>
      )}
      {currentView === 'addPlayer' && (
        <div>
         <PlayerForm 
      playerId={editingPlayerId}
      onSuccess={() => {
        fetchPlayersData();
        setCurrentView('players');
      }}
    />
          <button onClick={() => setCurrentView('players')}>Back to Players List</button>
        </div>
      )}
    </div>
  );
}

export default App;