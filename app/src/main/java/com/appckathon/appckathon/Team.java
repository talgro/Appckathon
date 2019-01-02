package com.appckathon.appckathon;

import java.util.ArrayList;

/**
 * This class represents a single team in a Hackathon
 */
public class Team {

    private String _teamName;
    private ArrayList<String> _teamMembers;

    public Team(String _teamName, ArrayList<String> _teamMembers) {
        this._teamName = _teamName;
        this._teamMembers = _teamMembers;
    }

    public Team(String _teamName) {
        this._teamName = _teamName;
        this._teamMembers = new ArrayList<String>();
    }

    public void addTeamMember(String name) {
        _teamMembers.add(name);
    }

    public void removeTeamMember(String name) {
        _teamMembers.remove(name);
    }

    public String getTeamName() {
        return _teamName;
    }

    public ArrayList<String> getTeamMembers() {
        return _teamMembers;
    }
}
