package com.example.gametracker;

import java.io.Serializable;
import java.util.ArrayList;

public class StrawberrySwing implements Serializable {

    private ArrayList<Team> teams;

    StrawberrySwing(){
        teams = new ArrayList<>();
    }

    public ArrayList<Team> getTeams(){
        return teams;
    }

    public Team getTeamAt(int index){
        return teams.get(index);
    }
}
