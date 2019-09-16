package com.example.gametracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayStats extends AppCompatActivity {

    private static class Info{
        int wins;
        int loss;
        int draws;
        int totalScore;
        int goalDiff;
    }

    private TextView[] teamviews;
    private int num_teams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_stats);

        num_teams = DiskIO.getInstance().getNumTeams();
        teamviews = new TextView[num_teams];
    }

    private Info getInfo(int team_index){

        int teamScore = 0,oppScore = 0;

        Info info = new Info();
        info.goalDiff = 0;

        Team team = DiskIO.getInstance().getTeamAt(team_index);
        int num_matches = team.getMatchesLength();
        for(int i = 0;i < num_matches;i++){

            if(!team.getMatchAt(i).IsMatchDone()){
                continue;
            }

            teamScore = team.getMatchAt(i).getTeamscore();
            oppScore = team.getMatchAt(i).getOppScore();

            if(teamScore > oppScore){
                info.wins++;
            }
            else if(oppScore > teamScore){
                info.loss++;
            }
            else{
                info.draws++;
            }
            info.goalDiff+= (teamScore-oppScore);
        }
        info.totalScore = (info.wins * Constants.WIN_SCORE) + (info.loss * Constants.LOSS_SCORE) + (info.draws * Constants.DRAW_SCORE);
        return info;
    }

    @Override
    protected void onResume(){
        super.onResume();

        String capName;
        int wins,loss,draws,goalDiff,matchesPlayed,totalScore;
        Team team;
        Info info;

        LinearLayout linearLayout = findViewById(R.id.linear_layout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for(int i = 0;i < num_teams;i++){
            team = DiskIO.getInstance().getTeamAt(i);
            teamviews[i] = new TextView(this);
            info = getInfo(i);
            wins = info.wins;
            loss = info.loss;
            draws = info.draws;
            matchesPlayed = team.getMatchesPlayed();
            totalScore = info.totalScore;
            goalDiff = info.goalDiff;
            capName = new StringBuilder().append(team.getPlayerNameAt(0)).append("'s team").toString();
            String diff = new StringBuilder().append("Total Score :").append(totalScore).append("\tGoal Difference :").append(goalDiff).append("\tGames Played :").append(matchesPlayed).toString();
            String win = new StringBuilder().append("Wins :").append(wins).append("\tLosses :").append(loss).append("\tDraws").append(draws).toString();
            String res = new StringBuilder().append(capName).append("\n").append(diff).append("\n").append(win).append("\n\n").toString();
            teamviews[i].setText(res);
            teamviews[i].setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            linearLayout.addView(teamviews[i],layoutParams);
        }
    }
}
