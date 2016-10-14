package org.alextan.android.exits.model.directions;

import com.google.gson.annotations.SerializedName;

public class Step {

    @SerializedName("name")
    private String mArrivalStop;
    @SerializedName("text")
    private String mArrivalTime;
    @SerializedName("name")
    private String mDepartureStop;
    @SerializedName("text")
    private String mDepartureTime;
    @SerializedName("name")
    private String mLine;

    public Step() {
    }

    public String getArrivalStop() {
        return mArrivalStop;
    }

    public void setArrivalStop(String arrivalStop) {
        mArrivalStop = arrivalStop;
    }

    public String getArrivalTime() {
        return mArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        mArrivalTime = arrivalTime;
    }

    public String getDepartureStop() {
        return mDepartureStop;
    }

    public void setDepartureStop(String departureStop) {
        mDepartureStop = departureStop;
    }

    public String getDepartureTime() {
        return mDepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        mDepartureTime = departureTime;
    }

    public String getLine() {
        return mLine;
    }

    public void setLine(String line) {
        mLine = line;
    }
}
