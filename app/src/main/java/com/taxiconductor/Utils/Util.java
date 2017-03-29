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
}
