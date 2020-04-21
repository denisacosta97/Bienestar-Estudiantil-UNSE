package com.unse.bienestarestudiantil.Modelos;

public class Estado {

    private int id;
    private String descripcion;
    private boolean select;

    public Estado(int id, String descripcion, boolean select) {
        this.id = id;
        this.descripcion = descripcion;
        this.select = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
