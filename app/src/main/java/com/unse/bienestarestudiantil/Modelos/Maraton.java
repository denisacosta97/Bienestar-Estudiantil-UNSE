package com.unse.bienestarestudiantil.Modelos;

import org.json.JSONException;
import org.json.JSONObject;

public class Maraton {

    public static final int COMPLETE = 1;
    public static final int LOW = 2;

    private int idInscripcion, idUsuario, dia, mes, anio;
    private String fechaRegistro, carrera, edad, nombre, apellido, descripcion, fechaNac;

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
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

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static Maraton mapper(JSONObject o, int tipo) {
        Maraton maraton = null;

        int idInscripcion, idUsuario, dia, mes, anio, edad;
        String fechaRegistro, carrera, nombre, apellido, descripcion, fechaNac;
        try {
            switch (tipo) {
                case LOW:
                    idInscripcion = Integer.parseInt(o.getString("idinscripcion"));
                    idUsuario = Integer.parseInt(o.getString("idusuario"));
                    carrera = o.getString("distancia");
                    nombre = o.getString("nombre");
                    apellido = o.getString("apellido");
                    descripcion = o.getString("descripcion");
                    edad = Integer.parseInt(o.getString("categoria"));
                    fechaNac = o.getString("fechanac");
                    maraton = new Maraton(idInscripcion, idUsuario, carrera, String.valueOf(edad),
                            nombre, apellido, descripcion, fechaNac);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return maraton;
    }

    public Maraton(int idInscripcion, int idUsuario, String carrera, String edad, String nombre, String apellido, String descripcion, String fechaNac) {
        this.idInscripcion = idInscripcion;
        this.idUsuario = idUsuario;
        this.carrera = carrera;
        this.edad = edad;
        this.nombre = nombre;
        this.apellido = apellido;
        this.descripcion = descripcion;
        this.fechaNac = fechaNac;
    }
}
