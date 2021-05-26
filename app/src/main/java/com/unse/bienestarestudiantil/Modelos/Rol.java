package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


import org.json.JSONException;
import org.json.JSONObject;

public class Rol implements Serializable {


    public static final String TABLE = "roles";
    public static final String KEY_USER = "idUsuario";
    public static final String KEY_ROL = "idRol";

    public static final int COMPLETE = 1;

    private int idRol;
    private int idUsuario;
    private String descripcion;
    private int idRolPadre;

    public Rol() {

    }


    public Rol(int idRol, int idUsuario) {
        this.idRol = idRol;
        this.idUsuario = idUsuario;
    }


    protected Rol(Parcel in) {
        idRol = in.readInt();
        idUsuario = in.readInt();
        idRolPadre = in.readInt();
        descripcion = in.readString();
    }


    public int getIdRol() {
        return idRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRolPadre() {
        return idRolPadre;
    }

    public void setIdRolPadre(int idRolPadre) {
        this.idRolPadre = idRolPadre;
    }

    public static Rol mapper(JSONObject o, int tipo) {
        int idRol;
        int idUsuario;
        String descripcion;
        int idRolPadre;
        Rol rol = null;
        try {
            switch (tipo) {
                case COMPLETE:
                    idRol = Integer.parseInt(o.getString("idrol"));
                    idRolPadre = Integer.parseInt(o.getString("idrolpadre"));
                    descripcion = o.getString("descripcion");
                    rol = new Rol(idRol, descripcion, idRolPadre);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rol;

    }

    public Rol(int idRol, String descripcion, int idRolPadre) {
        this.idRol = idRol;
        this.descripcion = descripcion;
        this.idRolPadre = idRolPadre;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID: ");
        builder.append(idRol);
        builder.append("\n");
        builder.append("Desc: ");
        builder.append(descripcion);
        return builder.toString();
    }
}
