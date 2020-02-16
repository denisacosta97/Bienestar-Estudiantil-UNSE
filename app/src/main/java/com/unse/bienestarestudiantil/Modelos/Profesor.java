package com.unse.bienestarestudiantil.Modelos;

import java.util.Date;

public class Profesor extends Usuario {

    public static final String TAG = Usuario.class.getSimpleName();
    public static final String TABLE = "profesor";
    // Labels Table Columns names
    public static final String KEY_ID_PRO = "idProfesor";
    public static final String KEY_PRFN = "profesion";
    public static final String KEY_FECHA_ING = "fechaIngreso";
    public static final String KEY_CHK_DATA = "checkData";

    private String profesion, checkData;
    private int idProfesor;
    private Date fechaIngreso;

    public Profesor(int idUsuario, int tipoUsuario, String id, String nombre, String apellido, String pais, String provincia, String localidad, String domicilio, String barrio, String telefono, String sexo, String mail, String checkData, String foto, String estatus, Date fechaNac, String profesion, String checkData1, int idProfesor, Date fechaIngreso) {
        super(idUsuario, tipoUsuario, id, nombre, apellido, pais, provincia, localidad, domicilio, barrio, telefono, sexo, mail, checkData, foto, estatus, fechaNac);
        this.profesion = profesion;
        this.checkData = checkData1;
        this.idProfesor = idProfesor;
        this.fechaIngreso = fechaIngreso;
    }

    public Profesor() {
        super(-1, -1, "", "", "", "", "", "", "", "", "", "", "", "", "", "", new Date());
        this.profesion = "";
        this.checkData = "";
        this.idProfesor = -1;
        this.fechaIngreso = new Date();
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

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
