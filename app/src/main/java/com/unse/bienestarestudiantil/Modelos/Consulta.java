package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Consulta extends Lista implements Parcelable {

    public static final int LOW = 0;
    public static final int UAPU = 1;
    public static final int HISTORIAL = 2;
    public static final int HISTORIAL_2 = 3;

    private int dni, dniReceptor;
    private String fecha, consulta;
    private String nomAp, motivo;

    public Consulta(int dni, int dniReceptor, String fecha, String consulta) {
        this.dni = dni;
        this.dniReceptor = dniReceptor;
        this.fecha = fecha;
        this.consulta = consulta;
    }

    public Consulta(int dni, int dniReceptor, String fecha, String consulta, String nomAp, String motivo) {
        this.dni = dni;
        this.dniReceptor = dniReceptor;
        this.fecha = fecha;
        this.consulta = consulta;
        this.nomAp = nomAp;
        this.motivo = motivo;
    }

    protected Consulta(Parcel in) {
        dni = in.readInt();
        dniReceptor = in.readInt();
        fecha = in.readString();
        consulta = in.readString();
    }

    public static final Creator<Consulta> CREATOR = new Creator<Consulta>() {
        @Override
        public Consulta createFromParcel(Parcel in) {
            return new Consulta(in);
        }

        @Override
        public Consulta[] newArray(int size) {
            return new Consulta[size];
        }
    };

    public static Consulta mapper(JSONObject o, int tipo) {
        Consulta consulta = null;
        int dni;
        String cons, fecha;
        String nomAp, motivo;
        try {

            switch (tipo) {
                case HISTORIAL:
                    fecha = o.getString("fecharegistro");
                    cons = o.getString("titulo");
                    consulta = new Consulta(0, 0, fecha, cons);
                    break;
                case HISTORIAL_2:
                    fecha = o.getString("fechaconsulta");
                    cons = o.getString("consulta");
                    consulta = new Consulta(0, 0, fecha, cons);
                    break;
                case LOW:
                    fecha = o.getString("fecharegistro");
                    cons = o.getString("descripcion");
                    dni = Integer.parseInt(o.getString("idusuario"));

                    consulta = new Consulta(dni, 0, fecha, cons);
                    break;
                case UAPU:
                    fecha = o.getString("fecharegistro");
                    cons = o.getString("descripcion");
                    dni = Integer.parseInt(o.getString("idusuario"));
                    nomAp = o.getString("nombre");
                    motivo = o.getString("motivo");

                    consulta = new Consulta(dni, 0, fecha, cons, nomAp, motivo);
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return consulta;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getDniReceptor() {
        return dniReceptor;
    }

    public void setDniReceptor(int dniReceptor) {
        this.dniReceptor = dniReceptor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dni);
        dest.writeInt(dniReceptor);
        dest.writeString(fecha);
        dest.writeString(consulta);
    }
}
