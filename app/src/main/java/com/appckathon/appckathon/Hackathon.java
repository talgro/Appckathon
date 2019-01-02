package com.appckathon.appckathon;

import java.util.ArrayList;

/**
 * This class represents a single Hackathon
 */
public class Hackathon {

    private static int _idCreator = 0;
    private int _id;
    private String _name;
    private ArrayList<Team> _teams;
    private String _managerName;
    private String _description;

    public Hackathon(String _name, ArrayList<Team> teams, String _managerName, String description) {
        this._id = _idCreator++;
        this._name = _name;
        this._teams = teams;
        this._managerName = _managerName;
        _description = description;
    }

    public Hackathon(String _name, String _managerName, String description) {
        this._id = _idCreator++;
        this._name = _name;
        this._teams = new ArrayList<Team>();
        this._managerName = _managerName;
        _description = description;
    }

    public void addTeam(Team team) {
        _teams.add(team);
    }

    public void removeTeam(String teamName) {
        for (Team team: _teams) {
            if(team.getTeamName().equals(teamName))
                _teams.remove(team);
        }
    }

    public void changeName(String name) {
        _name = name;
    }

    public void changeDescription(String description) {
        _description = description;
    }


    //----getters--------
    public String getName() {
        return _name;
    }

    public int getID() {
        return _id;
    }

    public ArrayList<Team> getTeams() {
        return _teams;
    }

    public String getManagerName() {
        return _managerName;
    }

    public String getDescription() {
        return _description;
    }
}
