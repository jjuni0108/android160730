package com.example.c.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class CrimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);
        FragmentManager fm= getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragmentConainer);

        if(fragment==null){
            fragment=new CrimFragment();
            FragmentTransaction ft=fm.beginTransaction();
            ft.add(R.id.fragmentConainer,fragment);
            ft.commit();
        }
    }
}
