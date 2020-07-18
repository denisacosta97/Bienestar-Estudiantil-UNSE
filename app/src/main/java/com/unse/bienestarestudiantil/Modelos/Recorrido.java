package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Recorrido implements Parcelable {

    private int idRecorrido;
    private String descripcion;
    private int validez;

    public Recorrido(int idRecorrido, String descripcion, int validez) {
        this.idRecorrido = idRecorrido;
        this.descripcion = descripcion;
        this.validez = validez;
    }


    protected Recorrido(Parcel in) {
        idRecorrido = in.readInt();
        descripcion = in.readString();
        validez = in.readInt();
    }

    public static final Creator<Recorrido> CREATOR = new Creator<Recorrido>() {
        @Override
        public Recorrido createFromParcel(Parcel in) {
            return new Recorrido(in);
        }

        @Override
        public Recorrido[] newArray(int size) {
            return new Recorrido[size];
        }
    };

    public int getIdRecorrido() {
        return idRecorrido;
    }

    public void setIdRecorrido(int idRecorrido) {
        this.idRecorrido = idRecorrido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idRecorrido);
        dest.writeString(descripcion);
        dest.writeInt(validez);
    }
}
