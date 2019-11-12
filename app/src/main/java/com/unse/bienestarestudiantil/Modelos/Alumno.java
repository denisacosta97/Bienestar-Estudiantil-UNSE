package com.unse.bienestarestudiantil.Modelos;

import com.unse.bienestarestudiantil.Modelos.Usuario;

public class Alumno extends Usuario{

    private String carrera;
    private String facultad;
    private String fecha;
    private String legajo;
    private boolean regularidad;

    public Alumno(String id, String nombre, String apellido, String foto, String estatus, String carrera, String facultad, String fecha, String legajo, boolean regularidad) {
        super(id, nombre, apellido, foto, estatus);
        this.carrera = carrera;
        this.facultad = facultad;
        this.fecha = fecha;
        this.legajo = legajo;
        this.regularidad = regularidad;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public boolean isRegularidad() {
        return regularidad;
    }

    public void setRegularidad(boolean regularidad) {
        this.regularidad = regularidad;
    }
}
