package org.alextan.android.exits.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.alextan.android.exits.deserialiser.DirectionsDeserialiser;
import org.alextan.android.exits.model.Step;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DirectionsApi {
    String PARAM_KEY_API_KEY = "key";
    String PARAM_VALUE_API_KEY = "***REMOVED***";
    String PARAM_KEY_ORIGIN = "origin";
    String PARAM_KEY_DESTINATION = "destination";
    String PARAM_KEY_MODE = "mode";
    String PARAM_VALUE_MODE = "transit";
    String PARAM_KEY_TRANSIT_MODE = "transit_mode";
    String PARAM_VALUE_TRANSIT_MODE = "train";
    String URL = "https://maps.googleapis.com/maps/api/directions/";

    //TODO
    String TEMP = "&departure_time=1476921600"; // 10 am weekday
    String TEMP2 = "&departure_time=1476886594"; // ~ midnight weekday


    @GET("json?" + PARAM_KEY_API_KEY + "=" + PARAM_VALUE_API_KEY + "&" + PARAM_KEY_MODE + "="
            + PARAM_VALUE_MODE + "&" + PARAM_KEY_TRANSIT_MODE + "=" + PARAM_VALUE_TRANSIT_MODE + TEMP)
    Call<List<Step>> getDirection(@Query("origin") String origin, @Query("destination") String destination);

    Type stepListType = new TypeToken<List<Step>>(){}.getType();

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(stepListType, new DirectionsDeserialiser())
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
