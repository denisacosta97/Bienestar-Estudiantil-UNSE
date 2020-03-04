package com.unse.bienestarestudiantil.Modelos;

public class Inscripcion {

    public static final String TAG = Usuario.class.getSimpleName();
    public static final String TABLE = "inscripcion_deporte";
    // Labels Table Columns names
    public static final String KEY_ID_INS = "idInscripcion";
    public static final String KEY_FB = "facebook";
    public static final String KEY_IG = "instagram";
    public static final String KEY_WPP = "whatsapp";
    public static final String KEY_OBJ = "objetivo";

    private int idInscripcion;
    private String fb, ig, objetivo;
    private long wpp;

    public Inscripcion(int idInscripcion, String fb, String ig, String objetivo, long wpp) {
        this.idInscripcion = idInscripcion;
        this.fb = fb;
        this.ig = ig;
        this.objetivo = objetivo;
        this.wpp = wpp;
    }

    public Inscripcion() {
        this.idInscripcion = -1;
        this.fb = "";
        this.ig = "";
        this.objetivo = "";
        this.wpp = 0;
    }

    public int getidInscripcion() {
        return idInscripcion;
    }

    public void setidInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
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
