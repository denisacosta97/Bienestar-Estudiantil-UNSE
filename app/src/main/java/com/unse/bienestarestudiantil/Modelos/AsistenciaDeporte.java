package com.unse.bienestarestudiantil.Modelos;

import java.util.Date;

public class AsistenciaDeporte {

    public static final String TAG = Usuario.class.getSimpleName();
    public static final String TABLE = "asistencia";
    // Labels Table Columns names
    public static final String KEY_ID_ASIS = "idAsistencia";
    public static final String KEY_FECHA_FAL = "fechaFalta";
    public static final String KEY_ASIS = "asistencia";

    private int idAsistencia, asistencia; //0: ausente, 1: presente. ALV.
    private Date fechaFalta;

    public AsistenciaDeporte(int idAsistencia, int asistencia, Date fechaFalta) {
        this.idAsistencia = idAsistencia;
        this.asistencia = asistencia;
        this.fechaFalta = fechaFalta;
    }

    public AsistenciaDeporte() {
        this.idAsistencia = -1;
        this.asistencia = -1;
        this.fechaFalta = new Date();
    }

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public int getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(int asistencia) {
        this.asistencia = asistencia;
    }

    public Date getFechaFalta() {
        return fechaFalta;
    }

    public void setFechaFalta(Date fechaFalta) {
        this.fechaFalta = fechaFalta;
    }
}
