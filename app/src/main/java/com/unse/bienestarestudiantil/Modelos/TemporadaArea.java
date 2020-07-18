package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class TemporadaArea implements Parcelable {

    private int dniUsuario;
    private int anio;
    private int validez;
    private String fechaIngreso;

    public TemporadaArea(int dniUsuario, int anio, int validez, String fechaIngreso) {
        this.dniUsuario = dniUsuario;
        this.anio = anio;
        this.validez = validez;
        this.fechaIngreso = fechaIngreso;
    }

    protected TemporadaArea(Parcel in) {
        dniUsuario = in.readInt();
        anio = in.readInt();
        validez = in.readInt();
        fechaIngreso = in.readString();
    }

    public static final Creator<TemporadaArea> CREATOR = new Creator<TemporadaArea>() {
        @Override
        public TemporadaArea createFromParcel(Parcel in) {
            return new TemporadaArea(in);
        }

        @Override
        public TemporadaArea[] newArray(int size) {
            return new TemporadaArea[size];
        }
    };

    public int getDniUsuario() {
        return dniUsuario;
    }

    public void setDniUsuario(int dniUsuario) {
        this.dniUsuario = dniUsuario;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dniUsuario);
        dest.writeInt(anio);
        dest.writeInt(validez);
        dest.writeString(fechaIngreso);
    }
}
