package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Certificado implements Parcelable {

    private int idEmisor, idUsuario, dia, mes, anio, validez;
    private String fechaRegistro, descripcion, nombreM, apellidoM, nombre, apellido,carrera, facultad;

    public static final int COMPLETE = 1;

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
        nombreM = in.readString();
        apellidoM = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        carrera = in.readString();
        facultad = in.readString();
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
        dest.writeString(nombreM);
        dest.writeString(apellidoM);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(carrera);
        dest.writeString(facultad);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getNombreM() {
        return nombreM;
    }

    public void setNombreM(String nombreM) {
        this.nombreM = nombreM;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
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

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

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


    public Certificado(int idEmisor, int idUsuario, String fechaRegistro, String descripcion, String nombreM, String apellidoM, String nombre, String apellido, String carrera, String facultad) {
        this.idEmisor = idEmisor;
        this.idUsuario = idUsuario;
        this.fechaRegistro = fechaRegistro;
        this.descripcion = descripcion;
        this.nombreM = nombreM;
        this.apellidoM = apellidoM;
        this.nombre = nombre;
        this.apellido = apellido;
        this.carrera = carrera;
        this.facultad = facultad;
    }

    public static Certificado mapper(JSONObject o, int tipo) {
        Certificado cert = null;
        int idEmisor, idUsuario, dia, mes, anio, validez;
        String fechaRegistro, descripcion,nombreM, apellidoM, nombre, apellido, carrera, facultad;
        try {
            switch (tipo){
                case COMPLETE:
                    idEmisor = Integer.parseInt(o.getString("idemisor"));
                    nombreM = o.getString("nombrem");
                    apellidoM = o.getString("apellidom");
                    idUsuario = Integer.parseInt(o.getString("idusuario"));
                    nombre = o.getString("nombre");
                    apellido = o.getString("apellido");
                    carrera = o.has("carrera") && !o.isNull("carrera") ? o.getString("carrera") : "NO ASIGNADO";
                    facultad = o.has("facultad") && !o.isNull("facultad") ? o.getString("facultad") : "NO ASIGNADO";
                    fechaRegistro = o.getString("fecharegistro");
                    descripcion = o.getString("descripcion");
                    cert = new Certificado(idEmisor, idUsuario, fechaRegistro, descripcion, nombreM, apellidoM,
                            nombre, apellido, carrera, facultad);
                    break;
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cert;
    }


}
