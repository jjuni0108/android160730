package com.example.c.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class CrimeListFragment extends Fragment {

    private ArrayList<Crime> mCrimes;
    ListView crimListView;

    public CrimeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrimes=CrimeLab.getInstance(getActivity()).getCrimes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_crime_list, container, false);
        crimListView= (ListView) v.findViewById(R.id.crimeListView);
        return  v;
    }

}
