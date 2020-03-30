package com.unse.bienestarestudiantil.Modelos;

public class Inscripcion{

    public static final String TAG = Usuario.class.getSimpleName();
    public static final String TABLE = "inscripcion_deporte";
    // Labels Table Columns names
    public static final String KEY_ID_INS = "idInscripcion";
    public static final String KEY_FB = "facebook";
    public static final String KEY_IG = "instagram";
    public static final String KEY_WPP = "whatsapp";
    public static final String KEY_OBJ = "objetivo";

    public static final int TIPO_BECA = 1;
    public static final int TIPO_DEPORTE = 2;

    private int idInscripcion;
    private String fb, ig, objetivo;
    private long wpp;

    private String titulo;
    private int anio, idUsuario, idConvocatoria, validez, tipo;

    public Inscripcion(int idInscripcion, String fb, String ig, String objetivo, long wpp) {
        this.idInscripcion = idInscripcion;
        this.fb = fb;
        this.ig = ig;
        this.objetivo = objetivo;
        this.wpp = wpp;
    }

    public Inscripcion(int idInscripcion, String titulo, int anio, int idUsuario, int idConvocatoria, int validez, int tipo) {
        this.idInscripcion = idInscripcion;
        this.titulo = titulo;
        this.anio = anio;
        this.idUsuario = idUsuario;
        this.idConvocatoria = idConvocatoria;
        this.validez = validez;
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(int idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public Inscripcion() {
        this.idInscripcion = -1;
        this.fb = "";
        this.ig = "";
        this.objetivo = "";
        this.wpp = 0;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getIg() {
        return ig;
    }

    public void setIg(String ig) {
        this.ig = ig;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public long getWpp() {
        return wpp;
    }

    public void setWpp(long wpp) {
        this.wpp = wpp;
    }
}
