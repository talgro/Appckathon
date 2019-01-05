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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ManagedHackathonsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ManagedHackathonsFragment extends Fragment {
    private SearchHackathonsFragment.OnFragmentInteractionListener mListener;
    FirebaseDatabase _db;

    public ManagedHackathonsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_managed_hackthons, container, false);
        final ListView hackathons_list = (ListView) view.findViewById(R.id.managed_hackathons_list);
        viewHackathonsList(view, hackathons_list);
        setListenerForHackathonsSelections(hackathons_list);
        return view;
    }

    private void viewHackathonsList(View view, final ListView hackathons_list) {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        _db.getReference("users").child(userID).child("hackathons").child("managing").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> hackathonsNames = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String currHackathonName = postSnapshot.getKey();
                    hackathonsNames.add(currHackathonName);
                }
                fillList(hackathons_list, hackathonsNames);
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

    private void setListenerForHackathonsSelections(final ListView hackathons_list){
        //set listner
        hackathons_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String chosenHackathonName = hackathons_list.getItemAtPosition(position).toString();
                FirebaseDatabase.getInstance().getReference("hackathons").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            if (postSnapshot.child("name").getValue().toString() == chosenHackathonName){
                                Hackathon selectedHackathonName = HackathonFromSnapshot(postSnapshot);
                                Intent intent = new Intent(getContext(), ManagedHackathonPage.class);
                                intent.putExtra("hackathon", selectedHackathonName);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
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
        if (context instanceof SearchHackathonsFragment.OnFragmentInteractionListener) {
            mListener = (SearchHackathonsFragment.OnFragmentInteractionListener) context;
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
