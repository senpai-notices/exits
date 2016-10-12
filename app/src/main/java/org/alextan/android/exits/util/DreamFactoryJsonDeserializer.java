package org.alextan.android.exits.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.alextan.android.exits.model.DreamFactoryResource;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DreamFactoryJsonDeserializer<T> implements JsonDeserializer<DreamFactoryResource<T>> {

    private final Class<T> clazz;

    public DreamFactoryJsonDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public DreamFactoryResource<T> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray jsonArray = jsonObject.entrySet().iterator().next().getValue().getAsJsonArray();
        List<T> list = new ArrayList<>();
        for(JsonElement element: jsonArray) {
            Gson gson = new Gson();
            T obj = gson.fromJson(element, clazz);
            list.add(obj);
        }
        return new DreamFactoryResource<>(list);
    }
}
