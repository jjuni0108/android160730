package com.example.c.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.c.criminalintent.data.Crime;
import com.example.c.criminalintent.data.CrimeLab;

import java.util.ArrayList;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {
    ArrayList<Crime> mCrimes;

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        mViewPager = (ViewPager) findViewById(R.id.crime_viewPager);
        mCrimes = CrimeLab.getInstance(this).getCrimes();
        FragmentManager fm = getSupportFragmentManager();
//        FragmentStatePagerAdapter
//        FragmentPagerAdapter
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        };
        mViewPager.setAdapter(adapter);
        UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_ID);

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                setTitle(mCrimes.get(i).getTitle());
                mViewPager.setCurrentItem(i);
                break;
            }
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String title=mCrimes.get(position).getTitle();
                if(title!=null){
                    setTitle(title);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
