package com.example.gametracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateTeam extends AppCompatActivity {

    private EditText team_name;
    private EditText num_players;
    private String name;
    private String number;

    private EditText[] players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_league);

        team_name = findViewById(R.id.name_of_team);
        num_players = findViewById(R.id.number_of_players);
    }

    public void next(View view){

        int number_of_players;
        try {
            number_of_players = Integer.parseInt(num_players.getText().toString());
        }catch (NumberFormatException nfe){
            Toast.makeText(this,"Invalid Input",Toast.LENGTH_SHORT).show();
            return;
        }

        name = team_name.getText().toString();
        number = num_players.getText().toString();

        if(name.length() == 0 || number.length() == 0){
            Toast.makeText(this,"Fields cannot be empty!",Toast.LENGTH_SHORT).show();
            return;
        }

        LinearLayout linearLayout = findViewById(R.id.linear_layout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        players = new EditText[number_of_players];

        for(int i = 0;i < number_of_players;i++){
            players[i] = new EditText(this);
            if(i==0){
                players[i].setHint("Captain's Name");
            }
            else{
                players[i].setHint("Player's Name");
            }
            linearLayout.addView(players[i],layoutParams);
        }

        Button button = findViewById(R.id.create_team_button);
        button.setEnabled(true);
    }

    public void createTeam(View view){

        String[] player_array = new String[players.length];
        for(int i = 0;i<players.length;i++){
            String temp = players[i].getText().toString();
            if(temp.length() == 0){
                Toast.makeText(this,"Fields cannot be empty!",Toast.LENGTH_SHORT).show();
                return;
            }
            player_array[i] = temp;
        }

        DiskIO.getInstance().createTeam(name, Integer.parseInt(number), player_array);
        finish();
    }
}
