package org.alextan.android.exits.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StationLocation {
    @SerializedName("stop_index")
    @Expose
    private int stopIndex;
    @SerializedName("stop_id")
    @Expose
    private String stopId;
    @SerializedName("stop_name")
    @Expose
    private String stopName;
    @SerializedName("stop_lat")
    @Expose
    private double stopLat;
    @SerializedName("stop_lon")
    @Expose
    private double stopLon;

    /**
     *
     * @return
     * The stopIndex
     */
    public int getStopIndex() {
        return stopIndex;
    }

    /**
     *
     * @param stopIndex
     * The stop_index
     */
    public void setStopIndex(int stopIndex) {
        this.stopIndex = stopIndex;
    }

    /**
     *
     * @return
     * The stopId
     */
    public String getStopId() {
        return stopId;
    }

    /**
     *
     * @param stopId
     * The stop_id
     */
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    /**
     *
     * @return
     * The stopName
     */
    public String getStopName() {
        return stopName;
    }

    /**
     *
     * @param stopName
     * The stop_name
     */
    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    /**
     *
     * @return
     * The stopLat
     */
    public double getStopLat() {
        return stopLat;
    }

    /**
     *
     * @param stopLat
     * The stop_lat
     */
    public void setStopLat(double stopLat) {
        this.stopLat = stopLat;
    }

    /**
     *
     * @return
     * The stopLon
     */
    public double getStopLon() {
        return stopLon;
    }

    /**
     *
     * @param stopLon
     * The stop_lon
     */
    public void setStopLon(double stopLon) {
        this.stopLon = stopLon;
    }

}
