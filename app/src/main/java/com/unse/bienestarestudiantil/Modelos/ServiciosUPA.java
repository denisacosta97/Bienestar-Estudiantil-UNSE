package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;


import com.unse.bienestarestudiantil.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiciosUPA implements Parcelable {

    private int idServicio, icon, turnos;
    private String name, desc, dias, hora, nomApMed, categoria;

    public static final int BASIC = 1;

    public ServiciosUPA(int idServicio, int icon, String name, String desc, String dias,
                        String hora, String nomApMed, int categ) {
        this.idServicio = idServicio;
        this.icon = icon;
        this.turnos = categ;
        this.name = name;
        this.desc = desc;
        this.dias = dias;
        this.hora = hora;
        this.nomApMed = nomApMed;
    }


    protected ServiciosUPA(Parcel in) {
        idServicio = in.readInt();
        icon = in.readInt();
        turnos = in.readInt();
        name = in.readString();
        desc = in.readString();
        dias = in.readString();
        hora = in.readString();
        nomApMed = in.readString();
        categoria = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idServicio);
        dest.writeInt(icon);
        dest.writeInt(turnos);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(dias);
        dest.writeString(hora);
        dest.writeString(nomApMed);
        dest.writeString(categoria);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ServiciosUPA> CREATOR = new Creator<ServiciosUPA>() {
        @Override
        public ServiciosUPA createFromParcel(Parcel in) {
            return new ServiciosUPA(in);
        }

        @Override
        public ServiciosUPA[] newArray(int size) {
            return new ServiciosUPA[size];
        }
    };

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNomApMed() {
        return nomApMed;
    }

    public void setNomApMed(String nomApMed) {
        this.nomApMed = nomApMed;
    }


    public int getTurnos() {
        return turnos;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public static ServiciosUPA mapper(JSONObject o, int tipo) {
        int idServicio, icon, categ, turnos;
        String name, desc, dias, hora, nomApMed, categoria;
        ServiciosUPA serviciosUPA = null;
        try {

            switch (tipo) {
                case BASIC:
                    name = o.getString("titulo");
                    idServicio = Integer.parseInt(o.getString("idservicio"));
                    desc = o.getString("descripcion");
                    hora = o.getString("horario");
                    dias = o.getString("dias");
                    categoria = o.getString("usuarios");
                    turnos = Integer.parseInt(o.getString("turno"));
                    serviciosUPA = new ServiciosUPA(idServicio, R.drawable.ic_medico, name, desc, dias,
                            hora, "", turnos);
                    serviciosUPA.setCategoria(categoria);

                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return serviciosUPA;
    }
}
