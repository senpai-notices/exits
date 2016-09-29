package org.alextan.android.exits.model;

import java.util.List;

/**
 * Contains all the associated stops
 */
public class Trip {
    List<Stop> mStops;

    public List<Stop> getStops() {
        return mStops;
    }

    public void setStops(List<Stop> stops) {
        mStops = stops;
    }
}
