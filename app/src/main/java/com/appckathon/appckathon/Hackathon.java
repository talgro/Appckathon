package com.appckathon.appckathon;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents a single Hackathon
 */
public class Hackathon implements Serializable {

    private String _managerName;
    private String _name;
    private ArrayList<Team> _teams;
    private String _description;
    private Date _startDate;
    private Date _endDate;

    public Hackathon(String name, Date startDate, Date endDate, String description, String managerName) {
        _name = name;
        _teams = new ArrayList<Team>();
        _description = description;
        _managerName = managerName;
        _startDate = startDate;
        _endDate = endDate;
    }

    /**
     * Date must be in the form of "dd/MM/yyyy HH:mm:ss"
     */

    //setters
    public void setName(String name) {
        _name = name;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public void setStartDate(Date startDate) {
        _startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        _endDate = endDate;
    }

//    private Date parseStringToDate(String date) {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date rtn = new Date();
//        try {
//            rtn = formatter.parse(date);
//        } catch (Exception ex) {
//        }
//        return rtn;
//    }

    //----getters--------
    public String getName() {
        return _name;
    }

    public ArrayList<Team> getTeams() {
        return _teams;
    }

    public ArrayList<String> getTeamNames() {
        ArrayList<String> teamNames = new ArrayList<String>();
        for (Team t: _teams) {
            teamNames.add(t.getTeamName());
        }
        return teamNames;
    }

    public String getManagername() {
        return _managerName;
    }

    public String getDescription() {
        return _description;
    }

    public Date getStartDate() {
        return _startDate;
    }

    public Date getEndDate() {
        return _endDate;
    }
}
