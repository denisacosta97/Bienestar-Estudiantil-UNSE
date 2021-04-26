package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Datos implements Parcelable {

    public static final int PROVINCIA = 1;
    public static final int FACULTAD = 2;

    String id;
    Float cantidad;

    public Datos(String id, Float cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    protected Datos(Parcel in) {
        id = in.readString();
        if (in.readByte() == 0) {
            cantidad = null;
        } else {
            cantidad = in.readFloat();
        }
    }

    public static final Creator<Datos> CREATOR = new Creator<Datos>() {
        @Override
        public Datos createFromParcel(Parcel in) {
            return new Datos(in);
        }

        @Override
        public Datos[] newArray(int size) {
            return new Datos[size];
        }
    };

    public static Datos mapper(JSONObject o, int tipo) {
        String id;
        Float cantidad;
        Datos datos = null;
        try {
            switch (tipo){
                case FACULTAD:
                    id = o.getString("facultad");
                    cantidad = Float.parseFloat(o.getString("cantidad"));
                    datos = new Datos(id, cantidad);
                    break;
                case PROVINCIA:
                    id = o.getString("tipo");
                    cantidad = Float.parseFloat(o.getString("cantidad"));
                    datos = new Datos(id, cantidad);
                    break;
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        return datos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        if (cantidad == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(cantidad);
        }
    }
}
