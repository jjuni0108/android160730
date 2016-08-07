package com.example.c.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.c.criminalintent.Common.SingleFragmentActivity;
import com.example.c.criminalintent.data.Crime;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = new Intent(this, CrimePagerActivity.class);
            intent.putExtra(CrimeFragment.EXTRA_ID, crime.getId());
            startActivity(intent);
        } else {
            Fragment newDatail = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, newDatail).commit();
        }
    }
}
