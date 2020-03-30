package com.unse.bienestarestudiantil.Modelos;

import com.unse.bienestarestudiantil.Modelos.Usuario;

import java.util.Date;

public class Alumno extends Usuario{

    public static final String TAG = Alumno.class.getSimpleName();
    public static final String TABLE = "alumno";
    // Labels Table Columns names
    public static final String KEY_ID_ALU = "idAlumno";
    public static final String KEY_CARR = "carrera";
    public static final String KEY_FAC = "facultad";
    public static final String KEY_ANIO = "anio";
    public static final String KEY_LEG = "legajo";
    public static final String KEY_REG = "idRegularidad";
    public static final String KEY_CHK_DATA = "checkData";

    private String carrera, facultad, legajo, anio, checkData;
    private int idAlumno, idRegularidad;

    public Alumno(Usuario usuario, String carrera, String facultad, String legajo, String anio,
                  int idAlumno, String checkData1, int idRegularidad) {
        super(usuario.getIdUsuario(), usuario.getTipoUsuario(), usuario.getNombre(), usuario.getApellido(), usuario.getPais(),
                usuario.getProvincia(), usuario.getLocalidad(), usuario.getDomicilio(), usuario.getBarrio(), usuario.getTelefono(),
                usuario.getSexo(), usuario.getMail(), usuario.getCheckData(), usuario.getFoto(), usuario.getFechaNac(), usuario.getFechaRegistro());
        this.carrera = carrera;
        this.facultad = facultad;
        this.legajo = legajo;
        this.anio = anio;
        this.idAlumno = idAlumno;
        this.checkData = checkData1;
        this.idRegularidad = idRegularidad;
    }

    public Alumno() {
        super(-1, -1, "", "", "", "", "", "", "", "", "", "", "", "", new Date(), new Date());
        this.carrera = "";
        this.facultad = "";
        this.legajo = "";
        this.anio = "";
        this.idAlumno = -1;
        this.checkData = "";
        this.idRegularidad = -1;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getCheckData() {
        return checkData;
    }

    public void setCheckData(String checkData) {
        this.checkData = checkData;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdRegularidad() {
        return idRegularidad;
    }

    public void setIdRegularidad(int idRegularidad) {
        this.idRegularidad = idRegularidad;
    }


}
