package com.unse.bienestarestudiantil.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Torneo;

import java.util.ArrayList;

public class TorneoRepo {

    private Torneo mTorneo;

    public TorneoRepo(Context context) {
        Utils.initBD(context);
        mTorneo = new Torneo();
    }

    public Torneo getTorneo() {
        return mTorneo;
    }

    public void setTorneo(Torneo carrito) {
        this.mTorneo = carrito;
    }

    static String createTable() {
        return String.format("create table %s(%s %s %s,%s %s %s,%s %s %s,%s %s %s,%s %s %s,%s %s %s,%s %s %s)",
                Torneo.TABLE,
                Torneo.KEY_ID_TOR, Utils.INT_TYPE, Utils.AUTO_INCREMENT,
                Torneo.KEY_NOM, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Torneo.KEY_LUG, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Torneo.KEY_FECHA_INI, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Torneo.KEY_FECHA_FIN, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Torneo.KEY_DESC, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Torneo.KEY_UBI, Utils.STRING_TYPE, Utils.NULL_TYPE);
    }

    public int insert(Torneo carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Torneo.KEY_ID_TOR, carrito.getId());
        values.put(Torneo.KEY_NOM, carrito.getNameTorneo());
        values.put(Torneo.KEY_LUG, carrito.getLugar());
        values.put(Torneo.KEY_FECHA_INI, Utils.getFechaNameWithinHour(carrito.getFechaInicio()));
        values.put(Torneo.KEY_FECHA_FIN, Utils.getFechaNameWithinHour(carrito.getFechaFin()));
        values.put(Torneo.KEY_DESC, carrito.getDesc());
        values.put(Torneo.KEY_UBI, carrito.getUbicacion());
        float x = db.insert(Torneo.TABLE, null, values);
        DBManager.getInstance().closeDatabase();
        return (int)x;
    }


    public void delete(Torneo carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Torneo.TABLE, Torneo.KEY_ID_TOR + " = ?", new String[]{String.valueOf(carrito.getId())});
        DBManager.getInstance().closeDatabase();
    }


    public Torneo get(int id) {
        mTorneo = new Torneo();
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Torneo.TABLE + " where " + Torneo.KEY_ID_TOR + " = " + id, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mTorneo.setId(cursor.getInt(0));
            mTorneo.setNameTorneo(cursor.getString(1));
            mTorneo.setLugar(cursor.getString(2));
            mTorneo.setFechaInicio(Utils.getFechaDate(cursor.getString(3)));
            mTorneo.setFechaFin(Utils.getFechaDate(cursor.getString(4)));
            mTorneo.setDesc(cursor.getString(5));
            mTorneo.setUbicacion(cursor.getString(6));
            cursor.close();
        }
        DBManager.getInstance().closeDatabase();
        return mTorneo;
    }

    public boolean isExist(int numero) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Torneo.TABLE + " where " + Torneo.KEY_ID_TOR + " = " + numero, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        DBManager.getInstance().closeDatabase();
        return false;

    }

    public void update(Torneo carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Torneo.KEY_ID_TOR, carrito.getId());
        values.put(Torneo.KEY_NOM, carrito.getNameTorneo());
        values.put(Torneo.KEY_LUG, carrito.getLugar());
        values.put(Torneo.KEY_FECHA_INI, Utils.getFechaNameWithinHour(carrito.getFechaInicio()));
        values.put(Torneo.KEY_FECHA_FIN, Utils.getFechaNameWithinHour(carrito.getFechaFin()));
        values.put(Torneo.KEY_DESC, carrito.getDesc());
        values.put(Torneo.KEY_UBI, carrito.getUbicacion());
        String id = String.valueOf(carrito.getId());
        String selection = Torneo.KEY_ID_TOR + " = " + id;
        db.update(Torneo.TABLE, values, selection, null);
        DBManager.getInstance().closeDatabase();
    }

    public int getCount() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String countQuery = "SELECT  * FROM " + Torneo.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        DBManager.getInstance().closeDatabase();
        return count;
    }

    public ArrayList<Torneo> getAll() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Torneo> list = new ArrayList<Torneo>();
        String query = String.format("select * from %s", Torneo.TABLE);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mTorneo = new Torneo();
                mTorneo.setId(cursor.getInt(0));
                mTorneo.setNameTorneo(cursor.getString(1));
                mTorneo.setLugar(cursor.getString(2));
                mTorneo.setFechaInicio(Utils.getFechaDate(cursor.getString(3)));
                mTorneo.setFechaFin(Utils.getFechaDate(cursor.getString(4)));
                mTorneo.setDesc(cursor.getString(5));
                mTorneo.setUbicacion(cursor.getString(6));
                list.add(mTorneo);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public ArrayList<Torneo> getAllByTorneo(int linea) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Torneo> list = new ArrayList<Torneo>();
        String query = String.format("select * from %s where %s = %s", Torneo.TABLE, Torneo.KEY_ID_TOR, linea);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mTorneo = new Torneo();
                mTorneo.setId(cursor.getInt(0));
                mTorneo.setNameTorneo(cursor.getString(1));
                mTorneo.setLugar(cursor.getString(2));
                mTorneo.setFechaInicio(Utils.getFechaDate(cursor.getString(3)));
                mTorneo.setFechaFin(Utils.getFechaDate(cursor.getString(4)));
                mTorneo.setDesc(cursor.getString(5));
                mTorneo.setUbicacion(cursor.getString(6));
                list.add(mTorneo);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public void deleteAll(){
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Torneo.TABLE,null,null);
        DBManager.getInstance().closeDatabase();
    }

}
