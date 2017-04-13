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

    private static final String PRESSED_HOW_TO_USE = "PressedHowToUse";



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
    public  void setPressedHowToUse(boolean pressedHowToUse)
    {
        editor.putBoolean(PRESSED_HOW_TO_USE, pressedHowToUse);
        editor.commit();
    }

    public boolean isPressedHowToUse()
    {
        return preferences.getBoolean(PRESSED_HOW_TO_USE, false);
    }
    public boolean isFirstTimeLaunch()
    {
        return preferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


}
