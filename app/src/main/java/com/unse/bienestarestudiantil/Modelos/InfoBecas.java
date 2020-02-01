package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class InfoBecas implements Parcelable {

    private int id, icon;
    private String nameBeca, desc, fechaInicio, fechaFin, pdf;

    public InfoBecas(int id, int icon, String nameBeca, String desc, String fechaInicio, String fechaFin, String pdf) {
        this.id = id;
        this.icon = icon;
        this.nameBeca = nameBeca;
        this.desc = desc;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.pdf = pdf;
    }

    public InfoBecas() {
        this.id = -1;
        this.icon = -1;
        this.nameBeca = "";
        this.desc = "";
        this.fechaInicio = "";
        this.fechaFin = "";
        this.pdf = "";
    }

    protected InfoBecas(Parcel in) {
        id = in.readInt();
        icon = in.readInt();
        nameBeca = in.readString();
        desc = in.readString();
        fechaInicio = in.readString();
        fechaFin = in.readString();
        pdf = in.readString();
    }

    public static final Creator<InfoBecas> CREATOR = new Creator<InfoBecas>() {
        @Override
        public InfoBecas createFromParcel(Parcel in) {
            return new InfoBecas(in);
        }

        @Override
        public InfoBecas[] newArray(int size) {
            return new InfoBecas[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getNameBeca() {
        return nameBeca;
    }

    public void setNameBeca(String nameBeca) {
        this.nameBeca = nameBeca;
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

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(icon);
        dest.writeString(nameBeca);
        dest.writeString(desc);
        dest.writeString(fechaInicio);
        dest.writeString(fechaFin);
        dest.writeString(pdf);
    }
}
