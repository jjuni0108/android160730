package com.example.c.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.c.criminalintent.data.Crime;
import com.example.c.criminalintent.data.CrimeLab;

import java.util.ArrayList;

public class CrimeListFragment extends Fragment {

    private ArrayList<Crime> mCrimes;
    //    ListView crimeListView;
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    public CrimeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrimes = CrimeLab.getInstance(getActivity()).getCrimes();
        setHasOptionsMenu(true);
    }

    class CrimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitleTextView;
        TextView mDataView;
        CheckBox mSolvedCheckBox;
        Crime mCrime;


        public CrimeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_list_item_titleTextView);
            mDataView = (TextView) itemView.findViewById(R.id.crime_list_item_dateTextView);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.crime_list_item_solvedCheckBox);

        }


        public void bindCrim(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDataView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v) {
//                Intent intent= new Intent(getActivity(),CrimeActivity.class);
            Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
            intent.putExtra(CrimeFragment.EXTRA_ID, mCrime.getId());
            startActivity(intent);
        }
    }

    class CrimeAdapter extends RecyclerView.Adapter<CrimeViewHolder> {

        @Override
        public CrimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.list_item_crime, parent, false);
            CrimeViewHolder holder = new CrimeViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(CrimeViewHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrim(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) v.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CrimeAdapter();
        mCrimeRecyclerView.setAdapter(mAdapter);
//        crimeListView = (ListView) v.findViewById(R.id.crimeListView);
//        CrimeAdapter adapter= new CrimeAdapter();
//        crimeListView.setAdapter(adapter);
//        crimeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Crime c = mCrimes.get(position);
////                Intent intent= new Intent(getActivity(),CrimeActivity.class);
//                Intent intent= new Intent(getActivity(),CrimePagerActivity.class);
//                intent.putExtra(CrimeFragment.EXTRA_ID,c.getId());
//                startActivity(intent);
//            }
//        });


        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_crime :
                Crime crime=new Crime();
                CrimeLab.getInstance(getActivity()).add(crime);
                Intent intent = new Intent(getActivity(),CrimePagerActivity.class);
                intent.putExtra(CrimeFragment.EXTRA_ID,crime.getId());
                startActivity(intent);
                return true;
            case  R.id.menu_item_show_subtitle:
                int count= CrimeLab.getInstance(getActivity()).getCrimes().size();
                String subtitle = getString(R.string.subtitle_format, count);
                AppCompatActivity activity = (AppCompatActivity)getActivity();
                activity.getSupportActionBar().setSubtitle(subtitle);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCrimes=CrimeLab.getInstance(getActivity()).getCrimes();
        mAdapter.notifyDataSetChanged();
    }
}
