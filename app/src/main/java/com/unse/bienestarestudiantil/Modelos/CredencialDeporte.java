package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class CredencialDeporte extends Credencial implements Parcelable {

    private int idTemporada, idDeporte, anio;
    private String nombre, descripcion;

    public CredencialDeporte(int id, String titulo, int validez, int idTemporada, int idDeporte, int anio, String nombre, String descripcion) {
        super(id, titulo, validez);
        this.validez = validez;
        this.idTemporada = idTemporada;
        this.idDeporte = idDeporte;
        this.anio = anio;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public CredencialDeporte(Parcel in) {
        super(in.readInt() ,in.readString(), in.readInt());
        idTemporada = in.readInt();
        idDeporte = in.readInt();
        anio = in.readInt();
        nombre = in.readString();
        descripcion = in.readString();
    }

    public static final Creator<CredencialDeporte> CREATOR = new Creator<CredencialDeporte>() {
        @Override
        public CredencialDeporte createFromParcel(Parcel in) {
            return new CredencialDeporte(in);
        }

        @Override
        public CredencialDeporte[] newArray(int size) {
            return new CredencialDeporte[size];
        }
    };

    public int getIdTemporada() {
        return idTemporada;
    }

    public void setIdTemporada(int idTemporada) {
        this.idTemporada = idTemporada;
    }

    public int getIdDeporte() {
        return idDeporte;
    }

    public void setIdDeporte(int idDeporte) {
        this.idDeporte = idDeporte;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getTitulo());
        dest.writeInt(getValidez());
        dest.writeInt(idTemporada);
        dest.writeInt(idDeporte);
        dest.writeInt(anio);
        dest.writeString(nombre);
        dest.writeString(descripcion);
    }
}
