package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;


import org.json.JSONException;
import org.json.JSONObject;

public class TurnosUAPU implements Parcelable {

    
    public static final int LOW = 0;
    
    public static final int MEDIUM = 1;
    
    public static final int COMPLETE = 2;

    private int idTurno, idUsuario, dia, mes, anio;
    private String nombre, apellido, horario, fechaRegistro, titulo, descripcion, estado;

    public TurnosUAPU(int idTurno, int idUsuario, int dia, int mes, int anio, String estado,
                      String nombre, String apellido, String horario, String fechaRegistro,
                      String titulo, String descripcion) {
        this.idTurno = idTurno;
        this.idUsuario = idUsuario;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.estado = estado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.horario = horario;
        this.fechaRegistro = fechaRegistro;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public TurnosUAPU(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    protected TurnosUAPU(Parcel in) {
        idTurno = in.readInt();
        idUsuario = in.readInt();
        dia = in.readInt();
        mes = in.readInt();
        anio = in.readInt();
        estado = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        horario = in.readString();
        fechaRegistro = in.readString();
        titulo = in.readString();
        descripcion = in.readString();
    }

    public static final Creator<TurnosUAPU> CREATOR = new Creator<TurnosUAPU>() {
        @Override
        public TurnosUAPU createFromParcel(Parcel in) {
            return new TurnosUAPU(in);
        }

        @Override
        public TurnosUAPU[] newArray(int size) {
            return new TurnosUAPU[size];
        }
    };

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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

    public static TurnosUAPU mapper(JSONObject o, int tipo) {
        TurnosUAPU turno = null;
        int idTurno, idUsuario, dia, mes, anio;
        String nombre, apellido, horario, fechaRegistro, titulo, descripcion, estado;
        try {
            switch (tipo) {
                case LOW:
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));

                    turno = new TurnosUAPU(dia, mes, anio);
                    break;
                case MEDIUM:

                    break;
                case COMPLETE:
                    idTurno = Integer.parseInt(o.getString("idturno"));
                    idUsuario = Integer.parseInt(o.getString("idusuario"));
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));
                    estado = o.getString("estado");
                    nombre = o.getString("nombre").equals("null") ? "SIN" : o.getString("nombre");
                    apellido = o.getString("apellido").equals("null") ? "DATOS" : o.getString("apellido");
                    horario = o.getString("horario");
                    fechaRegistro = o.getString("fecharegistro");
                    titulo = o.getString("titulo");
                    descripcion = o.getString("descripcion");
                    turno = new TurnosUAPU(idTurno, idUsuario, dia, mes, anio, estado, nombre,
                            apellido, horario, fechaRegistro, titulo, descripcion);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return turno;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idTurno);
        dest.writeInt(idUsuario);
        dest.writeInt(dia);
        dest.writeInt(mes);
        dest.writeInt(anio);
        dest.writeString(estado);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(horario);
        dest.writeString(fechaRegistro);
        dest.writeString(titulo);
        dest.writeString(descripcion);
    }
}
