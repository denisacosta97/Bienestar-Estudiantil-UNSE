package com.unse.bienestarestudiantil.Modelos;

public class Comedor {

    String dia, desc, fecha;
    int id;

    public Comedor(String dia, String desc, String fecha) {
        this.dia = dia;
        this.desc = desc;
        this.fecha = fecha;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
