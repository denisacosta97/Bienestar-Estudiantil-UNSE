package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Certificado implements Parcelable {

    private int idEmisor, idUsuario, dia, mes, anio, validez;
    private String fechaRegistro, descripcion;

    public Certificado(int idEmisor, int idUsuario, int dia, int mes, int anio, int validez,
                       String fechaRegistro, String descripcion) {
        this.idEmisor = idEmisor;
        this.idUsuario = idUsuario;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.validez = validez;
        this.fechaRegistro = fechaRegistro;
        this.descripcion = descripcion;
    }

    protected Certificado(Parcel in) {
        idEmisor = in.readInt();
        idUsuario = in.readInt();
        dia = in.readInt();
        mes = in.readInt();
        anio = in.readInt();
        validez = in.readInt();
        fechaRegistro = in.readString();
        descripcion = in.readString();
    }

    public static final Creator<Certificado> CREATOR = new Creator<Certificado>() {
        @Override
        public Certificado createFromParcel(Parcel in) {
            return new Certificado(in);
        }

        @Override
        public Certificado[] newArray(int size) {
            return new Certificado[size];
        }
    };

    public int getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(int idEmisor) {
        this.idEmisor = idEmisor;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static Certificado mapper(JSONObject o) {
        Certificado cert = null;
        int idEmisor, idUsuario, dia, mes, anio, validez;
        String fechaRegistro, descripcion;
        try {

            idEmisor = Integer.parseInt(o.getString("idrmisor"));
            idUsuario = Integer.parseInt(o.getString("idusuario"));
            dia = Integer.parseInt(o.getString("dia"));
            mes = Integer.parseInt(o.getString("mes"));
            anio = Integer.parseInt(o.getString("anio"));
            validez = Integer.parseInt(o.getString("validez"));
            fechaRegistro = o.getString("fecharegistro");
            descripcion = o.getString("descripcion");

            cert = new Certificado(idEmisor, idUsuario, dia, mes, anio, validez, fechaRegistro, descripcion);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cert;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idEmisor);
        dest.writeInt(idUsuario);
        dest.writeInt(dia);
        dest.writeInt(mes);
        dest.writeInt(anio);
        dest.writeInt(validez);
        dest.writeString(fechaRegistro);
        dest.writeString(descripcion);
    }
}
