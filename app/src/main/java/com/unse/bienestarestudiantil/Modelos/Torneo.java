package com.unse.bienestarestudiantil.Modelos;

public class Torneo {

    private int id, logo, lugar;
    private String nameTorneo, desc, fechaInicio, fechaFin;

    public Torneo(int id, int logo, int lugar, String nameTorneo, String desc, String fechaInicio, String fechaFin) {
        this.id = id;
        this.logo = logo;
        this.lugar = lugar;
        this.nameTorneo = nameTorneo;
        this.desc = desc;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Torneo() {
        this.id = 0;
        this.logo = 0;
        this.lugar = 0;
        this.nameTorneo = "";
        this.desc = "";
        this.fechaInicio = "";
        this.fechaFin = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public int getLugar() {
        return lugar;
    }

    public void setLugar(int lugar) {
        this.lugar = lugar;
    }

    public String getNameTorneo() {
        return nameTorneo;
    }

    public void setNameTorneo(String nameTorneo) {
        this.nameTorneo = nameTorneo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
}
