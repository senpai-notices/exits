package org.alextan.android.exits.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.alextan.android.exits.Constants;
import org.alextan.android.exits.R;
import org.alextan.android.exits.endpoint.DirectionsEndpoint;
import org.alextan.android.exits.endpoint.GtfsEndpoint;
import org.alextan.android.exits.model.DreamFactoryResource;
import org.alextan.android.exits.model.StationLocation;
import org.alextan.android.exits.model.directions.Step;
import org.alextan.android.exits.util.LocationMath;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static org.alextan.android.exits.util.TrainUtil.isSydneyTrainsLine;

/**
 * The first activity of the app.
 * Automatically fetches nearest train station.
 * User inputs destination station and exit.
 */
public class Form2Activity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener {

    @BindView(R.id.act_form_btn_go)
    Button mBtnGo;
    @BindView(R.id.act_form_btn_test)
    Button mBtnTest;
    @BindView(R.id.act_form_tv_nearest_station)
    TextView tvNearestStation;
    @BindView(R.id.sp_station_exits)
    Spinner mSpinnerStationExits;
    @BindView(R.id.act_form_tv_destination)
    TextView tvDestination;
    @BindView(R.id.act_form_test_a_to_b)
    Button mBtnTestAB;

    private StationLocation mOriginStation;
    private StationLocation mDestinationStation;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 10 * 1000;
    private long FASTEST_INTERVAL = 2000;

    static final int REQUEST_PICK_STATION = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        initialiseLocationServices();
        initialiseUi();
    }

    private void initialiseLocationServices() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
    }

    @Override
    protected void onStop() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    private void initialiseUi() {
        ButterKnife.bind(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.exits_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinnerStationExits.setAdapter(adapter);
        mBtnGo.setOnClickListener(this);
        mBtnTest.setOnClickListener(this);
        mBtnTestAB.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_history:
                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Get last known recent location.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "Not enabled location", Toast.LENGTH_SHORT).show();
            return;
        }
        Location mCurrentLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        // Note that this can be NULL if last location isn't already known.
        if (mCurrentLocation != null) {
            // Print current location if not null
            Log.d("DEBUG", "current location: " + mCurrentLocation.toString());
            LatLng latLng = new LatLng(mCurrentLocation.getLatitude(),
                    mCurrentLocation.getLongitude());
        }
        // Begin polling for new location updates.
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(this, "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        // Optional, a LatLng object
        // LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        new NearestStation(location.getLatitude(), location.getLongitude()).execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_form_btn_go:
                //startActivity(new Intent(this, TripActivity.class));
                Log.d("goBtn", "onClick");
                new TestDirections().execute();
                break;
            case R.id.act_form_btn_test:
                Intent stationIntent = new Intent(getApplicationContext(), StationsActivity.class);
                startActivityForResult(stationIntent, REQUEST_PICK_STATION);
                break;
            case R.id.act_form_test_a_to_b:
                String msg = "";
                if (mDestinationStation == null) msg += "dest is null. ";
                else msg+= mDestinationStation.getStopName();
                if (mOriginStation == null) msg += "origin is null. ";
                else msg+= mOriginStation.getStopName();
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICK_STATION) {
            if (resultCode == Activity.RESULT_OK) {
                int destinationIndex = data.getIntExtra(Constants.EXTRA_STATION_INDEX, Constants.STATION_INDEX_DEFAULT_VALUE);
                new FetchStationAsync(destinationIndex).execute();
            }
        }
    }

    private class NearestStation extends AsyncTask<Void, Void, StationLocation> {

        private double mCurrentLat;
        private double mCurrentLng;

        public NearestStation(double currentLat, double currentLng) {
            mCurrentLat = currentLat;
            mCurrentLng = currentLng;
        }

        @Override
        protected StationLocation doInBackground(Void... params) {
            List<StationLocation> result;

            GtfsEndpoint gtfsEndpoint = GtfsEndpoint.retrofit.create(GtfsEndpoint.class);
            Call<DreamFactoryResource<StationLocation>> call = gtfsEndpoint.getAllStationLocations();
            DreamFactoryResource<StationLocation> response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                call.cancel();
            } finally {
            }

            result = response != null ? response.getData() : null;
            return LocationMath.nearest(result, mCurrentLat, mCurrentLng);
        }

        @Override
        protected void onPostExecute(StationLocation result) {
            tvNearestStation.setText(result.getStopName());
            mOriginStation = result;
        }

    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "THIS DOESNT WORK SHOW? Not enabled location", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,
                this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class FetchStationAsync extends AsyncTask<Void, Void, StationLocation> {

        private int mStaIndex;

        public FetchStationAsync(int stationIndex) {
            mStaIndex = stationIndex;
        }

        @Override
        protected void onPreExecute() {
            if (mStaIndex < 0) {
                Log.e("FetchStationAsync", "Invalid index");
                return;
            }
        }

        @Override
        protected StationLocation doInBackground(Void... params) {
            GtfsEndpoint gtfsEndpoint = GtfsEndpoint.retrofit.create(GtfsEndpoint.class);
            Call<StationLocation> call = gtfsEndpoint.getStation(mStaIndex);
            StationLocation response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                call.cancel();
            }

            return response;
        }

        @Override
        protected void onPostExecute(StationLocation result) {
            tvDestination.setText(result.getStopName());
            mDestinationStation = result;
        }
    }

    private class TestDirections extends AsyncTask<Void, Void, List<Step>> {

        @Override
        protected List<Step> doInBackground(Void... params) {
            DirectionsEndpoint directionsEndpoint = DirectionsEndpoint.retrofit.create(DirectionsEndpoint.class);
            Call<List<Step>> call = directionsEndpoint.getDirection(
                    (mOriginStation.getStopLat() + "," + mOriginStation.getStopLon()),
                    (mDestinationStation.getStopLat() + "," + mDestinationStation.getStopLon())
            );
            List<Step> response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                call.cancel();
            }
            return response;
        }

        @Override
        protected void onPostExecute(List<Step> result) {
            Log.d("testdirections", "onPostExecute()");
            if (result.size() == 0) {
                Log.d("onPostExecute()", "Empty transitSteps! Should display error message. Also try to prevent selecting duplicate station.");
            }
            for (Step step : result) {
                Log.d("testdirections", step.getDepartureStop() + "->" + step.getArrivalStop());
                if (!isSydneyTrainsLine(step.getLine(), Form2Activity.this)) {
                    Log.d("onPostExecute()", "Non-Sydney Trains line detected. Should display error message.");
                }
            }
        }
    }
}
