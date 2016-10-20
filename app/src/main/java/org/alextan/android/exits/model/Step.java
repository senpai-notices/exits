package org.alextan.android.exits.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.alextan.android.exits.util.TripUtil;

/**
 * A component of a train trip. One trip may contain 1 or more Steps
 */
public class Step implements Parcelable {

    private String mArrivalStop;
    private String mArrivalTime;
    private String mDepartureStop;
    private String mDepartureTime;
    private String mLine;

    public Step() {
    }

    public String getArrivalStop() {
        return mArrivalStop;
    }

    public void setArrivalStop(String arrivalStop) {
        mArrivalStop = TripUtil.cleanseStationName(arrivalStop);
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
        mDepartureStop = TripUtil.cleanseStationName(departureStop);
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
        mLine = TripUtil.cleanseLineName(line);
    }

    /**
     * Constructor for the Parcelable representation
     */
    protected Step(Parcel in) {
        mArrivalStop = in.readString();
        mArrivalTime = in.readString();
        mDepartureStop = in.readString();
        mDepartureTime = in.readString();
        mLine = in.readString();
    }

    /**
     * Required for Parcelable objects. Includes defining the Parcelable constructor
     */
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
