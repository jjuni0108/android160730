package com.example.c.remotecontrol;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.c.remotecontrol.SQLite.PointSystemOpenHelper;

import static com.example.c.remotecontrol.SQLite.PointSystemDbSchema.PointSystemTable;

/**
 * Created by c on 2016-08-07.
 */
public class PointLab {

    private static PointLab pointLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private PointLab(Context context){
        mContext= context;
        mDatabase = new PointSystemOpenHelper(context).getWritableDatabase();
    }

    public static PointLab getInstance(Context c) {
        if(pointLab == null){
            pointLab = new PointLab(c.getApplicationContext());
        }
        return pointLab;
    }

    public void add (Point point){
        ContentValues values= getContentValues(point);
        mDatabase.insert(PointSystemTable.NAME,null ,values);
    }

    public void update(Point point) {
        ContentValues values = getContentValues(point);
        mDatabase.update(PointSystemTable.NAME, values,
                PointSystemTable.Cols.UUID + "=?", new String[]{point.getId().toString()});
    }

    private static ContentValues getContentValues(Point point) {
        ContentValues values = new ContentValues();
        values.put(PointSystemTable.Cols.UUID, point.getId().toString());
        values.put(PointSystemTable.Cols.BEVERAGE, point.getBeverage());
        values.put(PointSystemTable.Cols.DATE, point.getDate().getTime());
        values.put(PointSystemTable.Cols.TEL, point.getTel());
        return values;
    }

}
