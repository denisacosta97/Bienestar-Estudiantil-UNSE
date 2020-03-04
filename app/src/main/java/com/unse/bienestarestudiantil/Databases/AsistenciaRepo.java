package com.unse.bienestarestudiantil.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.AsistenciaDeporte;

import java.util.ArrayList;

public class AsistenciaRepo {

    private AsistenciaDeporte mAsistencia;

    public AsistenciaRepo(Context context) {
        Utils.initBD(context);
        mAsistencia = new AsistenciaDeporte();
    }

    public AsistenciaDeporte getAsistencia() {
        return mAsistencia;
    }

    public void setAsistencia(AsistenciaDeporte carrito) {
        this.mAsistencia = carrito;
    }

    static String createTable() {
        return String.format("create table %s(%s %s %s,%s %s %s,%s %s %s)",
                AsistenciaDeporte.TABLE,
                AsistenciaDeporte.KEY_ID_ASIS, Utils.INT_TYPE, Utils.AUTO_INCREMENT,
                AsistenciaDeporte.KEY_FECHA_FAL, Utils.STRING_TYPE, Utils.NULL_TYPE,
                AsistenciaDeporte.KEY_ASIS, Utils.INT_TYPE, Utils.NULL_TYPE);
    }

    public int insert(AsistenciaDeporte carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(AsistenciaDeporte.KEY_ASIS, carrito.getIdAsistencia());
        values.put(AsistenciaDeporte.KEY_FECHA_FAL, Utils.getFechaNameWithinHour(carrito.getFechaFalta()));
        values.put(AsistenciaDeporte.KEY_ASIS, carrito.getAsistencia());
        float x = db.insert(AsistenciaDeporte.TABLE, null, values);
        DBManager.getInstance().closeDatabase();
        return (int)x;
    }


    public void delete(AsistenciaDeporte carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(AsistenciaDeporte.TABLE, AsistenciaDeporte.KEY_ASIS + " = ?", new String[]{String.valueOf(carrito.getIdAsistencia())});
        DBManager.getInstance().closeDatabase();
    }


    public AsistenciaDeporte get(int id) {
        mAsistencia = new AsistenciaDeporte();
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + AsistenciaDeporte.TABLE + " where " + AsistenciaDeporte.KEY_ASIS + " = " + id, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mAsistencia.setIdAsistencia(cursor.getInt(0));
            mAsistencia.setFechaFalta(Utils.getFechaDate(cursor.getString(1)));
            mAsistencia.setAsistencia(cursor.getInt(2));
            cursor.close();
        }
        DBManager.getInstance().closeDatabase();
        return mAsistencia;
    }

    public boolean isExist(int numero) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + AsistenciaDeporte.TABLE + " where " + AsistenciaDeporte.KEY_ID_ASIS + " = " + numero, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        DBManager.getInstance().closeDatabase();
        return false;

    }

    public void update(AsistenciaDeporte carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(AsistenciaDeporte.KEY_ASIS, carrito.getIdAsistencia());
        values.put(AsistenciaDeporte.KEY_FECHA_FAL, Utils.getFechaNameWithinHour(carrito.getFechaFalta()));
        values.put(AsistenciaDeporte.KEY_ASIS, carrito.getAsistencia());
        String id = String.valueOf(carrito.getIdAsistencia());
        String selection = AsistenciaDeporte.KEY_ID_ASIS + " = " + id;
        db.update(AsistenciaDeporte.TABLE, values, selection, null);
        DBManager.getInstance().closeDatabase();
    }

    public int getCount() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String countQuery = "SELECT  * FROM " + AsistenciaDeporte.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        DBManager.getInstance().closeDatabase();
        return count;
    }

    public ArrayList<AsistenciaDeporte> getAll() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<AsistenciaDeporte> list = new ArrayList<AsistenciaDeporte>();
        String query = String.format("select * from %s", AsistenciaDeporte.TABLE);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mAsistencia = new AsistenciaDeporte();
                mAsistencia.setIdAsistencia(cursor.getInt(0));
                mAsistencia.setFechaFalta(Utils.getFechaDate(cursor.getString(1)));
                mAsistencia.setAsistencia(cursor.getInt(2));
                list.add(mAsistencia);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public ArrayList<AsistenciaDeporte> getAllByAsistencia(int linea) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<AsistenciaDeporte> list = new ArrayList<AsistenciaDeporte>();
        String query = String.format("select * from %s where %s = %s", AsistenciaDeporte.TABLE, AsistenciaDeporte.KEY_ID_ASIS, linea);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mAsistencia = new AsistenciaDeporte();
                mAsistencia.setIdAsistencia(cursor.getInt(0));
                mAsistencia.setFechaFalta(Utils.getFechaDate(cursor.getString(1)));
                mAsistencia.setAsistencia(cursor.getInt(2));
                list.add(mAsistencia);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public void deleteAll(){
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(AsistenciaDeporte.TABLE,null,null);
        DBManager.getInstance().closeDatabase();
    }

}
