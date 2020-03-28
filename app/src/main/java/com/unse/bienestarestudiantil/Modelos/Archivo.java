package com.unse.bienestarestudiantil.Modelos;

public class Archivo {
    private int id, validez, idArea;
    private String nombreArchivo, fecha, pdf, nombre;

    public Archivo(int id, String nombreArchivo, String fecha, String pdf) {
        this.id = id;
        this.nombreArchivo = nombreArchivo;
        this.fecha = fecha;
        this.pdf = pdf;
    }

    public Archivo(int id, int validez, int idArea, String nombreArchivo, String nombre) {
        this.id = id;
        this.validez = validez;
        this.idArea = idArea;
        this.nombreArchivo = nombreArchivo;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Archivo() {
        this.id = 0;
        this.nombreArchivo = "";
        this.fecha = "";
        this.pdf = "";
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
