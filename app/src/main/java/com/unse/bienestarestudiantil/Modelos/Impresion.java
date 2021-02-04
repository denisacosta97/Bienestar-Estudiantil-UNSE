package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Impresion implements Parcelable {

    public static final int COMPLETE = 1;
    public static final int MEDIUM = 2;

    private int dni, cantpag;
    private String descripcion, precio, nombre, apellido, fecharegistro, dia, mes, anio;

    public Impresion(int dni, int cantpag, String descripcion, String precio, String nombre,
                     String apellido, String fecharegistro) {
        this.dni = dni;
        this.cantpag = cantpag;
        this.descripcion = descripcion;
        this.precio = precio;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecharegistro = fecharegistro;
    }

    public Impresion(String dia, String mes, String anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public Impresion() {
        this.dni = -1;
        this.cantpag = -1;
        this.descripcion = "";
        this.precio = "";
        this.nombre = "";
        this.apellido = "";
        this.fecharegistro = "";
    }


    protected Impresion(Parcel in) {

        dni = in.readInt();
        cantpag = in.readInt();
        descripcion = in.readString();
        precio = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        fecharegistro = in.readString();
        dia = in.readString();
        mes = in.readString();
        anio = in.readString();
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(String fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public static Impresion mapper(JSONObject object, int op) {
        Impresion impresion = new Impresion();

        int dni, cantpag;
        String descripcion, precio, nombre, apellido, fecharegistro, dia, mes, anio;

        try {
            switch (op){
                case MEDIUM:
                    dia = object.getString("dia");
                    mes = object.getString("mes");
                    anio = object.getString("anio");

                    impresion = new Impresion(dia, mes, anio);
                    break;
                case COMPLETE:
                    dni = Integer.parseInt(object.getString("idusuario"));
                    cantpag = Integer.parseInt(object.getString("cantidad"));
                    descripcion = object.getString("descripcion");
                    precio = object.getString("precio");
                    nombre = !object.isNull("nombre") ? object.getString("nombre") : " - ";
                    apellido = !object.isNull("apellido") ? object.getString("apellido") : " - ";
                    fecharegistro = (object.getString("fecharegistro"));

                    impresion = new Impresion(dni, cantpag, descripcion, precio, nombre, apellido, fecharegistro);
                    break;
            }

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
        dest.writeInt(dni);
        dest.writeInt(cantpag);
        dest.writeString(descripcion);
        dest.writeString(precio);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(fecharegistro);
        dest.writeString(dia);
        dest.writeString(mes);
        dest.writeString(anio);
    }
}
