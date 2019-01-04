package com.appckathon.appckathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UnsignedHackathonPage extends AppCompatActivity {

    //TODO: need to pass hackathon object from previouse page
    private Hackathon hackthon = (Hackathon) getIntent().getSerializableExtra("hackathon");

    private TextView hackathonName;
    private TextView managerName;
    private TextView description;
    private ListView teams_list;
    private Button joinHackathon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managed_hackathon_page);
        hackathonName = (TextView) findViewById(R.id.hackathon_name);
        managerName = (TextView) findViewById(R.id.hackathon_manager_name);
        description = (TextView) findViewById(R.id.hackathon_description);

        //set text for these TextViews
        hackathonName.setText(hackthon.getName());
        managerName.setText(hackthon.getManagerName());
        description.setText(hackthon.getDescription());

        String[] teams_arr = (String[]) hackthon.getTeams().toArray();
        teams_list = (ListView) findViewById(R.id.hackathon_teams_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teams_arr);
        teams_list.setAdapter(adapter);

        joinHackathon = (Button) findViewById(R.id.join_hackathon);
        joinHackathon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinHackathon();
            }
        });

    }

    private void joinHackathon() {
        //TODO: (tal) add "hackthhon" object to current user hackathon list(participating list)

        //once succesfully added user toast + move to home page
        Toast.makeText(UnsignedHackathonPage.this, "joined hackathon successfully! ", Toast.LENGTH_SHORT).show();
        //navigate back to home page
        startActivity(new Intent(UnsignedHackathonPage.this, HomePage.class));
    }
}
