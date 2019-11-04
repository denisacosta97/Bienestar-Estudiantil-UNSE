package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Deporte implements Parcelable {

    private int mIconDeporte, idDep;
    private String mName, entrenador, dias, horario;

    public Deporte(int idDep, int iconDeporte, String name, String entrenador, String dias, String horario) {
        this.mIconDeporte = iconDeporte;
        this.idDep = idDep;
        this.mName = name;
        this.entrenador = entrenador;
        this.dias = dias;
        this.horario = horario;
    }

    public Deporte() {
        this.mIconDeporte = 0;
        this.idDep = 0;
        this.mName = "";
        this.entrenador = "";
        this.dias = "";
        this.horario = "";
    }

    protected Deporte(Parcel in) {
        mIconDeporte = in.readInt();
        idDep = in.readInt();
        mName = in.readString();
        entrenador = in.readString();
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

    public String getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(String entrenador) {
        this.entrenador = entrenador;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mIconDeporte);
        parcel.writeInt(idDep);
        parcel.writeString(mName);
        parcel.writeString(entrenador);
        parcel.writeString(dias);
        parcel.writeString(horario);
    }
}
