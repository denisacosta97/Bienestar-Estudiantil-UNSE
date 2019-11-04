package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Asistencia implements Parcelable {

    private int id, idFotoAlum;
    private Boolean checked, noChecked;
    private String mName;

    public Asistencia(int id, int idFotoAlum, String name) {
        this.id = id;
        this.idFotoAlum = idFotoAlum;
        mName = name;
    }

    public Asistencia() {
        this.id = 0;
        this.idFotoAlum = 0;
        mName = "";
    }

    protected Asistencia(Parcel in) {
        id = in.readInt();
        idFotoAlum = in.readInt();
        mName = in.readString();
    }

    public static final Creator<Asistencia> CREATOR = new Creator<Asistencia>() {
        @Override
        public Asistencia createFromParcel(Parcel in) {
            return new Asistencia(in);
        }

        @Override
        public Asistencia[] newArray(int size) {
            return new Asistencia[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFotoAlum() {
        return idFotoAlum;
    }

    public void setIdFotoAlum(int idFotoAlum) {
        this.idFotoAlum = idFotoAlum;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(idFotoAlum);
        parcel.writeString(mName);
    }
}
