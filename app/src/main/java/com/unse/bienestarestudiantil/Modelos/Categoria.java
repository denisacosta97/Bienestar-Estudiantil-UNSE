package com.unse.bienestarestudiantil.Modelos;


public class Categoria {

    private int idCategoria;
    private String nombre;

    public Categoria(int id, String nombre) {
        this.nombre = nombre;
        this.idCategoria = id;
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
