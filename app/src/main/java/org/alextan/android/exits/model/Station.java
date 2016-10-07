package org.alextan.android.exits.model;

import com.google.gson.annotations.SerializedName;

/**
 * Train station
 */

public class Station {
    @SerializedName("station_id") private int mId;
    @SerializedName("name") private String mName;

    public Station(int id, String name) {
        mId = id;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
