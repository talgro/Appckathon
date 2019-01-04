package com.appckathon.appckathon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private Date _startDate;
    private Date _endDate;

    public Hackathon(String _name, ArrayList<Team> teams, String _managerName, String description) {
        this._id = _idCreator++;
        this._name = _name;
        this._teams = teams;
        this._managerName = _managerName;
        _description = description;
    }

    public Hackathon(String _name, Date startDate, Date endDate, String description, String managerName) {
        this._id = _idCreator++;
        this._name = _name;
        this._teams = new ArrayList<Team>();
        _description = description;
        _managerName = managerName;
        _startDate = startDate;
        _endDate = endDate;
    }

    /**
     * Date must be in the form of "dd/MM/yyyy HH:mm:ss"
     *
     * @param _name
     * @param _managerName
     * @param description
     * @param startDate
     * @param endDate
     */
    public Hackathon(String _name, String _managerName, String description, String startDate, String endDate) {
        this._id = _idCreator++;
        this._name = _name;
        this._teams = new ArrayList<Team>();
        this._managerName = _managerName;
        this._description = description;
        this._startDate = parseStringToDate(startDate);
        this._endDate = parseStringToDate(endDate);
    }

    public void addTeam(Team team) {
        _teams.add(team);
    }

    public void removeTeam(String teamName) {
        for (Team team : _teams) {
            if (team.getTeamName().equals(teamName))
                _teams.remove(team);
        }
    }

    public void changeName(String name) {
        _name = name;
    }

    public void changeDescription(String description) {
        _description = description;
    }

    public void changeStartDate(Date startDate) {
        _startDate = startDate;
    }

    public void changeEndDate(Date endDate) {
        _endDate = endDate;
    }

    private Date parseStringToDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date rtn = new Date();
        try {
            rtn = formatter.parse(date);
        } catch (Exception ex) {
        }
        return rtn;
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

    public Date getStartDate() {
        return _startDate;
    }

    public Date getEndDate() {
        return _endDate;
    }
}
