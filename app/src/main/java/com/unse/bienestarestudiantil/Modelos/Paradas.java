package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Paradas implements Parcelable {
    private int idRecorrido, idParada, tipo;
    private Double lat, lon;
    private String descripcion;

    public Paradas(int idRecorrido, int idParada, int tipo, double lat, double lon, String descripcion) {
        this.idRecorrido = idRecorrido;
        this.idParada = idParada;
        this.tipo = tipo;
        this.lat = lat;
        this.lon = lon;
        this.descripcion = descripcion;
    }

    public Paradas() {
        this.idRecorrido = -1;
        this.idParada = -1;
        this.tipo = -1;
        this.lat = null;
        this.lon = null;
        this.descripcion = descripcion;
    }

    protected Paradas(Parcel in) {
        idRecorrido = in.readInt();
        idParada = in.readInt();
        tipo = in.readInt();
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
        descripcion = in.readString();
    }

    public static final Creator<Paradas> CREATOR = new Creator<Paradas>() {
        @Override
        public Paradas createFromParcel(Parcel in) {
            return new Paradas(in);
        }

        @Override
        public Paradas[] newArray(int size) {
            return new Paradas[size];
        }
    };

    public int getIdRecorrido() {
        return idRecorrido;
    }

    public void setIdRecorrido(int idRecorrido) {
        this.idRecorrido = idRecorrido;
    }

    public int getIdParada() {
        return idParada;
    }

    public void setIdParada(int idParada) {
        this.idParada = idParada;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
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
        dest.writeInt(idRecorrido);
        dest.writeInt(idParada);
        dest.writeInt(tipo);
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
        dest.writeString(descripcion);
    }
}
