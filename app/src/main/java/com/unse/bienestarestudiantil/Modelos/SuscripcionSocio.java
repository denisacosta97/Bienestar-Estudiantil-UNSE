package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class SuscripcionSocio implements Parcelable {

    private int idSuscripcion, idIdentificacion, idSocio, validez, estado, anio;
    private String fechaRegistro;

    public SuscripcionSocio(int idSuscripcion, int idIdentificacion, int idSocio, int validez, int estado, int anio, String fechaRegistro) {
        this.idSuscripcion = idSuscripcion;
        this.idIdentificacion = idIdentificacion;
        this.idSocio = idSocio;
        this.validez = validez;
        this.estado = estado;
        this.anio = anio;
        this.fechaRegistro = fechaRegistro;
    }

    protected SuscripcionSocio(Parcel in) {
        idSuscripcion = in.readInt();
        idIdentificacion = in.readInt();
        idSocio = in.readInt();
        validez = in.readInt();
        estado = in.readInt();
        anio = in.readInt();
        fechaRegistro = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idSuscripcion);
        dest.writeInt(idIdentificacion);
        dest.writeInt(idSocio);
        dest.writeInt(validez);
        dest.writeInt(estado);
        dest.writeInt(anio);
        dest.writeString(fechaRegistro);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SuscripcionSocio> CREATOR = new Creator<SuscripcionSocio>() {
        @Override
        public SuscripcionSocio createFromParcel(Parcel in) {
            return new SuscripcionSocio(in);
        }

        @Override
        public SuscripcionSocio[] newArray(int size) {
            return new SuscripcionSocio[size];
        }
    };

    public int getIdSuscripcion() {
        return idSuscripcion;
    }

    public void setIdSuscripcion(int idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
    }

    public int getIdIdentificacion() {
        return idIdentificacion;
    }

    public void setIdIdentificacion(int idIdentificacion) {
        this.idIdentificacion = idIdentificacion;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
