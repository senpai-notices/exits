package org.alextan.android.exits.model;

import java.util.List;

/**
 * An entrance/exit of a station
 */
public class Portal {
    private String mName;
    private List<Integer> mNearestPlatformLocation;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Integer> getNearestPlatformLocation() {
        return mNearestPlatformLocation;
    }

    public void setNearestPlatformLocation(List<Integer> nearestPlatformLocation) {
        mNearestPlatformLocation = nearestPlatformLocation;
    }
}
