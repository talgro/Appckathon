package com.appckathon.appckathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        String name = hackathonName.getText().toString();
        int startDay = Integer.parseInt(start_day.getText().toString());
        int startMonth = Integer.parseInt(start_month.getText().toString());
        int startYear = Integer.parseInt(start_year.getText().toString());
        int endDay = Integer.parseInt(end_day.getText().toString());
        int endMonth = Integer.parseInt(end_month.getText().toString());
        int endYear = Integer.parseInt(end_year.getText().toString());
        String desc = description.getText().toString();

        Date startDate = new Date(startYear, startMonth, startDay);//TODO: (daniel)check if works ok
        Date endDate = new Date(endYear, endMonth, endYear);//TODO: (daniel)check if works ok

        String managerName = "";//TODO: (tal) get current user name from db
        Hackathon hackathonToAdd = new Hackathon(name, startDate, endDate, desc, managerName);

        //TODO: (tal) add "hackathonToAdd" to FB

        //toast message if added hackathon succesfully
        Toast.makeText(CreateHackathon.this, "Hackathon added successfully! ", Toast.LENGTH_SHORT).show();
        //navigate back to home page
        startActivity(new Intent(CreateHackathon.this, HomePage.class));
    }
}
