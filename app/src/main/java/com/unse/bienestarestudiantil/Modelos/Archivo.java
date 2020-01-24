package com.unse.bienestarestudiantil.Modelos;

public class Archivo {
    private int id;
    private String nombreArchivo, fecha, pdf;

    public Archivo(int id, String nombreArchivo, String fecha, String pdf) {
        this.id = id;
        this.nombreArchivo = nombreArchivo;
        this.fecha = fecha;
        this.pdf = pdf;
    }

    public Archivo() {
        this.id = 0;
        this.nombreArchivo = "";
        this.fecha = "";
        this.pdf = "";
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
