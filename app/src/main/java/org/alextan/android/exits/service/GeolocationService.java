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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.alextan.android.exits.common.Constants;

/**
 * Service for accessing device's geolocation
 */
public class GeolocationService extends Service implements LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 15 * 1000;
    private long FASTEST_INTERVAL = 5 * 1000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
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
        Intent updateLocationIntent = new Intent(Constants.ACTION_LOCATION_UPDATE);
        updateLocationIntent.putExtra(Constants.EXTRA_CURRENT_LATLNG,
                new LatLng(location.getLatitude(), location.getLongitude()));
        sendBroadcast(updateLocationIntent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Get last known recent location.
        //noinspection MissingPermission
        Location currentLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        // Note that this can be NULL if last location isn't already known.
        if (currentLocation != null) {
            // Print current location if not null
        }
        // Begin polling for new location updates.
        startLocationUpdates();
    }

    /**
     * Poll for new location updates
     */
    protected void startLocationUpdates() {
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
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Intent geoStatusIntent = new Intent(Constants.ACTION_GEO_STATUS);
            geoStatusIntent.putExtra(Constants.EXTRA_LOCATION_DISCONNECTED, Constants.NULL);
            sendBroadcast(geoStatusIntent);
        } else if (i == CAUSE_NETWORK_LOST) {
            Intent geoStatusIntent = new Intent(Constants.ACTION_GEO_STATUS);
            geoStatusIntent.putExtra(Constants.EXTRA_NETWORK_LOST, Constants.NULL);
            sendBroadcast(geoStatusIntent);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Intent geoStatusIntent = new Intent(Constants.ACTION_GEO_STATUS);
        geoStatusIntent.putExtra(Constants.EXTRA_CONN_FAILED, connectionResult.getErrorMessage());
        sendBroadcast(geoStatusIntent);
    }

    /**
     * Checks if user has permissions for location
     */
    private boolean runtimePermissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest
                .permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission
                        .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
}
