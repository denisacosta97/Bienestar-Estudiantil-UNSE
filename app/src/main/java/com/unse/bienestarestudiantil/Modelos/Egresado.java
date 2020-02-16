package com.unse.bienestarestudiantil.Modelos;

import java.util.Date;

public class Egresado extends Usuario {

    public static final String TAG = Egresado.class.getSimpleName();
    public static final String TABLE = "egresado";
    // Labels Table Columns names
    public static final String KEY_ID_EGR = "idEgresado";
    public static final String KEY_PROFE = "proesion";
    public static final String KEY_FECHA_EGR = "fechaEgreso";
    public static final String KEY_CHK_DATA = "checkData";

    private String profesion, checkData;
    private int idEgresado;
    private Date fechaEgreso;

    public Egresado(Usuario usuario, String profesion, String checkData1, int idEgresado, Date fechaEgreso) {
        super(usuario.getIdUsuario(), usuario.getTipoUsuario(), usuario.getNombre(), usuario.getApellido(), usuario.getPais(),
                usuario.getProvincia(), usuario.getLocalidad(), usuario.getDomicilio(), usuario.getBarrio(), usuario.getTelefono(),
                usuario.getSexo(), usuario.getMail(), usuario.getCheckData(), usuario.getFoto(), usuario.getFechaNac());this.profesion = profesion;
        this.checkData = checkData1;
        this.idEgresado = idEgresado;
        this.fechaEgreso = fechaEgreso;
    }

    public Egresado() {
        super(-1, -1, "", "", "", "","", "", "", "", "", "", "", "", new Date());
        this.profesion = "";
        this.checkData = "";
        this.idEgresado = -1;
        this.fechaEgreso = new Date();
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getCheckData() {
        return checkData;
    }

    public void setCheckData(String checkData) {
        this.checkData = checkData;
    }

    public int getIdEgresado() {
        return idEgresado;
    }

    public void setIdEgresado(int idEgresado) {
        this.idEgresado = idEgresado;
    }

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }
}
