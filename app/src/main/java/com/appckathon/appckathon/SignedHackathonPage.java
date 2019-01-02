package com.appckathon.appckathon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SignedHackathonPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_hackathon_page);
        //TODO: insert actual list of teams
        String[] teams_arr = {"team1", "team2", "team3", "team4"};
        ListView teams_list = (ListView)findViewById(R.id.hackathon_teams_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teams_arr);
        teams_list.setAdapter(adapter);
    }
}
