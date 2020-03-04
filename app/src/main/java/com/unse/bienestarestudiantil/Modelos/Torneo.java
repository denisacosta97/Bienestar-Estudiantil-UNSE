package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

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

    private int id, logo;
    private String nameTorneo, desc, lugar, ubicacion;
    private Date fechaInicio, fechaFin;

    public Torneo(int id, int logo, String nameTorneo, String desc, String lugar, String ubicacion, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.logo = logo;
        this.nameTorneo = nameTorneo;
        this.desc = desc;
        this.lugar = lugar;
        this.ubicacion = ubicacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Torneo() {
        this.id = -1;
        this.logo = 0;
        this.nameTorneo = "";
        this.desc = "";
        this.lugar = "";
        this.ubicacion = "";
        this.fechaInicio = new Date();
        this.fechaFin = new Date();
    }

    protected Torneo(Parcel in) { //REVISAR, FALTAN LAS FECHAS
        id = in.readInt();
        logo = in.readInt();
        nameTorneo = in.readString();
        desc = in.readString();
        lugar = in.readString();
        ubicacion = in.readString();
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
        dest.writeString(ubicacion);
    }

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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}
