package com.unse.bienestarestudiantil.Modelos;

public class Espacio {
    private int image;
    private String title;
    private String desc;
    private String prize;
    private boolean seleccionado;

    public Espacio(int image, String title, String prize, String desc) {
        this.image = image;
        this.title = title;
        this.prize = prize;
        this.desc = desc;
        this.setSeleccionado(false);
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public Espacio() {
        this.image = 0;
        this.title = "";
        this.prize = "";
        this.desc = "";
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }
}
