package com.appckathon.appckathon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UnsignedHackathonPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsigned_hackathon_page);
        //TODO: insert actual list of teams
        String[] teams_arr = {"team1", "team2", "team3", "team4"};
        ListView teams_list = (ListView)findViewById(R.id.hackathon_teams_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teams_arr);
        teams_list.setAdapter(adapter);
    }
}
