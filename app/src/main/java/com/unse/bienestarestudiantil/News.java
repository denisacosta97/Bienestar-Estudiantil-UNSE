package com.unse.bienestarestudiantil;

public class News {

    private String titulo, cuerpo, fechahora, img;
    private int id;

    public News(String titulo, String cuerpo, String fechahora, int id, String img) {
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.fechahora = fechahora;
        this.id = id;
        this.img = img;
    }

    public News() {
        this.titulo = "";
        this.cuerpo = "";
        this.fechahora = "";
        this.id = 0;
        this.img = "";
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getFechahora() {
        return fechahora;
    }

    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
