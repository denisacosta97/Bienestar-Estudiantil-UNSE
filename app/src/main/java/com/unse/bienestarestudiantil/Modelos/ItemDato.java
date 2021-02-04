package com.unse.bienestarestudiantil.Modelos;

import com.unse.bienestarestudiantil.Herramientas.Utils;

import java.util.Calendar;

public class ItemDato extends ItemBase {

    public static final int TIPO_INSCRIPCION = 1;
    public static final int TIPO_TEMPORADA = 2;
    public static final int TIPO_DEPORTE = 3;
    public static final int TIPO_TURNO = 4;
    public static final int TIPO_IMPRE = 5;
    public static final int TIPO_INGRESO = 6;
    private Inscripcion mInscripcion;
    private Temporada mTemporada;
    private Deporte mDeporte;
    private Turno mTurno;
    private Impresion mImpre;
    private IngresoCiber mIngreso;
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
            case TIPO_TURNO:
                return String.format("%s %s", Utils.getDayWeek(Utils.getFechaDate(mTurno.getFechaRegistro())), mTurno.getDia());
            case TIPO_INGRESO:
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(Utils.getFechaDate(String.format("%s-%s-%s", mIngreso.getAnio(),
                        mIngreso.getMes(), mIngreso.getDia())));
                return String.format("%s %s", Utils.getDayWeek(calendar.getTime()), calendar.get(Calendar.DAY_OF_MONTH));
            case TIPO_IMPRE:
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(Utils.getFechaDate(String.format("%s-%s-%s", mImpre.getAnio(),
                        mImpre.getMes(), mImpre.getDia())));
                return String.format("%s %s", Utils.getDayWeek(calendar2.getTime()), calendar2.get(Calendar.DAY_OF_MONTH));

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
            case TIPO_IMPRE:
                return String.format("%s", getImpre().getDni());
            case TIPO_INGRESO:
                return String.format("%s", getIngreso().getDni());

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

    public Turno getTurno() {
        return mTurno;
    }

    public void setTurno(Turno turno) {
        mTurno = turno;
    }

    public Impresion getImpre() {
        return mImpre;
    }

    public void setImpre(Impresion im) {
        mImpre = im;
    }

    public IngresoCiber getIngreso() {
        return mIngreso;
    }

    public void setIngreso(IngresoCiber im) {
        mIngreso = im;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getTipoDato() {
        return tipo;
    }

    @Override
    public int getTipo() {
        return TIPO_DATO;
    }
}
