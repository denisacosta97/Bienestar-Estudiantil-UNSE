package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class IngresoCiber implements Parcelable {
    public static final int COMPLETO = 1;
    public static final int MEDIUM = 2;
    public static final int LOW = 3;

    private int dni;
    private String hora, minuto, dia, mes, anio, nombre, apellido, fecha;

    public IngresoCiber(int dni, String hora, String minuto, String dia, String mes, String anio, String nombre, String apellido) {
        this.dni = dni;
        this.hora = hora;
        this.minuto = minuto;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public IngresoCiber(int dni, String nombre, String apellido, String fecha) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha = fecha;
    }

    public IngresoCiber(String dia, String mes, String anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public IngresoCiber() {
        this.dni = -1;
        this.hora = "";
        this.minuto = "";
        this.dia = "";
        this.mes = "";
        this.anio = "";
        this.nombre = "";
        this.apellido = "";
    }

    protected IngresoCiber(Parcel in) {
        dni = in.readInt();
        hora = in.readString();
        minuto = in.readString();
        dia = in.readString();
        mes = in.readString();
        anio = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        fecha = in.readString();
    }

    public static final Creator<IngresoCiber> CREATOR = new Creator<IngresoCiber>() {
        @Override
        public IngresoCiber createFromParcel(Parcel in) {
            return new IngresoCiber(in);
        }

        @Override
        public IngresoCiber[] newArray(int size) {
            return new IngresoCiber[size];
        }
    };

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public static IngresoCiber mapper(JSONObject object, int tipo) {
        IngresoCiber ingreso = new IngresoCiber();

        int dni;
        String hora, minuto, dia, mes, anio, nombre, apellido, fecha;

        try {
            switch (tipo){
                case LOW:
                    dia = (object.getString("dia"));
                    mes = (object.getString("mes"));
                    anio = (object.getString("anio"));

                    ingreso = new IngresoCiber(dia, mes, anio);
                    break;
                case MEDIUM:

                    dni = Integer.parseInt(object.getString("idusuario"));
                    fecha = object.getString("fecharegistro");
                    nombre = !object.isNull("nombre") ? object.getString("nombre") : " - ";
                    apellido = !object.isNull("apellido") ? object.getString("apellido") : " - ";

                    ingreso = new IngresoCiber(dni, nombre, apellido, fecha);
                    break;
                case COMPLETO:
                    dni = Integer.parseInt(object.getString("idusuario"));
                    hora = object.getString("hora");
                    minuto = object.getString("minuto");
                    dia = (object.getString("dia"));
                    mes = (object.getString("mes"));
                    anio = (object.getString("anio"));
                    nombre = object.getString("nombre");
                    apellido = object.getString("apellido");

                    ingreso = new IngresoCiber(dni, hora, minuto, dia, mes, anio, nombre, apellido);
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ingreso;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dni);
        dest.writeString(hora);
        dest.writeString(minuto);
        dest.writeString(dia);
        dest.writeString(mes);
        dest.writeString(anio);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(fecha);
    }
}
