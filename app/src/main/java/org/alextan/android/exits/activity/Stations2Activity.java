package org.alextan.android.exits.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.alextan.android.exits.R;
import org.alextan.android.exits.adapter.StationAdapter;
import org.alextan.android.exits.model.DreamFactoryResource;
import org.alextan.android.exits.model.StationLocation;
import org.alextan.android.exits.service.GtfsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;

public class Stations2Activity extends AppCompatActivity {

    private RecyclerView mStationRecyclerView;
    private StationAdapter mStationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations2);
        new FetchStationsAsync().execute();
    }

    private class FetchStationsAsync extends AsyncTask<Void, Void, ArrayList<StationLocation>> {

        @Override
        protected ArrayList<StationLocation> doInBackground(Void... params) {
            GtfsService gtfsService = GtfsService.retrofit.create(GtfsService.class);
            Call<DreamFactoryResource<StationLocation>> call = gtfsService.getAllStationLocations();
            DreamFactoryResource<StationLocation> response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                call.cancel();
            }

            ArrayList<StationLocation> result = response != null ? (ArrayList<StationLocation>) response.getData() : null;

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<StationLocation> result) {
            Collections.sort(result, new Comparator<StationLocation>() {
                @Override
                public int compare(StationLocation o1, StationLocation o2) {
                    return o1.getStopName().compareTo(o2.getStopName());
                }
            });
            mStationRecyclerView = (RecyclerView) findViewById(R.id.rv);
            mStationAdapter = new StationAdapter(Stations2Activity.this, result);
            mStationRecyclerView.setHasFixedSize(true);
            mStationRecyclerView.setLayoutManager(new LinearLayoutManager(Stations2Activity.this));
            mStationRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mStationRecyclerView.setAdapter(mStationAdapter);
        }
    }
}
