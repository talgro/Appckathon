package com.appckathon.appckathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity {

    //data members
    private Button registerButton;
    EditText firstNameTxt;
    EditText lastNameTxt;
    EditText emailTxt;
    EditText passwordTxt;
    FirebaseAuth _FBauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        emailTxt = (EditText) findViewById(R.id.text_box_email);
        passwordTxt = (EditText) findViewById(R.id.text_box_password);
        firstNameTxt = (EditText) findViewById(R.id.text_box_first_name);
        lastNameTxt = (EditText) findViewById(R.id.text_box_last_name);
        registerButton = (Button) findViewById(R.id.button_register);

        //firebase init
        _FBauth = FirebaseAuth.getInstance();//TODO: (tal) is this necessary?
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


    }

    private void register() {

        final String email = emailTxt.getText().toString();
        final String password = passwordTxt.getText().toString();
        final String firstName = firstNameTxt.getText().toString();
        final String lastName = lastNameTxt.getText().toString();

        //TODO: (tal) add user to firebase and show this if failed registration
        boolean failed = false;
        if (failed)
            Toast.makeText(RegisterPage.this, "Registration failed.", Toast.LENGTH_SHORT).show();
        //

        //once succesfully added user toast + move to home page
        Toast.makeText(RegisterPage.this, "Registration successful! ", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterPage.this, HomePage.class));
    }

}
