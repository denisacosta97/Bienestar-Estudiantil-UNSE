package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "roles", primaryKeys = {"idRol", "idUsuario"})
public class Rol implements Serializable {

    @Ignore
    public static final String TABLE_ROL = "roles";
    @Ignore
    public static final String KEY_USER = "idUsuario";
    @Ignore
    public static final String KEY_ROL = "idRol";

    @NonNull
    private int idRol;
    @NonNull
    private int idUsuario;
    @NonNull
    private String descripcion;
    @Ignore
    private int idRolPadre;

    @Ignore
    public Rol(int idRol, int idUsuario) {
        this.idRol = idRol;
        this.idUsuario = idUsuario;
    }

    public Rol(int idRol, int idUsuario, @NonNull String descripcion) {
        this.idRol = idRol;
        this.idUsuario = idUsuario;
        this.descripcion = descripcion;
    }

    @Ignore
    public Rol(int idRol, int idRolPadre, @NonNull  String descripcion, int f) {
        this.idRol = idRol;
        this.idRolPadre = idRolPadre;
        this.descripcion = descripcion;
    }

    @Ignore
    public Rol(int idRol, int idUsuario, int idRolPadre) {
        this.idRol = idRol;
        this.idUsuario = idUsuario;
        this.idRolPadre = idRolPadre;
    }

    @Ignore
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

    @NonNull
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
