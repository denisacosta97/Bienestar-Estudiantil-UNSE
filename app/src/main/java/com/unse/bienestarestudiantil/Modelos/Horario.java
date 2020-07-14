package com.unse.bienestarestudiantil.Modelos;

public class Horario {

    private int id;
    private int estado;
    private String horaInicio, horaFin;

    public Horario(int id, int estado, String horaInicio, String horaFin) {
        this.id = id;
        this.estado = estado;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Horario() {
        this.id = -1;
        this.estado = -1;
        this.horaInicio = "";
        this.horaFin = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
}
