package com.unse.bienestarestudiantil.Modelos;

import java.util.Date;

public class Egresado extends Usuario {

    public static final String TAG = Usuario.class.getSimpleName();
    public static final String TABLE = "egresado";
    // Labels Table Columns names
    public static final String KEY_ID_EGR = "idEgresado";
    public static final String KEY_PROFE = "proesion";
    public static final String KEY_FECHA_EGR = "fechaEgreso";
    public static final String KEY_CHK_DATA = "checkData";

    private String profesion, checkData;
    private int idEgresado;
    private Date fechaEgreso;

    public Egresado(int idUsuario, int tipoUsuario, String id, String nombre, String apellido, String pais, String provincia, String localidad, String domicilio, String barrio, String telefono, String sexo, String mail, String checkData, String foto, String estatus, Date fechaNac, String profesion, String checkData1, int idEgresado, Date fechaEgreso) {
        super(idUsuario, tipoUsuario, id, nombre, apellido, pais, provincia, localidad, domicilio, barrio, telefono, sexo, mail, checkData, foto, estatus, fechaNac);
        this.profesion = profesion;
        this.checkData = checkData1;
        this.idEgresado = idEgresado;
        this.fechaEgreso = fechaEgreso;
    }

    public Egresado() {
        super(-1, -1, "", "", "", "", "", "", "", "", "", "", "", "", "", "", new Date());
        this.profesion = "";
        this.checkData = "";
        this.idEgresado = -1;
        this.fechaEgreso = new Date();
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    @Override
    public String getCheckData() {
        return checkData;
    }

    @Override
    public void setCheckData(String checkData) {
        this.checkData = checkData;
    }

    public int getIdEgresado() {
        return idEgresado;
    }

    public void setIdEgresado(int idEgresado) {
        this.idEgresado = idEgresado;
    }

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }
}
