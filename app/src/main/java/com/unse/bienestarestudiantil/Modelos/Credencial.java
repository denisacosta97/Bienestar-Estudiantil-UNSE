package com.unse.bienestarestudiantil.Modelos;

public class Credencial {

    int id, validez;
    String titulo;

    public Credencial(int id, String titulo, int validez) {
        this.id = id;
        this.titulo = titulo;
        this.validez = validez;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}

