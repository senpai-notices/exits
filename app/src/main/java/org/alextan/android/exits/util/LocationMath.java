package org.alextan.android.exits.util;

import android.util.Pair;

import org.alextan.android.exits.model.StationLocation;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Mathematical functions associated with calculating location.
 */
public class LocationMath {

    public static final double MAX_VALUE = 999999.99;


    /**
     * Calculate the nearest station, given the current location.
     * Searches through a list of possible stations, then calculates nearest one.
     */
    public static StationLocation nearest(List<StationLocation> stations, double currentLat,
                                          double currentLng) {
        Hashtable<StationLocation, Double> distances = new Hashtable<>();

        for (StationLocation station : stations) {
            double distance = pythagoreanTheorem(station.getStopLat() - currentLat,
                    station.getStopLon() - currentLng);
            distances.put(station, distance);
        }

        Pair<StationLocation, Double> closest = new Pair<>(new StationLocation(), MAX_VALUE);
        Iterator<Map.Entry<StationLocation, Double>> it = distances.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<StationLocation, Double> entry = it.next();
            if (entry.getValue() < closest.second) {
                closest = new Pair<>(entry.getKey(), entry.getValue());
            }
        }
        return closest.first;
    }

    /**
     * The Pythagorean Theorem.
     */
    public static double pythagoreanTheorem(double x, double y) {
        double c = Math.sqrt((x*x)+(y*y));
        return c;
    }
}
