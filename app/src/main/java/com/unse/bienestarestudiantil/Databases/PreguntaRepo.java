package com.unse.bienestarestudiantil.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Pregunta;

import java.util.ArrayList;

public class PreguntaRepo {

    private Pregunta mPregunta;

    public PreguntaRepo(Context context) {

        Utils.initBD(context);
        mPregunta = new Pregunta();
    }

    public Pregunta getPregunta() {
        return mPregunta;
    }

    public void setPregunta(Pregunta carrito) {
        this.mPregunta = carrito;
    }

    static String createTable() {
        return String.format("create table %s(%s %s %s,%s %s %s,%s %s %s,%s %s %s)",
                Pregunta.TABLE,
                Pregunta.KEY_ID_PREG, Utils.INT_TYPE, Utils.AUTO_INCREMENT,
                Pregunta.KEY_CUAL, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Pregunta.KEY_INTES, Utils.INT_TYPE, Utils.NULL_TYPE,
                Pregunta.KEY_UBI, Utils.STRING_TYPE, Utils.NULL_TYPE);
    }

    public int insert(Pregunta carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Pregunta.KEY_ID_PREG, carrito.getIdPregunta());
        values.put(Pregunta.KEY_CUAL, carrito.getCual());
        values.put(Pregunta.KEY_INTES, carrito.getIntensidad());
        values.put(Pregunta.KEY_UBI, carrito.getUbi());
        float x = db.insert(Pregunta.TABLE, null, values);
        DBManager.getInstance().closeDatabase();
        return (int)x;
    }


    public void delete(Pregunta carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Pregunta.TABLE, Pregunta.KEY_ID_PREG + " = ?", new String[]{String.valueOf(carrito.getIdPregunta())});
        DBManager.getInstance().closeDatabase();
    }


    public Pregunta get(int id) {
        mPregunta = new Pregunta();
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Pregunta.TABLE + " where " + Pregunta.KEY_ID_PREG + " = " + id, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mPregunta.setIdPregunta(cursor.getInt(0));
            mPregunta.setCual(cursor.getString(1));
            mPregunta.setIntensidad(cursor.getInt(2));
            mPregunta.setUbi(cursor.getString(3));
            cursor.close();
        }
        DBManager.getInstance().closeDatabase();
        return mPregunta;
    }

    public boolean isExist(int numero) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Pregunta.TABLE + " where " + Pregunta.KEY_ID_PREG + " = " + numero, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        DBManager.getInstance().closeDatabase();
        return false;

    }

    public void update(Pregunta carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Pregunta.KEY_ID_PREG, carrito.getIdPregunta());
        values.put(Pregunta.KEY_CUAL, carrito.getCual());
        values.put(Pregunta.KEY_INTES, carrito.getIntensidad());
        values.put(Pregunta.KEY_UBI, carrito.getUbi());
        String id = String.valueOf(carrito.getIdPregunta());
        String selection = Pregunta.KEY_ID_PREG + " = " + id;
        db.update(Pregunta.TABLE, values, selection, null);
        DBManager.getInstance().closeDatabase();
    }

    public int getCount() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String countQuery = "SELECT  * FROM " + Pregunta.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        DBManager.getInstance().closeDatabase();
        return count;
    }

    public ArrayList<Pregunta> getAll() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Pregunta> list = new ArrayList<Pregunta>();
        String query = String.format("select * from %s", Pregunta.TABLE);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mPregunta = new Pregunta();
                mPregunta.setIdPregunta(cursor.getInt(0));
                mPregunta.setCual(cursor.getString(1));
                mPregunta.setIntensidad(cursor.getInt(2));
                mPregunta.setUbi(cursor.getString(3));
                list.add(mPregunta);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public ArrayList<Pregunta> getAllByPregunta(int linea) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Pregunta> list = new ArrayList<Pregunta>();
        String query = String.format("select * from %s where %s = %s", Pregunta.TABLE, Pregunta.KEY_ID_PREG, linea);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mPregunta = new Pregunta();
                mPregunta.setIdPregunta(cursor.getInt(0));
                mPregunta.setCual(cursor.getString(1));
                mPregunta.setIntensidad(cursor.getInt(2));
                mPregunta.setUbi(cursor.getString(3));
                list.add(mPregunta);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public void deleteAll(){
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Pregunta.TABLE,null,null);
        DBManager.getInstance().closeDatabase();
    }

}
