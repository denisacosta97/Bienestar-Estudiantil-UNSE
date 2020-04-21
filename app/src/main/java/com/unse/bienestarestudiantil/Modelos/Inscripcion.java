package com.unse.bienestarestudiantil.Modelos;

import org.json.JSONException;
import org.json.JSONObject;

public class Inscripcion {

    public static final int TIPO_BECA = 1;
    public static final int TIPO_DEPORTE = 2;

    public static final int COMPLETE = 1;

    private int idInscripcion;
    private int idEstado;
    private int idUsuario;
    private int idTemporada;
    private int wsp;
    private int cantMaterias;
    private String facebook;
    private String instagram;
    private String objetivo;
    private String peso;
    private String altura;
    private String fechaRegistro;
    private String fechaModificacion;
    private int disponible;
    private int validez;
    private int idPregunta;
    private String cuales;
    private int intensidad;
    private String lugar;

    private String nombreEstado;

    private String titulo;
    private int anio, idConvocatoria, tipo;

    //Inscripcion principal
    public Inscripcion(int idInscripcion, int idEstado, int idUsuario, int idTemporada, int wsp,
                       int cantMaterias, String facebook, String instagram, String objetivo,
                       String peso, String altura, String fechaRegistro, String fechaModificacion,
                       int disponible, int validez, int idPregunta, String cuales, int intensidad,
                       String lugar) {
        this.idInscripcion = idInscripcion;
        this.idEstado = idEstado;
        this.idUsuario = idUsuario;
        this.idTemporada = idTemporada;
        this.wsp = wsp;
        this.cantMaterias = cantMaterias;
        this.facebook = facebook;
        this.instagram = instagram;
        this.objetivo = objetivo;
        this.peso = peso;
        this.altura = altura;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
        this.disponible = disponible;
        this.validez = validez;
        this.idPregunta = idPregunta;
        this.cuales = cuales;
        this.intensidad = intensidad;
        this.lugar = lugar;
    }

    //Inscripcion para el perfil, muestra inscripciones generales
    public Inscripcion(int idInscripcion, String titulo, int anio, int idUsuario,
                       int idConvocatoria, int validez, int tipo, int idEstado, String estado) {
        this.idInscripcion = idInscripcion;
        this.titulo = titulo;
        this.anio = anio;
        this.idUsuario = idUsuario;
        this.idConvocatoria = idConvocatoria;
        this.validez = validez;
        this.tipo = tipo;
        this.idEstado = idEstado;
        this.nombreEstado = estado;
    }

    //Inscripcion reducida para listado
    public Inscripcion(int idInscripcion, int idEstado, String nombreEstado, String titulo, int idUsuario, int tipo) {
        this.idInscripcion = idInscripcion;
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
        this.titulo = titulo;
        this.idUsuario = idUsuario;
        this.tipo = tipo;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTemporada() {
        return idTemporada;
    }

    public void setIdTemporada(int idTemporada) {
        this.idTemporada = idTemporada;
    }

    public int getWsp() {
        return wsp;
    }

    public void setWsp(int wsp) {
        this.wsp = wsp;
    }

    public int getCantMaterias() {
        return cantMaterias;
    }

    public void setCantMaterias(int cantMaterias) {
        this.cantMaterias = cantMaterias;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getCuales() {
        return cuales;
    }

    public void setCuales(String cuales) {
        this.cuales = cuales;
    }

    public int getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(int intensidad) {
        this.intensidad = intensidad;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
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

    public int getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(int idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public static Inscripcion mapper(JSONObject o, int tipo) {
        Inscripcion inscripcion = null;
        int idInscripcion, disponible, idEstado, idUsuario, idTemporada, wsp,
                inten, cantMaterias, idPreguntas, validez;
        String facebook, instagram, objetivo, altura, peso, cuales, lugar, fechaRegistro, fechaModificacion;

        try {
            switch (tipo) {
                case COMPLETE:
                    idEstado = Integer.parseInt(o.getString("estado"));
                    idInscripcion = Integer.parseInt(o.getString("idInscripcion"));
                    idUsuario = Integer.parseInt(o.getString("idUsuario"));
                    idTemporada = Integer.parseInt(o.getString("idTemporada"));
                    wsp = Integer.parseInt(o.getString("isWhatsapp"));
                    inten = Integer.parseInt(o.getString("intensidad"));
                    cantMaterias = Integer.parseInt(o.getString("cantMaterias"));
                    idPreguntas = Integer.parseInt(o.getString("idPregunta"));
                    disponible = Integer.parseInt(o.getString("disponible"));
                    facebook = o.getString("facebook");
                    instagram = o.getString("instagram");
                    objetivo = o.getString("objetivo");
                    altura = o.getString("altura");
                    peso = o.getString("peso");
                    cuales = o.getString("cuales");
                    lugar = o.getString("lugar");
                    fechaModificacion = o.getString("fechaModificacion");
                    fechaRegistro = o.getString("fechaRegistro");
                    validez = Integer.parseInt(o.getString("validez"));
                    inscripcion = new Inscripcion(idInscripcion, idEstado, idUsuario, idTemporada,
                            wsp, cantMaterias, facebook,
                            instagram, objetivo, peso, altura, fechaRegistro, fechaModificacion,
                            disponible, validez, idPreguntas, cuales, inten, lugar);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return inscripcion;
    }
}
