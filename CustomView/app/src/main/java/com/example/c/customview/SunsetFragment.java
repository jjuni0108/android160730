package com.example.c.customview;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by c on 2016-08-14.
 */
public class SunsetFragment extends Fragment{
    private View mSceneView,mSunView,mSkyView;
    private int mBlueSkyColor, mSunsetSkyColor, mNightSkyColor;


    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_sunset,container,false);
        mSceneView= v;
        mSunView = v.findViewById(R.id.sun);
        mSkyView = v.findViewById(R.id.sky);

        mBlueSkyColor=getResources().getColor(R.color.bright_sky);
        mSunsetSkyColor= getResources().getColor(R.color.sunset_sky);
        mNightSkyColor=getResources().getColor(R.color.night_sky);


        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });
        return v;
    }

    private void startAnimation(){
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();
        ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(mSunView,"y",sunYStart,sunYEnd)
                .setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator.ofInt(mSkyView,"backgroundColor",mBlueSkyColor,mSunsetSkyColor)
                .setDuration(3000);

        sunsetSkyAnimator.start();
        heightAnimator.start();
    }



}
