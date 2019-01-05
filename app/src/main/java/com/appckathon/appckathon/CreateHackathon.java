package com.appckathon.appckathon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class CreateHackathon extends AppCompatActivity {

    private EditText hackathonName;
    private EditText start_day;
    private EditText start_month;
    private EditText start_year;
    private EditText end_day;
    private EditText end_month;
    private EditText end_year;
    private EditText description;
    private Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hackathon);
        buildTextObjects();
        createBtn = (Button) findViewById(R.id.button_create_hackathon);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHackathon();
            }
        });

    }

    private void buildTextObjects() {
        hackathonName = (EditText) findViewById(R.id.fill_hackathon_name);
        start_day = (EditText) findViewById(R.id.insert_start_day);
        start_month = (EditText) findViewById(R.id.insert_start_month);
        start_year = (EditText) findViewById(R.id.insert_start_year);
        end_day = (EditText) findViewById(R.id.insert_end_day);
        end_month = (EditText) findViewById(R.id.insert_end_month);
        end_year = (EditText) findViewById(R.id.insert_end_year);
        description = (EditText) findViewById(R.id.fill_hackathon_description);
    }

    private void createHackathon() {
        //hackathon properties
        String name = hackathonName.getText().toString();
        int startDay = Integer.parseInt(start_day.getText().toString());
        int startMonth = Integer.parseInt(start_month.getText().toString());
        int startYear = Integer.parseInt(start_year.getText().toString());
        int endDay = Integer.parseInt(end_day.getText().toString());
        int endMonth = Integer.parseInt(end_month.getText().toString());
        int endYear = Integer.parseInt(end_year.getText().toString());
        String desc = description.getText().toString();
        Date startDate = new Date(startYear, startMonth, startDay);//TODO: (daniel)check if works ok
        Date endDate = new Date(endYear, endMonth, endDay);//TODO: (daniel)check if works ok
        //create new hackathon and insert to DB
        String managerName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        Hackathon hackathonToAdd = new Hackathon(name, startDate, endDate, desc, managerName);
        checkHackathonNotExists(hackathonToAdd);
    }

    private void checkHackathonNotExists(final Hackathon hackathonToAdd) {
        FirebaseDatabase.getInstance().getReference("hackathons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(hackathonToAdd.getName())) {
                    Toast.makeText(CreateHackathon.this, "Hackathon with that name already exists.", Toast.LENGTH_SHORT).show();
                } else {
                    hackathonToDB(hackathonToAdd);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void hackathonToDB(final Hackathon hackathon){
        //add hackathon to hackathons table
        FirebaseDatabase.getInstance().getReference("hackathons").child(hackathon.getName()).setValue(hackathon);
        //add hackathon to managed by current user
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users").child(userID).child("hackathons").child("managing").child(hackathon.getName()).setValue("");
        //pop a relevant message
        Toast.makeText(CreateHackathon.this, "Hackathon added successfully! ", Toast.LENGTH_SHORT).show();
        //navigate back to home page
        startActivity(new Intent(CreateHackathon.this, HomePage.class));
    }
}
