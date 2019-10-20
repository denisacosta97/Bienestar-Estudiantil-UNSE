package com.unse.bienestarestudiantil.Modelos;

public class SwipeViewPoliModel {
    private int image;
    private String title;
    private String desc;
    private String prize;

    public SwipeViewPoliModel(int image, String title, String prize, String desc) {
        this.image = image;
        this.title = title;
        this.prize = prize;
        this.desc = desc;
    }

    public SwipeViewPoliModel() {
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
