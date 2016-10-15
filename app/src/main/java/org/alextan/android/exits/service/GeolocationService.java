package org.alextan.android.exits.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.alextan.android.exits.Constants;

public class GeolocationService extends Service implements LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 10 * 1000;
    private long FASTEST_INTERVAL = 2000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("GeoService", "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("GeoService", "onCreate");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        Log.d("GeoService", "onDestroy");

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("GeoService", "onLocationChanged");

        Intent updateLocationIntent = new Intent(Constants.ACTION_LOCATION_UPDATE);
        updateLocationIntent.putExtra(Constants.EXTRA_CURRENT_LATLNG, new LatLng(location.getLatitude(), location.getLongitude()));
        sendBroadcast(updateLocationIntent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("GeoService", "onConnected");

        // Get last known recent location.

        //noinspection MissingPermission
        Location currentLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        // Note that this can be NULL if last location isn't already known.
        if (currentLocation != null) {
            // Print current location if not null
            Log.d("DEBUG", "current location: " + currentLocation.toString());
            LatLng latLng = new LatLng(currentLocation.getLatitude(),
                    currentLocation.getLongitude());

            //TODO: make intent here?
        }
        // Begin polling for new location updates.
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        Log.d("GeoService", "startLocationUpdates");

        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if (!runtimePermissions()) {
            //noinspection MissingPermission
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("GeoService", "onConnectionSuspended");

        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Intent geoStatusIntent = new Intent(Constants.ACTION_GEO_STATUS);
            geoStatusIntent.putExtra(Constants.EXTRA_LOCATION_DISCONNECTED, "");
            sendBroadcast(geoStatusIntent);
        } else if (i == CAUSE_NETWORK_LOST) {
            Intent geoStatusIntent = new Intent(Constants.ACTION_GEO_STATUS);
            geoStatusIntent.putExtra(Constants.EXTRA_NETWORK_LOST, "");
            sendBroadcast(geoStatusIntent);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("GeoService", "onConnectionFailed");
        Intent geoStatusIntent = new Intent(Constants.ACTION_GEO_STATUS);
        geoStatusIntent.putExtra(Constants.EXTRA_CONN_FAILED, connectionResult.getErrorMessage());
        sendBroadcast(geoStatusIntent);
    }

    private boolean runtimePermissions() {
        Log.d("GeoService", "runtimePermissions");
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest
                .permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission
                        .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
}
