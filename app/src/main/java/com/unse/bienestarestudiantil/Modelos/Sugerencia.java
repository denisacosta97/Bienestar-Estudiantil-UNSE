package com.unse.bienestarestudiantil.Modelos;

import org.json.JSONException;
import org.json.JSONObject;

public class Sugerencia {

    public static final int MEDIUM = 1;

    private int idSugerencia;
    private String fechaRegistro, descripcion;

    public Sugerencia() {
        this.idSugerencia = 0;
        this.fechaRegistro = "fechaRegistro";
        this.descripcion = "descripcion";
    }

    public Sugerencia(int idSugerencia, String fechaRegistro, String descripcion) {
        this.idSugerencia = idSugerencia;
        this.fechaRegistro = fechaRegistro;
        this.descripcion = descripcion;
    }

    public static Sugerencia mapper(JSONObject object, int tipo) {
        Sugerencia sugerencia = new Sugerencia();
        String fecha, descripcion;
        int idSugerencia;
        try {
            switch (tipo) {
                case MEDIUM:
                    idSugerencia = Integer.parseInt(object.getString("idsugerencia"));
                    descripcion = object.getString("descripcion");
                    fecha = object.getString("fecharegistro");
                    sugerencia = new Sugerencia(idSugerencia, fecha, descripcion);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sugerencia;
    }

    public int getIdSugerencia() {
        return idSugerencia;
    }

    public void setIdSugerencia(int idSugerencia) {
        this.idSugerencia = idSugerencia;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
