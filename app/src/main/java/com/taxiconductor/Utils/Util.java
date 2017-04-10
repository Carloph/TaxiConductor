package com.taxiconductor.Utils;

import android.content.SharedPreferences;

/**
 * Created by carlos on 18/03/17.
 */

public class Util {

    public static String getUserPrefs(SharedPreferences preferences){
        return preferences.getString("user", "");
    }

    public static int getIdDriverPrefs(SharedPreferences preferences){
        return preferences.getInt("id_driver", 0);
    }

    public static int getStatePrefs(SharedPreferences preferences){
        return preferences.getInt("saved_state", 0);
    }

    public static int getCounterPrefs(SharedPreferences preferences){
        return preferences.getInt("counter", 0);
    }

    public static String getOriginPrefs(SharedPreferences preferences){
        return preferences.getString("coordinates_origin", "");
    }

    public static String getDestinationPrefs(SharedPreferences preferences){
        return preferences.getString("coordinates_destination", "");
    }

    public static boolean getValidatorPrefs(SharedPreferences preferences){
        return preferences.getBoolean("validator_occupied",true);
    }
}
