package com.unse.bienestarestudiantil.Modelos;

public class ItemFecha extends ItemBase {

    String anio;


    public ItemFecha(String anio) {
        this.anio = anio;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    @Override
    public int getTipo() {
        return TIPO_FECHA;
    }
}
