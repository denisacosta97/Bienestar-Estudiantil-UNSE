package com.unse.bienestarestudiantil.Modelos;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Paciente implements Parcelable {
    public static final int COMPLETE = 1;
    public static final int LOW = 2;
    public static final int MEDIUM = 3;

    private int idUsuario, estado, dia, mes, anio, id;
    private String nombre, apellido, facultad, carrera, doctor, fecha, hora, motivoconsulta, tratamiento,
            descripcionEstado;

    public Paciente(int idUsuario, int estado, String nombre, String apellido, String facultad,
                    String carrera, String doctor, String fecha, String hora, String motivoconsulta,
                    String tratamiento) {
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.facultad = facultad;
        this.carrera = carrera;
        this.doctor = doctor;
        this.fecha = fecha;
        this.hora = hora;
        this.motivoconsulta = motivoconsulta;
        this.tratamiento = tratamiento;
    }

    public Paciente(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }


    protected Paciente(Parcel in) {
        idUsuario = in.readInt();
        estado = in.readInt();
        dia = in.readInt();
        mes = in.readInt();
        anio = in.readInt();
        id = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        facultad = in.readString();
        carrera = in.readString();
        doctor = in.readString();
        fecha = in.readString();
        hora = in.readString();
        motivoconsulta = in.readString();
        tratamiento = in.readString();
        descripcionEstado = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idUsuario);
        dest.writeInt(estado);
        dest.writeInt(dia);
        dest.writeInt(mes);
        dest.writeInt(anio);
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(facultad);
        dest.writeString(carrera);
        dest.writeString(doctor);
        dest.writeString(fecha);
        dest.writeString(hora);
        dest.writeString(motivoconsulta);
        dest.writeString(tratamiento);
        dest.writeString(descripcionEstado);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Paciente> CREATOR = new Creator<Paciente>() {
        @Override
        public Paciente createFromParcel(Parcel in) {
            return new Paciente(in);
        }

        @Override
        public Paciente[] newArray(int size) {
            return new Paciente[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
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

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivoconsulta() {
        return motivoconsulta;
    }

    public void setMotivoconsulta(String motivoconsulta) {
        this.motivoconsulta = motivoconsulta;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
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

    public static Paciente mapper(JSONObject o, int tipo) {
        Paciente paciente = null;
        int idUsuario, estado, dia, mes, anio, id;
        String nombre, apellido, facultad, carrera, doctor, fecha,
                hora, motivoconsulta, tratamiento, descripcion, horario, fechaRegistro;
        try {
            switch (tipo) {
                case MEDIUM:
                    id = Integer.parseInt(o.getString("idturno"));
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));
                    idUsuario = Integer.parseInt(o.getString("idusuario"));
                    estado = Integer.parseInt(o.getString("estado"));
                    descripcion = o.getString("descripcion");
                    nombre = o.getString("nombre");
                    apellido = o.getString("apellido");
                    carrera = o.has("carrera") && !o.isNull("carrera") ? o.getString("carrera") : "NO ASIGNADO";
                    facultad = o.has("facultad") && !o.isNull("facultad") ? o.getString("facultad") : "NO ASIGNADO";
                    motivoconsulta = o.has("motivo") && !o.isNull("motivo") ? o.getString("motivo") : "";
                    horario = o.getString("horario");
                    fechaRegistro = o.getString("fecharegistro");
                    paciente = new Paciente(idUsuario, estado, nombre, apellido, facultad,
                            carrera, "", fechaRegistro, horario, motivoconsulta, "");
                    paciente.setDia(dia);
                    paciente.setMes(mes);
                    paciente.setAnio(anio);
                    paciente.setDescripcionEstado(descripcion);
                    paciente.setId(id);
                    break;
                case LOW:
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));

                    paciente = new Paciente(dia, mes, anio);
                    break;
                case COMPLETE:
                    idUsuario = Integer.parseInt(o.getString("idusuario"));
                    estado = Integer.parseInt(o.getString("estado"));
                    nombre = o.getString("nombre");
                    apellido = o.getString("apellido");
                    carrera = o.has("carrera") && !o.isNull("carrera") ? o.getString("carrera") : "NO ASIGNADO";
                    facultad = o.has("facultad") && !o.isNull("facultad") ? o.getString("facultad") : "NO ASIGNADO";
                    doctor = o.getString("doctor");
                    fecha = o.getString("fecha");
                    hora = o.getString("hora");
                    motivoconsulta = o.getString("motivoconsulta");
                    tratamiento = o.getString("tratamiento");
                    paciente = new Paciente(idUsuario, estado, nombre, apellido, facultad,
                            carrera, doctor, fecha, hora, motivoconsulta, tratamiento);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return paciente;
    }


}
