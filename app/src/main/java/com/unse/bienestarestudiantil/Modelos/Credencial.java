package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Credencial implements Parcelable {

    private int id, validez, anio;
    private String titulo;
    private String fecha;

    public Credencial(int id, int validez, int anio, String titulo, String fecha) {
        this.id = id;
        this.validez = validez;
        this.anio = anio;
        this.titulo = titulo;
        this.fecha = fecha;
    }

    protected Credencial(Parcel in) {
        id = in.readInt();
        validez = in.readInt();
        anio = in.readInt();
        titulo = in.readString();
        fecha = in.readString();
    }

    public static final Creator<Credencial> CREATOR = new Creator<Credencial>() {
        @Override
        public Credencial createFromParcel(Parcel in) {
            return new Credencial(in);
        }

        @Override
        public Credencial[] newArray(int size) {
            return new Credencial[size];
        }
    };

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(validez);
        dest.writeInt(anio);
        dest.writeString(titulo);
        dest.writeString(fecha);
    }
}

