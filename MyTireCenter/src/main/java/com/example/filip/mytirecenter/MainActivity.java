package com.example.filip.mytirecenter;

import android.Manifest;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.filip.mytirecenter.fragments.FavouriteTireCentersFragment;
import com.example.filip.mytirecenter.fragments.FragmentEmptyDetails;
import com.example.filip.mytirecenter.fragments.SearchTireCentersFragment;
import com.example.filip.mytirecenter.fragments.SettingsFragment;
import com.example.filip.mytirecenter.fragments.TireCenterDetailsFragment;
import com.example.filip.mytirecenter.fragments.TireCentersListFragment;
import com.example.filip.mytirecenter.fragments.TireCentersListFragment.OnMiniMapListener;
import com.example.filip.mytirecenter.fragments.TireCentersListFragment.TireCenterAdapter.OnTireCenterListener;
import com.example.filip.mytirecenter.fragments.TireCentersMapFragment;
import com.example.filip.mytirecenter.model.TireCenter;
import com.example.filip.mytirecenter.utils.LocationProvider;
import com.example.filip.mytirecenter.utils.MyTireCenterDatabaseHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * This class is the implementation of the main activity.
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements LocationProvider, OnTireCenterListener, OnMiniMapListener, OnMapReadyCallback {

    /**
     * The Key to use in Bundle for the TireCenter selected
     */
    public static final String TIRE_CENTER_SELECTED_KEY = "TIRE_CENTER_SELECTED_KEY";
    /**
     * The Action to use for update location
     */
    public static final String LOCATION_UPDATE_ACTION = "com.example.filip.mytirecenter.action.LOCATION_UPDATE";

    // The Tag for the Log
    private static final String TAG = MainActivity.class.getSimpleName();

    // The Tag for the error dialog
    private static final String DIALOG_ERROR_TAG = "dialog_error";

    // The request code to access location permission
    private static final int REQUEST_ACCESS_LOCATION = 1;

    // The request code for the resolve error Intent
    private static final int REQUEST_RESOLVE_ERROR = 1001;

    // The Key to use in Bundle for state
    private static final String RESOLVING_ERROR_STATE_KEY = "RESOLVING_ERROR_STATE_KEY";

    // Request code for update location
    private static final int UPDATE_LOCATION_REQUEST_CODE = 2;

    // The default value for the Zoom
    private final static float DEFAULT_ZOOM = 12.0f;

    // The smaller interval to get location information
    private final static long FASTEST_INTERVAL = 1000L;

    // The interval to receive location update
    private final static long UPDATE_INTERVAL = 1000L;

    // Min distance value to update the location
    private final static int MIN_DISPLACEMENT = 20;

    // Test if the app is already resolving an error
    private boolean mResolvingError = false;

    // ApiClient for Google Play Services
    private GoogleApiClient mGoogleApiClient;

    // The OnConnectionFailedListener implementation
    private final GoogleApiClient.OnConnectionFailedListener mOnConnectionFailedListener =
            new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                    if (mResolvingError) {
                        Log.d(TAG, "already resolving");
                        // already managing an error
                    } else if (connectionResult.hasResolution()) {
                        try {
                            mResolvingError = true;
                            connectionResult.startResolutionForResult(MainActivity.this, REQUEST_RESOLVE_ERROR);
                        } catch (IntentSender.SendIntentException e) {
                            mGoogleApiClient.connect();
                        }
                    } else {
                        showErrorDialog(connectionResult.getErrorCode());
                        mResolvingError = true;
                    }
                }
            };

    // The Layout for the Drawer
    private DrawerLayout mDrawerLayout;

    // The Object used to navigate into the drawer
    private NavigationView mNavigationView;

    // The current Location
    private Location mCurrentLocation;

    // The ConnectionCallbacks implementation
    private final GoogleApiClient.ConnectionCallbacks mConnectionCallbacks =
            new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {
                    Log.d(TAG, "onConnectionSuspended");
                    manageLocationPermission();
                }

                @Override
                public void onConnectionSuspended(int i) {
                    Log.d(TAG, "onConnectionSuspended");
                }
            };

    // The reference to the Map
    private GoogleMap mGoogleMap;

    // Is two pane mode
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTwoPane = findViewById(R.id.left_fragment) != null;
        if (savedInstanceState == null) {
            if (mTwoPane) {
                final TireCentersListFragment leftFragment = new TireCentersListFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.left_fragment, leftFragment, TireCentersListFragment.TAG)
                        .commit();
                final FragmentEmptyDetails rightFragment = new FragmentEmptyDetails();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.anchor_point, rightFragment, FragmentEmptyDetails.TAG)
                        .commit();
            } else {
                FragmentManager fm = getSupportFragmentManager();
                Fragment found = fm.findFragmentByTag(TireCenterDetailsFragment.TAG);
                FragmentTransaction ft = fm.beginTransaction();
                if (found != null) {
                    ft.replace(R.id.anchor_point, found);
                } else {
                    ft.replace(R.id.anchor_point, new TireCentersListFragment());
                }
                ft.commit();
            }
        } else {
            mResolvingError = savedInstanceState.getBoolean(RESOLVING_ERROR_STATE_KEY, false);
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.d(TAG, "Drawer Opened");
                showMapInDrawerHeader();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d(TAG, "Drawer Closed");
            }
        });

        mNavigationView = findViewById(R.id.navigation);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mNavigationView.inflateHeaderView(R.layout.drawer_header_map);
            //the fragment
            final SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.header_map);
            mapFragment.getMapAsync(this);
        } else {
            mNavigationView.inflateHeaderView(R.layout.drawer_header_image);
        }

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                showFragment(menuItem);
                Log.d(TAG, "Selected " + menuItem);
                mDrawerLayout.closeDrawer(mNavigationView);
                return false;
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(mConnectionCallbacks)
                .addOnConnectionFailedListener(mOnConnectionFailedListener)
                .build();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(RESOLVING_ERROR_STATE_KEY, mResolvingError);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mResolvingError) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        final PendingIntent callbackIntent = PendingIntent
                .getBroadcast(this, UPDATE_LOCATION_REQUEST_CODE,
                        new Intent(LOCATION_UPDATE_ACTION), PendingIntent.FLAG_UPDATE_CURRENT);
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(callbackIntent);
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    // Utility method to show the Fragment for the given menu position
    private void showFragment(final MenuItem menuItem) {
        final int itemId = menuItem.getItemId();
        final Fragment nextFragment;
        final String TAG;
        switch (itemId) {
            case R.id.nearest_tire_centers:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    manageLocationPermission();
                    nextFragment = null;
                    TAG = null;
                    break;
                }
                nextFragment = TireCentersMapFragment.newInstance(MyTireCenterDatabaseHelper.getMyTireCenterDatabase(this).getAllTireCenters());
                TAG = TireCentersMapFragment.TAG;
                break;
            case R.id.search_tire_center:
                nextFragment = new SearchTireCentersFragment();
                TAG = SearchTireCentersFragment.TAG;
                break;
            case R.id.settings:
                nextFragment = new SettingsFragment();
                TAG = SettingsFragment.TAG;
                break;
            case R.id.favourite:
                nextFragment = new FavouriteTireCentersFragment();
                TAG = FavouriteTireCentersFragment.TAG;
                break;
            default:
                throw new IllegalArgumentException("No Fragment for the given menu item");
        }
        if (nextFragment != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(null);
            int container = R.id.anchor_point;
            if (mTwoPane && itemId != R.id.nearest_tire_centers) {
                container = R.id.left_fragment;
            }
            ft.replace(container, nextFragment, TAG);
            ft.commit();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_ACCESS_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNavigationView.inflateHeaderView(R.layout.drawer_header_map);
                //the fragment
                final SupportMapFragment mapFragment =
                        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.header_map);
                mapFragment.getMapAsync(this);
                startLocationListener();
            } else {
                // In this case we cannot manage location so the app is not working
                new AlertDialog.Builder(this)
                        .setTitle(R.string.no_location_permission_title)
                        .setMessage(R.string.no_location_permission_message)
                        .setPositiveButton(android.R.string.ok, null)
                        .create()
                        .show();
            }
        }
    }

    // Contains the logic to access the current Location
    private void manageLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // We show a message which explain the reason
                // Here we show the Dialog for the message
                new AlertDialog.Builder(this)
                        .setTitle(R.string.permission_reason_title)
                        .setMessage(R.string.permission_reason_message)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // We retry to acquire the permission
                                ActivityCompat.requestPermissions(
                                        MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_ACCESS_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(
                        this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_ACCESS_LOCATION);
            }
        } else {
            LocationServices.getFusedLocationProviderClient(this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.d(TAG, "onSuccess");
                    if (location != null) {
                        mCurrentLocation = location;
                    }
                }
            });
            startLocationListener();
        }
    }

    // Utility method to get Location updates
    private void startLocationListener() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(FASTEST_INTERVAL)
                .setSmallestDisplacement(MIN_DISPLACEMENT)
                .setInterval(UPDATE_INTERVAL);
        final PendingIntent callbackIntent = PendingIntent
                .getBroadcast(this, UPDATE_LOCATION_REQUEST_CODE,
                        new Intent(LOCATION_UPDATE_ACTION), PendingIntent.FLAG_UPDATE_CURRENT);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(this)
                    .requestLocationUpdates(locationRequest, callbackIntent);
        }

    }

    // Show the map in the drawer header
    private void showMapInDrawerHeader() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || mGoogleMap == null || mCurrentLocation == null) {
            return;
        }
        final LatLng currentLatLng = new LatLng(mCurrentLocation.getLatitude(),
                mCurrentLocation.getLongitude());
        final CameraUpdate cameraUpdate = CameraUpdateFactory
                .newLatLngZoom(currentLatLng, DEFAULT_ZOOM);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.setMyLocationEnabled(true);
    }

    // Show the error dialog
    private void showErrorDialog(int errorCode) {
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR_TAG, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), DIALOG_ERROR_TAG);
    }

    /**
     * This method dismiss error dialog and stop resolving phase
     */
    public void onDialogDismissed() {
        mResolvingError = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            mResolvingError = false;
            if (resultCode == RESULT_OK) {
                if (!mGoogleApiClient.isConnecting() &&
                        !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            }
        }
    }

    @Nullable
    @Override
    public Location getLocation() {
        return mCurrentLocation;
    }

    @Override
    public void onTireCenterClicked(TireCenter tireCenter) {
        final TireCenterDetailsFragment fragment = TireCenterDetailsFragment.newInstance(tireCenter);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.anchor_point, fragment, TireCenterDetailsFragment.TAG);
        ft.commit();
    }

    @Override
    public void onMiniMapClicked() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            manageLocationPermission();
        } else {
            final TireCentersMapFragment fragment = TireCentersMapFragment.newInstance(MyTireCenterDatabaseHelper.getMyTireCenterDatabase(this).getAllTireCenters());
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.anchor_point, fragment, TireCentersMapFragment.TAG);
            ft.commit();
        }
    }

    /**
     * Verify if is in two pane mode
     *
     * @return true if is two pane mode, false otherwise
     */
    public boolean isTwoPane() {
        return mTwoPane;
    }

    /**
     * This class is used to create a Dialog for the error. It's static to prevent memory leak
     */
    public static class ErrorDialogFragment extends DialogFragment {

        public ErrorDialogFragment() {
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR_TAG);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            ((MainActivity) getActivity()).onDialogDismissed();
        }
    }

}
