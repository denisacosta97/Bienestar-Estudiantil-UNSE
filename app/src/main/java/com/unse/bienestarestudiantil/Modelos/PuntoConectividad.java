package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class PuntoConectividad implements Parcelable {

    public static final int LOW = 1;
    public static final int LOW_2 = 3;
    public static final int LOW_3 = 5;
    public static final int MEDIUM = 4;
    public static final int COMPLETE = 2;

    private int id, idUsuario, dia, mes, anio, estado, validez, lugar, cantidad;
    private String descripcion, nombreLugar, fechaRegistro, fechaModificacion, horario, nombre, apellido;

    public PuntoConectividad(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    protected PuntoConectividad(Parcel in) {
        id = in.readInt();
        idUsuario = in.readInt();
        dia = in.readInt();
        mes = in.readInt();
        anio = in.readInt();
        estado = in.readInt();
        validez = in.readInt();
        lugar = in.readInt();
        descripcion = in.readString();
        nombreLugar = in.readString();
        fechaRegistro = in.readString();
        fechaModificacion = in.readString();
        horario = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        cantidad = in.readInt();
    }

    public static final Creator<PuntoConectividad> CREATOR = new Creator<PuntoConectividad>() {
        @Override
        public PuntoConectividad createFromParcel(Parcel in) {
            return new PuntoConectividad(in);
        }

        @Override
        public PuntoConectividad[] newArray(int size) {
            return new PuntoConectividad[size];
        }
    };

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public int getLugar() {
        return lugar;
    }

    public void setLugar(int lugar) {
        this.lugar = lugar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreLugar() {
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public PuntoConectividad(int id, int idUsuario, int dia, int mes, int anio, String descripcion, String horario, String nombre, String apellido) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.descripcion = descripcion;
        this.horario = horario;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public static PuntoConectividad mapper(JSONObject o, int tipo) {

        int id, idUsuario, dia, mes, anio, estado, validez, lugar, cantidad;
        String descripcion, nombreLugar, horario, fechaRegistro, fechaModificacion, nombre, apellido;
        PuntoConectividad puntoConectividad = null;
        try {
            switch (tipo) {
                case COMPLETE:
                    id = Integer.parseInt(o.getString("id"));
                    idUsuario = Integer.parseInt(o.getString("idusuario"));
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));
                    nombre = o.getString("nombre");
                    apellido = o.getString("apellido");
                    horario = o.getString("horario");
                    descripcion = o.getString("descripcion");
                    nombreLugar = o.getString("nombrelugar");
                    puntoConectividad = new PuntoConectividad(id, idUsuario, dia, mes, anio, descripcion,
                            horario, nombre, apellido);
                    puntoConectividad.setNombreLugar(nombreLugar);
                    break;
                case MEDIUM:
                    lugar = Integer.parseInt(o.getString("lugar"));
                    nombreLugar = o.getString("descripcion");
                    cantidad = Integer.parseInt(o.getString("cantidad"));
                    puntoConectividad = new PuntoConectividad(0, 0, 0);
                    puntoConectividad.setNombreLugar(nombreLugar);
                    puntoConectividad.setLugar(lugar);
                    puntoConectividad.setCantidad(cantidad);
                    break;
                case LOW_3:
                    estado = Integer.parseInt(o.getString("estado"));
                    descripcion = o.getString("descripcion");
                    cantidad = Integer.parseInt(o.getString("cantidad"));
                    puntoConectividad = new PuntoConectividad(0, 0, 0);
                    puntoConectividad.setDescripcion(descripcion);
                    puntoConectividad.setEstado(estado);
                    puntoConectividad.setCantidad(cantidad);
                    break;
                case LOW_2:
                    horario = o.getString("horario");
                    cantidad = Integer.parseInt(o.getString("cantidad"));
                    puntoConectividad = new PuntoConectividad(0, 0, 0);
                    puntoConectividad.setHorario(horario);
                    puntoConectividad.setCantidad(cantidad);
                    break;
                case LOW:
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));
                    puntoConectividad = new PuntoConectividad(dia, mes, anio);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return puntoConectividad;


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(idUsuario);
        dest.writeInt(dia);
        dest.writeInt(mes);
        dest.writeInt(anio);
        dest.writeInt(estado);
        dest.writeInt(validez);
        dest.writeInt(lugar);
        dest.writeString(descripcion);
        dest.writeString(nombreLugar);
        dest.writeString(fechaRegistro);
        dest.writeString(fechaModificacion);
        dest.writeString(horario);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeInt(cantidad);
    }
}
