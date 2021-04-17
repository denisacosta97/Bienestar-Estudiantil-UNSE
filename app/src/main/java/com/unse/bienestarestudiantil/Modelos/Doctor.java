package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Doctor extends Lista implements Parcelable {

    public static final int COMPLETE = 1;
    public static final int MEDIUM = 2;
    private int idUsuario, anio, validez, idServicio;
    private String especialidad, matricula, casaestudio, nombre, apellido;

    public static final int BASIC = 3;

    public Doctor(int idUsuario, int anio, int validez, String especialidad, String matricula,
                  String casaestudio, String nombre, String apellido) {
        this.idUsuario = idUsuario;
        this.anio = anio;
        this.validez = validez;
        this.especialidad = especialidad;
        this.matricula = matricula;
        this.casaestudio = casaestudio;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Doctor(int idUsuario, String nombre, String apellido, String especialidad) {
        this.idUsuario = idUsuario;
        this.especialidad = especialidad;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Doctor(int idUsuario, int validez, String nombre, String apellido) {
        this.idUsuario = idUsuario;
        this.validez = validez;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Doctor() {
    }

    protected Doctor(Parcel in) {
        idUsuario = in.readInt();
        anio = in.readInt();
        validez = in.readInt();
        especialidad = in.readString();
        matricula = in.readString();
        casaestudio = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        idServicio = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idUsuario);
        dest.writeInt(anio);
        dest.writeInt(validez);
        dest.writeString(especialidad);
        dest.writeString(matricula);
        dest.writeString(casaestudio);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeInt(idServicio);
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

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

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCasaestudio() {
        return casaestudio;
    }

    public void setCasaestudio(String casaestudio) {
        this.casaestudio = casaestudio;
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

    public static Doctor mapper(JSONObject o, int tipo) {

        Doctor doctor = new Doctor();
        String nombre, apellido, especialidad;
        int idServicio;
        int validez, idUsuario;

        try {
            switch (tipo) {
                case BASIC:
                    nombre = o.getString("nombre");
                    apellido = o.getString("apellido");
                    idUsuario = Integer.parseInt(o.getString("idusuario"));
                    validez = Integer.parseInt(o.getString("validez"));
                    doctor = new Doctor(idUsuario, validez, nombre, apellido);
                    break;
                case MEDIUM:
                    idServicio = Integer.parseInt(o.getString("idservicio"));
                    nombre = o.getString("nombre");
                    apellido = o.getString("apellido");
                    //especialidad = o.getString("especialidad");

                    doctor = new Doctor(idServicio, nombre, apellido, "");
                    doctor.setIdServicio(idServicio);

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return doctor;
    }

}
