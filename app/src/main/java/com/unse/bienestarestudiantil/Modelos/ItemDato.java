package com.unse.bienestarestudiantil.Modelos;

public class ItemDato extends ItemBase {

    public static final int TIPO_INSCRIPCION = 1;
    public static final int TIPO_TEMPORADA = 2;
    public static final int TIPO_DEPORTE = 3;
    private Inscripcion mInscripcion;
    private Temporada mTemporada;
    private Deporte mDeporte;
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
            case TIPO_TEMPORADA:
                return String.valueOf(getTemporada().getAnio());
            case TIPO_DEPORTE:
                return getDeporte().getName();

        }
        return null;
    }

    public String getEstadoValue() {
        switch (getTipoDato()) {
            case TIPO_INSCRIPCION:
                return String.format("%s", getInscripcion().getNombreEstado());

        }
        return null;
    }

    public int getEstadoValueId() {
        switch (getTipoDato()) {
            case TIPO_INSCRIPCION:
                return getInscripcion().getIdEstado();

        }
        return 0;
    }

    public String getIdValue() {
        switch (getTipoDato()) {
            case TIPO_INSCRIPCION:
                return String.format("%s/%s", getInscripcion().getIdInscripcion(), String.valueOf(
                        getInscripcion().getAnio()
                ).substring(2));
            case TIPO_DEPORTE:
                return String.format("%s", getDeporte().getIdDep());
            case TIPO_TEMPORADA:
                return String.format("%s", getTemporada().getIdDeporte());

        }
        return null;
    }

    public Deporte getDeporte() {
        return mDeporte;
    }

    public void setDeporte(Deporte deporte) {
        mDeporte = deporte;
    }

    public Temporada getTemporada() {
        return mTemporada;
    }

    public void setTemporada(Temporada temporada) {
        mTemporada = temporada;
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
