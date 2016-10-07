package org.alextan.android.exits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Resource {

    @SerializedName("stop_index")
    @Expose
    private int stopIndex;
    @SerializedName("stop_id")
    @Expose
    private String stopId;
    @SerializedName("stop_code")
    @Expose
    private Object stopCode;
    @SerializedName("stop_name")
    @Expose
    private String stopName;
    @SerializedName("stop_desc")
    @Expose
    private Object stopDesc;
    @SerializedName("zone_index")
    @Expose
    private int zoneIndex;
    @SerializedName("zone_id")
    @Expose
    private Object zoneId;
    @SerializedName("stop_lat")
    @Expose
    private double stopLat;
    @SerializedName("stop_lon")
    @Expose
    private double stopLon;
    @SerializedName("location_type")
    @Expose
    private int locationType;
    @SerializedName("parent_station")
    @Expose
    private Object parentStation;
    @SerializedName("parent_station_index")
    @Expose
    private Object parentStationIndex;
    @SerializedName("wheelchair_boarding")
    @Expose
    private int wheelchairBoarding;
    @SerializedName("stop_url")
    @Expose
    private Object stopUrl;
    @SerializedName("stop_timezone")
    @Expose
    private Object stopTimezone;

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
     * The stopCode
     */
    public Object getStopCode() {
        return stopCode;
    }

    /**
     *
     * @param stopCode
     * The stop_code
     */
    public void setStopCode(Object stopCode) {
        this.stopCode = stopCode;
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
     * The stopDesc
     */
    public Object getStopDesc() {
        return stopDesc;
    }

    /**
     *
     * @param stopDesc
     * The stop_desc
     */
    public void setStopDesc(Object stopDesc) {
        this.stopDesc = stopDesc;
    }

    /**
     *
     * @return
     * The zoneIndex
     */
    public int getZoneIndex() {
        return zoneIndex;
    }

    /**
     *
     * @param zoneIndex
     * The zone_index
     */
    public void setZoneIndex(int zoneIndex) {
        this.zoneIndex = zoneIndex;
    }

    /**
     *
     * @return
     * The zoneId
     */
    public Object getZoneId() {
        return zoneId;
    }

    /**
     *
     * @param zoneId
     * The zone_id
     */
    public void setZoneId(Object zoneId) {
        this.zoneId = zoneId;
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

    /**
     *
     * @return
     * The locationType
     */
    public int getLocationType() {
        return locationType;
    }

    /**
     *
     * @param locationType
     * The location_type
     */
    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    /**
     *
     * @return
     * The parentStation
     */
    public Object getParentStation() {
        return parentStation;
    }

    /**
     *
     * @param parentStation
     * The parent_station
     */
    public void setParentStation(Object parentStation) {
        this.parentStation = parentStation;
    }

    /**
     *
     * @return
     * The parentStationIndex
     */
    public Object getParentStationIndex() {
        return parentStationIndex;
    }

    /**
     *
     * @param parentStationIndex
     * The parent_station_index
     */
    public void setParentStationIndex(Object parentStationIndex) {
        this.parentStationIndex = parentStationIndex;
    }

    /**
     *
     * @return
     * The wheelchairBoarding
     */
    public int getWheelchairBoarding() {
        return wheelchairBoarding;
    }

    /**
     *
     * @param wheelchairBoarding
     * The wheelchair_boarding
     */
    public void setWheelchairBoarding(int wheelchairBoarding) {
        this.wheelchairBoarding = wheelchairBoarding;
    }

    /**
     *
     * @return
     * The stopUrl
     */
    public Object getStopUrl() {
        return stopUrl;
    }

    /**
     *
     * @param stopUrl
     * The stop_url
     */
    public void setStopUrl(Object stopUrl) {
        this.stopUrl = stopUrl;
    }

    /**
     *
     * @return
     * The stopTimezone
     */
    public Object getStopTimezone() {
        return stopTimezone;
    }

    /**
     *
     * @param stopTimezone
     * The stop_timezone
     */
    public void setStopTimezone(Object stopTimezone) {
        this.stopTimezone = stopTimezone;
    }

}