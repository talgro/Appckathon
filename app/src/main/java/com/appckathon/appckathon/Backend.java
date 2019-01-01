package com.appckathon.appckathon;

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

    class GetHackathons_EL implements ValueEventListener {
        private ArrayList<Hackathon> hackathons = new ArrayList<>();

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
    class SignUpListener implements OnCompleteListener{
        boolean ans = false;
        @Override
        public void onComplete(@NonNull Task task) {
            if (task.isSuccessful()) {
                ans = true;
            }
        }

        public boolean signupSucceed(){
            return ans;
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
        _FBdb = db;
        _FBauth = auth;
//        _hackathonsRef = _FBdb.getReference("hackathons");
//        _findEL = new Find_EL();
        //set "offline mode"
        _FBdb.setPersistenceEnabled(true);
    }

    public static void setInstance(FirebaseAuth auth, FirebaseDatabase db){
        if (_singleton == null){
            _singleton = new Backend(auth, db);
        }
    }

    public static Backend getInstance(){
        return _singleton;
    }

    //methods
    public void signin (final LoginPage loginPage, String email, String password){
        OnCompleteListener listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success
                    loginPage.signinAnswer(true);
                }
                else{
                    // Sign in failes
                    loginPage.signinAnswer(false);
                }
            }
        };

        _FBauth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(loginPage, listener);
    }

    private void setLoginSuccessful(boolean val){

    }

    public boolean signup(String email, String password){
        SignUpListener listener = new SignUpListener();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
        return listener.signupSucceed();
    }

    public void createNewHackathon(int ownerID, Hackathon hackathon) throws HackathonAlreadyExists{
        //check if name is not already used
        if (!hackathonAlreadyExists(hackathon)){
            String key = _hackathonsRef.push().getKey();
            _hackathonsRef.child(key).setValue(hackathon);
        }
        else{
            throw new HackathonAlreadyExists();
        }
    }

    private boolean hackathonAlreadyExists(Hackathon hackathon){
        Query checkIfNameExists = _hackathonsRef.orderByChild("name").equalTo(hackathon.getName());
        Find_EL findEL = new Find_EL();
        checkIfNameExists.addChildEventListener(findEL);
        return findEL.isHackathonAlreadyExists();
    }

    private class HackathonAlreadyExists extends Exception{
        HackathonAlreadyExists(){
            super("Hackathon with the same name exists.");
        }
    }

    /////////////////

    public ArrayList<Hackathon> getListOfHackathons() throws NoHackathonsInDB{
        GetHackathons_EL handler = new GetHackathons_EL();
        Query checkIfNameExists = _hackathonsRef.orderByChild("name");
        checkIfNameExists.addValueEventListener(handler);

        ArrayList<Hackathon> hackathonsList = handler.getHackathons();
        if (hackathonsList == null){
            throw new NoHackathonsInDB();
        }
        return handler.getHackathons();
    }

    private class NoHackathonsInDB extends Exception{
        NoHackathonsInDB(){
            super("There are no hackathons in DB.");
        }
    }

    public void signupToHackathon(int userID, Hackathon hackathon){
        //TODO: implement
    }


}
