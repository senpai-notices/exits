package org.alextan.android.exits.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.alextan.android.exits.R;
import org.alextan.android.exits.adapter.StationAdapter;
import org.alextan.android.exits.model.DreamFactoryResource;
import org.alextan.android.exits.model.Station;
import org.alextan.android.exits.model.StationLocation;
import org.alextan.android.exits.service.GtfsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;

public class StationsActivity extends AppCompatActivity {

    //TextView mTvStations;
    List<String> mStationList;
    ArrayList<StationLocation> mStationList2 = new ArrayList<>();
    private RecyclerView mStationRecyclerView;
    private StationAdapter mStationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
        //setupRest();
        setupUi();
        new DownloadStationsAsyncTask().execute();
        Log.d("SIZE after", mStationList2.size() + "");

    } // cd data/data/org.alextan.android.exits/databases

    private void setupUi() {
        //ButterKnife.bind(this);

        mStationRecyclerView = (RecyclerView) findViewById(R.id.act_stations_rv_stations);
        mStationAdapter = new StationAdapter(StationsActivity.this, mStationList2);
        mStationRecyclerView.setHasFixedSize(true);
        mStationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mStationRecyclerView.setAdapter(mStationAdapter);
    }
    private void setupRest() {

        new DownloadStationsAsyncTask().execute();
    }

    private class DownloadStationsAsyncTask extends AsyncTask<Void, Void, ArrayList<StationLocation>> {

        @Override
        protected ArrayList<StationLocation> doInBackground(Void... params) {
            GtfsService gtfsService = GtfsService.retrofit.create(GtfsService.class);
            Call<DreamFactoryResource<StationLocation>> call = gtfsService.getAllStationLocations();
            DreamFactoryResource<StationLocation> response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<StationLocation> result = response != null ? (ArrayList<StationLocation>) response.getData() : null;

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<StationLocation> result) {
            //mStationList2 = result;
            mStationList2 = (ArrayList<StationLocation>) result.clone();
            mStationAdapter.notifyDataSetChanged();
            mStationRecyclerView.setVisibility(View.VISIBLE);
            Log.d("SIZE onpost", mStationList2.size() + "");
        }
    }


    private class DownloadStations extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> result = new LinkedList<>();

            GtfsService gtfsService = GtfsService.retrofit.create(GtfsService.class);
            Call<DreamFactoryResource<Station>> call = gtfsService.getAllStations();
            DreamFactoryResource<Station> response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Station> stations = response != null ? response.getData() : null;

            for (Station station : stations) {
                result.add(station.getName());
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            mStationList = result;
            //mTvStations.setText(result.toString());
        }
    }
}