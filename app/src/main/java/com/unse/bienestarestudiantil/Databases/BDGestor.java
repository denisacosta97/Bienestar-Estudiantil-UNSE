package com.unse.bienestarestudiantil.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BDGestor extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bienestar.db";
    private static final int DATABASE_VERSION = 2;


    public BDGestor(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(RolViewModel.createTable());
        sqLiteDatabase.execSQL(UsuarioViewModel.createTable());


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old, int newVers) {
        //onCreate(sqLiteDatabase);
        if (old == 1 && newVers == 2) {
            sqLiteDatabase.execSQL(UsuarioViewModel.createTable());
        }

    }
}
