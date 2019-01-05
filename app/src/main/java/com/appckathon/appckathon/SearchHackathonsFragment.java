package com.appckathon.appckathon;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.Date;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchHackathonsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchHackathonsFragment newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchHackathonsFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    FirebaseDatabase _db;

    public SearchHackathonsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_search_hackathons, container, false);
        final ListView hackathons_list = (ListView) view.findViewById(R.id.search_hackathons_list);
        viewHackathonsList(view, hackathons_list);
        setListenerForHackathonsSelections(hackathons_list);
        return view;
    }

    private void viewHackathonsList(View view, final ListView hackathons_list) {
        _db.getReference("hackathons").orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Hackathon> hackathons = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //create hackathon
                    Hackathon currHackathon = HackathonFromSnapshot(postSnapshot);
                    hackathons.add(currHackathon);
                }
                getNamesFromHackathonsList(hackathons, hackathons_list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private Hackathon HackathonFromSnapshot (DataSnapshot ds){
        //get properties
        String description = ds.child("description").getValue().toString();
        String managerName = ds.child("managername").getValue().toString();
        String name = ds.child("name").getValue().toString();
        Date start = handleDate(ds.child("startDate"));
        Date end = handleDate(ds.child("endDate"));
        //build and return hackathon
        return new Hackathon(name, start, end, description, managerName);
    }

    private void getNamesFromHackathonsList(final ArrayList<Hackathon> hackathons, ListView hackathons_list) {
        //get list of hackathons names
        ArrayList<String> hackathonNames = new ArrayList<>();
        for (Hackathon h : hackathons) {
            hackathonNames.add(h.getName());
        }
        //view names
        fillList(hackathons_list, hackathonNames);
    }

    private void setListenerForHackathonsSelections(final ListView hackathons_list){
        //set listner
        hackathons_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get relevant hackathon
                String selectedHackathonName =(hackathons_list.getItemAtPosition(position).toString());
                //move to relevant page by user type
                considerUserType(selectedHackathonName);
            }
        });
    }

    private void considerUserType(final String hackathon){
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //manager
        DatabaseReference relatedHackathons = FirebaseDatabase.getInstance().getReference("users").child(userID)
                .child("hackathons");
        relatedHackathons.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent = null;
                if (dataSnapshot.child("managing").hasChild(hackathon)) {
                    intent = new Intent(getContext(), ManagedHackathonPage.class);
                }
                else if (dataSnapshot.child("participating").hasChild(hackathon)) {
                    intent = new Intent(getContext(), SignedHackathonPage.class);
                }
                else{
                    intent = new Intent(getContext(), UnsignedHackathonPage.class);
                }
                intent.putExtra("hackathon", hackathon);
                //TODO: (daniel) app crashes when try to start activity
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void fillList(final ListView hackathons_list, List<String> hackathonNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, hackathonNames);
        hackathons_list.setAdapter(adapter);
    }

    public Date handleDate(DataSnapshot dateDS){
        //get properties
        int day = Integer.parseInt(dateDS.child("date").getValue().toString());
        int month = Integer.parseInt(dateDS.child("month").getValue().toString());
        int year = 2000 + Integer.parseInt(dateDS.child("year").getValue().toString());
        //build and return date
        return new Date(year, month, day);
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
