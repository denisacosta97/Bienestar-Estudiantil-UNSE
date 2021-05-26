package com.unse.bienestarestudiantil.Databases;

import androidx.lifecycle.LiveData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.Modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioViewModel {


    private Usuario mUsuario;

    public UsuarioViewModel(Context context) {
        mUsuario = new Usuario();
    }

    public UsuarioViewModel() {
        mUsuario = new Usuario();
    }

    public Usuario getUsuario() {
        return mUsuario;
    }

    public void setUsuario(Usuario Usuario) {
        this.mUsuario = Usuario;
    }

    static String createTable() {
        return String.format("create table %s(%s %s %s,%s %s %s,%s %s %s)",
                Usuario.TABLE_USER,
                Usuario.KEY_ID_USER, Utils.INT_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_NOMBRE, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_APELLIDO, Utils.STRING_TYPE, Utils.NULL_TYPE);
    }

    public ContentValues loadValues(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(Usuario.KEY_ID_USER, usuario.getIdUsuario());
        values.put(Usuario.KEY_NOMBRE, usuario.getNombre());
        values.put(Usuario.KEY_APELLIDO, usuario.getApellido());
        return values;
    }


    public void insert(Usuario usuario) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.insert(Usuario.TABLE_USER, null, loadValues(usuario));
        DBManager.getInstance().closeDatabase();
    }


   /* public void delete(Usuario Usuario) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Usuario.TABLE, Usuario.KEY_Usuario + " = ?",
                new String[]{String.valueOf(Usuario.getIdUsuario())});
        DBManager.getInstance().closeDatabase();
    }*/


    public Usuario get(int id) {
        mUsuario = new Usuario();
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Usuario.TABLE_USER + " where " +
                Usuario.KEY_ID_USER + " = " + id, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mUsuario = loadCursor(cursor);
            cursor.close();
        }

        DBManager.getInstance().closeDatabase();
        return mUsuario;
    }

    private Usuario loadCursor(Cursor cursor) {
        mUsuario = new Usuario();
        mUsuario.setIdUsuario(cursor.getInt(0));
        mUsuario.setNombre(cursor.getString(1));
        mUsuario.setApellido(cursor.getString(2));
        return mUsuario;
    }

    public void deleteAll() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Usuario.TABLE_USER, null, null);
        DBManager.getInstance().closeDatabase();
    }


    public boolean isExist(int numero) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Usuario.TABLE_USER + " where "
                + Usuario.KEY_ID_USER + " = " + numero, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        DBManager.getInstance().closeDatabase();
        return false;

    }

    public void update(Usuario usuario) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String id = String.valueOf(usuario.getIdUsuario());
        String selection = Usuario.KEY_ID_USER + " = " + id;
        db.update(Usuario.TABLE_USER, loadValues(usuario), selection, null);
        DBManager.getInstance().closeDatabase();
    }


    public int getCount() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String countQuery = "SELECT  * FROM " + Usuario.TABLE_USER;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        DBManager.getInstance().closeDatabase();
        cursor.close();
        return count;
    }

    public ArrayList<Usuario> getAll(int user) {

        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Usuario> list = new ArrayList<Usuario>();
        String query = String.format("select * from %s where %s = %s", Usuario.TABLE_USER, Usuario.KEY_ID_USER, user);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mUsuario = loadCursor(cursor);
                list.add(mUsuario);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public ArrayList<Usuario> getAll() {

        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Usuario> list = new ArrayList<Usuario>();
        String query = String.format("select * from %s", Usuario.TABLE_USER);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mUsuario = loadCursor(cursor);
                list.add(mUsuario);
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }
}
