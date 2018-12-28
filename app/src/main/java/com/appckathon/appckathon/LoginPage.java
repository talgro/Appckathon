package com.appckathon.appckathon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    class LoginListener implements OnCompleteListener {
        boolean ans = false;
        @Override
        public void onComplete(@NonNull Task task) {
            if (task.isSuccessful()) {
                ans = true;
            }
        }

        public boolean loginSucceed(){
            return ans;
        }
    }

    //data members

    private Button signInButton;
    EditText emailTxt;
    EditText passwordTxt;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        auth = FirebaseAuth.getInstance();
        emailTxt = (EditText)findViewById(R.id.text_box_email);
        passwordTxt = (EditText)findViewById(R.id.text_box_password);
        signInButton = (Button) findViewById(R.id.button_login);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
    }

    private void signin (){
        String email = emailTxt.getText().toString();
        String pass = passwordTxt.getText().toString();
        LoginListener listener = new LoginListener();
        if (auth.getCurrentUser() == null) {
            auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(listener);
            if (listener.loginSucceed()){
                startActivity(new Intent(this, HomePage.class));
            }
            else{
                System.out.println("failed");
                //TODO: Pop a message to the user : login failed
            }
        }
        System.out.println("not null");
    }

}
