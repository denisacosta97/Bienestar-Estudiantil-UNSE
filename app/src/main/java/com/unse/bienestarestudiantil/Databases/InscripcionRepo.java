package com.unse.bienestarestudiantil.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Inscripcion;

import java.util.ArrayList;

public class InscripcionRepo {

    private Inscripcion mInscripcion;

    public InscripcionRepo(Context context) {

        Utils.initBD(context);
        mInscripcion = new Inscripcion();
    }

    public Inscripcion getInscripcion() {
        return mInscripcion;
    }

    public void setInscripcion(Inscripcion carrito) {
        this.mInscripcion = carrito;
    }

    static String createTable() {
        return String.format("create table %s(%s %s %s,%s %s %s,%s %s %s,%s %s %s,%s %s %s)",
                Inscripcion.TABLE,
                Inscripcion.KEY_ID_INS, Utils.INT_TYPE, Utils.AUTO_INCREMENT,
                Inscripcion.KEY_FB, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Inscripcion.KEY_IG, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Inscripcion.KEY_WPP, Utils.BIGINT_TYPE, Utils.NULL_TYPE,
                Inscripcion.KEY_OBJ, Utils.STRING_TYPE, Utils.NULL_TYPE);
    }

    public int insert(Inscripcion carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Inscripcion.KEY_ID_INS, carrito.getidInscripcion());
        values.put(Inscripcion.KEY_FB, carrito.getFb());
        values.put(Inscripcion.KEY_IG, carrito.getIg());
        values.put(Inscripcion.KEY_WPP, carrito.getWpp());
        values.put(Inscripcion.KEY_OBJ, carrito.getObjetivo());
        float x = db.insert(Inscripcion.TABLE, null, values);
        DBManager.getInstance().closeDatabase();
        return (int)x;
    }


    public void delete(Inscripcion carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Inscripcion.TABLE, Inscripcion.KEY_ID_INS + " = ?", new String[]{String.valueOf(carrito.getidInscripcion())});
        DBManager.getInstance().closeDatabase();
    }


    public Inscripcion get(int id) {
        mInscripcion = new Inscripcion();
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Inscripcion.TABLE + " where " + Inscripcion.KEY_ID_INS + " = " + id, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mInscripcion.setidInscripcion(cursor.getInt(0));
            mInscripcion.setFb(cursor.getString(1));
            mInscripcion.setIg(cursor.getString(2));
            mInscripcion.setWpp(cursor.getLong(3));
            mInscripcion.setObjetivo(cursor.getString(4));
            cursor.close();
        }
        DBManager.getInstance().closeDatabase();
        return mInscripcion;
    }

    public boolean isExist(int numero) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Inscripcion.TABLE + " where " + Inscripcion.KEY_ID_INS + " = " + numero, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        DBManager.getInstance().closeDatabase();
        return false;

    }

    public void update(Inscripcion carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Inscripcion.KEY_ID_INS, carrito.getidInscripcion());
        values.put(Inscripcion.KEY_FB, carrito.getFb());
        values.put(Inscripcion.KEY_IG, carrito.getIg());
        values.put(Inscripcion.KEY_WPP, carrito.getWpp());
        values.put(Inscripcion.KEY_OBJ, carrito.getObjetivo());
        String id = String.valueOf(carrito.getidInscripcion());
        String selection = Inscripcion.KEY_ID_INS + " = " + id;
        db.update(Inscripcion.TABLE, values, selection, null);
        DBManager.getInstance().closeDatabase();
    }

    public int getCount() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String countQuery = "SELECT  * FROM " + Inscripcion.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        DBManager.getInstance().closeDatabase();
        return count;
    }

    public ArrayList<Inscripcion> getAll() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Inscripcion> list = new ArrayList<Inscripcion>();
        String query = String.format("select * from %s", Inscripcion.TABLE);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mInscripcion = new Inscripcion();
                mInscripcion.setidInscripcion(cursor.getInt(0));
                mInscripcion.setFb(cursor.getString(1));
                mInscripcion.setIg(cursor.getString(2));
                mInscripcion.setWpp(cursor.getLong(3));
                mInscripcion.setObjetivo(cursor.getString(4));
                list.add(mInscripcion);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public ArrayList<Inscripcion> getAllByInscripcion(int linea) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Inscripcion> list = new ArrayList<Inscripcion>();
        String query = String.format("select * from %s where %s = %s", Inscripcion.TABLE, Inscripcion.KEY_ID_INS, linea);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mInscripcion = new Inscripcion();
                mInscripcion.setidInscripcion(cursor.getInt(0));
                mInscripcion.setFb(cursor.getString(1));
                mInscripcion.setIg(cursor.getString(2));
                mInscripcion.setWpp(cursor.getLong(3));
                mInscripcion.setObjetivo(cursor.getString(4));
                list.add(mInscripcion);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public void deleteAll(){
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Inscripcion.TABLE,null,null);
        DBManager.getInstance().closeDatabase();
    }

}
