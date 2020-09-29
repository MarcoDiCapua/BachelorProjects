package com.example.filip.mytirecenter.fragments;

import android.os.Bundle;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.filip.mytirecenter.utils.FavouriteTireCentersManager;
import com.example.filip.mytirecenter.R;

/**
 * The placeholder for the SettingsFragment
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class SettingsFragment extends PreferenceFragmentCompat
        implements DialogPreference.TargetFragment {

    /**
     * The Tag for this Fragment
     */
    public static final String TAG = SettingsFragment.class.getSimpleName();

    // clear favourite key
    private static final String CLEAR_FAVOURITE_KEY = "clear_favourite";

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        findPreference(CLEAR_FAVOURITE_KEY).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.d(TAG, "Clear favourite! ");
                FavouriteTireCentersManager.get(getActivity()).clear();
                Toast.makeText(getActivity(), R.string.tire_centers_cleared, Toast.LENGTH_SHORT)
                        .show();
                return true;
            }
        });
    }
}
