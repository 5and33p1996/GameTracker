package com.example.gametracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DisplayTeamActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private int team_id;
    private Team team;
    private EditText[] player_fields;
    private EditText team_name_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_team);

        linearLayout = findViewById(R.id.linear_layout);
        team_id = getIntent().getIntExtra("TEAM_ID",0);
        team = DiskIO.getInstance().getTeamAt(team_id);
        team_name_field = findViewById(R.id.team_name);
    }

    @Override
    protected void onResume(){
        super.onResume();
        player_fields = new EditText[team.getNumPlayers()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        team_name_field.setText(team.getName());

        for(int i = 0;i < team.getNumPlayers();i++){
            player_fields[i] = new EditText(this);
            player_fields[i].setText(team.getPlayerNameAt(i));
            linearLayout.addView(player_fields[i],layoutParams);
        }
    }

    public void updateTeam(View view){
        String temp;

        for(int i = 0;i < player_fields.length;i++){
            temp = player_fields[i].getText().toString();
            if(temp.length() == 0){
                Toast.makeText(this,"Fields cannot be empty!",Toast.LENGTH_SHORT).show();
                return;
            }
            team.setPlayetNameAt(temp,i);
        }

        EditText team_name = findViewById(R.id.team_name);
        String name = team_name.getText().toString();
        if(name.length() == 0){
            Toast.makeText(this,"Fileds cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        team.setName(name);

        DiskIO.getInstance().updateTeam(team,team_id);
        finish();
    }
}
