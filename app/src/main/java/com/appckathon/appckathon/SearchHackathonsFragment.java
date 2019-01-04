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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
        View view = inflater.inflate(R.layout.fragment_managed_hackthons, container, false);
        viewHackathonsList(view);
        return view;
    }

    private void viewHackathonsList(View view) {
        final ListView hackathons_list = (ListView) view.findViewById(R.id.managed_hackathons_list);
        _db.getReference("hackathons").orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Hackathon> hackathons = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //dates handling
                    Date startDate = handleDate(postSnapshot.child("startDate"));
                    Date endDate = handleDate(postSnapshot.child("endDate"));
                    //create hackathon
                    Hackathon currHackathon = postSnapshot.getValue(Hackathon.class);
                    currHackathon.changeStartDate(startDate);
                    currHackathon.changeEndDate(endDate);
                    hackathons.add(currHackathon);
                }
                getNamesFromHackathonsList(hackathons, hackathons_list);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, hackathons);
                hackathons_list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getNamesFromHackathonsList(final ArrayList<Hackathon> hackathons, ListView hackathons_list) {
        //get list of hackathons names
        ArrayList<String> hackathonNames = new ArrayList<>();
        for (Hackathon h : hackathons) {
            hackathonNames.add(h.getName());
        }
        //view names
        fillList(hackathons_list, hackathonNames);
        //set listner
        hackathons_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get relevant hackathon
                final Hackathon hackathon = hackathons.get(position);
                //move to relevant page by user type
                considerUserType(hackathons);
            }
        });
    }

    private void considerUserType(final Hackathon hackathon){
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //manager
        FirebaseDatabase.getInstance().getReference("users").child(userID)
                .child("hackathons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //managing
                if (dataSnapshot.child("managing").hasChild(hackathon.getName())) {
                    Intent intent = new Intent(getContext(), ManagedHackathonPage.class);
                    intent.putExtra("hackathon", hackathon);
                    startActivity(intent);
                }
                //participating
                else if (dataSnapshot.child("participating").hasChild(hackathon.getName())) {
                    Intent intent = new Intent(getContext(), SignedHackathonPage.class);
                    intent.putExtra("hackathon", hackathon);
                    startActivity(intent);
                }
                //unsigned
                else {
                    Intent intent = new Intent(getContext(), UnsignedHackathonPage.class);
                    intent.putExtra("hackathon", hackathon);
                    startActivity(intent);
                }
                getActivity().finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fillList(final ListView hackathons_list, List<String> hackathonNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, hackathonNames);
        hackathons_list.setAdapter(adapter);
    }

    public Date handleDate(DataSnapshot dateDS){
        return null;
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
