package com.appckathon.appckathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SignedHackathonPage extends AppCompatActivity {

    private TextView hackathonName;
    private TextView managerName;
    private TextView description;
    private ListView teams_list;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_hackathon_page);
        Hackathon hackthon = (Hackathon) getIntent().getSerializableExtra("hackathon");
        hackathonName = (TextView) findViewById(R.id.hackathon_name);
        managerName = (TextView) findViewById(R.id.hackathon_manager_name);
        description = (TextView) findViewById(R.id.hackathon_description);
        image = (ImageView) findViewById(R.id.hackathon_image);

        //set text for these TextViews
        hackathonName.setText(hackthon.getName());
        managerName.setText(hackthon.getManagername());
        description.setText(hackthon.getDescription());

        String[] teams_arr = new String[hackthon.getTeamNames().size()];
        teams_arr = hackthon.getTeamNames().toArray(teams_arr);
        teams_list = (ListView) findViewById(R.id.hackathon_teams_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teams_arr);
        teams_list.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignedHackathonPage.this, HomePage.class));
        finish();
    }

}
