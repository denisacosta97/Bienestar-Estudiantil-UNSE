package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Noticia implements Parcelable {

    public static final int COMPLETE = 1;
    public static final int TIPO_ADMIN = 10;
    private int idNoticia, idUsuario, idArea, validez;
    private String titulo, subtitlo, descripcion, fechaRegistro, fechaModif, imagen
            , nombre, apellido, nombreArea;

    public Noticia(int idNoticia, int idUsuario, int idArea, int validez, String titulo, String subtitlo,
                   String descripcion, String fechaRegistro, String fechaModif, String imagen) {
        this.idNoticia = idNoticia;
        this.idUsuario = idUsuario;
        this.idArea = idArea;
        this.validez = validez;
        this.titulo = titulo;
        this.subtitlo = subtitlo;
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
        this.fechaModif = fechaModif;
        this.imagen = imagen;
    }

    protected Noticia(Parcel in) {
        idNoticia = in.readInt();
        idUsuario = in.readInt();
        idArea = in.readInt();
        validez = in.readInt();
        titulo = in.readString();
        subtitlo = in.readString();
        descripcion = in.readString();
        fechaRegistro = in.readString();
        fechaModif = in.readString();
        imagen = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        nombreArea = in.readString();
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

    public static Noticia mapper(JSONObject o, int complete) {
        Noticia noticia = null;
         int idNoticia, idUsuario, idArea, validez;
         String titulo, subtitulo, descripcion, fechaRegistro, fechaModif, imagen, nombre, apellido, nombreArea;
        try {
            switch (complete){
                case COMPLETE:
                    idNoticia = Integer.parseInt(o.getString("idnoticia"));
                    idArea = Integer.parseInt(o.getString("idarea"));
                    validez = Integer.parseInt(o.getString("validez"));
                    titulo = o.getString("titulo");
                    subtitulo = o.getString("subtitulo");
                    descripcion = o.getString("descripcion");
                    imagen = o.getString("imagen");
                    fechaRegistro = o.getString("fecharegistro");
                    fechaModif = o.getString("fechamodificacion");
                    nombre = o.getString("nombre");
                    apellido = o.getString("apellido");
                    nombreArea = o.getString("area");
                    noticia = new Noticia(idNoticia, 0, idArea, validez, titulo, subtitulo, descripcion,
                            fechaRegistro, fechaModif, imagen);
                    noticia.setNombre(nombre);
                    noticia.setApellido(apellido);
                    noticia.setNombreArea(nombreArea);
                    break;
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        return noticia;
    }

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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
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

    public String getSubtitlo() {
        return subtitlo;
    }

    public void setSubtitlo(String subtitlo) {
        this.subtitlo = subtitlo;
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
        dest.writeString(subtitlo);
        dest.writeString(descripcion);
        dest.writeString(fechaRegistro);
        dest.writeString(fechaModif);
        dest.writeString(imagen);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(nombreArea);
    }
}
