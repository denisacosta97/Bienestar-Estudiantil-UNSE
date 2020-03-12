package com.unse.bienestarestudiantil.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Profesor;

import java.util.ArrayList;
import java.util.prefs.PreferencesFactory;

public class ProfesorRepo {

    private Profesor mProfesor;

    public ProfesorRepo(Context context) {

        Utils.initBD(context);
        mProfesor = new Profesor();
    }

    public Profesor getProfesors() {
        return mProfesor;
    }

    public void setProfesors(Profesor profesor) {
        this.mProfesor = profesor;
    }

    static String createTable() {
        return String.format("create table %s(%s %s %s,%s %s %s,%s %s %s,%s %s %s)",
                Profesor.TABLE,
                Profesor.KEY_ID_PRO, Utils.INT_TYPE, Utils.AUTO_INCREMENT,
                Profesor.KEY_PRFN, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Profesor.KEY_FECHA_ING, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Profesor.KEY_CHK_DATA, Utils.STRING_TYPE, Utils.NULL_TYPE);
    }
    
    public ContentValues loadValues(Profesor profesor){
        ContentValues values = new ContentValues();
        values.put(Profesor.KEY_ID_PRO, profesor.getIdProfesor());
        values.put(Profesor.KEY_PRFN, profesor.getProfesion());
        values.put(Profesor.KEY_FECHA_ING, profesor.getFechaIngreso());
        values.put(Profesor.KEY_CHK_DATA, profesor.getCheckData());
        return values;
    }

    public Profesor loadCursor(Cursor cursor){
        Profesor profesor = new Profesor();
        profesor.setIdProfesor(cursor.getInt(0));
        profesor.setProfesion(cursor.getString(1));
        profesor.setFechaIngreso(cursor.getString(2));
        profesor.setCheckData(cursor.getString(3));
        return profesor;
    }

    public int insert(Profesor profesor) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();

        float x = db.insert(Profesor.TABLE, null, loadValues(profesor));
        DBManager.getInstance().closeDatabase();
        return (int)x;
    }


    public void delete(Profesor profesor) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Profesor.TABLE, Profesor.KEY_ID_PRO + " = ?", new String[]{String.valueOf(profesor.getIdProfesor())});
        DBManager.getInstance().closeDatabase();
    }


    public Profesor get(int id) {
        mProfesor = new Profesor();
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Profesor.TABLE + " where " + Profesor.KEY_ID_PRO + " = " + id, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mProfesor = loadCursor(cursor);
            cursor.close();
        }
        DBManager.getInstance().closeDatabase();
        return mProfesor;
    }

    public boolean isExist(int numero) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Profesor.TABLE + " where " + Profesor.KEY_ID_PRO + " = " + numero, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        DBManager.getInstance().closeDatabase();
        return false;

    }

    public void update(Profesor profesor) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String id = String.valueOf(profesor.getIdProfesor());
        String selection = Profesor.KEY_ID_PRO + " = " + id;
        db.update(Profesor.TABLE, loadValues(profesor), selection, null);
        DBManager.getInstance().closeDatabase();
    }

    public int getCount() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String countQuery = "SELECT  * FROM " + Profesor.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        DBManager.getInstance().closeDatabase();
        return count;
    }

    public ArrayList<Profesor> getAll() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Profesor> list = new ArrayList<Profesor>();
        String query = String.format("select * from %s", Profesor.TABLE);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mProfesor = loadCursor(cursor);
                list.add(mProfesor);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public ArrayList<Profesor> getAllByProfesor(int linea) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Profesor> list = new ArrayList<Profesor>();
        String query = String.format("select * from %s where %s = %s", Profesor.TABLE, Profesor.KEY_ID_PRO, linea);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mProfesor = loadCursor(cursor);
                list.add(mProfesor);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public void deleteAll(){
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Profesor.TABLE,null,null);
        DBManager.getInstance().closeDatabase();
    }
    
}
