package org.alextan.android.exits.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.alextan.android.exits.Constants;
import org.alextan.android.exits.R;
import org.alextan.android.exits.adapter.StationsAdapter;
import org.alextan.android.exits.model.DreamFactoryResource;
import org.alextan.android.exits.model.StationLocation;
import org.alextan.android.exits.api.GtfsApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;

public class StationsActivity extends AppCompatActivity {

    private RecyclerView mStationRecyclerView;
    private StationsAdapter mStationsAdapter;
    private int mOriginStationIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
        mOriginStationIndex = getIntent()
                .getIntExtra(Constants.EXTRA_STATION_INDEX, Constants.STATION_INDEX_DEFAULT_VALUE);
        if (mOriginStationIndex > 0) {
            new FetchStationList().execute();
        }
        // or redirect back to form
    }

    private class FetchStationList extends AsyncTask<Void, Void, ArrayList<StationLocation>> {

        @Override
        protected ArrayList<StationLocation> doInBackground(Void... params) {
            GtfsApi gtfsApi = GtfsApi.retrofit.create(GtfsApi.class);
            Call<DreamFactoryResource<StationLocation>> call = gtfsApi.getAllStationLocations();
            DreamFactoryResource<StationLocation> response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                call.cancel();
            }

            ArrayList<StationLocation> result;
            if (response != null) {
                result = (ArrayList<StationLocation>) response.getData();
                StationLocation originStation = null;
                for (StationLocation station : result) {
                    if (station.getStopIndex() == mOriginStationIndex) {
                        originStation = station;
                    }
                }
                if (originStation != null) {
                    result.remove(originStation);
                }
            } else {
                result = null;
            }

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
            mStationRecyclerView = (RecyclerView) findViewById(R.id.act_stations_rv);
            mStationsAdapter = new StationsAdapter(StationsActivity.this, result);
            mStationRecyclerView.setHasFixedSize(true);
            mStationRecyclerView.setLayoutManager(new LinearLayoutManager(StationsActivity.this));
            mStationRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mStationRecyclerView.setAdapter(mStationsAdapter);
        }
    }
}
