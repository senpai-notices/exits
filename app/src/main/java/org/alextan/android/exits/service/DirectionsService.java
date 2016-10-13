package org.alextan.android.exits.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.alextan.android.exits.model.DreamFactoryResource;
import org.alextan.android.exits.model.Station;
import org.alextan.android.exits.model.StationLocation;
import org.alextan.android.exits.util.DreamFactoryJsonDeserializer;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DirectionsService {
    String PARAM_KEY_API_KEY = "key";
    String PARAM_VALUE_API_KEY = "***REMOVED***";
    String PARAM_KEY_ORIGIN = "origin";
    String PARAM_KEY_DESTINATION = "destination";
    String PARAM_KEY_MODE = "mode";
    String PARAM_VALUE_MODE = "transit";
    String PARAM_KEY_TRANSIT_MODE = "transit_mode";
    String PARAM_VALUE_TRANSIT_MODE = "train";
    String URL = "https://maps.googleapis.com/maps/api/directions/json?"
            +PARAM_KEY_API_KEY+"="+PARAM_VALUE_API_KEY+"&"
            +PARAM_KEY_MODE+"="+PARAM_VALUE_MODE+"&"
            +PARAM_KEY_TRANSIT_MODE+"="+PARAM_VALUE_TRANSIT_MODE;


    @GET()
    Call<Object> getDirection(@Query("origin") String origin, @Query("destination") String destination);

    Type stationResourceType = new TypeToken<DreamFactoryResource<Station>>(){}.getType();
    Type stationLocationResourceType = new TypeToken<DreamFactoryResource<StationLocation>>(){}.getType();

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(stationResourceType, new DreamFactoryJsonDeserializer<>(Station.class))
            .registerTypeAdapter(stationLocationResourceType, new DreamFactoryJsonDeserializer<>(StationLocation.class))
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
