package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Deporte implements Parcelable {

    private int mIconDeporte, idDep;
    private String mName, desc, lugar, dias, horario;
    private Profesor mProfesor;

    public Deporte(int iconDeporte, int idDep, String name, String desc, String lugar, String dias, String horario, Profesor profesor) {
        mIconDeporte = iconDeporte;
        this.idDep = idDep;
        mName = name;
        this.desc = desc;
        this.lugar = lugar;
        this.dias = dias;
        this.horario = horario;
        mProfesor = profesor;
    }

    public Deporte() {
        mIconDeporte = -1;
        this.idDep = -1;
        mName = "";
        this.desc = "";
        this.lugar = "";
        this.dias = "";
        this.horario = "";
        mProfesor = new Profesor();
    }

    protected Deporte(Parcel in) {
        mIconDeporte = in.readInt();
        idDep = in.readInt();
        mName = in.readString();
        desc = in.readString();
        lugar = in.readString();
        dias = in.readString();
        horario = in.readString();
    }

    public static final Creator<Deporte> CREATOR = new Creator<Deporte>() {
        @Override
        public Deporte createFromParcel(Parcel in) {
            return new Deporte(in);
        }

        @Override
        public Deporte[] newArray(int size) {
            return new Deporte[size];
        }
    };

    public int getIconDeporte() {
        return mIconDeporte;
    }

    public void setIconDeporte(int iconDeporte) {
        mIconDeporte = iconDeporte;
    }

    public int getIdDep() {
        return idDep;
    }

    public void setIdDep(int idDep) {
        this.idDep = idDep;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Profesor getProfesor() {
        return mProfesor;
    }

    public void setProfesor(Profesor profesor) {
        mProfesor = profesor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIconDeporte);
        dest.writeInt(idDep);
        dest.writeString(mName);
        dest.writeString(desc);
        dest.writeString(lugar);
        dest.writeString(dias);
        dest.writeString(horario);
    }
}
