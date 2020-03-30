package com.unse.bienestarestudiantil.Modelos;

public class ItemDato extends ItemBase {

    public static final int TIPO_INSCRIPCION = 1;
    private Inscripcion mInscripcion;
    private int tipo;

    public ItemDato() {
    }

    public Inscripcion getInscripcion() {
        return mInscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        mInscripcion = inscripcion;
    }

    public String getTextValue() {
        switch (getTipoDato()) {
            case TIPO_INSCRIPCION:
                return String.format("%s - %s", getInscripcion().getTitulo(), getInscripcion().getAnio());

        }
        return null;
    }

    public String getIdValue() {
        switch (getTipoDato()) {
            case TIPO_INSCRIPCION:
                return String.format("%s", getInscripcion().getIdInscripcion());

        }
        return null;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getTipoDato(){
        return tipo;
    }

    @Override
    public int getTipo() {
        return TIPO_DATO;
    }
}
