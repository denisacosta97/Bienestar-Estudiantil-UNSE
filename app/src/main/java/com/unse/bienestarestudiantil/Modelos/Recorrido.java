package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Recorrido implements Parcelable {

    private int idRecorrido, idPunto, validez;
    private String descripcion;
    private Double lat, lon;

    public Recorrido(int idRecorrido, int idPunto, String descripcion, Double lat, Double lon) {
        this.idRecorrido = idRecorrido;
        this.idPunto = idPunto;
        this.descripcion = descripcion;
        this.lat = lat;
        this.lon = lon;
    }

    public Recorrido(int idRecorrido, String descripcion, int validez) {
        this.idRecorrido = idRecorrido;
        this.descripcion = descripcion;
        this.validez = validez;
    }

    public Recorrido() {
        this.idRecorrido = -1;
        this.idPunto = -1;
        this.descripcion = "";
        this.lat = null;
        this.lon = null;
    }

    protected Recorrido(Parcel in) {
        idRecorrido = in.readInt();
        idPunto = in.readInt();
        descripcion = in.readString();
        validez = in.readInt();
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

    public int getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(int idPunto) {
        this.idPunto = idPunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public static Recorrido mapper(JSONObject object) {
        Recorrido recorrido = new Recorrido();

        int idRecorrido, idPunto;
        String descripcion;
        Double lat, lon;

        try {
            idRecorrido = Integer.parseInt(object.getString("idRecorrido"));
            idPunto = Integer.parseInt(object.getString("idPunto"));
            descripcion = object.getString("descripcion");
            lat = object.getDouble("lat");
            lon = object.getDouble("long");

            recorrido = new Recorrido(idRecorrido, idPunto, descripcion, lat, lon);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return recorrido;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idRecorrido);
        dest.writeInt(idPunto);
        dest.writeString(descripcion);
        dest.writeInt(validez);
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
