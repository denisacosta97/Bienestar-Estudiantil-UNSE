package com.unse.bienestarestudiantil.Modelos;

public class Usuario {

    private String id;
    private String nombre;
    private String apellido;
    private String foto;
    private String estatus;
    private String fechaNac;
    //Aqui se agregaria toda la info que tendría un usuario


    public Usuario(String id, String nombre, String apellido, String foto, String estatus) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.foto = foto;
        this.estatus = estatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreCompleto1(){
        return String.format("%s %s",getNombre(),getApellido());
    }

    public String getNombreCompleto2(){
        return String.format("%s %s",getApellido(),getNombre());
    }

    public String getNombreCompleto3(){
        return String.format("%s, %s",getApellido(),getNombre());
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
