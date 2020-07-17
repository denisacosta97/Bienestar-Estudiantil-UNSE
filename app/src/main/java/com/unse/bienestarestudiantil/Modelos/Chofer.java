package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Chofer implements Parcelable {

    private int dni;
    private String nombre, apellido, fechaNac, fechaRegistro, fechaModificacion;

    public Chofer(int dni, String nombre, String apellido, String fechaNac, String fechaRegistro,
                  String fechaModificacion) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
    }

    public Chofer() {
        this.dni = -1;
        this.nombre = "";
        this.apellido = "";
        this.fechaNac = "";
        this.fechaRegistro = "";
        this.fechaModificacion = "";
    }

    protected Chofer(Parcel in) {
        dni = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        fechaNac = in.readString();
        fechaRegistro = in.readString();
        fechaModificacion = in.readString();
    }

    public static final Creator<Chofer> CREATOR = new Creator<Chofer>() {
        @Override
        public Chofer createFromParcel(Parcel in) {
            return new Chofer(in);
        }

        @Override
        public Chofer[] newArray(int size) {
            return new Chofer[size];
        }
    };

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dni);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(fechaNac);
        dest.writeString(fechaRegistro);
        dest.writeString(fechaModificacion);
    }
}
