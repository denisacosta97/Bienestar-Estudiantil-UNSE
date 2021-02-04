package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import com.unse.bienestarestudiantil.Herramientas.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiciosU implements Parcelable {

    private String titulo, descripcion, horario, dias, usuarios;
    private int idServicio, validez;
    private Doctor mDoctor;

    public ServiciosU(int idServicio, String titulo, String descripcion, String horario,
                      String dias, String usuarios, int validez, Doctor doctor) {
        this.idServicio = idServicio;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.horario = horario;
        this.dias = dias;
        this.usuarios = usuarios;
        this.validez = validez;
        mDoctor = doctor;
    }

    public ServiciosU() {
        this.idServicio = -1;
        this.titulo = "";
        this.descripcion = "";
        this.horario = "";
        this.dias = "";
        this.usuarios = "";
        this.validez = -1;
        mDoctor = new Doctor();
    }

    protected ServiciosU(Parcel in) {
        idServicio = in.readInt();
        titulo = in.readString();
        descripcion = in.readString();
        horario = in.readString();
        dias = in.readString();
        usuarios = in.readString();
        validez = in.readInt();
        mDoctor = in.readParcelable(Profesor.class.getClassLoader());
    }

    public static final Creator<ServiciosU> CREATOR = new Creator<ServiciosU>() {
        @Override
        public ServiciosU createFromParcel(Parcel in) {
            return new ServiciosU(in);
        }

        @Override
        public ServiciosU[] newArray(int size) {
            return new ServiciosU[size];
        }
    };

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuarios) {
        this.usuarios = usuarios;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public Doctor getDoctor() {
        return mDoctor;
    }

    public void setDoctor(Doctor doctor) {
        mDoctor = doctor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static ServiciosU mapper(JSONObject o) {

        ServiciosU servicio = new ServiciosU();
        String titulo, descripcion, horario, dias, usuarios;
        int idServicio, validez, idUsuario;

        try {

            idServicio = Integer.parseInt(o.getString("idservicio"));
            titulo = o.getString("titulo");
            descripcion = o.getString("descripcion");
            horario = o.getString("horario");
            dias = o.getString("dias");
            usuarios = o.getString("usuarios");

            servicio = new ServiciosU(idServicio, titulo, descripcion, horario, dias,
                    usuarios, 1, null);

            }

         catch (JSONException e) {
            e.printStackTrace();
        }
        return servicio;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idServicio);
        dest.writeString(titulo);
        dest.writeString(descripcion);
        dest.writeString(horario);
        dest.writeString(dias);
        dest.writeString(usuarios);
        dest.writeInt(validez);
        dest.writeParcelable(mDoctor, flags);
    }
}
