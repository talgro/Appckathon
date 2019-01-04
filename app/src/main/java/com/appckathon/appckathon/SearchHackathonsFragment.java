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
        ListView hackathons_list = (ListView) view.findViewById(R.id.managed_hackathons_list);
        final List<String> hackathonNames;
        hackathonNames = Arrays.asList("Hack1", "Hack2", "Hack3");//TODO: (daniel) remove
        // List<String> hackathonNames = getHackathonNamesFromDB(); //TODO: (daniel) fix once tal implements
        fillList(hackathons_list, hackathonNames);

        //set listner
        hackathons_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hackathon hackathon = getHackathonByName(hackathonNames.get(position));
                int pageToOpen = getUserType(hackathonNames.get(position));
                Intent intent = new Intent();
                switch (pageToOpen) {
                    case 1:
                        intent = new Intent(getContext(), UnsignedHackathonPage.class);
                        break;
                    case 2:
                        intent = new Intent(getContext(), SignedHackathonPage.class);
                        break;
                    case 3:
                        intent = new Intent(getContext(), ManagedHackathonPage.class);
                        break;
                }
                intent.putExtra("hackathon", hackathon);
                startActivity(intent);
            }
        });
        return view;
    }

    private void fillList(final ListView hackathons_list, List<String> hackathonNames) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, hackathonNames);
        hackathons_list.setAdapter(adapter);
    }


    //TODO: (tal) this function needs to return a list of ALL Hackathons in FB
    private List<String> getHackathonNamesFromDB() {
        String currUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        _db.getReference("users").child(currUserID).child("managedHackathons").orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> hackathons = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //dates handling
                    Date startDate = handleDate(postSnapshot.child("startDate"));
                    Date endDate = handleDate(postSnapshot.child("endDate"));

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

        return null;
    }

    //TODO: (tal) this function needs to return the hackathon object of a given hackathon name
    private Hackathon getHackathonByName(String hackathonName) {
        return null;
    }

    /*
    TODO: (tal) return which user type the current user is:
    1 - user not signed to hackathon.
    2 - user is participant in hackathon
    3 - user is manager of hackathon
     */
    private int getUserType(String hackathonName) {
        return -1;
    }

    private Date handleDate(DataSnapshot dateDS){
        Date date = new Date();
        date.setDate(Integer.parseInt(dateDS.child("date").toString()));
        date.set(Integer.parseInt(dateDS.child("date").toString()));

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
