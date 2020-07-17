package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Parada implements Parcelable {

    private int idParada, idRecorrido;
    private Double lat, lon;

    public Parada(int idParada, int idRecorrido, Double lat, Double lon) {
        this.idParada = idParada;
        this.idRecorrido = idRecorrido;
        this.lat = lat;
        this.lon = lon;
    }

    public Parada() {
        this.idParada = -1;
        this.idRecorrido = -1;
        this.lat = null;
        this.lon = null;
    }

    protected Parada(Parcel in) {
        idParada = in.readInt();
        idRecorrido = in.readInt();
        if (in.readByte() == 0) {
            lat = null;
        } else {
            lat = in.readDouble();
        }
        if (in.readByte() == 0) {
            lon = null;
        } else {
            lon = in.readDouble();
        }
    }

    public static final Creator<Parada> CREATOR = new Creator<Parada>() {
        @Override
        public Parada createFromParcel(Parcel in) {
            return new Parada(in);
        }

        @Override
        public Parada[] newArray(int size) {
            return new Parada[size];
        }
    };

    public int getIdParada() {
        return idParada;
    }

    public void setIdParada(int idParada) {
        this.idParada = idParada;
    }

    public int getIdRecorrido() {
        return idRecorrido;
    }

    public void setIdRecorrido(int idRecorrido) {
        this.idRecorrido = idRecorrido;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idParada);
        dest.writeInt(idRecorrido);
        if (lat == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lat);
        }
        if (lon == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lon);
        }
    }
}
