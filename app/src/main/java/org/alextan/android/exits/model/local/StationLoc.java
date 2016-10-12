package org.alextan.android.exits.model.local;

import com.orm.SugarRecord;

public class StationLoc extends SugarRecord{

    private int stopIndex;
    private String stopId;
    private String stopName;
    private double stopLat;
    private double stopLon;

    public StationLoc() {
    }
}
