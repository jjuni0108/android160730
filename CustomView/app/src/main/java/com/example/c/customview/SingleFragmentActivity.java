package com.example.c.customview;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by c on 2016-07-30.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    //Layout 리소스를 리턴해주는 어노테이션
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_frame;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
//            fragment=new CrimeFragment();
//            fragment = new CrimeListFragment();
            fragment = createFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragmentContainer, fragment);
            ft.commit();
        }
    }
}
