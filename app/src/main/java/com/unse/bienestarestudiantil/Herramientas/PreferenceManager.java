package com.unse.bienestarestudiantil.Herramientas;

import android.content.Context;
import android.content.SharedPreferences;

import com.unse.bienestarestudiantil.Herramientas.Utils;

public class PreferenceManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;
    private Context context;
    private int MODE = 0;
    private static final String PREFERENCE = "Bienestar";

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
