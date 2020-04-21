package com.unse.bienestarestudiantil.Modelos;

import org.json.JSONException;
import org.json.JSONObject;

public class Temporada {

    private int idTemporada;
    private int idDeporte;
    private int anio;
    private int disponible;

    public Temporada(){

    }

    public Temporada(int idTemporada, int idDeporte, int anio, int disponible) {
        this.idTemporada = idTemporada;
        this.idDeporte = idDeporte;
        this.anio = anio;
        this.disponible = disponible;
    }

    public static Temporada mapper(JSONObject tem) {
        Temporada temporada = new Temporada();
        try {
            int idTemp = Integer.parseInt(tem.getString("idTemporada"));
            int idDeporte = Integer.parseInt(tem.getString("idDeporte"));
            int anio = Integer.parseInt(tem.getString("anio"));
            int disponible = Integer.parseInt(tem.getString("disponible"));

            temporada = new Temporada(idTemp,idDeporte,anio, disponible);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return temporada;
    }

    public int getIdTemporada() {
        return idTemporada;
    }

    public void setIdTemporada(int idTemporada) {
        this.idTemporada = idTemporada;
    }

    public int getIdDeporte() {
        return idDeporte;
    }

    public void setIdDeporte(int idDeporte) {
        this.idDeporte = idDeporte;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }
}
