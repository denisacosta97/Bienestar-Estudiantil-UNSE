package com.unse.bienestarestudiantil.Herramientas.Almacenamiento;

import android.content.Context;
import android.content.SharedPreferences;

import com.unse.bienestarestudiantil.Herramientas.Utils;

public class PreferenceManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int MODE = 0;
    private static final String PREFERENCE = "Bienestar";

    public PreferenceManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFERENCE, MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(Utils.IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean FirstLaunch() {
        return pref.getBoolean(Utils.IS_FIRST_TIME_LAUNCH, true);
    }

    public String getValueString(String tag) {
        return pref.getString(tag, "");
    }

    public int getValueInt(String tag) {
        return pref.getInt(tag, 0);
    }

    public void setValue(String tag, String value) {
        editor.putString(tag, value);
        editor.commit();
    }

    public void setValue(String tag, int value) {
        editor.putInt(tag, value);
        editor.commit();
    }

    public boolean getValue(String tag) {
        return pref.getBoolean(tag, false);
    }

    public void setValue(String tag, boolean value) {
        editor.putBoolean(tag, value);
        editor.commit();
    }

}
