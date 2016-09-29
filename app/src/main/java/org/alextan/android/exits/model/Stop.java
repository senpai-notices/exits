package org.alextan.android.exits.model;

/**
 * A stop of a trip
 */
public class Stop {
    private Station mStation;
    private int mPlatform;
    private int mTimeDepart;
    private int mTimeArrive;
    private int mCarriage;

    public Station getStation() {
        return mStation;
    }

    public void setStation(Station station) {
        mStation = station;
    }

    public int getPlatform() {
        return mPlatform;
    }

    public void setPlatform(int platform) {
        mPlatform = platform;
    }

    public int getTimeDepart() {
        return mTimeDepart;
    }

    public void setTimeDepart(int timeDepart) {
        mTimeDepart = timeDepart;
    }

    public int getTimeArrive() {
        return mTimeArrive;
    }

    public void setTimeArrive(int timeArrive) {
        mTimeArrive = timeArrive;
    }

    public int getCarriage() {
        return mCarriage;
    }

    public void setCarriage(int carriage) {
        mCarriage = carriage;
    }
}
