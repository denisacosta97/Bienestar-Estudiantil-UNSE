package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Noticia implements Parcelable {

    private String titulo, cuerpo, fechahora, urlImagen, urlWeb, etiqueta;
    private int id, idTipoNoticias, idCategoria;
    private boolean isURL = false, isEncuesta = false, isButton = false;

    public Noticia() {
        this.titulo = "";
        this.cuerpo = "";
        this.fechahora = "";
        this.id = -1;
        this.idTipoNoticias = 0;
        this.etiqueta = "";
    }

    public Noticia(String titulo, String cuerpo, String fechahora, String urlImagen, int id, int idTipoNoticias, int cat, String
            etiqueta) {
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.fechahora = fechahora;
        this.urlImagen = urlImagen;
        this.id = id;
        this.idTipoNoticias = idTipoNoticias;
        this.idCategoria = cat;
        this.etiqueta = etiqueta;
    }

    protected Noticia(Parcel in) {
        titulo = in.readString();
        cuerpo = in.readString();
        fechahora = in.readString();
        urlImagen = in.readString();
        id = in.readInt();
        idTipoNoticias = in.readInt();
        idCategoria = in.readInt();
        etiqueta = in.readString();
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

    public int getIdTipoNoticias() {
        return idTipoNoticias;
    }

    public void setIdTipoNoticias(int idTipoNoticias) {
        this.idTipoNoticias = idTipoNoticias;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public boolean isButton() {
        return isButton;
    }

    public void setButton(boolean button) {
        isButton = button;
    }

    public String getTitulo() {
        return titulo;
    }

    public Noticia setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public Noticia setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
        return this;
    }

    public String getFechahora() {
        return fechahora;
    }

    public Noticia setFechahora(String fechahora) {
        this.fechahora = fechahora;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;

    }

    public String getUrlWeb() {
        return urlWeb;
    }

    public void setUrlWeb(String urlWeb) {
        this.urlWeb = urlWeb;
        setButton(true);
        setURL(true);

    }

    public int getIdTipo() {
        return idTipoNoticias;
    }

    public void setIdTipo(int idTipoNoticias) {
        this.idTipoNoticias = idTipoNoticias;

    }

    public boolean isURL() {
        return isURL;
    }

    public void setURL(boolean URL) {
        isURL = URL;
    }

    public boolean isEncuesta() {
        return isEncuesta;
    }

    public void setEncuesta(boolean encuesta) {
        isEncuesta = encuesta;
        setButton(true);
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(titulo);
        parcel.writeString(cuerpo);
        parcel.writeString(fechahora);
        parcel.writeString(urlImagen);
        parcel.writeInt(id);
        parcel.writeInt(idTipoNoticias);
        parcel.writeInt(idCategoria);
        parcel.writeString(etiqueta);
    }
}
