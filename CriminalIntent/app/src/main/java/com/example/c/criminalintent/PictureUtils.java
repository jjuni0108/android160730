package com.example.c.criminalintent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by c on 2016-08-07.
 */
public class PictureUtils {
    public static Bitmap getScaledBitmap(String path , int destWidth , int destHeight ){
        //비트맵 크기 알아내기
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);

        float srcWidth= options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 4;

        if(srcHeight>destHeight || srcWidth > destWidth){
            if(srcWidth>destHeight){
                inSampleSize = Math.round(srcHeight/destHeight);
            }else{
                inSampleSize = Math.round(srcWidth/destWidth);
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(path,options);
    }
}
