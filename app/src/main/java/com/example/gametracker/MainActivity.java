package com.example.gametracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int num_teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DiskIO.createInstance(getApplicationContext().getFilesDir().getAbsolutePath());

        DiskIO.getInstance().InitDiskIO();
    }

    @Override
    protected void onResume(){
        super.onResume();

        Spinner dropdown = findViewById(R.id.league_list);
        num_teams = DiskIO.getInstance().getNumTeams();
        String[] list = new String[num_teams + 2];
        list[0] = " ";

        if(num_teams == 0)
        {
            list[1] = "New Team";
        }
        else {
            for (int i = 1; i <= num_teams; i++) {
                list[i] = "Team "+i;
            }
            list[num_teams+1] = "New Team";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id){
        if(position == num_teams + 1){
            Intent intent = new Intent(this, CreateTeam.class);
            startActivity(intent);
        }

        else if(position != 0){
            Intent intent = new Intent(this,AfterTeamSelect.class);
            intent.putExtra("TEAM_ID",position-1);
            startActivity(intent);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0){

    }

    public void viewStats(View view){
        Intent intent = new Intent(this,DisplayStats.class);
        startActivity(intent);
    }

    public void deleteData(View view){
        getApplicationContext().deleteFile(DiskIO.FILE_NAME);
        this.recreate();
    }
}
