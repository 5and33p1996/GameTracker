package com.example.gametracker;

import java.io.Serializable;
import java.util.ArrayList;

public class Match implements Serializable {
    private boolean done;
    private int opp_team_id;
    private int team_score;
    private int opp_score;
    private ArrayList<Integer> player_scores;

    Match(){
        done=false;
        opp_team_id = 0;
        team_score = 0;
        team_score = 0;
        opp_score = 0;
        player_scores = new ArrayList<>();
    }

    public boolean IsMatchDone(){
        return done;
    }

    public void setMatchDone(boolean status){
        done = status;
    }

    public int getOppTeamId(){
        return opp_team_id;
    }

    public int getTeamscore(){
        return team_score;
    }

    public int getOppScore(){
        return opp_score;
    }

    public ArrayList<Integer> getPlayerScores(){
        return player_scores;
    }

    public int getPlayerScoreAt(int i){
        return player_scores.get(i);
    }

    public void setOppTeamId(int id){
        opp_team_id = id;
    }

    public void setTeamScore(int score){
        team_score = score;
    }

    public void setOppScore(int score){
        opp_score = score;
    }

    public void setPlayerScoreAt(int i,int score){
        if(i > player_scores.size()){
            for(int j = player_scores.size();j<i;j++){
                player_scores.add(0);
            }
        }
        player_scores.add(i,score);
        team_score+=score;
    }
}
