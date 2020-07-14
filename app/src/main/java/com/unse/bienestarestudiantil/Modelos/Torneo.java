package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import com.unse.bienestarestudiantil.Herramientas.Utils;

import org.slf4j.helpers.Util;

import java.util.Date;

public class Torneo implements Parcelable {

    public static final String TAG = Usuario.class.getSimpleName();
    public static final String TABLE = "torneo";
    // Labels Table Columns names
    public static final String KEY_ID_TOR = "idPregunta";
    public static final String KEY_NOM = "nombre";
    public static final String KEY_LUG = "lugar";
    public static final String KEY_FECHA_INI = "fechaInicio";
    public static final String KEY_FECHA_FIN = "fechaFin";
    public static final String KEY_DESC = "descripcion";
    public static final String KEY_UBI = "ubicacion";

    private int id, logo, validez, dispo;
    private String nameTorneo, desc, lugar, lat, lon, fechaInicio, fechaFin;

    public Torneo(int id, int logo, String nameTorneo, String desc, String lugar, String lat,
                  String lon, String fechaInicio, String fechaFin, int validez, int dispo) {
        this.id = id;
        this.logo = logo;
        this.nameTorneo = nameTorneo;
        this.desc = desc;
        this.lugar = lugar;
        this.lat = lat;
        this.lon = lon;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.validez = validez;
        this.dispo = dispo;
    }

    public Torneo() {
        this.id = -1;
        this.logo = 0;
        this.nameTorneo = "";
        this.desc = "";
        this.lugar = "";
        this.lat = "";
        this.lon = "";
        this.fechaInicio = "";
        this.fechaFin = "";
        this.validez = -1;
        this.dispo = -1;
    }

    protected Torneo(Parcel in) {
        id = in.readInt();
        logo = in.readInt();
        nameTorneo = in.readString();
        desc = in.readString();
        lugar = in.readString();
        lat = in.readString();
        lon = in.readString();
        fechaInicio = in.readString();
        fechaFin = in.readString();
        validez = in.readInt();
        dispo = in.readInt();
    }

    public static final Creator<Torneo> CREATOR = new Creator<Torneo>() {
        @Override
        public Torneo createFromParcel(Parcel in) {
            return new Torneo(in);
        }

        @Override
        public Torneo[] newArray(int size) {
            return new Torneo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getNameTorneo() {
        return nameTorneo;
    }

    public void setNameTorneo(String nameTorneo) {
        this.nameTorneo = nameTorneo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public int getDispo() {
        return dispo;
    }

    public void setDispo(int dispo) {
        this.dispo = dispo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(logo);
        dest.writeString(nameTorneo);
        dest.writeString(desc);
        dest.writeString(lugar);
        dest.writeString(lat);
        dest.writeString(lon);
        dest.writeString(fechaInicio);
        dest.writeString(fechaFin);
        dest.writeInt(validez);
        dest.writeInt(dispo);
    }
}
