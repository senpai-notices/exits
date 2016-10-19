package org.alextan.android.exits.common;

/**
 * Constants used throughout the app. It may seem that some constants here belong in a string
 * resource, however, it is stored in a constant because the calling object does not have access
 * to a Context object.
 */
public class Constants {
    public static final int REQUEST_LOCATION_PERMISSION = 100;
    public static final int STATION_INDEX_DEFAULT_VALUE = -1;

    public static final String ACTION_LOCATION_UPDATE = "location_update";
    public static final String ACTION_GEO_STATUS = "geo_status";

    public static final String EXTRA_STATION_INDEX = "station_index_key";
    public static final String EXTRA_CURRENT_LATLNG = "latlng";

    public static final String EXTRA_LOCATION_DISCONNECTED = "location_disconnected";
    public static final String EXTRA_NETWORK_LOST = "network_lost";
    public static final String EXTRA_CONN_FAILED= "conn_failed";

    public static final String KEY_STEPS = "key_steps";
    public static final String T1_LINE = "T1 Line";
    public static final String T2_LINE = "T2 Line";
    public static final String T3_LINE = "T3 Line";
    public static final String T4_LINE = "T4 Line";
    public static final String T5_LINE = "T5 Line";
    public static final String T6_LINE = "T6 Line";
    public static final String T7_LINE = "T7 Line";
    public static final String NORTH_COAST_NSW_LINE = "North Coast NSW Line";
    public static final String SOUTHERN_NSW_LINE = "Southern NSW Line";
    public static final String WESTERN_NSW_LINE = "Western NSW Line";
    public static final String NORTH_WESTERN_NSW_LINE = "North Western NSW Line";
    public static final String SUBSTRING_T1 = "T1";
    public static final String SUBSTRING_T2 = "T2";
    public static final String SUBSTRING_T3 = "T3";
    public static final String SUBSTRING_T4 = "T4";
    public static final String SUBSTRING_T5 = "T5";
    public static final String SUBSTRING_T6 = "T6";
    public static final String SUBSTRING_T7 = "T7";
    public static final String SUBSTRING_NORTH_COAST = "North Coast";
    public static final String SUBSTRING_SOUTHERN_NSW = "Southern NSW";
    public static final String SUBSTRING_WESTERN_NSW = "Western NSW";
    public static final String SUBSTRING_NORTH_WESTERN = "North Western";
    public static final String NULL = "";
    public static final String SEARCH_STATION_1 = "Station";
    public static final String SEARCH_STATION_2 = "station";
    public static final String SEARCH_TRAIN_1 = "Train";
    public static final String SEARCH_TRAIN_2 = "train";
    public static final String CLEANSED_SHELLHARBOUR = "Shellharbour";
    public static final String SEARCH_SHELLHARBOUR = "Shellharbour Junction";
    public static final String SUBSTRING_SYD_MEL_1 = "Melbourne - Sydney";
    public static final String SUBSTRING_SYD_MEL_2 = "Sydney - Melbourne";
}
