package com.appckathon.appckathon;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HackthonsManagersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HackthonsManagersFragment extends Fragment {
    private HackthonsManagersFragment.OnFragmentInteractionListener mListener;
    FirebaseDatabase _db;

    public HackthonsManagersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _db = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hackthons_managers, container, false);
        //TODO: insert actual list of hackathon managers

        String[] managers_arr = {"hackathon1", "hackathon2", "hackathon3", "hackathon4"};
        ListView managers_list = (ListView)view.findViewById(R.id.teams_list);
        fillListWithValeusFromDB(managers_list);
        return view;
    }

    //TODO: change this function so it fills the list with managers
    private void fillListWithValeusFromDB(final ListView hackathons_list){
        String currUserMailHash = Integer.toString(FirebaseAuth.getInstance().getCurrentUser().getEmail().hashCode());
        _db.getReference("users").child(currUserMailHash).child("managedHackathons").orderByChild("name").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> hackathons = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Hackathon currHackathon = postSnapshot.getValue(Hackathon.class);
                    hackathons.add(currHackathon.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, hackathons);
                hackathons_list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HackthonsManagersFragment.OnFragmentInteractionListener) {
            mListener = (HackthonsManagersFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
