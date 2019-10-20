package com.unse.bienestarestudiantil.Modelos;

public class Deportes {

    private int mIconDeporte, idDep;
    private String mName;

    public Deportes(int idDeporte, int iconDeporte, String name) {
        idDep = idDeporte;
        mIconDeporte = iconDeporte;
        mName = name;
    }

    public Deportes() {
        idDep = 0;
        mIconDeporte = 0;
        mName = "";
    }

    public int getIdDep() {
        return idDep;
    }

    public void setIdDep(int idDep) {
        this.idDep = idDep;
    }

    public int getIconDeporte() {
        return mIconDeporte;
    }

    public void setIconDeporte(int iconDeporte) {
        mIconDeporte = iconDeporte;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
