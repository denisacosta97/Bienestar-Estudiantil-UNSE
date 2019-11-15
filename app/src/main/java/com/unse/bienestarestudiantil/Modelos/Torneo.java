package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Torneo implements Parcelable {

    private int id, logo;
    private String nameTorneo, desc, fechaInicio, fechaFin, lugar;

    public Torneo(int id, int logo, String lugar, String nameTorneo, String desc, String fechaInicio, String fechaFin) {
        this.id = id;
        this.logo = logo;
        this.lugar = lugar;
        this.nameTorneo = nameTorneo;
        this.desc = desc;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Torneo() {
        this.id = 0;
        this.logo = 0;
        this.lugar = "";
        this.nameTorneo = "";
        this.desc = "";
        this.fechaInicio = "";
        this.fechaFin = "";
    }

    protected Torneo(Parcel in) {
        id = in.readInt();
        logo = in.readInt();
        lugar = in.readString();
        nameTorneo = in.readString();
        desc = in.readString();
        fechaInicio = in.readString();
        fechaFin = in.readString();
    }

    public static final Creator<Torneo> CREATOR = new Creator<Torneo>() {
        @Override
        public Torneo createFromParcel(Parcel in) {
            return new Torneo(in);
        }

        @Override
        public Torneo[] newArray(int size) {
            return new Torneo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getNameTorneo() {
        return nameTorneo;
    }

    public void setNameTorneo(String nameTorneo) {
        this.nameTorneo = nameTorneo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(logo);
        dest.writeString(lugar);
        dest.writeString(nameTorneo);
        dest.writeString(desc);
        dest.writeString(fechaInicio);
        dest.writeString(fechaFin);
    }
}
