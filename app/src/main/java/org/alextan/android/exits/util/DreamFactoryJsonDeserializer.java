package org.alextan.android.exits.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.alextan.android.exits.model.DreamFactoryJsonResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DreamFactoryJsonDeserializer<T> implements JsonDeserializer<DreamFactoryJsonResponse<T>> {

    private final Class<T> clazz;

    public DreamFactoryJsonDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public DreamFactoryJsonResponse<T> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray jsonArray = jsonObject.entrySet().iterator().next().getValue().getAsJsonArray();
        List<T> list = new ArrayList<>();
        for(JsonElement element: jsonArray) {
/*            JsonElement innerElement = element.getAsJsonObject().entrySet().iterator().next().getValue();
            list.add((T) context.deserialize(innerElement, clazz));*/
            /*String key = element.getAsJsonObject().entrySet().iterator().next().getKey();
            JsonElement e = element.getAsJsonObject().entrySet().iterator().next().getValue();
            JsonObject obj = new JsonObject();
            obj.addProperty("key", e.getAsString());
            list.add((T) context.deserialize(obj, clazz));*/
            Gson gson = new Gson();
            T obj2 = gson.fromJson(element, clazz);
            list.add(obj2);
        }
        return new DreamFactoryJsonResponse<>(list);
    }
}
