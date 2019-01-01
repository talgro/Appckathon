package com.appckathon.appckathon;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class Backend {

    class Find_EL implements ChildEventListener{
        private boolean _ans = false;
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            _ans = true;
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            _ans = true;
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

        public boolean isHackathonAlreadyExists(){
            return _ans;
        }
    }
    class Groups_EL implements ValueEventListener {
        private ArrayList<Hackathon> hackathons = new ArrayList<Hackathon>();

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Hackathon currHackathon = postSnapshot.getValue(Hackathon.class);
                hackathons.add(currHackathon);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

        public ArrayList<Hackathon> getHackathons(){
            return hackathons;
        }
    }
    class LoginListener implements OnCompleteListener{
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
    public class FBinit extends Application {

        @Override
        public void onCreate() {
            super.onCreate();
            if (!FirebaseApp.getApps(this).isEmpty()) {
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            }
        }
    }

    //data member
    private FirebaseAuth _FBauth;
    private FirebaseDatabase _FBdb;
    private DatabaseReference _hackathonsRef;
    private Find_EL _findEL;
    private static Backend _singleton = null;

    //singleton
    private Backend(FirebaseAuth auth, FirebaseDatabase db){
        //init main components
        _FBdb = db;
        _FBauth = auth;
        //set "offline mode"
        _FBdb.setPersistenceEnabled(true);
        //init main DB tables
        _hackathonsRef = _FBdb.getReference("hackathons");
    }

    //methods
//
//    public void signup(final SignupPage signupPage, String email, String password){
//        _FBauth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(signupPage, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success
//                            signupPage.signupAnswer(true);
//                        }
//                        else{
//                            // Sign in failes
//                            signupPage.signupAnswer(false);
//                        }
//                    }
//                });
//    }

    public void addHackathonToDB(Hackathon hackathon){
        String key = _hackathonsRef.push().getKey();
        _hackathonsRef.child(key).setValue(hackathon);
    }

    public ArrayList<Hackathon> getExistsingHackathons(final SearchHackathonsFragment client){
        class HackathonsListener implements ValueEventListener{
            ArrayList<Hackathon> _hackathons = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Hackathon currHackathon = postSnapshot.getValue(Hackathon.class);
                    _hackathons.add(currHackathon);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            public ArrayList<Hackathon> getHackathons(){
                return _hackathons;
            }
        }

        HackathonsListener listener = new HackathonsListener();
        _hackathonsRef.orderByChild("name").addValueEventListener(listener);
        return listener.getHackathons();
    }

    public void signupToHackathon(int userID, Hackathon hackathon){
        //TODO: implement
    }


}
