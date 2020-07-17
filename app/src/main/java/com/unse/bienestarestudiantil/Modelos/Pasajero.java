package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Pasajero implements Parcelable {

    private int dni, idRecorrido;
    private String nombre, apellido, dia, mes, anio, fechaRegistro, fechaLocal;
    private Double lat, lon;

    public Pasajero(int dni, int idRecorrido, String nombre, String apellido, String dia, String mes,
                    String anio, String fechaRegistro, String fechaLocal, Double lat, Double lon) {
        this.dni = dni;
        this.idRecorrido = idRecorrido;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.fechaRegistro = fechaRegistro;
        this.fechaLocal = fechaLocal;
        this.lat = lat;
        this.lon = lon;
    }

    public Pasajero() {
        this.dni = -1;
        this.idRecorrido = -1;
        this.nombre = "";
        this.apellido = "";
        this.dia = "";
        this.mes = "";
        this.anio = "";
        this.fechaRegistro = "";
        this.fechaLocal = "";
        this.lat = null;
        this.lon = null;
    }

    protected Pasajero(Parcel in) {
        dni = in.readInt();
        idRecorrido = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        dia = in.readString();
        mes = in.readString();
        anio = in.readString();
        fechaRegistro = in.readString();
        fechaLocal = in.readString();
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

    public static final Creator<Pasajero> CREATOR = new Creator<Pasajero>() {
        @Override
        public Pasajero createFromParcel(Parcel in) {
            return new Pasajero(in);
        }

        @Override
        public Pasajero[] newArray(int size) {
            return new Pasajero[size];
        }
    };

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getIdRecorrido() {
        return idRecorrido;
    }

    public void setIdRecorrido(int idRecorrido) {
        this.idRecorrido = idRecorrido;
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

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaLocal() {
        return fechaLocal;
    }

    public void setFechaLocal(String fechaLocal) {
        this.fechaLocal = fechaLocal;
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
        dest.writeInt(dni);
        dest.writeInt(idRecorrido);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(dia);
        dest.writeString(mes);
        dest.writeString(anio);
        dest.writeString(fechaRegistro);
        dest.writeString(fechaLocal);
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
