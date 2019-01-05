package com.appckathon.appckathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UnsignedHackathonPage extends AppCompatActivity {

    private TextView hackathonName;
    private TextView managerName;
    private TextView description;
    private ListView teams_list;
    private Button joinHackathon;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsigned_hackathon_page);
        final Hackathon hackathon = (Hackathon) getIntent().getSerializableExtra("hackathon");
        hackathonName = (TextView) findViewById(R.id.hackathon_name);
        managerName = (TextView) findViewById(R.id.hackathon_manager_name);
        description = (TextView) findViewById(R.id.hackathon_description);
        image = (ImageView) findViewById(R.id.hackathon_image);
        joinHackathon = (Button) findViewById(R.id.join_hackathon);


        //set text for these TextViews
        hackathonName.setText(hackathon.getName());
        managerName.setText(hackathon.getManagername());
        description.setText(hackathon.getDescription());

        String[] teams_arr = new String[hackathon.getTeamNames().size()];
        teams_arr = hackathon.getTeamNames().toArray(teams_arr);
        teams_list = (ListView) findViewById(R.id.hackathon_teams_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teams_arr);
        teams_list.setAdapter(adapter);


        joinHackathon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinHackathon(hackathon);
            }
        });

    }

    private void joinHackathon(Hackathon hackathon) {

        String currUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users")
                .child(currUserID).child("hackathons").child("participating").child(hackathon.getName()).setValue(hackathon);
        //once succesfully added user toast + move to home page
        Toast.makeText(UnsignedHackathonPage.this, "joined hackathon successfully! ", Toast.LENGTH_SHORT).show();
        //navigate back to home page
        startActivity(new Intent(UnsignedHackathonPage.this, HomePage.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UnsignedHackathonPage.this, HomePage.class));
        finish();
    }
}
