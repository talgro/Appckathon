package com.appckathon.appckathon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity {

    //data members
    private Button signInButton;
    EditText emailTxt;
    EditText passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //activity init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        emailTxt = (EditText)findViewById(R.id.text_box_email);
        passwordTxt = (EditText)findViewById(R.id.text_box_password);
        signInButton = (Button) findViewById(R.id.button_login);

        //firebase init
        Backend.setInstance (FirebaseAuth.getInstance(), FirebaseDatabase.getInstance());

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trySignin();
            }
        });
    }

    private void trySignin(){
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        Backend.getInstance().signin(this, email, password);
    }

    public void signinAnswer(boolean answer){
        if (true == answer){
            Toast.makeText(this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginPage.this, HomePage.class));
        }
        else{
            Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
        }
    }
}
