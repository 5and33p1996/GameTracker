package com.example.gametracker;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {

    private int id;
    private int num_players;
    private int matchesPlayed;

    private String name;
    private ArrayList<Match> matches;
    private ArrayList<Player> players;

    Team(int id, int num_players, String name){
        this.id = id;
        this.num_players = num_players;
        matchesPlayed = 0;
        this.name = name;
        matches = new ArrayList<>();
        players = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public int getNumPlayers(){
        return num_players;
    }

    public void setName(String name){
        this.name = name;
    }

    public void addPlayers(String[] player_array){
        for(int i = 0;i < player_array.length;i++){
            Player temp = new Player(player_array[i]);
            players.add(temp);
        }
    }

    public Player getPlayerAt(int index){
        return players.get(index);
    }

    public Match getMatchAt(int index){

        if(index >= matches.size()){
            for(int i = matches.size();i <= index;i++){
                matches.add(new Match());
            }
        }
        return matches.get(index);
    }

    public int getMatchesLength(){
        return matches.size();
    }

    public String getPlayerNameAt(int index){
        return players.get(index).getName();
    }

    public void setPlayetNameAt(String name,int index){
        players.get(index).setName(name);
    }

    public void incrementMatchesPlayed(){
        matchesPlayed++;
    }

    public int getMatchesPlayed(){
        return matchesPlayed;
    }
}
