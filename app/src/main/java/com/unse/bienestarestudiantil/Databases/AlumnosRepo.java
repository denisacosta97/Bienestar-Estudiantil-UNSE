package com.unse.bienestarestudiantil.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Alumno;

import java.util.ArrayList;

public class AlumnosRepo {

    private Alumno mAlumno;

    public AlumnosRepo(Context context) {

        Utils.initBD(context);
        mAlumno = new Alumno("", "", "", "2015", "as2d", "", "FCEyT", "29/09/2018", "23/15", true);
    }

    public Alumno getAlumnos() {
        return mAlumno;
    }

    public void setAlumnos(Alumno carrito) {
        this.mAlumno = carrito;
    }

    static String createTable() {
        return String.format("create table %s(%s %s %s,%s %s %s,%s %s %s, %s %s %s, %s %s %s,%s %s %s,%s %s %s)",
                Alumno.TABLE,
                Alumno.KEY_ID_ALU, Utils.INT_TYPE, Utils.AUTO_INCREMENT,
                Alumno.KEY_CARR, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Alumno.KEY_FAC, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Alumno.KEY_ANIO, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Alumno.KEY_LEG, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Alumno.KEY_REG, Utils.INT_TYPE, Utils.NULL_TYPE,
                Alumno.KEY_CHK_DATA, Utils.STRING_TYPE, Utils.NULL_TYPE);
    }

    public int insert(Alumno carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Alumno.KEY_ID_ALU, carrito.getIdAlumno());
        values.put(Alumno.KEY_CARR, carrito.getCarrera());
        values.put(Alumno.KEY_FAC, carrito.getFacultad());
        values.put(Alumno.KEY_ANIO, carrito.getAnio());
        values.put(Alumno.KEY_LEG, carrito.getLegajo());
        values.put(Alumno.KEY_REG, carrito.getIdRegularidad());
        values.put(Alumno.KEY_CHK_DATA, carrito.getCheckData());
        float x = db.insert(Alumno.TABLE, null, values);
        DBManager.getInstance().closeDatabase();
        return (int)x;
    }


    public void delete(Alumno carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Alumno.TABLE, Alumno.KEY_ID_ALU + " = ?", new String[]{String.valueOf(carrito.getIdAlumno())});
        DBManager.getInstance().closeDatabase();
    }


    public Alumno get(int id) {
        mAlumno = new Alumno("", "", "", "2015", "as2d", "", "FCEyT", "29/09/2018", "23/15", true);
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Alumno.TABLE + " where " + Alumno.KEY_ID_ALU + " = " + id, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mAlumno.setIdAlumno(cursor.getInt(0));
            mAlumno.setCarrera(cursor.getString(1));
            mAlumno.setFacultad(cursor.getString(2));
            mAlumno.setAnio(cursor.getString(3));
            mAlumno.setLegajo(cursor.getString(4));
            mAlumno.setIdRegularidad(cursor.getInt(5));
            mAlumno.setCheckData(cursor.getString(6));
            cursor.close();
        }
        DBManager.getInstance().closeDatabase();
        return mAlumno;
    }

    public boolean isExist(int numero) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Alumno.TABLE + " where " + Alumno.KEY_ID_ALU + " = " + numero, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        DBManager.getInstance().closeDatabase();
        return false;

    }

    public void update(Alumno carrito) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Alumno.KEY_ID_ALU, carrito.getIdAlumno());
        values.put(Alumno.KEY_CARR, carrito.getCarrera());
        values.put(Alumno.KEY_FAC, carrito.getFacultad());
        values.put(Alumno.KEY_ANIO, carrito.getAnio());
        values.put(Alumno.KEY_LEG, carrito.getLegajo());
        values.put(Alumno.KEY_REG, carrito.getIdRegularidad());
        values.put(Alumno.KEY_CHK_DATA, carrito.getCheckData());
        String id = String.valueOf(carrito.getIdAlumno());
        String selection = Alumno.KEY_ID_ALU + " = " + id;
        db.update(Alumno.TABLE, values, selection, null);
        DBManager.getInstance().closeDatabase();
    }

    public int getCount() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String countQuery = "SELECT  * FROM " + Alumno.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        DBManager.getInstance().closeDatabase();
        return count;
    }

    public ArrayList<Alumno> getAll() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Alumno> list = new ArrayList<Alumno>();
        String query = String.format("select * from %s", Alumno.TABLE);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mAlumno = new Alumno("", "", "", "2015", "as2d", "", "FCEyT", "29/09/2018", "23/15", true);
                mAlumno.setIdAlumno(cursor.getInt(0));
                mAlumno.setCarrera(cursor.getString(1));
                mAlumno.setFacultad(cursor.getString(2));
                mAlumno.setAnio(cursor.getString(3));
                mAlumno.setLegajo(cursor.getString(4));
                mAlumno.setIdRegularidad(cursor.getInt(5));
                mAlumno.setCheckData(cursor.getString(6));
                list.add(mAlumno);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public ArrayList<Alumno> getAllByAlumno(int linea) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Alumno> list = new ArrayList<Alumno>();
        String query = String.format("select * from %s where %s = %s", Alumno.TABLE, Alumno.KEY_ID_ALU, linea);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mAlumno = new Alumno("", "", "", "2015", "as2d", "", "FCEyT", "29/09/2018", "23/15", true);
                mAlumno.setIdAlumno(cursor.getInt(0));
                mAlumno.setCarrera(cursor.getString(1));
                mAlumno.setFacultad(cursor.getString(2));
                mAlumno.setAnio(cursor.getString(3));
                mAlumno.setLegajo(cursor.getString(4));
                mAlumno.setIdRegularidad(cursor.getInt(5));
                mAlumno.setCheckData(cursor.getString(6));
                list.add(mAlumno);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public void deleteAll(){
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Alumno.TABLE,null,null);
        DBManager.getInstance().closeDatabase();
    }
    
}
