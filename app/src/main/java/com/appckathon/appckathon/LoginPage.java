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
import com.google.firebase.database.FirebaseDatabase;

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

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginPage.this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginPage.this, HomePage.class);
                            User user = getUserFromFB(email);
                            intent.putExtra("User", user);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(LoginPage.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //TODO: Tal implement. get user object from fireBase by its email.
    private User getUserFromFB(String email) {
        return null;
    }
}
