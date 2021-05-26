package com.unse.bienestarestudiantil.Modelos;

public class Opciones {

    private String titulo, colorString;
    private int icon, orientation;
    private int color, colorText = 0, sizeText = 0;
    private int id;
    private boolean disponibility = true;

    public Opciones(int orientation, int id, String titulo, int icon, int color) {
        this.titulo = titulo;
        this.icon = icon;
        this.color = color;
        this.id = id;
        this.orientation = orientation;
    }

    public Opciones(int orientation, int id, String titulo, int color) {
        this.titulo = titulo;
        this.color = color;
        this.id = id;
        this.orientation = orientation;
    }

    public Opciones(boolean dis, int orientation, int id, String titulo, int icon, int color) {
        this.titulo = titulo;
        this.icon = icon;
        this.color = color;
        this.id = id;
        this.orientation = orientation;
        this.disponibility = dis;
    }

    public Opciones(boolean dis, int orientation, int id, String titulo, int icon, String color) {
        this.titulo = titulo;
        this.icon = icon;
        this.colorString = color;
        this.id = id;
        this.orientation = orientation;
        this.disponibility = dis;
    }

    public Opciones(int orientation, int id, String titulo, int icon, int color, int colorText) {
        this.titulo = titulo;
        this.icon = icon;
        this.color = color;
        this.id = id;
        this.colorText = colorText;
        this.orientation = orientation;
    }

    public Opciones(boolean dis, int orientation, int id, String titulo, int icon, int color, int colorText) {
        this.titulo = titulo;
        this.icon = icon;
        this.color = color;
        this.id = id;
        this.colorText = colorText;
        this.orientation = orientation;
        this.disponibility = dis;
    }

    public Opciones(int orientation, int id, String titulo, int icon, int color, int colorText, int sizeText) {
        this.titulo = titulo;
        this.icon = icon;
        this.color = color;
        this.id = id;
        this.colorText = colorText;
        this.sizeText = sizeText;
        this.orientation = orientation;
    }

    public Opciones(int orientation, boolean disponibility, int id, String titulo, int icon, int color, int colorText, int sizeText) {
        this.titulo = titulo;
        this.icon = icon;
        this.color = color;
        this.id = id;
        this.colorText = colorText;
        this.sizeText = sizeText;
        this.orientation = orientation;
        this.disponibility = disponibility;
    }

    public Opciones(String titulo) {
        this.titulo = titulo;
    }

    public Opciones(String titulo, int id) {
        this.titulo = titulo;
        this.id = id;
    }

    public String getColorString() {
        return colorString;
    }

    public void setColorString(String colorString) {
        this.colorString = colorString;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public boolean getDisponibility() {
        return disponibility;
    }

    public void setDisponibility(boolean disponibility) {
        this.disponibility = disponibility;
    }

    public int getSizeText() {
        return sizeText;
    }

    public void setSizeText(int sizeText) {
        this.sizeText = sizeText;
    }

    public int getColorText() {
        return colorText;
    }

    public void setColorText(int colorText) {
        this.colorText = colorText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
