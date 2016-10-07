package org.alextan.android.exits.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.alextan.android.exits.GtfsService;
import org.alextan.android.exits.R;
import org.alextan.android.exits.model.DreamFactoryJsonResponse;
import org.alextan.android.exits.model.Station;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;

public class StationsActivity extends AppCompatActivity {

    TextView tvStations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
        setupRest();
    }

    private void setupRest() {
        tvStations = (TextView) findViewById(R.id.tv_stations);

        new DownloadStations().execute();
    }

    private class DownloadStations extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> result = new LinkedList<>();
            /*GtfsService gtfsService = GtfsService.retrofit.create(GtfsService.class);
            Call<StationData> call = gtfsService.getStationsOld("stop_id like PST%");
            StationData stationsWrapper = null;
            try {
                stationsWrapper = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Resource> stations = stationsWrapper.getResource();
            for (Resource station: stations) {
                String stationName = station.getStopName();
                if (stationName.contains("(VIC)")) {
                    result.add(stationName.substring(0, stationName.length() - 14));
                }
                else {
                    result.add(stationName.substring(0, stationName.length() - 8));
                }
            }

            Collections.sort(result);*/
            GtfsService gtfsService = GtfsService.retrofit.create(GtfsService.class);
            Call<DreamFactoryJsonResponse<Station>> call = gtfsService.testing();
            DreamFactoryJsonResponse<Station> response = null;
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
            // super.onPostExecute(result);
            tvStations.setText(result.toString());

            List<String> substr = result.subList(Math.max(result.size() - 20, 0), result.size());

            System.out.println(result.toString());
            System.out.println(substr.toString());
            System.out.println(result.size());
        }
    }
}