package com.unse.bienestarestudiantil.Modelos;

public class Pregunta {

    public static final String TAG = Usuario.class.getSimpleName();
    public static final String TABLE = "pregunta";
    // Labels Table Columns names
    public static final String KEY_ID_PREG = "idPregunta";
    public static final String KEY_CUAL = "cuales";
    public static final String KEY_INTES = "intensidad";
    public static final String KEY_UBI = "lugar";

    private int idPregunta, intensidad;
    private String cual, ubi;

    public Pregunta(int idPregunta, int intensidad, String cual, String ubi) {
        this.idPregunta = idPregunta;
        this.intensidad = intensidad;
        this.cual = cual;
        this.ubi = ubi;
    }

    public Pregunta() {
        this.idPregunta = -1;
        this.intensidad = 0;
        this.cual = "";
        this.ubi = "";
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(int intensidad) {
        this.intensidad = intensidad;
    }

    public String getCual() {
        return cual;
    }

    public void setCual(String cual) {
        this.cual = cual;
    }

    public String getUbi() {
        return ubi;
    }

    public void setUbi(String ubi) {
        this.ubi = ubi;
    }
}
