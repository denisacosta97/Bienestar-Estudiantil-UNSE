package com.unse.bienestarestudiantil.Modelos;

import java.util.Date;

public class Rol {

    public static final String TAG = Usuario.class.getSimpleName();
    public static final String TABLE = "roles";
    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_ID_USER = "idUsuario";
    public static final String KEY_TIPO_ROL = "tipoRol";
    public static final String KEY_FECHA = "fecha";
    public static final String KEY_CHK_DATA = "checkData";

    private int idUsuario, id, tipoRol;
    private String checkData;
    private Date fecha;

    public Rol(int id, int idUsuario, int tipoRol, String checkData, Date fecha) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.tipoRol = tipoRol;
        this.checkData = checkData;
        this.fecha = fecha;
    }

    public Rol() {
        this.id = -1;
        this.idUsuario = -1;
        this.tipoRol = -1;
        this.checkData = "";
        this.fecha = new Date();
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipoRol() {
        return tipoRol;
    }

    public void setTipoRol(int tipoRol) {
        this.tipoRol = tipoRol;
    }

    public String getCheckData() {
        return checkData;
    }

    public void setCheckData(String checkData) {
        this.checkData = checkData;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
