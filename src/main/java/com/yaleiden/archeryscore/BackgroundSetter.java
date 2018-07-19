package com.yaleiden.archeryscore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

/**
 * Handles changing and returning a background image resource.
 * This class handles bkg resources changes and setting
 *
 **/
public class BackgroundSetter {

    private static final String TAG = "BackgroundSetter";
    private final Context localContext;
    private final View layout;
    private static final  String[] name = {"Silhoutte", "Carbon fiber", "Paisley", "Rough wood", "Diamond plate", "BW funky", "Rainbow", "Cats", "Black"};
    private static final  int[] bkg_resource = {R.drawable.bkg_tile_0, R.drawable.bkg_tile_1, R.drawable.bkg_tile_2, R.drawable.bkg_tile_3,
            R.drawable.bk_diamond_plate, R.drawable.bkg_tile_5, R.drawable.bkg_tile_6, R.drawable.bkg_tile_7, R.drawable.bk_black};

    /**
     * Constructor for background setter
     * @param context
     * @param layout
     */
    public BackgroundSetter(Context context, View layout) {
        this.localContext = context;
        this.layout = layout;
    }

    /**
     * Returns background image drawable resource
     *
     * @return int   the id of the selected image drawable
     **/

    private int getBkg() {
        int appBkg;
        SharedPreferences app_preferences = localContext.getSharedPreferences("USER_PREFS",
                0);

        appBkg = app_preferences.getInt("APP_BKG", 0);

            if (BuildConfig.DEBUG) {
                Log.d(TAG, "getBkg() "+appBkg + " "+getBkgName());
            }
        return bkg_resource[appBkg];

    }

    /**
     *
     * @return String the name for the currently displayed background
     */
    public String getBkgName() {
        int appBkg;
        SharedPreferences app_preferences = localContext.getSharedPreferences("USER_PREFS",
                8);

        appBkg = app_preferences.getInt("APP_BKG", 0);

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "getBkgName() ");
        }
        return name[appBkg];

    }

    /**
     * Changes background image resource by incrementing an integer stored in prefs.
     **/
    void incrementBkg() {
        int appBkg;
        SharedPreferences app_preferences = localContext.getSharedPreferences("USER_PREFS",
                0);

        appBkg = app_preferences.getInt("APP_BKG", 0);

        appBkg = appBkg + 1;

        if (appBkg > 8) {
            appBkg = 0;
        }
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putInt("APP_BKG", appBkg);
        editor.commit();
    }

    @SuppressLint("NewApi")
    public void applyBkg() {

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "applyBkg ");
        }

        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            layout.setBackgroundDrawable(localContext.getResources().getDrawable(
                    getBkg()));
        } else {
            layout.setBackgroundResource(getBkg());
        }
    }

}
