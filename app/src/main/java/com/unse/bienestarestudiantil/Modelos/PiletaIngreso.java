package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import com.unse.bienestarestudiantil.Herramientas.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PiletaIngreso implements Parcelable {

    private int dni;
    private int id;
    private int categoria;
   // private int cantMayores;
    //private int cantMenores;
    private String fecha;
    private float precio1;
    private int dniEmpleado;
    private int cantidadTotal;
    private ArrayList<PiletaIngresoParcial> acompaniantes;

    protected PiletaIngreso(Parcel in) {
        dni = in.readInt();
        id = in.readInt();
        categoria = in.readInt();
        fecha = in.readString();
        precio1 = in.readFloat();
        dniEmpleado = in.readInt();
        cantidadTotal = in.readInt();
    }

    public static final Creator<PiletaIngreso> CREATOR = new Creator<PiletaIngreso>() {
        @Override
        public PiletaIngreso createFromParcel(Parcel in) {
            return new PiletaIngreso(in);
        }

        @Override
        public PiletaIngreso[] newArray(int size) {
            return new PiletaIngreso[size];
        }
    };

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public PiletaIngreso() {
        this.dni = 0;
        this.id = 0;
        this.categoria = 0;
        this.fecha = null;
        this.precio1 = 0;
        acompaniantes = new ArrayList<>();
    }

    public PiletaIngreso(int dni, int id, int categoria, String fecha, float precio1,
                         int cantidadTotal, int dniEmpleado) {
        this.dni = dni;
        this.id = id;
        this.categoria = categoria;
        this.fecha = fecha;
        this.precio1 = precio1;
        this.cantidadTotal = cantidadTotal;
        this.dniEmpleado = dniEmpleado;
        acompaniantes = new ArrayList<>();
    }

    public int getDniEmpleado() {
        return dniEmpleado;
    }

    public void setDniEmpleado(int dniEmpleado) {
        this.dniEmpleado = dniEmpleado;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getPrecio1() {
        return precio1;
    }

    public void setPrecio1(float precio1) {
        this.precio1 = precio1;
    }

    public ArrayList<PiletaIngresoParcial> getAcompaniantes() {
        return acompaniantes;
    }

    public void setAcompaniantes(ArrayList<PiletaIngresoParcial> acompaniantes) {
        this.acompaniantes = acompaniantes;
    }

    public static PiletaIngreso mapper(JSONObject object, int mode) {
        PiletaIngreso piletaIngreso = new PiletaIngreso();

        int dni, id, categoria, dniEmpleado, cantidadTotal;
        String fecha;
        float precio1;

        try {
            switch (mode) {
                case 0:
                    id = Integer.parseInt(object.getString("idingreso"));
                    dni = Integer.parseInt(object.getString("idusuario"));
                    categoria = Integer.parseInt(object.getString("categoriausuario"));
                    cantidadTotal = Integer.parseInt(object.getString("cantidad"));
                    fecha = object.getString("fechaingreso");
                    precio1 = Float.parseFloat(object.getString("preciototal"));

                    piletaIngreso = new PiletaIngreso(dni, id, categoria, fecha, precio1, cantidadTotal, 0);
                    break;
                case 1:
                    id = Integer.parseInt(object.getString("idingreso"));
                    dni = Integer.parseInt(object.getString("idusuario"));
                    categoria = Integer.parseInt(object.getString("categoriausuario"));
                    cantidadTotal = Integer.parseInt(object.getString("cantidad"));
                    fecha = object.getString("fechaingreso");

                    piletaIngreso = new PiletaIngreso(dni, id, categoria, fecha, -1, cantidadTotal, 0);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return piletaIngreso;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dni);
        dest.writeInt(id);
        dest.writeInt(categoria);
        dest.writeString(fecha);
        dest.writeFloat(precio1);
        dest.writeInt(dniEmpleado);
        dest.writeInt(cantidadTotal);
    }
}
