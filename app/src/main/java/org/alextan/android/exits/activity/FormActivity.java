package org.alextan.android.exits.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.alextan.android.exits.R;
import org.alextan.android.exits.api.DirectionsApi;
import org.alextan.android.exits.api.GtfsApi;
import org.alextan.android.exits.common.Constants;
import org.alextan.android.exits.model.DreamFactoryResource;
import org.alextan.android.exits.model.StationLocation;
import org.alextan.android.exits.model.Step;
import org.alextan.android.exits.service.GeolocationService;
import org.alextan.android.exits.util.LocationMath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * The first activity of the app.
 * Automatically fetches nearest train station.
 * User inputs destination station and exit.
 */
public class FormActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.act_form_btn_go)
    Button mBtnGo;
    @BindView(R.id.act_form_tv_origin)
    TextView mTvOriginStation;
    @BindView(R.id.act_form_tv_destination)
    TextView mTvDestination;
    @BindView(R.id.act_form_progress_origin)
    ProgressBar mOriginProgressRing;

    private ProgressDialog mLoadingDialog;

    private BroadcastReceiver mGeoBroadcastReceiver;
    private BroadcastReceiver mGeoStatusBroadcastReceiver;

    private StationLocation mOriginStation;
    private StationLocation mDestinationStation;

    private static final int REQUEST_PICK_STATION = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        initialiseUi();

        if(!runtimePermissions()) {
            mBtnGo.setEnabled(true);
            mTvDestination.setEnabled(true);
            Intent i = new Intent(getApplicationContext(), GeolocationService.class);
            startService(i);
        }
    }

    private void initialiseUi() {
        ButterKnife.bind(this);

        mBtnGo.setOnClickListener(this);
        mTvDestination.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isServiceRunning(GeolocationService.class)) {
            stopService(new Intent(getApplicationContext(), GeolocationService.class));
            startService(new Intent(getApplicationContext(), GeolocationService.class));
        } else {
            startService(new Intent(getApplicationContext(), GeolocationService.class));
        }

        if (mGeoBroadcastReceiver == null) {
            mGeoBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle bundle = intent.getExtras();

                    if (bundle.getParcelable(Constants.EXTRA_CURRENT_LATLNG) != null) {
                        LatLng currentPosition = bundle.getParcelable(
                                Constants.EXTRA_CURRENT_LATLNG);

                        new FetchNearestStationTask(currentPosition).execute();
                    }
                }
            };
        }
        registerReceiver(mGeoBroadcastReceiver, new IntentFilter(Constants.ACTION_LOCATION_UPDATE));

        if (mGeoStatusBroadcastReceiver == null) {
            mGeoStatusBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle bundle = intent.getExtras();

                    if (bundle != null) {
                        if (bundle.containsKey(Constants.EXTRA_LOCATION_DISCONNECTED)) {
                            Toast.makeText(FormActivity.this,
                                    R.string.error_msg_location_disconnected, Toast.LENGTH_SHORT)
                                    .show();
                        }
                        if (bundle.containsKey(Constants.EXTRA_NETWORK_LOST)) {
                            Toast.makeText(FormActivity.this, R.string.error_msg_network_lost,
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (bundle.containsKey(Constants.EXTRA_CONN_FAILED)) {
                            Toast.makeText(FormActivity.this, getString(R.string.error_tag)
                                    + bundle.getString(Constants.EXTRA_CONN_FAILED),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            };
        }
        registerReceiver(mGeoStatusBroadcastReceiver,
                new IntentFilter(Constants.ACTION_GEO_STATUS));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGeoBroadcastReceiver != null) {
            unregisterReceiver(mGeoBroadcastReceiver);
        }
        if (mGeoStatusBroadcastReceiver != null) {
            unregisterReceiver(mGeoStatusBroadcastReceiver);
        }

        if (isServiceRunning(GeolocationService.class)) {
            stopService(new Intent(getApplicationContext(), GeolocationService.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // apprarently, it does not check enough that a reciever is registered
        if (mGeoBroadcastReceiver != null) {
            unregisterReceiver(mGeoBroadcastReceiver);
        }
        if (mGeoStatusBroadcastReceiver != null) {
            unregisterReceiver(mGeoStatusBroadcastReceiver);
        }

        if (isServiceRunning(GeolocationService.class)) {
            stopService(new Intent(getApplicationContext(), GeolocationService.class));
        }
    }

    private boolean runtimePermissions() {
        mBtnGo.setEnabled(false);
        mTvDestination.setEnabled(false);
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest
                .permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission
                        .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                    }
                    , Constants.REQUEST_LOCATION_PERMISSION);
            mBtnGo.setEnabled(true);
            mTvDestination.setEnabled(true);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Constants.REQUEST_LOCATION_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]
                    == PackageManager.PERMISSION_GRANTED) {
                if (isServiceRunning(GeolocationService.class)) {
                    stopService(new Intent(getApplicationContext(), GeolocationService.class));
                    startService(new Intent(getApplicationContext(), GeolocationService.class));
                } else {
                    startService(new Intent(getApplicationContext(), GeolocationService.class));
                }
            } else {
                Toast.makeText(FormActivity.this, R.string.permission_rationale_location,
                        Toast.LENGTH_SHORT).show();
                runtimePermissions();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_form_btn_go:
                if (mOriginStation != null && mDestinationStation != null) {
                    new FetchDirectionsTask().execute();
                } else if (mOriginStation == null && mDestinationStation == null) {
                    Toast.makeText(FormActivity.this, getString(R.string.msg_wait),
                            Toast.LENGTH_SHORT).show();
                } else if (mOriginStation == null) {
                    Toast.makeText(FormActivity.this, getString(R.string.msg_wait),
                            Toast.LENGTH_SHORT).show();
                } else if (mDestinationStation == null) {
                    Toast.makeText(FormActivity.this, getString(R.string.msg_empty_destination),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.act_form_tv_destination:
                if (mOriginStation != null) {
                    Intent pickStationIntent = new Intent(getApplicationContext(),
                            StationsActivity.class);
                    pickStationIntent.putExtra(Constants.EXTRA_STATION_INDEX, mOriginStation
                            .getStopIndex());
                    startActivityForResult(pickStationIntent, REQUEST_PICK_STATION);
                } else if (mOriginStation == null && mDestinationStation == null) {
                    Toast.makeText(FormActivity.this, getString(R.string.msg_wait),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICK_STATION) {
            if (resultCode == Activity.RESULT_OK) {
                int destinationIndex = data.getIntExtra(Constants.EXTRA_STATION_INDEX,
                        Constants.STATION_INDEX_DEFAULT_VALUE);
                if (destinationIndex > Constants.ZERO) {
                    new FetchStationTask(destinationIndex).execute();
                }
            }
        }
    }

    private class FetchNearestStationTask extends AsyncTask<Void, Void, StationLocation> {

        private double mCurrentLat;
        private double mCurrentLng;

        public FetchNearestStationTask(LatLng currentPosition) {
            mCurrentLat = currentPosition.latitude;
            mCurrentLng = currentPosition.longitude;
        }

        @Override
        protected void onPreExecute() {
            mTvOriginStation.setVisibility(View.GONE);
            mOriginProgressRing.setVisibility(View.VISIBLE);
        }

        @Override
        protected StationLocation doInBackground(Void... params) {
            List<StationLocation> result;

            GtfsApi gtfsApi = GtfsApi.retrofit.create(GtfsApi.class);
            Call<DreamFactoryResource<StationLocation>> call = gtfsApi.getAllStationLocations();
            DreamFactoryResource<StationLocation> response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                call.cancel();
                cancel(true);
            }

            result = response != null ? response.getData() : null;
            return LocationMath.nearest(result, mCurrentLat, mCurrentLng);
        }

        @Override
        protected void onPostExecute(StationLocation result) {
            mOriginProgressRing.setVisibility(View.GONE);
            mTvOriginStation.setVisibility(View.VISIBLE);
            mTvOriginStation.setText(result.getStopName());
            mOriginStation = result;
        }

        @Override
        protected void onCancelled(StationLocation stationLocation) {
            Toast.makeText(FormActivity.this, getString(R.string.error_msg_network_lost),
                    Toast.LENGTH_SHORT);
        }
    }

    private class FetchStationTask extends AsyncTask<Void, Void, StationLocation> {

        private int mStationIndex;

        public FetchStationTask(int stationIndex) {
            mStationIndex = stationIndex;
        }

        @Override
        protected StationLocation doInBackground(Void... params) {
            GtfsApi gtfsApi = GtfsApi.retrofit.create(GtfsApi.class);
            Call<StationLocation> call = gtfsApi.getStation(mStationIndex);
            StationLocation response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                call.cancel();
                cancel(true);
            }

            return response;
        }

        @Override
        protected void onPostExecute(StationLocation result) {
            mTvDestination.setText(result.getStopName());
            mDestinationStation = result;
        }

        @Override
        protected void onCancelled(StationLocation stationLocation) {
            Toast.makeText(FormActivity.this, getString(R.string.error_msg_network_lost),
                    Toast.LENGTH_SHORT);
        }
    }

    private class FetchDirectionsTask extends AsyncTask<Void, Void, List<Step>> {

        @Override
        protected void onPreExecute() {
            mLoadingDialog = ProgressDialog.show(FormActivity.this, null,
                    getString(R.string.msg_loading), true, false);
        }

        @Override
        protected List<Step> doInBackground(Void... params) {
            DirectionsApi directionsApi = DirectionsApi.retrofit.create(DirectionsApi.class);
            Call<List<Step>> call = directionsApi.getDirection(
                    (mOriginStation.getStopLat() + Constants.COMMA + mOriginStation.getStopLon()),
                    (mDestinationStation.getStopLat() + Constants.COMMA +
                            mDestinationStation.getStopLon())
            );
            List<Step> response = null;
            try {
                response = call.execute().body();
            } catch (IOException e) {
                call.cancel();
                cancel(true);
            }
            return response;
        }

        @Override
        protected void onPostExecute(List<Step> result) {
            mLoadingDialog.dismiss();
            if (result.size() == Constants.ZERO) {
                Toast.makeText(FormActivity.this, R.string.msg_no_results, Toast.LENGTH_SHORT)
                        .show();
            } else {
                Intent tripIntent = new Intent(getApplicationContext(), TripActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constants.KEY_STEPS,
                        (ArrayList<? extends Parcelable>) result);
                tripIntent.putExtras(bundle);
                startActivity(tripIntent);
            }
        }
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service :
                manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
