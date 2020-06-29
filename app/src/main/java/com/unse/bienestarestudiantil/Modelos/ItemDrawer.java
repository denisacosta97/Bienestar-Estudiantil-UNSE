package com.unse.bienestarestudiantil.Modelos;

public class ItemDrawer {

    public static int TIPO_OPCION = 1;
    public static int TIPO_DIVISION = 2;
    public static int TIPO_GRUPO = 3;


    private int id;
    private String titulo;
    private int icono;
    private boolean select;
    private int tipo;

    public ItemDrawer(int id, String titulo, int icono, boolean select, int tipo) {
        this.id = id;
        this.titulo = titulo;
        this.icono = icono;
        this.select = select;
        this.tipo = tipo;
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

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
