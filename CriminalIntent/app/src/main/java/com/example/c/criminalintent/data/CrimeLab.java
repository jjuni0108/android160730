package com.example.c.criminalintent.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.c.criminalintent.SQLite.CrimeBaseHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import static com.example.c.criminalintent.SQLite.CrimeDbSchema.CrimeTable;

/**
 * Created by c on 2016-07-30.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    //    private ArrayList<Crime> mCrimes;
    private SQLiteDatabase mDatabase;

    private CrimeLab(Context c) {
        mAppContext = c;
//        mCrimes = new ArrayList<Crime>();
        mDatabase = new CrimeBaseHelper(mAppContext).getWritableDatabase();
//        for (int i = 0; i < 100; i++) {
//            Crime crime = new Crime();
//            crime.setTitle("범죄 #" + i);
//            crime.setSolved(i % 2 == 0);
//            mCrimes.add(crime);
//        }

    }

    public ArrayList<Crime> getCrimes() {
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {
            while (cursor.moveToNext()) {
                Crime crime = cursor.getCrime();
                crimes.add(crime);
            }
        } finally {
            cursor.close();
        }

        return crimes;
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor= queryCrimes(
                CrimeTable.Cols.UUID +" = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            } else {
                cursor.moveToFirst();
                return cursor.getCrime();
            }
        }finally {
            cursor.close();
        }
    }

    public static CrimeLab getInstance(Context c) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public void add(Crime c) {
//        mCrimes.add(c);
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);

    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        return values;
    }

    public void update(Crime c) {
        ContentValues values = getContentValues(c);
        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + "=?", new String[]{c.getId().toString()});
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(CrimeTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new CrimeCursorWrapper(cursor);
    }


    public File getPhotoFile(Crime crime){
//        String path = Environment.getExternalStorageDirectory()+""
        //path +"/pictures/abc.jpg
        File externalFilesDir  = mAppContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalFilesDir==null){
            return null;
        }

        return new File(externalFilesDir,crime.getPhotoFileName());
    }

}
