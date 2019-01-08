package com.appckathon.appckathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ManagedHackathonPage extends AppCompatActivity {

    private TextView hackathonName;
    private TextView managerName;
    private EditText description;
    private ListView teams_list;
    private ImageView image;
    private ImageButton editDesc;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managed_hackathon_page);
        Hackathon hackthon = (Hackathon) getIntent().getSerializableExtra("hackathon");
        hackathonName = (TextView) findViewById(R.id.hackathon_name);
        managerName = (TextView) findViewById(R.id.hackathon_manager_name);
        description = (EditText) findViewById(R.id.hackathon_description);
        image = (ImageView) findViewById(R.id.hackathon_image);
        editDesc = (ImageButton) findViewById(R.id.button_edit);
        saveBtn = (Button) findViewById(R.id.save_Btn);
        saveBtn.setVisibility(View.GONE);
        //set text for these TextViews
        hackathonName.setText(hackthon.getName());
        managerName.setText(hackthon.getManagername());
        description.setText(hackthon.getDescription());
        description.setEnabled(false);

        String[] teams_arr = new String[hackthon.getTeamNames().size()];
        teams_arr = hackthon.getTeamNames().toArray(teams_arr);
        teams_list = (ListView) findViewById(R.id.hackathon_teams_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teams_arr);
        teams_list.setAdapter(adapter);
        editDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDescription();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        //TODO:(daniel) handle editable fields and change them in FB
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ManagedHackathonPage.this, HomePage.class));
        finish();
    }

    public void editDescription() {
        description.setEnabled(true);
        saveBtn.setVisibility(View.VISIBLE);
    }

    public void save() {
        description.setEnabled(false);
        saveBtn.setVisibility(View.GONE);
    }

}
