package com.unse.bienestarestudiantil.Modelos;

public class Reserva {

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
}
