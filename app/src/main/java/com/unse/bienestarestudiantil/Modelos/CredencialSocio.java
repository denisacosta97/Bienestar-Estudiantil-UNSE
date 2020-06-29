package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class CredencialSocio extends Credencial implements Parcelable {

    private int idSocio, idUsuario, anio, tipoUsuario;
    private String nombre, apellido, fechaRegistro, sexo;
    private int validez, idSuscripcionSocio, idCredencialSocio;

    public CredencialSocio(int id, String titulo, int validez, int idUsuario,
                           int anio, int tipoUsuario, String nombre, String apellido, String fechaRegistro, String sexo) {
        super(id, validez, 0, titulo, null);
        this.idSocio = id;
        this.idUsuario = idUsuario;
        this.anio = anio;
        this.tipoUsuario = tipoUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaRegistro = fechaRegistro;
        this.sexo = sexo;
    }

    public CredencialSocio(int id, int validez, int anio, String titulo, String fecha, int idSocio,
                           int idUsuario, String nombre, int idSuscripcionSocio, int idCredencialSocio) {
        super(id, validez, anio, titulo, fecha);
        this.idSocio = idSocio;
        this.idUsuario = idUsuario;
        this.anio = anio;
        this.nombre = nombre;
        this.validez = validez;
        this.idSuscripcionSocio = idSuscripcionSocio;
        this.idCredencialSocio = idCredencialSocio;
    }

    protected CredencialSocio(Parcel in) {
        super(in.readInt(), in.readInt(), in.readInt() ,in.readString(), in.readString());
        idSocio = getId();
        idUsuario = in.readInt();
        anio = in.readInt();
        tipoUsuario = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        fechaRegistro = in.readString();
        sexo = in.readString();
        validez = in.readInt();
        idCredencialSocio = in.readInt();
        idSuscripcionSocio = in.readInt();
    }

    public static final Creator<CredencialSocio> CREATOR = new Creator<CredencialSocio>() {
        @Override
        public CredencialSocio createFromParcel(Parcel in) {
            return new CredencialSocio(in);
        }

        @Override
        public CredencialSocio[] newArray(int size) {
            return new CredencialSocio[size];
        }
    };

    @Override
    public int getValidez() {
        return validez;
    }

    @Override
    public void setValidez(int validez) {
        this.validez = validez;
    }

    public int getIdSuscripcionSocio() {
        return idSuscripcionSocio;
    }

    public void setIdSuscripcionSocio(int idSuscripcionSocio) {
        this.idSuscripcionSocio = idSuscripcionSocio;
    }

    public int getIdCredencialSocio() {
        return idCredencialSocio;
    }

    public void setIdCredencialSocio(int idCredencialSocio) {
        this.idCredencialSocio = idCredencialSocio;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeInt(getValidez());
        dest.writeInt(getAnio());
        dest.writeString(getTitulo());
        dest.writeString(getFecha());
        dest.writeInt(idUsuario);
        dest.writeInt(anio);
        dest.writeInt(tipoUsuario);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(fechaRegistro);
        dest.writeString(sexo);
        dest.writeInt(validez);
        dest.writeInt(idCredencialSocio);
        dest.writeInt(idSuscripcionSocio);
    }
}
