package com.appckathon.appckathon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    }
}
