package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Noticia implements Parcelable {

    private int idNoticia, idUsuario, idArea, validez;
    private String titulo, descripcion, fechaRegistro, fechaModif;

    public Noticia(int idNoticia, int idUsuario, int idArea, int validez, String titulo,
                   String descripcion, String fechaRegistro, String fechaModif) {
        this.idNoticia = idNoticia;
        this.idUsuario = idUsuario;
        this.idArea = idArea;
        this.validez = validez;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
        this.fechaModif = fechaModif;
    }

    protected Noticia(Parcel in) {
        idNoticia = in.readInt();
        idUsuario = in.readInt();
        idArea = in.readInt();
        validez = in.readInt();
        titulo = in.readString();
        descripcion = in.readString();
        fechaRegistro = in.readString();
        fechaModif = in.readString();
    }

    public static final Creator<Noticia> CREATOR = new Creator<Noticia>() {
        @Override
        public Noticia createFromParcel(Parcel in) {
            return new Noticia(in);
        }

        @Override
        public Noticia[] newArray(int size) {
            return new Noticia[size];
        }
    };

    public int getIdNoticia() {
        return idNoticia;
    }

    public void setIdNoticia(int idNoticia) {
        this.idNoticia = idNoticia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(String fechaModif) {
        this.fechaModif = fechaModif;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idNoticia);
        dest.writeInt(idUsuario);
        dest.writeInt(idArea);
        dest.writeInt(validez);
        dest.writeString(titulo);
        dest.writeString(descripcion);
        dest.writeString(fechaRegistro);
        dest.writeString(fechaModif);
    }
}
