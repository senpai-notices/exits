package org.alextan.android.exits.deserialiser;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.alextan.android.exits.model.Step;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DirectionsDeserialiser implements JsonDeserializer<List<Step>>{

    public static final String KEY_ROUTES = "routes";
    public static final String KEY_LEGS = "legs";
    public static final String KEY_STEPS = "steps";
    public static final String KEY_TRAVEL_MODE = "travel_mode";
    public static final String VALUE_TRANSIT = "TRANSIT";
    public static final String KEY_TRANSIT_DETAILS = "transit_details";
    public static final String KEY_ARRIVAL_STOP = "arrival_stop";
    public static final String KEY_NAME = "name";
    public static final String KEY_ARRIVAL_TIME = "arrival_time";
    public static final String KEY_TEXT = "text";
    public static final String KEY_DEPARTURE_STOP = "departure_stop";
    public static final String KEY_DEPARTURE_TIME = "departure_time";
    public static final String KEY_LINE = "line";
    public static final String KEY_HEAVY_RAIL = "HEAVY_RAIL";
    public static final String KEY_VEHICLE = "vehicle";
    public static final String KEY_TYPE = "type";

    @Override
    public List<Step> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = (JsonObject) json;

        JsonArray stepsArray = jsonObject.getAsJsonObject().getAsJsonArray(KEY_ROUTES).get(0)
                .getAsJsonObject().getAsJsonArray(KEY_LEGS).get(0)
                .getAsJsonObject().getAsJsonArray(KEY_STEPS);

        // TODO: remove
        Log.d("deserialize", "step size: " + stepsArray.size());

        List<Step> transitSteps = new ArrayList<>();
        for (int i = 0; i <stepsArray.size(); i++) {
            JsonElement innerObject = stepsArray.get(i);
            if (innerObject.getAsJsonObject().get(KEY_TRAVEL_MODE).getAsString().equals(VALUE_TRANSIT)) {
                JsonElement transitDetails = innerObject.getAsJsonObject().get(KEY_TRANSIT_DETAILS);

                String vehicleType = transitDetails.getAsJsonObject().get(KEY_LINE).getAsJsonObject().get(KEY_VEHICLE).getAsJsonObject().get(KEY_TYPE).getAsString();
                if (vehicleType.equals(KEY_HEAVY_RAIL)) {
                    Step step = new Step();
                    step.setArrivalStop(transitDetails.getAsJsonObject()
                            .get(KEY_ARRIVAL_STOP).getAsJsonObject()
                            .get(KEY_NAME).getAsString());
                    step.setArrivalTime(transitDetails.getAsJsonObject()
                            .get(KEY_ARRIVAL_TIME).getAsJsonObject()
                            .get(KEY_TEXT).getAsString());
                    step.setDepartureStop(transitDetails.getAsJsonObject()
                            .get(KEY_DEPARTURE_STOP).getAsJsonObject()
                            .get(KEY_NAME).getAsString());
                    step.setDepartureTime(transitDetails.getAsJsonObject()
                            .get(KEY_DEPARTURE_TIME).getAsJsonObject()
                            .get(KEY_TEXT).getAsString());
                    step.setLine(transitDetails.getAsJsonObject().getAsJsonObject()
                            .get(KEY_LINE).getAsJsonObject()
                            .get(KEY_NAME).getAsString());
                    transitSteps.add(step);
                } else {
                    return transitSteps;
                }
            }
        }

        // TODO: remove
        Log.d("deserialize()", "transitSteps size: " + transitSteps.size());

        return transitSteps;
    }
}
