package org.alextan.android.exits.util;

import android.util.Pair;

import org.alextan.android.exits.model.DreamFactoryJsonResponse;
import org.alextan.android.exits.model.StationLocation;
import org.alextan.android.exits.service.GtfsService;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;


public class GetNearestStation {

    private static List<StationLocation> mList;

    public static void fetch() {
        List<StationLocation> result = new LinkedList<>();

        GtfsService gtfsService = GtfsService.retrofit.create(GtfsService.class);
        Call<DreamFactoryJsonResponse<StationLocation>> call = gtfsService.getAllStationLocations();
        DreamFactoryJsonResponse<StationLocation> response = null;
        try {
            response = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result = response != null ? response.getData() : null;

        mList = result;
    }

    public static StationLocation findNearest(double lat, double lng) {
        Hashtable<StationLocation, Double> distances = new Hashtable<>();

        for (StationLocation s : mList) {
            double distance = pythagoreanTheorem(s.getStopLat(), s.getStopLon());
            distances.put(s, distance);
        }

        Pair<StationLocation, Double> closest = new Pair<>(new StationLocation(), 9999999999.99);
        Iterator<Map.Entry<StationLocation, Double>> it = distances.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<StationLocation, Double> entry = it.next();
            if (entry.getValue() < closest.second) {
                closest = new Pair<>(it.next().getKey(), it.next().getValue());
            }
        }
        return closest.first;
    }

    public static double pythagoreanTheorem(double x, double y) {
        double c = Math.sqrt((x*x)+(y*y));
        return c;
    }
}
