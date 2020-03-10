package com.unse.bienestarestudiantil.Modelos;

import java.util.Date;

public class Usuario {

    public static final String TAG = Usuario.class.getSimpleName();
    public static final String TABLE = "usuario";
    // Labels Table Columns names
    public static final String KEY_ID_USER = "idUsuario";
    public static final String KEY_NOMB = "nombre";
    public static final String KEY_APE = "apellido";
    public static final String KEY_FECHA_NAC = "fechaNac";
    public static final String KEY_PAIS = "pais";
    public static final String KEY_PROV = "provincia";
    public static final String KEY_LOC = "localidad";
    public static final String KEY_DOM = "domicilio";
    public static final String KEY_BAR = "barrio";
    public static final String KEY_TEL = "telefono";
    public static final String KEY_SEX = "sexo";
    public static final String KEY_MAIL = "mail";
    public static final String KEY_TYPE_USER = "tipoUsuario";
    public static final String KEY_FECHA_REGISTRO = "fechaRegistro";
    public static final String KEY_CHK_DATA = "checkData";

    private int idUsuario, tipoUsuario;
    private String nombre, apellido, pais, provincia, localidad, domicilio,
            barrio, telefono, sexo, mail, checkData;
    private String foto;
    private Date fechaNac, fechaRegistro;

    public Usuario(int idUsuario, int tipoUsuario, String nombre, String apellido,
                   String pais, String provincia, String localidad, String domicilio, String barrio,
                   String telefono, String sexo, String mail, String checkData, String foto,
                   Date fechaNac, Date fechaRegistro) {
        this.idUsuario = idUsuario;
        this.tipoUsuario = tipoUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.pais = pais;
        this.provincia = provincia;
        this.localidad = localidad;
        this.domicilio = domicilio;
        this.barrio = barrio;
        this.telefono = telefono;
        this.sexo = sexo;
        this.mail = mail;
        this.checkData = checkData;
        this.foto = foto;
        this.fechaNac = fechaNac;
        this.fechaRegistro = fechaRegistro;
    }

    public Usuario() {
        this.idUsuario = -1;
        this.tipoUsuario = -1;
        this.nombre = "";
        this.apellido = "";
        this.pais = "";
        this.provincia = "";
        this.localidad = "";
        this.domicilio = "";
        this.barrio = "";
        this.telefono = "";
        this.sexo = "";
        this.mail = "";
        this.checkData = "";
        this.foto = "";
        this.fechaNac = new Date();
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCheckData() {
        return checkData;
    }

    public void setCheckData(String checkData) {
        this.checkData = checkData;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }
}
