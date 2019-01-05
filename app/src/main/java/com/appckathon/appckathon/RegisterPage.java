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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterPage extends AppCompatActivity {

    //data members
    private Button registerButton;
    EditText firstNameTxt;
    EditText lastNameTxt;
    EditText emailTxt;
    EditText passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        emailTxt = (EditText) findViewById(R.id.text_box_email);
        passwordTxt = (EditText) findViewById(R.id.text_box_password);
        firstNameTxt = (EditText) findViewById(R.id.text_box_first_name);
        lastNameTxt = (EditText) findViewById(R.id.text_box_last_name);
        registerButton = (Button) findViewById(R.id.button_register);
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
        final String fullName = firstNameTxt.getText().toString() + " " + lastNameTxt.getText().toString();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        db.getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(fullName)) {
                    Toast.makeText(RegisterPage.this, "Users with same name is already exists.", Toast.LENGTH_SHORT).show();
                } else {
                    signupNewUser(email, password, fullName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private  void signupNewUser(final String email, final String password, final String fullName){
        //user does not exist. create it
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //add user's display name
                            FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(fullName).build();
                            currUser.updateProfile(profileUpdates);
                            //load home page
                            Toast.makeText(RegisterPage.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterPage.this, HomePage.class));
                        }
                        else {
                            Toast.makeText(RegisterPage.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
