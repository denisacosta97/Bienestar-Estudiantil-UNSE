package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Transporte implements Parcelable {

    private int id;
    private int dispo;
    private String nombre;
    private String descripcion;
    private String horarioInicio;
    private String horarioFin;

    public Transporte(int id, int dispo, String nombre, String descripcion, String horarioInicio, String horarioFin) {
        this.id = id;
        this.dispo = dispo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
    }

    public Transporte() {
        this.id = -1;
        this.dispo = -1;
        this.nombre = "";
        this.descripcion = "";
        this.horarioInicio = "";
        this.horarioFin = "";
    }

    protected Transporte(Parcel in) {
        id = in.readInt();
        dispo = in.readInt();
        nombre = in.readString();
        descripcion = in.readString();
        horarioInicio = in.readString();
        horarioFin = in.readString();
    }

    public static final Creator<Transporte> CREATOR = new Creator<Transporte>() {
        @Override
        public Transporte createFromParcel(Parcel in) {
            return new Transporte(in);
        }

        @Override
        public Transporte[] newArray(int size) {
            return new Transporte[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDispo() {
        return dispo;
    }

    public void setDispo(int dispo) {
        this.dispo = dispo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFin() {
        return horarioFin;
    }

    public void setHorarioFin(String horarioFin) {
        this.horarioFin = horarioFin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static Transporte mapper(JSONObject o) {
        Transporte transporte1 = new Transporte();
        try {
            int id = o.getInt("idTrans");
            int dispo = o.getInt("dispoTrans");
            String nombre = o.getString("nombreTrans");
            String descripcion = o.getString("descripcionTrans");
            String horarioInicio = o.getString("horarioInicioT");
            String horarioFin = o.getString("horarioFinT");
            transporte1 = new Transporte(id, dispo, nombre, descripcion, horarioInicio, horarioFin);

            return transporte1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return transporte1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(dispo);
        dest.writeString(nombre);
        dest.writeString(descripcion);
        dest.writeString(horarioInicio);
        dest.writeString(horarioFin);
    }
}
