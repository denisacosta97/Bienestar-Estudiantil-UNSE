package com.unse.bienestarestudiantil.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Rol;

import java.util.ArrayList;
import java.util.List;

public class RolViewModel {

    private Rol mRol;

    public RolViewModel(Context context) {
        mRol = new Rol();
    }

    public RolViewModel() {
        mRol = new Rol();
    }

    public Rol getRol() {
        return mRol;
    }

    public void setRol(Rol rol) {
        this.mRol = rol;
    }

    static String createTable() {
        return String.format("create table %s(%s %s %s,%s %s %s)",
                Rol.TABLE,
                Rol.KEY_USER, Utils.INT_TYPE, Utils.NULL_TYPE,
                Rol.KEY_ROL, Utils.INT_TYPE, Utils.NULL_TYPE);
    }


    public void insert(Rol rol) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Rol.KEY_USER, rol.getIdUsuario());
        values.put(Rol.KEY_ROL, rol.getIdRol());
        db.insert(Rol.TABLE, null, values);
        DBManager.getInstance().closeDatabase();
    }


    public void delete(Rol rol) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Rol.TABLE, Rol.KEY_ROL + " = ?", new String[]{String.valueOf(rol.getIdRol())});
        DBManager.getInstance().closeDatabase();
    }


    public Rol get(int id) {

        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Rol.TABLE + " where " + Rol.KEY_ROL + " = " + id, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mRol = new Rol();
            mRol.setIdUsuario(cursor.getInt(0));
            mRol.setIdRol(cursor.getInt(1));
            cursor.close();
        }

        DBManager.getInstance().closeDatabase();
        return mRol;
    }

    public void deleteAll() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Rol.TABLE, null, null);
        DBManager.getInstance().closeDatabase();
    }


    public boolean isExist(int numero) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Rol.TABLE + " where " + Rol.KEY_ROL + " = " + numero, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        DBManager.getInstance().closeDatabase();
        return false;

    }

    public void update(Rol rol) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Rol.KEY_ROL, rol.getIdRol());
        values.put(Rol.KEY_USER, rol.getIdUsuario());
        String id = String.valueOf(rol.getIdRol());
        String selection = Rol.KEY_ROL + " = " + id;
        db.update(Rol.TABLE, values, selection, null);
        DBManager.getInstance().closeDatabase();
    }


    public int getCount() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String countQuery = "SELECT  * FROM " + Rol.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        DBManager.getInstance().closeDatabase();
        cursor.close();
        return count;
    }

    public ArrayList<Rol> getAll(int user) {

        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Rol> list = new ArrayList<Rol>();
        String query = String.format("select * from %s where %s = %s", Rol.TABLE, Rol.KEY_USER, user);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mRol = new Rol();
                mRol.setIdUsuario(cursor.getInt(0));
                mRol.setIdRol(cursor.getInt(1));
                list.add(mRol);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public ArrayList<Rol> getAll() {

        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Rol> list = new ArrayList<Rol>();
        String query = String.format("select * from %s", Rol.TABLE);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mRol = new Rol();
                mRol.setIdUsuario(cursor.getInt(0));
                mRol.setIdRol(cursor.getInt(1));
                list.add(mRol);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }
}
