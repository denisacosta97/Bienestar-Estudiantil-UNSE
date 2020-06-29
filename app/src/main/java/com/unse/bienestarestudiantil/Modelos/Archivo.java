package com.unse.bienestarestudiantil.Modelos;

import android.app.job.JobService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Archivo implements Serializable {
    private int id, validez, idArea, idUsuario;
    private String nombreArchivo, fechaCreacion, fechaModificacion, nombre;

    public Archivo(int id, int validez, int idArea, int idUsuario, String nombreArchivo,
                   String fechaCreacion, String fechaModificacion, String nombre) {
        this.id = id;
        this.validez = validez;
        this.idArea = idArea;
        this.idUsuario = idUsuario;
        this.nombreArchivo = nombreArchivo;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.nombre = nombre;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public static Archivo toMapper(JSONObject object){
        Archivo archivo = null;
        try {
            int id = Integer.parseInt(object.getString("idArchivo"));
            int idArea = Integer.parseInt(object.getString("idArea"));
            int validez = Integer.parseInt(object.getString("validez"));
            int idUsuario = Integer.parseInt(object.getString("idUsuario"));
            String nombre = object.getString("nombre");
            String fecha = object.getString("fechaCreacion");
            String fechaModif = object.getString("fechaModificacion");
            String nombreArchivo = object.getString("nombreArchivo");

            archivo = new Archivo(id, validez,idArea, idUsuario, nombreArchivo, fecha, fechaModif,
                    nombre);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return archivo;
    }
}
