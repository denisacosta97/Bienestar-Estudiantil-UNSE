package com.unse.bienestarestudiantil.Modelos;


public class Categoria {

    private int idCategoria;
    private String nombre;
    boolean estado = false;

    public Categoria(int id, String nombre) {
        this.nombre = nombre;
        this.idCategoria = id;
        this.estado = false;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
