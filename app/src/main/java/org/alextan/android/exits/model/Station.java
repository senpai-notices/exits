package org.alextan.android.exits.model;

import java.util.List;

/**
 * Train station
 */

public class Station {
    private String mName;
    private List<Portal> mPortals;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Portal> getPortals() {
        return mPortals;
    }

    public void setPortals(List<Portal> portals) {
        mPortals = portals;
    }
}
