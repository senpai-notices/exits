package org.alextan.android.exits.deserialiser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.alextan.android.exits.model.directions.StepList;

import java.lang.reflect.Type;

public class DirectionsDeserialiser implements JsonDeserializer<StepList>{

    @Override
    public StepList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
