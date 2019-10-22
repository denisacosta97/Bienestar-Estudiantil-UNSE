package com.unse.bienestarestudiantil.Modelos;

public class ReservaHorario {

    private int estado;
    private String horaInicio;
    private String horaFin;

    public ReservaHorario(int estado, String horaInicio, String horaFin) {
        this.estado = estado;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
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
