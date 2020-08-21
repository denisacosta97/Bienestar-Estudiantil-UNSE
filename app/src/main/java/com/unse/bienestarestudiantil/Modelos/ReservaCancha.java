package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class ReservaCancha implements Parcelable{

    private int dni, turno;
    private String hora, fechaReserva, cancha, fecha;
    private float precio;

    public ReservaCancha(int dni, int turno, String hora, String fechaReserva,
                         String cancha, String fecha, float precio) {
        this.dni = dni;
        this.turno = turno;
        this.hora = hora;
        this.fechaReserva = fechaReserva;
        this.cancha = cancha;
        this.fecha = fecha;
        this.precio = precio;
    }

    public ReservaCancha() {
        this.dni = -1;
        this.turno = -1;
        this.hora = "";
        this.fechaReserva = "";
        this.cancha = "";
        this.fecha = "";
        this.precio = -1;
    }

    protected ReservaCancha(Parcel in) {
        dni = in.readInt();
        turno = in.readInt();
        hora = in.readString();
        fechaReserva = in.readString();
        cancha = in.readString();
        fecha = in.readString();
        precio = in.readFloat();
    }

    public static final Creator<ReservaCancha> CREATOR = new Creator<ReservaCancha>() {
        @Override
        public ReservaCancha createFromParcel(Parcel in) {
            return new ReservaCancha(in);
        }

        @Override
        public ReservaCancha[] newArray(int size) {
            return new ReservaCancha[size];
        }
    };

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getCancha() {
        return cancha;
    }

    public void setCancha(String cancha) {
        this.cancha = cancha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dni);
        dest.writeInt(turno);
        dest.writeString(hora);
        dest.writeString(fechaReserva);
        dest.writeString(cancha);
        dest.writeString(fecha);
        dest.writeFloat(precio);
    }

}
