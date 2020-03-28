package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Reserva implements Parcelable {

    private int mIcon, idReserva;
    private String mTitulo;
    private ReservaHorario mRangoHora;

    public Reserva(int idReserva, int icon, String titulo, ReservaHorario rangoHora) {
        this.idReserva = idReserva;
        mIcon = icon;
        mTitulo = titulo;
        mRangoHora = rangoHora;
    }

    public Reserva() {
        this.idReserva = 0;
        mIcon = 0;
        mTitulo = "";
        mRangoHora = null;
    }

    protected Reserva(Parcel in) {
        mIcon = in.readInt();
        idReserva = in.readInt();
        mTitulo = in.readString();
    }

    public static final Creator<Reserva> CREATOR = new Creator<Reserva>() {
        @Override
        public Reserva createFromParcel(Parcel in) {
            return new Reserva(in);
        }

        @Override
        public Reserva[] newArray(int size) {
            return new Reserva[size];
        }
    };

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getTitulo() {
        return mTitulo;
    }

    public void setTitulo(String titulo) {
        mTitulo = titulo;
    }

    public ReservaHorario getRangoHora() {
        return mRangoHora;
    }

    public void setRangoHora(ReservaHorario rangoHora) {
        mRangoHora = rangoHora;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIcon);
        dest.writeInt(idReserva);
        dest.writeString(mTitulo);
    }
}
