package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Instalacion implements Parcelable {

    private int instalacion, estado, id, icon;
    private String nombre, desc, fecha;

    public Instalacion(int instalacion, int estado, String nombre, int icon, String desc ,int id) {
        this.instalacion = instalacion;
        this.estado = estado;
        this.nombre = nombre;
        this.icon = icon;
        this.desc = desc;
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Instalacion() {
        this.instalacion = -1;
        this.estado = -1;
        this.nombre = "";
        this.icon = 0;
        this.desc = "";
        this.id = 0;
    }

    protected Instalacion(Parcel in) {
        instalacion = in.readInt();
        estado = in.readInt();
        nombre = in.readString();
        icon = in.readInt();
        desc = in.readString();
        id = in.readInt();

    }

    public static final Creator<Instalacion> CREATOR = new Creator<Instalacion>() {
        @Override
        public Instalacion createFromParcel(Parcel in) {
            return new Instalacion(in);
        }

        @Override
        public Instalacion[] newArray(int size) {
            return new Instalacion[size];
        }
    };

    public int getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(int instalacion) {
        this.instalacion = instalacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(instalacion);
        dest.writeInt(estado);
        dest.writeString(nombre);
        dest.writeInt(icon);
        dest.writeString(desc);
        dest.writeInt(id);
    }
}
