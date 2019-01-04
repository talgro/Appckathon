package com.appckathon.appckathon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    //data members
    private Button signInButton;
    EditText emailTxt;
    EditText passwordTxt;
    TextView signUpText;
    FirebaseAuth _FBauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //activity init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        emailTxt = (EditText) findViewById(R.id.text_box_email);
        passwordTxt = (EditText) findViewById(R.id.text_box_password);
        signInButton = (Button) findViewById(R.id.button_login);
        signUpText = (TextView) findViewById(R.id.text_signup);

        //firebase init
        _FBauth = FirebaseAuth.getInstance();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, RegisterPage.class));
            }
        });
    }

    private void signin() {
        final String email = emailTxt.getText().toString();
        final String password = passwordTxt.getText().toString();

        _FBauth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // moveUserToHomePage(task.getResult().getUser().getUid()); TODO: (tal) so we do not need this as we spoke?
                            Toast.makeText(LoginPage.this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginPage.this, HomePage.class));
                        }
                        else {
                            Toast.makeText(LoginPage.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
