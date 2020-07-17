package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Colectivo implements Parcelable {

    private int idColectivo, capacidad;
    private String patente, fechaRegistro;

    public Colectivo(int idColectivo, int capacidad, String patente, String fechaRegistro) {
        this.idColectivo = idColectivo;
        this.capacidad = capacidad;
        this.patente = patente;
        this.fechaRegistro = fechaRegistro;
    }

    public Colectivo() {
        this.idColectivo = -1;
        this.capacidad = -1;
        this.patente = "";
        this.fechaRegistro = "";
    }

    protected Colectivo(Parcel in) {
        idColectivo = in.readInt();
        capacidad = in.readInt();
        patente = in.readString();
        fechaRegistro = in.readString();
    }

    public static final Creator<Colectivo> CREATOR = new Creator<Colectivo>() {
        @Override
        public Colectivo createFromParcel(Parcel in) {
            return new Colectivo(in);
        }

        @Override
        public Colectivo[] newArray(int size) {
            return new Colectivo[size];
        }
    };

    public int getIdColectivo() {
        return idColectivo;
    }

    public void setIdColectivo(int idColectivo) {
        this.idColectivo = idColectivo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idColectivo);
        dest.writeInt(capacidad);
        dest.writeString(patente);
        dest.writeString(fechaRegistro);
    }
}
