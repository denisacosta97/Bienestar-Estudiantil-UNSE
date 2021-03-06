package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Pasajero implements Parcelable {

    public static final int MEDIUM = 1;
    public static final int COMPLETE = 2;
    private int dni, idRecorrido;
    private String nombre, apellido, dia, mes, anio, fechaRegistro, fechaLocal, patente;
    private Double lat, lon;

    public Pasajero(int dni, int idRecorrido, String nombre, String apellido, String dia, String mes,
                    String anio, String fechaRegistro, String fechaLocal, Double lat, Double lon) {
        this.dni = dni;
        this.idRecorrido = idRecorrido;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.fechaRegistro = fechaRegistro;
        this.fechaLocal = fechaLocal;
        this.lat = lat;
        this.lon = lon;
    }

    public Pasajero() {
        this.dni = -1;
        this.idRecorrido = -1;
        this.nombre = "";
        this.apellido = "";
        this.dia = "";
        this.mes = "";
        this.anio = "";
        this.fechaRegistro = "";
        this.fechaLocal = "";
        this.lat = null;
        this.lon = null;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    protected Pasajero(Parcel in) {
        dni = in.readInt();
        idRecorrido = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        dia = in.readString();
        mes = in.readString();
        anio = in.readString();
        fechaRegistro = in.readString();
        fechaLocal = in.readString();
        patente = in.readString();
        if (in.readByte() == 0) {
            lat = null;
        } else {
            lat = in.readDouble();
        }
        if (in.readByte() == 0) {
            lon = null;
        } else {
            lon = in.readDouble();
        }
    }

    public static final Creator<Pasajero> CREATOR = new Creator<Pasajero>() {
        @Override
        public Pasajero createFromParcel(Parcel in) {
            return new Pasajero(in);
        }

        @Override
        public Pasajero[] newArray(int size) {
            return new Pasajero[size];
        }
    };

    public static Pasajero mapper(JSONObject o, int tipo) {
        Pasajero pasajero = new Pasajero();
        String fechaRegistro, fechaLocal, patente, nombre, apellido;
        int idRecorrido, dia, mes, anio, dni;
        try {
            switch (tipo) {
                case MEDIUM:
                    idRecorrido = Integer.parseInt(o.getString("idrecorrido"));
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));
                    fechaRegistro = o.getString("fecharegistro");
                    fechaLocal = o.getString("fechalocal");
                    patente = o.getString("patente");
                    pasajero = new Pasajero(0, idRecorrido, "", "", String.valueOf(dia),
                            String.valueOf(mes), String.valueOf(anio), fechaRegistro, fechaLocal,
                            0d, 0d);
                    pasajero.setPatente(patente);
                    break;
                case COMPLETE:
                    idRecorrido = Integer.parseInt(o.getString("idrecorrido"));
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));
                    dni = Integer.parseInt(o.getString("idusuario"));
                    fechaRegistro = o.getString("fecharegistro");
                    fechaLocal = o.getString("fechalocal");
                    nombre = o.getString("nombre");
                    apellido = o.getString("apellido");
                    patente = o.getString("patente");
                    pasajero = new Pasajero(0, idRecorrido, "", "", String.valueOf(dia),
                            String.valueOf(mes), String.valueOf(anio), fechaRegistro, fechaLocal,
                            0d, 0d);
                    pasajero.setPatente(patente);
                    pasajero.setNombre(nombre);
                    pasajero.setApellido(apellido);
                    pasajero.setDni(dni);
                    break;
            }
            return pasajero;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pasajero;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getIdRecorrido() {
        return idRecorrido;
    }

    public void setIdRecorrido(int idRecorrido) {
        this.idRecorrido = idRecorrido;
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

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaLocal() {
        return fechaLocal;
    }

    public void setFechaLocal(String fechaLocal) {
        this.fechaLocal = fechaLocal;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dni);
        dest.writeInt(idRecorrido);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(dia);
        dest.writeString(mes);
        dest.writeString(anio);
        dest.writeString(fechaRegistro);
        dest.writeString(fechaLocal);
        dest.writeString(patente);
        if (lat == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lat);
        }
        if (lon == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lon);
        }
    }
}
