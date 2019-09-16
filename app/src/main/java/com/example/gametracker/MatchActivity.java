package com.example.gametracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MatchActivity extends AppCompatActivity {

    private int team_id;
    private int opponent_id;
    private int team_num_players;
    private int opp_num_players;

    private boolean shouldIncrementMatchCount;

    private Team team;
    private Team opp_team;

    private EditText[] teamScores;
    private EditText[] opponentScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        team_id = getIntent().getIntExtra("TEAM_ID",0);
        opponent_id = getIntent().getIntExtra("OPPONENT_ID",1);
    }

    @Override
    protected void onResume(){
        super.onResume();

        TextView team_name = findViewById(R.id.team_text_view);
        TextView opp_name = findViewById(R.id.opponent_text_view);

        team = DiskIO.getInstance().getTeamAt(team_id);
        opp_team = DiskIO.getInstance().getTeamAt(opponent_id);

        String temp = team.getPlayerNameAt(0) + "'s team";
        team_name.setText(temp);
        temp = opp_team.getPlayerNameAt(0) + "'s team";
        opp_name.setText(temp);

        generateViews();
        shouldIncrementMatchCount = true;

        if(opponent_id < team.getMatchesLength()){
            if(team.getMatchAt(opponent_id).IsMatchDone()) {
                fillViews();
                shouldIncrementMatchCount = false;
            }
        }

    }

    private void generateViews(){

        LinearLayout team_layout = findViewById(R.id.team_layout);
        LinearLayout opponent_layout = findViewById(R.id.opponent_layout);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        team_num_players = team.getNumPlayers();
        opp_num_players = opp_team.getNumPlayers();
        teamScores = new EditText[team_num_players];
        opponentScores = new EditText[opp_num_players];
        TextView[] teamViews = new TextView[team_num_players];
        TextView[] oppViews = new TextView[opp_num_players];

        for(int i = 0;i < team_num_players;i++){
            teamScores[i] = new EditText(this);
            teamViews[i] = new TextView(this);
            teamScores[i].setHint("Score");
            teamViews[i].setText(team.getPlayerNameAt(i));
            teamViews[i].setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            team_layout.addView(teamViews[i],layoutParams);
            team_layout.addView(teamScores[i],layoutParams);
        }

        for(int i = 0;i < opp_num_players;i++){
            opponentScores[i] = new EditText(this);
            oppViews[i] = new TextView(this);
            opponentScores[i].setHint("Score");
            oppViews[i].setText(opp_team.getPlayerNameAt(i));
            oppViews[i].setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            opponent_layout.addView(oppViews[i],layoutParams);
            opponent_layout.addView(opponentScores[i],layoutParams);
        }
    }

    private void fillViews(){

        for(int i = 0;i < team_num_players;i++){
            int score = team.getMatchAt(opponent_id).getPlayerScoreAt(i);
            String temp = Integer.toString(score);
            teamScores[i].setText(temp);
        }

        for(int i = 0;i < opp_num_players;i++){
            int score = opp_team.getMatchAt(team_id).getPlayerScoreAt(i);
            String temp = Integer.toString(score);
            opponentScores[i].setText(temp);
        }
    }

    public void saveMatch(View view){

        int team_score=0,opp_score=0;
        int score;

        for(int i = 0;i < teamScores.length;i++){
            String temp = teamScores[i].getText().toString();
            try {
                score = temp.length() == 0 ? 0 : Integer.parseInt(temp);
            }catch (NumberFormatException nfe){
                Toast.makeText(this,"Invalid Input!",Toast.LENGTH_SHORT).show();
                return;
            }
            team_score+=score;
            team.getMatchAt(opponent_id).setPlayerScoreAt(i,score);
        }

        for(int i = 0;i < opponentScores.length;i++){
            String temp = opponentScores[i].getText().toString();
            try {
                score = temp.length() == 0 ? 0 : Integer.parseInt(temp);
            }catch(NumberFormatException nfe){
                Toast.makeText(this,"Invalid Input",Toast.LENGTH_SHORT).show();
                return;
            }
            opp_score+=score;
            opp_team.getMatchAt(team_id).setPlayerScoreAt(i,score);
        }

        team.getMatchAt(opponent_id).setMatchDone(true);
        opp_team.getMatchAt(team_id).setMatchDone(true);

        team.getMatchAt(opponent_id).setOppScore(opp_score);
        opp_team.getMatchAt(team_id).setOppScore(team_score);

        team.getMatchAt(opponent_id).setTeamScore(team_score);
        opp_team.getMatchAt(team_id).setTeamScore(opp_score);

        if(shouldIncrementMatchCount) {
            team.incrementMatchesPlayed();
            opp_team.incrementMatchesPlayed();
        }

        DiskIO.getInstance().writeToFile();

        finish();
    }
}
