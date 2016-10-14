package org.alextan.android.exits.model.directions;

public class Step {

    private String mArrivalStop;
    private String mArrivalTime;
    private String mDepartureStop;
    private String mDepartureTime;
    private String mLine;

    public Step(String arrivalStop, String arrivalTime, String departureStop, String departureTime, String line) {
        mArrivalStop = arrivalStop;
        mArrivalTime = arrivalTime;
        mDepartureStop = departureStop;
        mDepartureTime = departureTime;
        mLine = line;
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
