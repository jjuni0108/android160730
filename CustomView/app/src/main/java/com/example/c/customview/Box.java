package com.example.c.customview;

import android.graphics.PointF;

/**
 * Created by c on 2016-08-14.
 */
public class Box {
    private PointF mOrgin;
    private PointF mCurrent;


    public Box(PointF orgin) {
        mOrgin = orgin;
        mCurrent = orgin;
    }

    public void setCurrent(PointF current) {
        mCurrent = current;
    }

    public PointF getOrgin() {
        return mOrgin;
    }

    public void setOrgin(PointF orgin) {
        mOrgin = orgin;
    }

    public PointF getCurrent() {
        return mCurrent;
    }
}
