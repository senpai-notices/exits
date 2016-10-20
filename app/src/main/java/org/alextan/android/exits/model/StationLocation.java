package org.alextan.android.exits.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Train station with location
 */
public class StationLocation {
    @SerializedName("stop_index")
    @Expose
    private int mStopIndex;
    @SerializedName("stop_id")
    @Expose
    private String mStopId;
    @SerializedName("stop_name")
    @Expose
    private String mStopName;
    @SerializedName("stop_lat")
    @Expose
    private double mStopLat;
    @SerializedName("stop_lon")
    @Expose
    private double mStopLon;

    /**
     *
     * @return
     * The mStopIndex
     */
    public int getStopIndex() {
        return mStopIndex;
    }

    /**
     *
     * @param stopIndex
     * The stop_index
     */
    public void setStopIndex(int stopIndex) {
        this.mStopIndex = stopIndex;
    }

    /**
     *
     * @return
     * The mStopId
     */
    public String getStopId() {
        return mStopId;
    }

    /**
     *
     * @param stopId
     * The stop_id
     */
    public void setStopId(String stopId) {
        this.mStopId = stopId;
    }

    /**
     *
     * @return
     * The mStopName
     */
    public String getStopName() {
        return mStopName;
    }

    /**
     *
     * @param stopName
     * The stop_name
     */
    public void setStopName(String stopName) {
        this.mStopName = stopName;
    }

    /**
     *
     * @return
     * The mStopLat
     */
    public double getStopLat() {
        return mStopLat;
    }

    /**
     *
     * @param stopLat
     * The stop_lat
     */
    public void setStopLat(double stopLat) {
        this.mStopLat = stopLat;
    }

    /**
     *
     * @return
     * The mStopLon
     */
    public double getStopLon() {
        return mStopLon;
    }

    /**
     *
     * @param stopLon
     * The stop_lon
     */
    public void setStopLon(double stopLon) {
        this.mStopLon = stopLon;
    }

}
