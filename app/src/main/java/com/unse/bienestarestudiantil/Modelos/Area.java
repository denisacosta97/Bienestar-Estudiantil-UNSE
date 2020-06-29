package com.unse.bienestarestudiantil.Modelos;

import java.io.Serializable;

public class Area implements Serializable {

    private int idArea;
    private String descripcion;

    public Area(int idArea, String descripcion) {
        this.idArea = idArea;
        this.descripcion = descripcion;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
