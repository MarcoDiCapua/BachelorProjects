package com.example.filip.mytirecenter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.filip.mytirecenter.model.TireCenter;

import java.util.LinkedList;
import java.util.List;

/**
 * This class provides methods to save and modify a list o favourite {@link TireCenter} objects
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class FavouriteTireCentersManager {

    // The name for this SharedPreferences
    private static final String SHARED_PREFERENCES_NAME = "FavouriteTireCentersManager";

    // Constant that identify an existing data
    private static final boolean PRESENT = true;

    // Constant that identify a non existing data
    private static final boolean NOT_PRESENT = false;

    private static final String IS_PRESENT = "FavouriteTireCenterIsPresent";

    // The Singleton instance
    private static FavouriteTireCentersManager sInstance;

    // The SharedPreferences to manage Favourite TireCenter
    private final SharedPreferences mSharedPreferences;

    // The Cache for the favourite
    private List<TireCenter> mFavouriteCache;

    // If true data are dirty
    private boolean mDirty = true;

    private FavouriteTireCentersManager(final Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Returns the FavouriteTireCentersManager singleton instance
     *
     * @param context The Context
     * @return The FavouriteTireCentersManager singleton
     */
    public static FavouriteTireCentersManager get(Context context) {
        if (sInstance == null) {
            sInstance = new FavouriteTireCentersManager(context);
        }
        return sInstance;
    }

    /**
     * Returns the FavouriteTireCentersManager Singleton instance already created with @get(Context) before
     *
     * @return The FavouriteTireCentersManager singleton
     */
    public static FavouriteTireCentersManager get() {
        if (sInstance == null) {
            throw new IllegalStateException("Invoked get(Context) before!");
        }
        return sInstance;
    }

    /**
     * Get the list of the favourite tire centers
     *
     * @return The List of favourite TireCenter
     */
    public @NonNull
    List<TireCenter> getFavouriteTireCenters() {
        if (!mDirty && mFavouriteCache != null) {
            return mFavouriteCache;
        }
        final boolean favouriteIsPresent = mSharedPreferences.getBoolean(IS_PRESENT + "0", false);
        if (favouriteIsPresent) {
            int i = 0;
            List<TireCenter> favouriteTemp = new LinkedList<>();
            while (mSharedPreferences.getBoolean(IS_PRESENT + i, false)) {
                final int id = mSharedPreferences.getInt(TireCenter.Keys.ID + i, 0);
                final String name = mSharedPreferences.getString(TireCenter.Keys.NAME + i, "");
                final String provinceCode = mSharedPreferences.getString(TireCenter.Keys.PROVINCE_CODE + i, "");
                final String address = mSharedPreferences.getString(TireCenter.Keys.ADDRESS + i, "unknown");
                final String telephoneNumber = mSharedPreferences.getString(TireCenter.Keys.TELEPHONE_NUMBER + i, "unknown");
                final float latitude = mSharedPreferences.getFloat(TireCenter.Keys.LATITUDE + i, 0.0f);
                final float longitude = mSharedPreferences.getFloat(TireCenter.Keys.LONGITUDE + i, 0.0f);
                final TireCenter tireCenter = TireCenter.Builder.create(id, name, provinceCode)
                        .withAddress(address)
                        .withTelephoneNumber(telephoneNumber)
                        .withLocation(latitude, longitude)
                        .build();
                favouriteTemp.add(tireCenter);
                i++;
            }
            mFavouriteCache = favouriteTemp;
        } else {
            mFavouriteCache = new LinkedList<>();
        }
        mDirty = false;
        return mFavouriteCache;
    }

    /**
     * Adds the given TireCenter to the Favourite
     *
     * @param tireCenter The TireCenter to add to favourite
     * @return True if the operation is successful, false otherwise
     */
    public boolean addFavourite(@NonNull final TireCenter tireCenter) {
        List<TireCenter> currentFavourite = getFavouriteTireCenters();
        if (currentFavourite == null) {
            currentFavourite = new LinkedList<>();
        }
        // We search the data to be sure we don't add twice
        int duplicateIndex = -1;
        for (int i = 0; i < currentFavourite.size(); i++) {
            final TireCenter item = currentFavourite.get(i);
            if (item.getId() == tireCenter.getId()) {
                duplicateIndex = i;
                break;
            }
        }
        if (duplicateIndex >= 0) {
            mFavouriteCache.set(duplicateIndex, tireCenter);
            save();
            mDirty = false;
        } else {
            // We add the current one
            currentFavourite.add(tireCenter);
            mDirty = save();
        }
        return mDirty;
    }

    /**
     * Force the update for the given favorite
     *
     * @param newFavourite The new favourite data
     * @return True if the replace had success, false otherwise
     */
    public boolean forceFavourite(final List<TireCenter> newFavourite) {
        mFavouriteCache = newFavourite;
        return save();
    }

    /**
     * Clear the data
     *
     * @return If the clear was successful or not
     */
    public boolean clear() {
        mFavouriteCache = new LinkedList<>();
        return save();
    }

    /**
     * Removed the TireCenter with the given id from Favourite
     *
     * @param tireCenter The tireCenter to remove
     * @return True if remove was successful and false otherwise
     */
    public boolean removeFavourite(final TireCenter tireCenter) {
        List<TireCenter> currentFavourite = getFavouriteTireCenters();
        boolean removed = false;
        if (currentFavourite != null) {
            mFavouriteCache.remove(tireCenter);
            removed = save();
        }
        return removed;
    }


    // Save the data into the cache when we add or remove data
    // Return True if the dave was successful and false otherwise
    private boolean save() {
        if (mFavouriteCache != null) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            for (int i = 0; i < mFavouriteCache.size(); i++) {
                TireCenter tireCenter = mFavouriteCache.get(i);
                editor.putBoolean(IS_PRESENT + i, PRESENT);
                editor.putInt(TireCenter.Keys.ID + i, tireCenter.getId());
                editor.putString(TireCenter.Keys.NAME + i, tireCenter.getName());
                editor.putString(TireCenter.Keys.PROVINCE_CODE + i, tireCenter.getProvinceCode());
                editor.putString(TireCenter.Keys.ADDRESS + i, tireCenter.getAddress());
                editor.putString(TireCenter.Keys.TELEPHONE_NUMBER + i, tireCenter.getTelephoneNumber());
                editor.putFloat(TireCenter.Keys.LATITUDE + i, tireCenter.getLatitude());
                editor.putFloat(TireCenter.Keys.LONGITUDE + i, tireCenter.getLongitude());
            }
            editor.putBoolean(IS_PRESENT + (mFavouriteCache.size()), NOT_PRESENT);
            return editor.commit();
        }
        return false;
    }

}
