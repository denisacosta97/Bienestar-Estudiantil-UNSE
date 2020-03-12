package com.unse.bienestarestudiantil.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Usuario;

import java.util.ArrayList;

public class UsuariosRepo {

    private Usuario mUsuario;

    public UsuariosRepo(Context context) {

        Utils.initBD(context);
        mUsuario = new Usuario();
    }

    public Usuario getUsuarios() {
        return mUsuario;
    }

    public void setUsuarios(Usuario usuario) {
        this.mUsuario = usuario;
    }

    static String createTable() {
        return String.format("create table %s(%s %s %s,%s %s %s,%s %s %s, %s %s %s,%s %s %s,%s %s %s, %s %s %s," +
                        "%s %s %s,%s %s %s, %s %s %s,%s %s %s,%s %s %s, %s %s %s,%s %s %s,%s %s %s)",
                Usuario.TABLE,
                Usuario.KEY_ID_USER, Utils.INT_TYPE, Utils.AUTO_INCREMENT,
                Usuario.KEY_NOMB, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_APE, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_FECHA_NAC, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_PAIS, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_PROV, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_LOC, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_DOM, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_BAR, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_TEL, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_SEX, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_MAIL, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_TYPE_USER, Utils.INT_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_FECHA_REGISTRO, Utils.STRING_TYPE, Utils.NULL_TYPE,
                Usuario.KEY_CHK_DATA, Utils.STRING_TYPE, Utils.NULL_TYPE);
    }

    public ContentValues loadValues(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(Usuario.KEY_ID_USER, usuario.getIdUsuario());
        values.put(Usuario.KEY_NOMB, usuario.getNombre());
        values.put(Usuario.KEY_APE, usuario.getApellido());
        values.put(Usuario.KEY_FECHA_NAC, Utils.getFechaNameWithinHour(usuario.getFechaNac()));
        values.put(Usuario.KEY_PAIS, usuario.getPais());
        values.put(Usuario.KEY_PROV, usuario.getProvincia());
        values.put(Usuario.KEY_LOC, usuario.getLocalidad());
        values.put(Usuario.KEY_DOM, usuario.getDomicilio());
        values.put(Usuario.KEY_BAR, usuario.getBarrio());
        values.put(Usuario.KEY_TEL, usuario.getTelefono());
        values.put(Usuario.KEY_SEX, usuario.getSexo());
        values.put(Usuario.KEY_MAIL, usuario.getMail());
        values.put(Usuario.KEY_FECHA_REGISTRO, Utils.getFechaName(usuario.getFechaRegistro()));
        values.put(Usuario.KEY_TYPE_USER, usuario.getTipoUsuario());
        values.put(Usuario.KEY_CHK_DATA, usuario.getCheckData());
        return values;
    }

    public Usuario loadData(Cursor cursor) {
        mUsuario = new Usuario();
        mUsuario.setIdUsuario(cursor.getInt(0));
        mUsuario.setNombre(cursor.getString(1));
        mUsuario.setApellido(cursor.getString(2));
        mUsuario.setFechaNac(Utils.getFechaDate(cursor.getString(3)));
        mUsuario.setPais(cursor.getString(4));
        mUsuario.setProvincia(cursor.getString(5));
        mUsuario.setLocalidad(cursor.getString(6));
        mUsuario.setDomicilio(cursor.getString(7));
        mUsuario.setBarrio(cursor.getString(8));
        mUsuario.setTelefono(cursor.getString(9));
        mUsuario.setSexo(cursor.getString(10));
        mUsuario.setMail(cursor.getString(11));
        mUsuario.setTipoUsuario(cursor.getInt(12));
        mUsuario.setCheckData(cursor.getString(14));
        mUsuario.setFechaRegistro(Utils.getFechaDateWithHour(cursor.getString(13)));
        return mUsuario;
    }


    public int insert(Usuario usuario) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        float x = db.insert(Usuario.TABLE, null, loadValues(usuario));
        DBManager.getInstance().closeDatabase();
        return (int) x;
    }


    public void delete(Usuario usuario) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Usuario.TABLE, Usuario.KEY_ID_USER + " = ?", new String[]{String.valueOf(usuario.getIdUsuario())});
        DBManager.getInstance().closeDatabase();
    }


    public Usuario get(int id) {
        mUsuario = new Usuario();
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Usuario.TABLE + " where " + Usuario.KEY_ID_USER + " = " + id, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mUsuario = loadData(cursor);
            cursor.close();
        }
        DBManager.getInstance().closeDatabase();
        return mUsuario;
    }

    public boolean isExist(int numero) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + Usuario.TABLE + " where " + Usuario.KEY_ID_USER + " = " + numero, null);
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
        db.update(Usuario.TABLE, loadValues(usuario), selection, null);
        DBManager.getInstance().closeDatabase();
    }

    public int getCount() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String countQuery = "SELECT  * FROM " + Usuario.TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        DBManager.getInstance().closeDatabase();
        return count;
    }

    public ArrayList<Usuario> getAll() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Usuario> list = new ArrayList<Usuario>();
        String query = String.format("select * from %s", Usuario.TABLE);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(loadData(cursor));
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public ArrayList<Usuario> getAllByUsuario(int linea) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ArrayList<Usuario> list = new ArrayList<Usuario>();
        String query = String.format("select * from %s where %s = %s", Usuario.TABLE, Usuario.KEY_ID_USER, linea);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(loadData(cursor));
            } while (cursor.moveToNext());
        }
        DBManager.getInstance().closeDatabase();
        cursor.close();

        return list;

    }

    public void deleteAll() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        db.delete(Usuario.TABLE, null, null);
        DBManager.getInstance().closeDatabase();
    }

}
