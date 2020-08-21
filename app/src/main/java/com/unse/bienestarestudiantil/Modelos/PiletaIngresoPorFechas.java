package com.unse.bienestarestudiantil.Modelos;

public class PiletaIngresoPorFechas {

    private String dni;
    private int categoria;
    private String fechas;
    private Integer tipos;

    public PiletaIngresoPorFechas(String dni, int categoria, String fechas, Integer tipos) {
        this.dni = dni;
        this.categoria = categoria;
        this.fechas = fechas;
        this.tipos = tipos;
    }

    public PiletaIngresoPorFechas() {

    }

    public String getFechas() {
        return fechas;
    }

    public void setFechas(String fechas) {
        this.fechas = fechas;
    }

    public Integer getTipos() {
        return tipos;
    }

    public void setTipos(Integer tipos) {
        this.tipos = tipos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }


}
