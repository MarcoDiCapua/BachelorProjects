package com.example.filip.mytirecenter.fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filip.mytirecenter.MainActivity;
import com.example.filip.mytirecenter.R;
import com.example.filip.mytirecenter.model.TireCenter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * The Fragment to show TireCenter in the Map
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class TireCentersMapFragment extends LocationAwareFragment implements OnMapReadyCallback {

    /**
     * The Tag for this class
     */
    public static final String TAG = TireCentersMapFragment.class.getSimpleName();

    // The key to use to show TireCenter
    private final static String TIRE_CENTER_LIST_ARG = "TIRE_CENTER_LIST_ARG";
    // The default value for the Zoom
    private final static float DEFAULT_ZOOM = 12.0f;
    // The default value for animation duration (in millisec)
    private final static int MAP_ANIMATION_DURATION = 400;
    // The key for the map type in the SharedPreferences
    private final static String MAP_TYPE_PREFS_KEY = "map_type";
    // The Default map type as String that identify the map type
    private final static String DEFAULT_MAP_TYPE = "1";

    // The reference to the GoogleMap object
    private GoogleMap mGoogleMap;

    //The model of the item to show, in this case the tire center
    private ArrayList<TireCenter> mModel;

    //The List of markers
    private List<Marker> mMarkers;

    //The user marker
    private Marker mUserMarker;

    //The MainActivity
    private MainActivity mActivity;

    /**
     * We create the instance of the model using the list of tire centers
     *
     * @param model The model of the TireCenter
     * @return The TireCentersMapFragment instance
     */
    public static TireCentersMapFragment newInstance(List<TireCenter> model) {
        final TireCentersMapFragment frag = new TireCentersMapFragment();
        final Bundle args = new Bundle();
        args.putParcelableArrayList(TIRE_CENTER_LIST_ARG, new ArrayList<>(model));
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mModel = getArguments().getParcelableArrayList(TIRE_CENTER_LIST_ARG);
        if (mModel != null) {
            mMarkers = new ArrayList<>(mModel.size());
        }
        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.map_anchor, mMapFragment)
                .commit();
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mActivity = (MainActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_tire_center_map, container, false);
        Toolbar mToolbar = layout.findViewById(R.id.my_toolbar);
        mToolbar.inflateMenu(R.menu.fragment_tire_center_list);
        mActivity.setSupportActionBar(mToolbar);
        if(mActivity.getSupportActionBar() != null){
            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return layout;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "map ready");
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
        }
        updateMapType();
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                TireCenter tireCenter = null;
                for (int i = 0; i < mMarkers.size(); i++) {
                    if (mMarkers.get(i).equals(marker)) {
                        tireCenter = mModel.get(i);
                    }
                }
                if (tireCenter != null) {
                    mActivity.onTireCenterClicked(tireCenter);
                }
            }
        });
        showTireCentersMarkers();
        showMyLocation(mActivity.getLocation());
    }

    @Override
    protected void onLocationAvailable() {
        Log.d(TAG, "on location available");
        super.onLocationAvailable();
        showMyLocation(super.getLocation());
    }

    // Method to show the Location in map using animation
    private void showMyLocation(final Location location) {
        if (location == null) {
            Log.d(TAG, "Null location given");
            return;
        }

        final LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        if (mUserMarker != null) {
            mUserMarker.remove();
        } else{
            final CameraUpdate cameraUpdate = CameraUpdateFactory
                    .newLatLngZoom(currentLatLng, DEFAULT_ZOOM);
            mGoogleMap.animateCamera(cameraUpdate, MAP_ANIMATION_DURATION,
                    new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            Log.d(TAG, "Animation completed successfully!");
                        }

                        @Override
                        public void onCancel() {
                            Log.d(TAG, "Animation Cancelled!");
                        }
                    });
        }

        MarkerOptions markerOptions = new MarkerOptions()
                .position(currentLatLng)
                .title(getResources().getString(R.string.my_position))
                .draggable(false)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .snippet(getResources().getString(R.string.my_position_snippet));
        mUserMarker = mGoogleMap.addMarker(markerOptions);
    }

    //This method gets the data of the map type and if they not correspond to the oldMapType
    //it set again the map type with the new data
    private void updateMapType() {
        if (mGoogleMap == null) {
            // if not present we do nothing
            return;
        }
        // We read the value from the SharedPreferences
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        final String mapTypeStr = sharedPref
                .getString(MAP_TYPE_PREFS_KEY, DEFAULT_MAP_TYPE);
        final int mapType = Integer.parseInt(mapTypeStr);
        final int oldMapType = mGoogleMap.getMapType();
        if (oldMapType != mapType) {
            mGoogleMap.setMapType(mapType);
        }
    }

    //This method create markers on the map showing the tire centers
    private void showTireCentersMarkers() {
        // We clean the previous markers if present
        for (Marker marker : mMarkers) {
            marker.remove();
        }
        mMarkers.clear();
        // We add the markers
        String address = getResources().getString(R.string.address);
        for (TireCenter tireCenter : mModel) {
            final LatLng tireCenterPosition = new LatLng(tireCenter.getLatitude(), tireCenter.getLongitude());
            final MarkerOptions markerOptions = new MarkerOptions()
                    .position(tireCenterPosition)
                    .title(tireCenter.getName())
                    .draggable(false)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .snippet(address + tireCenter.getAddress());
            final Marker tireCenterMarker = mGoogleMap.addMarker(markerOptions);
            mMarkers.add(tireCenterMarker);
        }
    }
}