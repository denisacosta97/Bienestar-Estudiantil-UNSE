package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Impresion implements Parcelable {

    private int idImpresion, dni, cantpag;
    private String descripcion, precio, fecha;

    public Impresion(int idImpresion, int dni, int cantpag, String descripcion, String precio) {
        this.idImpresion = idImpresion;
        this.dni = dni;
        this.cantpag = cantpag;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Impresion() {
        this.idImpresion = -1;
        this.dni = -1;
        this.cantpag = -1;
        this.descripcion = "";
        this.precio = "";
    }

    protected Impresion(Parcel in) {
        idImpresion = in.readInt();
        dni = in.readInt();
        cantpag = in.readInt();
        descripcion = in.readString();
        precio = in.readString();
    }

    public static final Creator<Impresion> CREATOR = new Creator<Impresion>() {
        @Override
        public Impresion createFromParcel(Parcel in) {
            return new Impresion(in);
        }

        @Override
        public Impresion[] newArray(int size) {
            return new Impresion[size];
        }
    };

    public int getIdImpresion() {
        return idImpresion;
    }

    public void setIdImpresion(int idImpresion) {
        this.idImpresion = idImpresion;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getCantpag() {
        return cantpag;
    }

    public void setCantpag(int cantpag) {
        this.cantpag = cantpag;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public static Impresion mapper(JSONObject object) {
        Impresion impresion = new Impresion();

        int idImpresion, dni, cantpag;
        String descripcion, precio;

        try {
            idImpresion = Integer.parseInt(object.getString("idrecorrido"));
            dni = Integer.parseInt(object.getString("dni"));
            cantpag = Integer.parseInt(object.getString("cantpag"));
            descripcion = object.getString("descripcion");
            precio = object.getString("precio");

            impresion = new Impresion(idImpresion, dni, cantpag, descripcion, precio);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return impresion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idImpresion);
        dest.writeInt(dni);
        dest.writeInt(cantpag);
        dest.writeString(descripcion);
        dest.writeString(precio);
    }
}
