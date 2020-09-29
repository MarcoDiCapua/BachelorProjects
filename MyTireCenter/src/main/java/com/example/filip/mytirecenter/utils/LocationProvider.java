package com.example.filip.mytirecenter.utils;

import android.location.Location;
import android.support.annotation.Nullable;

/**
 * This is the interface that every Object responsible to provide location should implement
 */
public interface LocationProvider {


    /**
     * @return The current Location if any or null if not
     */
    @Nullable
    Location getLocation();

}

