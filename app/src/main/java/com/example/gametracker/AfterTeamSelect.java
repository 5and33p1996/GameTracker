package com.example.gametracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AfterTeamSelect extends AppCompatActivity {

    private Spinner team_list;
    private int team_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_team_select);
        team_list = findViewById(R.id.team_list);
        team_id = getIntent().getIntExtra("TEAM_ID",0);
    }

    @Override
    protected void onResume(){
        super.onResume();
        String[] drop_list = new String[DiskIO.getInstance().getNumTeams() - 1];
        int count = 0;

        for(int i = 0;i < DiskIO.getInstance().getNumTeams();i++){
            if(i == team_id){
                continue;
            }
            drop_list[count] = i +" .  "+ DiskIO.getInstance().getTeamAt(i).getPlayerNameAt(0) + "'s team";
            count++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,drop_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        team_list.setAdapter(adapter);
    }

    public void startMatch(View view){

        Intent intent = new Intent(this,MatchActivity.class);

        String selection = team_list.getSelectedItem().toString();
        String[] temp = selection.split(" ");
        int opp_id = Integer.parseInt(temp[0]);

        intent.putExtra("TEAM_ID",team_id);
        intent.putExtra("OPPONENT_ID",opp_id);
        startActivity(intent);
    }

    public void displayTeam(View view){

        Intent intent = new Intent(this,DisplayTeamActivity.class);
        intent.putExtra("TEAM_ID",team_id);
        startActivity(intent);
    }
}
