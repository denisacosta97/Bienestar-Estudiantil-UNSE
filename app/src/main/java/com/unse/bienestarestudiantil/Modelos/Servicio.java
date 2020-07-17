package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Servicio implements Parcelable {

    private int idServicio, dniChofer, idRecorrido;
    private String nombre, apellido, descripcio, patente, dia, mes, anio;

    public Servicio(int idServicio, int dniChofer, int idRecorrido, String nombre, String apellido,
                    String descripcio, String patente, String dia, String mes, String anio) {
        this.idServicio = idServicio;
        this.dniChofer = dniChofer;
        this.idRecorrido = idRecorrido;
        this.nombre = nombre;
        this.apellido = apellido;
        this.descripcio = descripcio;
        this.patente = patente;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public Servicio() {
        this.idServicio = -1;
        this.dniChofer = -1;
        this.idRecorrido = -1;
        this.nombre = "";
        this.apellido = "";
        this.descripcio = "";
        this.patente = "";
        this.dia = "";
        this.mes = "";
        this.anio = "";
    }

    protected Servicio(Parcel in) {
        idServicio = in.readInt();
        dniChofer = in.readInt();
        idRecorrido = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        descripcio = in.readString();
        patente = in.readString();
        dia = in.readString();
        mes = in.readString();
        anio = in.readString();
    }

    public static final Creator<Servicio> CREATOR = new Creator<Servicio>() {
        @Override
        public Servicio createFromParcel(Parcel in) {
            return new Servicio(in);
        }

        @Override
        public Servicio[] newArray(int size) {
            return new Servicio[size];
        }
    };

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getDniChofer() {
        return dniChofer;
    }

    public void setDniChofer(int dniChofer) {
        this.dniChofer = dniChofer;
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

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idServicio);
        dest.writeInt(dniChofer);
        dest.writeInt(idRecorrido);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(descripcio);
        dest.writeString(patente);
        dest.writeString(dia);
        dest.writeString(mes);
        dest.writeString(anio);
    }
}
