package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Punto implements Parcelable {

    public static final int COMPLETE = 1;

    private int id;
    private int idRecorrido;
    private String fechaRecepcion;
    private Double latitud;
    private Double longitud;

    public Punto(int id, int idRecorrido, Double latitud, Double longitud) {
        this.id = id;
        this.idRecorrido = idRecorrido;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Punto(String fechaRecepcion, Double latitud, Double longitud) {
        this.fechaRecepcion = fechaRecepcion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Punto() {

    }

    protected Punto(Parcel in) {
        fechaRecepcion = in.readString();
        if (in.readByte() == 0) {
            latitud = null;
        } else {
            latitud = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitud = null;
        } else {
            longitud = in.readDouble();
        }
    }

    public static final Creator<Punto> CREATOR = new Creator<Punto>() {
        @Override
        public Punto createFromParcel(Parcel in) {
            return new Punto(in);
        }

        @Override
        public Punto[] newArray(int size) {
            return new Punto[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRecorrido() {
        return idRecorrido;
    }

    public void setIdRecorrido(int idRecorrido) {
        this.idRecorrido = idRecorrido;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static Punto mapper(JSONObject o, int tipo) {
        Punto punto = new Punto();
        String fechaRecepcion, latitud, longitud;
        try {
            switch (tipo) {
                case COMPLETE:
                    fechaRecepcion = o.getString("fecharecepcion");
                    latitud = o.getString("lat");
                    longitud = o.getString("long");
                    if (latitud.equals("null"))
                        latitud = "-27.8013843811806";
                    if (longitud.equals("null")) {
                        longitud = "-64.25174295902252";
                    }
                    punto = new Punto(fechaRecepcion, Double.parseDouble(latitud), Double.parseDouble(longitud));
                    break;
            }
            return punto;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return punto;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fechaRecepcion);
        if (latitud == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitud);
        }
        if (longitud == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitud);
        }
    }
}
