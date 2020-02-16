package com.unse.bienestarestudiantil.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Egresado;

import java.util.ArrayList;

public class EgresadosRepo {

    private Egresado mEgresado;

    public EgresadosRepo(Context context) {

        Utils.initBD(context);
        mEgresado = new Egresado();
    }

    public Egresado getEgresados() {
        return mEgresado;
    }

    public void setEgresados(Egresado carrito) {
        this.mEgresado = carrito;
    }

    static String createTable() {
        return String.format("create table %s(%s %s %s,%s %s %s,%s %s %s,%s %s %s)",
                Egresado.TABLE,
                Egresado.KEY_ID_EGR, Utils.INT_TYPE, Utils.AUTO_INCREMENT,
                Egresado.KEY_PROFE, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Egresado.KEY_FECHA_EGR, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Egresado.KEY_CHK_DATA, Utils.STRING_TYPE, Utils.NULL_TYPE);
    }

    public int insert(Egresado carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Egresado.KEY_ID_EGR, carrito.getIdEgresado());
        values.put(Egresado.KEY_PROFE, carrito.getProfesion());
        values.put(Egresado.KEY_FECHA_EGR, Utils.getFechaNameWithinHour(carrito.getFechaEgreso()));
        values.put(Egresado.KEY_CHK_DATA, carrito.getCheckData());
        float x = db.insert(Egresado.TABLE, null, values);
        DBManager.getInstance().closeDatabase();
        return (int)x;
    }


    public void delete(Egresado carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Egresado.TABLE, Egresado.KEY_ID_EGR + " = ?", new String[]{String.valueOf(carrito.getIdEgresado())});
        DBManager.getInstance().closeDatabase();
    }


    public Egresado get(int id) {
        mEgresado = new Egresado();
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Egresado.TABLE + " where " + Egresado.KEY_ID_EGR + " = " + id, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEgresado.setIdEgresado(cursor.getInt(0));
            mEgresado.setProfesion(cursor.getString(1));
            mEgresado.setFechaEgreso(Utils.getFechaDate(cursor.getString(2)));
            mEgresado.setCheckData(cursor.getString(3));
            cursor.close();
        }
        DBManager.getInstance().closeDatabase();
        return mEgresado;
    }

    public boolean isExist(int numero) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Egresado.TABLE + " where " + Egresado.KEY_ID_EGR + " = " + numero, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        DBManager.getInstance().closeDatabase();
        return false;

    }

    public void update(Egresado carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Egresado.KEY_ID_EGR, carrito.getIdEgresado());
        values.put(Egresado.KEY_PROFE, carrito.getProfesion());
        values.put(Egresado.KEY_FECHA_EGR, Utils.getFechaNameWithinHour(carrito.getFechaEgreso()));
        values.put(Egresado.KEY_CHK_DATA, carrito.getCheckData());
        String id = String.valueOf(carrito.getIdEgresado());
        String selection = Egresado.KEY_ID_EGR + " = " + id;
        db.update(Egresado.TABLE, values, selection, null);
        DBManager.getInstance().closeDatabase();
    }

    public int getCount() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String countQuery = "SELECT  * FROM " + Egresado.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        DBManager.getInstance().closeDatabase();
        return count;
    }

    public ArrayList<Egresado> getAll() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Egresado> list = new ArrayList<Egresado>();
        String query = String.format("select * from %s", Egresado.TABLE);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mEgresado = new Egresado();
                mEgresado.setIdEgresado(cursor.getInt(0));
                mEgresado.setProfesion(cursor.getString(1));
                mEgresado.setFechaEgreso(Utils.getFechaDate(cursor.getString(2)));
                mEgresado.setCheckData(cursor.getString(3));
                list.add(mEgresado);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public ArrayList<Egresado> getAllByEgresado(int linea) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Egresado> list = new ArrayList<Egresado>();
        String query = String.format("select * from %s where %s = %s", Egresado.TABLE, Egresado.KEY_ID_EGR, linea);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mEgresado = new Egresado();
                mEgresado.setIdEgresado(cursor.getInt(0));
                mEgresado.setProfesion(cursor.getString(1));
                mEgresado.setFechaEgreso(Utils.getFechaDate(cursor.getString(2)));
                mEgresado.setCheckData(cursor.getString(3));
                list.add(mEgresado);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public void deleteAll(){
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Egresado.TABLE,null,null);
        DBManager.getInstance().closeDatabase();
    }
    
}
