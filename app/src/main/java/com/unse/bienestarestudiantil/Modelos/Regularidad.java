package com.unse.bienestarestudiantil.Modelos;

import java.io.Serializable;

public class Regularidad implements Serializable {

    private int idRegularidad;
    private int anio;
    private String fechaOtorg;
    private int validez;

    public Regularidad(int idRegularidad, int anio, String fechaOtorg, int validez) {
        this.idRegularidad = idRegularidad;
        this.anio = anio;
        this.fechaOtorg = fechaOtorg;
        this.validez = validez;
    }

    public int getIdRegularidad() {
        return idRegularidad;
    }

    public void setIdRegularidad(int idRegularidad) {
        this.idRegularidad = idRegularidad;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getFechaOtorg() {
        return fechaOtorg;
    }

    public void setFechaOtorg(String fechaOtorg) {
        this.fechaOtorg = fechaOtorg;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }
}
