package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Familiar implements Parcelable {

    private int dni, validez;
    private String nombre, apellido, relacion;
    private String fechaNac, fechaRegistro;

    public Familiar(int dni, String nombre, String apellido, String relacion, String fechaNac, String fechaRegistro, int validez) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.relacion = relacion;
        this.fechaNac = fechaNac;
        this.fechaRegistro = fechaRegistro;
        this.validez = validez;
    }

    protected Familiar(Parcel in) {
        dni = in.readInt();
        validez = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        relacion = in.readString();
        fechaNac = in.readString();
        fechaRegistro = in.readString();
    }

    public static final Creator<Familiar> CREATOR = new Creator<Familiar>() {
        @Override
        public Familiar createFromParcel(Parcel in) {
            return new Familiar(in);
        }

        @Override
        public Familiar[] newArray(int size) {
            return new Familiar[size];
        }
    };

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

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

    public String getRelacion() {
        return relacion;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
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


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dni);
        dest.writeInt(validez);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(relacion);
        dest.writeString(fechaNac);
        dest.writeString(fechaRegistro);
    }
}
