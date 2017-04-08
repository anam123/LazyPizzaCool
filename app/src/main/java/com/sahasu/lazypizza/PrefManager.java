package com.sahasu.lazypizza;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by himanshu on 4/7/2017.
 */

public class PrefManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Context context;

    int PRIVATE_MODE = 0;

    // shared preferences file name
    private static final String PREF_NAME = "LazyPizza";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context c)
    {
        context = c;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }
    public void setFirstTimeLaunch(boolean isFirstTime)
    {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }
    public boolean isFirstTimeLaunch()
    {
        return preferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


}
