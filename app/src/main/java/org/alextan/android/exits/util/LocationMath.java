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

    public static StationLocation nearest(List<StationLocation> stations, double currentLat, double currentLng) {
        Hashtable<StationLocation, Double> distances = new Hashtable<>();

        for (StationLocation s : stations) {
            double distance = pythagoreanTheorem(s.getStopLat() - currentLat, s.getStopLon() - currentLng);
            distances.put(s, distance);
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

    public static double pythagoreanTheorem(double x, double y) {
        double c = Math.sqrt((x*x)+(y*y));
        return c;
    }
}
