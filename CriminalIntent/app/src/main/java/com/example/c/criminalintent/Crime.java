package com.example.c.criminalintent;

import java.util.UUID;

/**
 * Created by c on 2016-07-30.
 */
public class Crime {
    private String mTitle;
    private UUID mId;

    public Crime(){
        mId=UUID.randomUUID();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    @Override
    public String toString() {
        return "Crime{" +
                "mTitle='" + mTitle + '\'' +
                ", mId=" + mId +
                '}';
    }
}
