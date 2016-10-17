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

    @Override
    public List<Step> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = (JsonObject) json;

        JsonArray stepsArray = jsonObject.getAsJsonObject().getAsJsonArray("routes").get(0)
                .getAsJsonObject().getAsJsonArray("legs").get(0)
                .getAsJsonObject().getAsJsonArray("steps");

        Log.d("deserialize", "step size: " + stepsArray.size());

        List<Step> transitSteps = new ArrayList<>();
        for (int i = 0; i <stepsArray.size(); i++) {
            JsonElement innerObject = stepsArray.get(i);
            if (innerObject.getAsJsonObject().get("travel_mode").getAsString().equals("TRANSIT")) {
                JsonElement innerObject2 = innerObject.getAsJsonObject().get("transit_details");
                Step step = new Step();
                step.setArrivalStop(innerObject2.getAsJsonObject()
                        .get("arrival_stop").getAsJsonObject()
                        .get("name").getAsString());
                step.setArrivalTime(innerObject2.getAsJsonObject()
                        .get("arrival_time").getAsJsonObject()
                        .get("text").getAsString());
                step.setDepartureStop(innerObject2.getAsJsonObject()
                        .get("departure_stop").getAsJsonObject()
                        .get("name").getAsString());
                step.setDepartureTime(innerObject2.getAsJsonObject()
                        .get("departure_time").getAsJsonObject()
                        .get("text").getAsString());
                step.setLine(innerObject2.getAsJsonObject().getAsJsonObject()
                        .get("line").getAsJsonObject()
                        .get("name").getAsString());
                transitSteps.add(step);
            }
        }

        Log.d("deserialize()", "transitSteps size: " + transitSteps.size());

        return transitSteps;
    }
}
