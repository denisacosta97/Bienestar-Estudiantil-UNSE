package com.unse.bienestarestudiantil.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Rol;

import java.util.ArrayList;

public class RolRepo {

    private Rol mRol;

    public RolRepo(Context context) {

        Utils.initBD(context);
        mRol = new Rol();
    }

    public Rol getRols() {
        return mRol;
    }

    public void setRols(Rol carrito) {
        this.mRol = carrito;
    }

    static String createTable() {
        return String.format("create table %s(%s %s %s,%s %s %s,%s %s %s,%s %s %s,%s %s %s)",
                Rol.TABLE,
                Rol.KEY_ID, Utils.INT_TYPE, Utils.AUTO_INCREMENT,
                Rol.KEY_ID_USER, Utils.INT_TYPE, Utils.NULL_TYPE,
                Rol.KEY_TIPO_ROL, Utils.INT_TYPE, Utils.NULL_TYPE,
                Rol.KEY_FECHA, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Rol.KEY_CHK_DATA, Utils.STRING_TYPE, Utils.NULL_TYPE);
    }

    public int insert(Rol carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Rol.KEY_ID, carrito.getId());
        values.put(Rol.KEY_ID_USER, carrito.getIdUsuario());
        values.put(Rol.KEY_TIPO_ROL, carrito.getTipoRol());
        values.put(Rol.KEY_FECHA, Utils.getFechaName(carrito.getFecha()));
        values.put(Rol.KEY_CHK_DATA, carrito.getCheckData());
        float x = db.insert(Rol.TABLE, null, values);
        DBManager.getInstance().closeDatabase();
        return (int)x;
    }


    public void delete(Rol carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Rol.TABLE, Rol.KEY_ID + " = ?", new String[]{String.valueOf(carrito.getId())});
        DBManager.getInstance().closeDatabase();
    }


    public Rol get(int id) {
        mRol = new Rol();
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Rol.TABLE + " where " + Rol.KEY_ID + " = " + id, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mRol.setId(cursor.getInt(0));
            mRol.setIdUsuario(cursor.getInt(1));
            mRol.setTipoRol(cursor.getInt(2));
            mRol.setFecha(Utils.getFechaDate(cursor.getString(3)));
            mRol.setCheckData(cursor.getString(4));
            cursor.close();
        }
        DBManager.getInstance().closeDatabase();
        return mRol;
    }

    public boolean isExist(int numero) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Rol.TABLE + " where " + Rol.KEY_ID + " = " + numero, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        DBManager.getInstance().closeDatabase();
        return false;

    }

    public void update(Rol carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Rol.KEY_ID, carrito.getId());
        values.put(Rol.KEY_ID_USER, carrito.getIdUsuario());
        values.put(Rol.KEY_TIPO_ROL, carrito.getTipoRol());
        values.put(Rol.KEY_FECHA, Utils.getFechaName(carrito.getFecha()));
        values.put(Rol.KEY_CHK_DATA, carrito.getCheckData());
        String id = String.valueOf(carrito.getId());
        String selection = Rol.KEY_ID + " = " + id;
        db.update(Rol.TABLE, values, selection, null);
        DBManager.getInstance().closeDatabase();
    }

    public int getCount() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String countQuery = "SELECT  * FROM " + Rol.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        DBManager.getInstance().closeDatabase();
        return count;
    }

    public ArrayList<Rol> getAll() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Rol> list = new ArrayList<Rol>();
        String query = String.format("select * from %s", Rol.TABLE);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mRol = new Rol();
                mRol.setId(cursor.getInt(0));
                mRol.setIdUsuario(cursor.getInt(1));
                mRol.setTipoRol(cursor.getInt(2));
                mRol.setFecha(Utils.getFechaDate(cursor.getString(3)));
                mRol.setCheckData(cursor.getString(4));
                list.add(mRol);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public ArrayList<Rol> getAllByRol(int linea) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Rol> list = new ArrayList<Rol>();
        String query = String.format("select * from %s where %s = %s", Rol.TABLE, Rol.KEY_ID, linea);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mRol = new Rol();
                mRol.setId(cursor.getInt(0));
                mRol.setIdUsuario(cursor.getInt(1));
                mRol.setTipoRol(cursor.getInt(2));
                mRol.setFecha(Utils.getFechaDate(cursor.getString(3)));
                mRol.setCheckData(cursor.getString(4));
                list.add(mRol);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public void deleteAll(){
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Rol.TABLE,null,null);
        DBManager.getInstance().closeDatabase();
    }
    
}
