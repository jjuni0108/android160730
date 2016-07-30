package com.example.c.criminalintent.Common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.c.criminalintent.R;

/**
 * Created by c on 2016-07-30.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected  abstract  Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        FragmentManager fm= getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragmentConainer);

        if(fragment==null){
//            fragment=new CrimeFragment();
//            fragment = new CrimeListFragment();
            fragment = createFragment();
            FragmentTransaction ft=fm.beginTransaction();
            ft.add(R.id.fragmentConainer,fragment);
            ft.commit();
        }
    }
}
