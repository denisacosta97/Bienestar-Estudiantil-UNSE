package com.unse.bienestarestudiantil.Modelos;

public class ItemFecha extends ItemBase {

    private String id;
    private String anio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
