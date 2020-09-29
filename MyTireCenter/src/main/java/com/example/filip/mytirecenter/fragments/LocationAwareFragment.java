package com.example.filip.mytirecenter.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.location.LocationResult;

/**
 * Fragment implementation which is aware of Location broadcast Intent
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class LocationAwareFragment extends Fragment {

    /**
     * The Tag for this class
     */
    public static final String TAG = LocationAwareFragment.class.getSimpleName();

    /**
     * The Action to use for update location
     */
    public static final String LOCATION_UPDATE_ACTION = "com.example.filip.mytirecenter.action.LOCATION_UPDATE";

    // The current Location
    private volatile Location mCurrentLocation;

    // The BroadcastReceiver for Location
    private LocationBroadcastReceiver mLocationBroadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationBroadcastReceiver = new LocationBroadcastReceiver();
    }

    @Override
    public void onStart() {
        super.onStart();
        final IntentFilter intentFilter = new IntentFilter(LOCATION_UPDATE_ACTION);
        getContext().registerReceiver(mLocationBroadcastReceiver, intentFilter);
        Log.d(TAG, "register receiver done");
    }

    @Override
    public void onStop() {
        super.onStop();
        getContext().unregisterReceiver(mLocationBroadcastReceiver);
        Log.d(TAG, "unregister receiver done");
    }

    /**
     * Invoked to notify that the Location is available
     *
     */
    protected void onLocationAvailable() {
    }


    // The LocationBroadcastReceiver to use for Location
    private class LocationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive");
            // extract the location from the Intent
            if (LocationResult.hasResult(intent)) {
                LocationResult locationResult = LocationResult.extractResult(intent);
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    // use the Location
                    mCurrentLocation = location;
                    onLocationAvailable();
                }
            }
        }
    }

    /**
     * @return The current location
     */
    protected Location getLocation() {
        if (mLocationBroadcastReceiver != null) {
            return mCurrentLocation;
        } else {
            return null;
        }
    }
}
