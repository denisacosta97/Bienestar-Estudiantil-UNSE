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

    public void setEgresados(Egresado egresado) {
        this.mEgresado = egresado;
    }

    static String createTable() {
        return String.format("create table %s(%s %s %s,%s %s %s,%s %s %s,%s %s %s)",
                Egresado.TABLE,
                Egresado.KEY_ID_EGR, Utils.INT_TYPE, Utils.AUTO_INCREMENT,
                Egresado.KEY_PROFE, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Egresado.KEY_FECHA_EGR, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Egresado.KEY_CHK_DATA, Utils.STRING_TYPE, Utils.NULL_TYPE);
    }
    
    public ContentValues loadValues(Egresado egresado){
        ContentValues values = new ContentValues();
        values.put(Egresado.KEY_ID_EGR, egresado.getIdEgresado());
        values.put(Egresado.KEY_PROFE, egresado.getProfesion());
        values.put(Egresado.KEY_FECHA_EGR, egresado.getFechaEgreso());
        values.put(Egresado.KEY_CHK_DATA, egresado.getCheckData());
        return values;
    }

    public Egresado loadCursor(Cursor cursor){
        Egresado egresado = new Egresado();
        egresado.setIdEgresado(cursor.getInt(0));
        egresado.setProfesion(cursor.getString(1));
        egresado.setFechaEgreso(cursor.getString(2));
        egresado.setCheckData(cursor.getString(3));
        return egresado;
    }

    public int insert(Egresado egresado) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        float x = db.insert(Egresado.TABLE, null, loadValues(egresado));
        DBManager.getInstance().closeDatabase();
        return (int)x;
    }


    public void delete(Egresado egresado) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Egresado.TABLE, Egresado.KEY_ID_EGR + " = ?", new String[]{String.valueOf(egresado.getIdEgresado())});
        DBManager.getInstance().closeDatabase();
    }


    public Egresado get(int id) {
        mEgresado = new Egresado();
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Egresado.TABLE + " where " + Egresado.KEY_ID_EGR + " = " + id, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEgresado = loadCursor(cursor);
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

    public void update(Egresado egresado) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String id = String.valueOf(egresado.getIdEgresado());
        String selection = Egresado.KEY_ID_EGR + " = " + id;
        db.update(Egresado.TABLE, loadValues(egresado), selection, null);
        DBManager.getInstance().closeDatabase();
    }

    public int getCount() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String countQuery = "SELECT  * FROM " + Egresado.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        DBManager.getInstance().closeDatabase();
        cursor.close();
        return count;
    }

    public ArrayList<Egresado> getAll() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Egresado> list = new ArrayList<Egresado>();
        String query = String.format("select * from %s", Egresado.TABLE);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mEgresado = loadCursor(cursor);
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
                mEgresado = loadCursor(cursor);
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
