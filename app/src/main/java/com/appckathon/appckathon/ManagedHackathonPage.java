package com.appckathon.appckathon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ManagedHackathonPage extends AppCompatActivity {

    //TODO: need to pass hackathon object from previouse page
    private Hackathon hackthon = (Hackathon) getIntent().getSerializableExtra("hackathon");

    private TextView hackathonName;
    private TextView managerName;
    private TextView description;
    private ListView teams_list;
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
        teams_list = (ListView)findViewById(R.id.hackathon_teams_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teams_arr);
        teams_list.setAdapter(adapter);

        //TODO:(daniel) handle editable fields and change them in FB
    }
}
