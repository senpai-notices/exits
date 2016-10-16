package org.alextan.android.exits.model.directions;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable{

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

    protected Step(Parcel in) {
        mArrivalStop = in.readString();
        mArrivalTime = in.readString();
        mDepartureStop = in.readString();
        mDepartureTime = in.readString();
        mLine = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mArrivalStop);
        dest.writeString(mArrivalTime);
        dest.writeString(mDepartureStop);
        dest.writeString(mDepartureTime);
        dest.writeString(mLine);
    }
}
