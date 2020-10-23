package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class FechasInhabilitadas implements Parcelable {

    private String dia, mes, anio, desc;
    private int val;

    public FechasInhabilitadas(String dia, String mes, String anio, int val, String desc) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.val = val;
        this.desc = desc;
    }

    protected FechasInhabilitadas(Parcel in) {
        dia = in.readString();
        mes = in.readString();
        anio = in.readString();
        val = in.readInt();
        desc = in.readString();
    }

    public static final Creator<FechasInhabilitadas> CREATOR = new Creator<FechasInhabilitadas>() {
        @Override
        public FechasInhabilitadas createFromParcel(Parcel in) {
            return new FechasInhabilitadas(in);
        }

        @Override
        public FechasInhabilitadas[] newArray(int size) {
            return new FechasInhabilitadas[size];
        }
    };

    public static FechasInhabilitadas mapper(JSONObject o) {
        FechasInhabilitadas fechasInhabilitadas = null;
        String desc, dia, mes, anio;
        int val;
        try {
            dia = o.getString("dia");
            mes = o.getString("mes");
            anio = o.getString("anio");
            desc = o.getString("descripcion");
            val = Integer.parseInt(o.getString("validez"));

            fechasInhabilitadas = new FechasInhabilitadas(dia, mes, anio, val, desc);

        }catch (JSONException e){
            e.printStackTrace();
        }

        return fechasInhabilitadas;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dia);
        dest.writeString(mes);
        dest.writeString(anio);
        dest.writeInt(val);
        dest.writeString(desc);
    }
}
