package com.unse.bienestarestudiantil.Modelos;

import android.provider.BaseColumns;

public class PiletaIngresoParcial {

    public static final String TAG = PiletaIngresoParcial.class.getSimpleName();
    public static final String TABLE = "piletaParcial";
    // Labels Table Columns names
    public static final String KEY_ID = BaseColumns._ID;
    public static final String KEY_CATEGORIA = "categoria";
    public static final String KEY_CANTIDAD = "cantidad";
    public static final String KEY_PRECIO = "precio";
    public static final String KEY_ID_INGRESO = "idIngreso";

    private int id, categoria, cantidad, idIngreso;
    private float precio;

    public PiletaIngresoParcial(int categoria, int cantidad, float precio) {
        this.id = -1;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.idIngreso = -1;
        this.precio = precio;
    }

    public PiletaIngresoParcial(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(int idIngreso) {
        this.idIngreso = idIngreso;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

}
