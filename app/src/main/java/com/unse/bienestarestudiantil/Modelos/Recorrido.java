package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Recorrido implements Parcelable {

    private int idRecorrido, idPunto, validez;
    private String descripcion;


    public Recorrido(int idRecorrido, String descripcion, int validez) {
        this.idRecorrido = idRecorrido;
        this.descripcion = descripcion;
        this.validez = validez;
    }

    public Recorrido() {
        this.idRecorrido = -1;
        this.idPunto = -1;
        this.descripcion = "";
    }

    protected Recorrido(Parcel in) {
        idRecorrido = in.readInt();
        idPunto = in.readInt();
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


    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public static Recorrido mapper(JSONObject object) {
        Recorrido recorrido = new Recorrido();

        int idRecorrido, validez;
        String descripcion;
        try {
            idRecorrido = Integer.parseInt(object.getString("idrecorrido"));
            validez = Integer.parseInt(object.getString("validez"));
            descripcion = object.getString("descripcion");
            recorrido = new Recorrido(idRecorrido, descripcion, 1);
            //lat = object.getDouble("lat");
            //lon = object.getDouble("long");

            recorrido = new Recorrido(idRecorrido, descripcion, validez);
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
    }
}
