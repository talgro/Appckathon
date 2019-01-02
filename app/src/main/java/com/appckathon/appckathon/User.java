package com.appckathon.appckathon;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    String _name;
    String _email;
    ArrayList<String> _managedHackathons;
    ArrayList<String> _paticipatingHackathons;

    public User(String _name, String _email) {
        this._name = _name;
        this._email = _email;
        this._managedHackathons = new ArrayList<String>();
        this._paticipatingHackathons = new ArrayList<String>();
    }

    public void addMananagedHackathon(String hackathonName) {
        _managedHackathons.add(hackathonName);
    }

    public void addParticipatingHackathon(String hackathonName) {
        _paticipatingHackathons.add(hackathonName);
    }

    public boolean isManager(String hackathonName) {
        return _managedHackathons.contains(hackathonName);
    }

    public boolean isParticipent(String hackathonName) {
        return _paticipatingHackathons.contains(hackathonName);
    }

    public String getName() {
        return _name;
    }

    public String getEmail() {
        return _email;
    }
}
