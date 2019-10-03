package com.unse.bienestarestudiantil;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor spEditor;
    Context context;

    private static final String FIRST_LAUNCH = "firstLaunch";
    int MODE = 0;
    private static final String PREFERENCE = "Javapapers";

    public PreferenceManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCE, MODE);
        spEditor = sharedPreferences.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        spEditor.putBoolean(Utils.IS_FIRST_TIME_LAUNCH, isFirstTime);
        spEditor.commit();
    }

    public boolean FirstLaunch() {
        return sharedPreferences.getBoolean(Utils.IS_FIRST_TIME_LAUNCH, true);
    }
}
