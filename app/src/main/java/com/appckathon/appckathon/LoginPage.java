package com.appckathon.appckathon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {

    //data members
    private Button signInButton;
    EditText emailTxt;
    EditText passwordTxt;
    FirebaseAuth _FBauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //activity init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        emailTxt = (EditText)findViewById(R.id.text_box_email);
        passwordTxt = (EditText)findViewById(R.id.text_box_password);
        signInButton = (Button) findViewById(R.id.button_login);

        //firebase init
        _FBauth = FirebaseAuth.getInstance();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
    }

    private void signin(){
        final String email = emailTxt.getText().toString();
        final String password = passwordTxt.getText().toString();

        _FBauth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            moveUserToHomePage(task.getResult().getUser().getUid());
                        }
                        else{
                            Toast.makeText(LoginPage.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void moveUserToHomePage(String userID){
        //view message
        Toast.makeText(LoginPage.this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
        //start home page activity with current User object
        FirebaseDatabase.getInstance().getReference("users").child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //get user from DB and start new activity
                        User currUser = dataSnapshot.getValue(User.class);
                        Intent intent = new Intent(LoginPage.this, HomePage.class);
                        //TODO: Daniel, why do we need to pass the User object? (Tal)
                        intent.putExtra("User", currUser);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
