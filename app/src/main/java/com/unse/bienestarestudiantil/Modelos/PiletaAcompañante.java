package com.unse.bienestarestudiantil.Modelos;

public class PiletaAcompañante {

    int categoria, cantidad;
    float precioTotal;

    public PiletaAcompañante(int categoria, int cantidad, float precioTotal) {
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
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

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }
}
