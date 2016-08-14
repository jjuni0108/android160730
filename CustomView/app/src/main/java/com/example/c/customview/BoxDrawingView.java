package com.example.c.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by c on 2016-08-14.
 */
public class BoxDrawingView extends View {
    private Box mCurrentBox;
    private ArrayList<Box> mBoxes= new ArrayList<>();




    public BoxDrawingView(Context context) {
        super(context);
    }

    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBackGroundPaint = new Paint();
        mBackGroundPaint.setColor(0xfff8efe0);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);
    }

    private Paint mBoxPaint;
    private Paint mBackGroundPaint;



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(mBackGroundPaint);
        for(Box box : mBoxes){
            float left = Math.min(box.getOrgin().x, box.getCurrent().x);
            float right = Math.max(box.getOrgin().x, box.getCurrent().x);
            float top = Math.min(box.getOrgin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrgin().y, box.getCurrent().y);
            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(),event.getY());
        Log.d("onTouchEvent"," current x "+current.x+" corrnet  y : "+current.y);

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mCurrentBox = new Box(current);
                mBoxes.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE:

                if(mCurrentBox!=null) {
                    mCurrentBox.setCurrent(current);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                mCurrentBox = null;
                break;
        }

        return true;
    }
}
