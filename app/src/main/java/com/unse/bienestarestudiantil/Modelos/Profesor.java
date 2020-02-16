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

    public Profesor(Usuario usuario, String profesion, String checkData1, int idProfesor, Date fechaIngreso) {
        super(usuario.getIdUsuario(), usuario.getTipoUsuario(), usuario.getNombre(), usuario.getApellido(), usuario.getPais(),
                usuario.getProvincia(), usuario.getLocalidad(), usuario.getDomicilio(), usuario.getBarrio(), usuario.getTelefono(),
                usuario.getSexo(), usuario.getMail(), usuario.getCheckData(), usuario.getFoto(), usuario.getFechaNac());
        this.profesion = profesion;
        this.checkData = checkData1;
        this.idProfesor = idProfesor;
        this.fechaIngreso = fechaIngreso;
    }

    public Profesor() {
        super(-1, -1, "", "", "", "", "", "", "", "", "", "", "", "", new Date());
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

    public String getCheckData() {
        return checkData;
    }

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
