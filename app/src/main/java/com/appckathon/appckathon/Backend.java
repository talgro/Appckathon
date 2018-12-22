package com.appckathon.appckathon;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

class Find_EL implements ChildEventListener{
    boolean _ans = false;
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

public class Backend {
    //data member
    private FirebaseDatabase _FBinstance;
    private DatabaseReference _hackathonsRef;
    private Find_EL _findEL;
    private static Backend _singleton = null;

    //singleton
    private Backend(){
        _FBinstance = FirebaseDatabase.getInstance();
        _hackathonsRef = _FBinstance.getReference("hackathons");
        _findEL = new Find_EL();
        //set "offline mode"
        _FBinstance.setPersistenceEnabled(true);
    }

    public static Backend getInstance(){
        if (_singleton == null){
            _singleton = new Backend();
        }
        return _singleton;
    }

    //methods
    public void userSignin (String UN, String PW){
        //TODO: implement
    }

    public void registerNewUser(String UN, String PW){
        //TODO: add more details to the input
        //TODO: implement
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

    public

}
