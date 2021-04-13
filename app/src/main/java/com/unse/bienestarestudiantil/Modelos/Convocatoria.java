package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Convocatoria implements Parcelable {

    private int idBeca, anio, disponibilidad;
    private String nombre, fechaInicio, fechaFin, nombreBeca;

    public String getNombreBeca() {
        return nombreBeca;
    }

    public void setNombreBeca(String nombreBeca) {
        this.nombreBeca = nombreBeca;
    }

    public Convocatoria(int idBeca, int anio, String nombreBeca, String fechaInicio, String fechaFin) {
        this.idBeca = idBeca;
        this.anio = anio;
        this.nombreBeca = nombreBeca;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Convocatoria(int idBeca, int anio, String nombre, String fechaInicio, String fechaFin, int disponibilidad) {
        this.idBeca = idBeca;
        this.anio = anio;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.disponibilidad = disponibilidad;
    }

    protected Convocatoria(Parcel in) {
        idBeca = in.readInt();
        anio = in.readInt();
        nombre = in.readString();
        fechaInicio = in.readString();
        fechaFin = in.readString();
        disponibilidad = in.readInt();
        nombreBeca = in.readString();
    }

    public static final Creator<Convocatoria> CREATOR = new Creator<Convocatoria>() {
        @Override
        public Convocatoria createFromParcel(Parcel in) {
            return new Convocatoria(in);
        }

        @Override
        public Convocatoria[] newArray(int size) {
            return new Convocatoria[size];
        }
    };

    public static Convocatoria mapper(JSONObject o) {
        Convocatoria convocatoria = null;
        int idBeca, anio, disponibilidad;
        String nombre, fechaInicio, fechaFin;
        try {
            idBeca = Integer.parseInt(o.getString("idbeca"));
            anio = Integer.parseInt(o.getString("anio"));
            nombre = o.getString("nombre");
            fechaInicio = o.getString("fechainicio");
            fechaFin = o.getString("fechafin");
            disponibilidad = Integer.parseInt(o.getString("disponible"));

            convocatoria = new Convocatoria(idBeca, anio, nombre, fechaInicio, fechaFin, disponibilidad);

        }catch (JSONException e){
            e.printStackTrace();
        }

        return convocatoria;
    }

    public int getIdBeca() {
        return idBeca;
    }

    public void setIdBeca(int idBeca) {
        this.idBeca = idBeca;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idBeca);
        dest.writeInt(anio);
        dest.writeString(nombre);
        dest.writeString(fechaInicio);
        dest.writeString(fechaFin);
        dest.writeInt(disponibilidad);
        dest.writeString(nombreBeca);
    }
}
