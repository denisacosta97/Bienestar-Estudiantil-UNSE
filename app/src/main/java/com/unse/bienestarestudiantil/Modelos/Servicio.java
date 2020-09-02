package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Servicio implements Parcelable {

    public static final int MEDIUM = 1;
    public static final int COMPLETE = 2;
    public static final int SERVICIO = 3;

    private int idServicio, dniChofer, idRecorrido, dia, mes, anio, estado;
    private String nombre, apellido, descripcio, patente, fechaInicio, fechaFin;

    public Servicio(int idServicio, int dniChofer, int idRecorrido, String nombre, String apellido,
                    String descripcio, String patente, int dia, int mes, int anio,
                    String fechaInicio, String fechaFin) {
        this.idServicio = idServicio;
        this.dniChofer = dniChofer;
        this.idRecorrido = idRecorrido;
        this.nombre = nombre;
        this.apellido = apellido;
        this.descripcio = descripcio;
        this.patente = patente;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Servicio() {
        this.idServicio = -1;
        this.dniChofer = -1;
        this.idRecorrido = -1;
        this.nombre = "";
        this.apellido = "";
        this.descripcio = "";
        this.patente = "";
        this.dia = -1;
        this.mes = -1;
        this.anio = -1;
    }

    protected Servicio(Parcel in) {
        idServicio = in.readInt();
        dniChofer = in.readInt();
        idRecorrido = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        descripcio = in.readString();
        patente = in.readString();
        dia = in.readInt();
        mes = in.readInt();
        anio = in.readInt();
        fechaInicio = in.readString();
        fechaFin = in.readString();
        estado = in.readInt();
    }

    public static final Creator<Servicio> CREATOR = new Creator<Servicio>() {
        @Override
        public Servicio createFromParcel(Parcel in) {
            return new Servicio(in);
        }

        @Override
        public Servicio[] newArray(int size) {
            return new Servicio[size];
        }
    };

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getDniChofer() {
        return dniChofer;
    }

    public void setDniChofer(int dniChofer) {
        this.dniChofer = dniChofer;
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

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static Servicio mapper(JSONObject o, int tipo) {
        Servicio servicio = new Servicio();
        String recorrido, fechaFin, fechaInicio, patente, nombre, apellido;
        int idServicio, idRecorrido, dia, mes, anio, idChofer;
        try {
            switch (tipo) {
                case MEDIUM:
                    idServicio = Integer.parseInt(o.getString("idservicio"));
                    idRecorrido = Integer.parseInt(o.getString("idrecorrido"));
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));
                    recorrido = o.getString("descripcion");
                    fechaInicio = o.getString("fechainicio");
                    fechaFin = o.getString("fechafin");
                    patente = o.getString("patente");
                    servicio = new Servicio(idServicio, 0, idRecorrido,
                            "", "", recorrido, patente,
                            dia, mes, anio, fechaInicio, fechaFin);
                    break;
                case COMPLETE:
                    idServicio = Integer.parseInt(o.getString("idservicio"));
                    idChofer = Integer.parseInt(o.getString("idchofer"));
                    idRecorrido = Integer.parseInt(o.getString("idrecorrido"));
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));
                    recorrido = o.getString("descripcion");
                    nombre = o.getString("nombre");
                    apellido = o.getString("apellido");
                    if (o.has("fechainicio")) fechaInicio = o.getString("fechainicio");
                    else fechaInicio = "null";
                    if (o.has("fechafin")) fechaFin = o.getString("fechafin");
                    else fechaFin = "null";
                    patente = o.getString("patente");
                    servicio = new Servicio(idServicio, idChofer, idRecorrido,
                            nombre, apellido, recorrido, patente,
                            dia, mes, anio, fechaInicio, fechaFin);
                    break;
                case SERVICIO:
                    idServicio = Integer.parseInt(o.getString("idservicio"));
                    idRecorrido = Integer.parseInt(o.getString("idrecorrido"));
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));
                    recorrido = o.getString("descripcion");
                    servicio = new Servicio(idServicio, 0, idRecorrido,
                            "", "", recorrido, "",
                            dia, mes, anio, "", "");
                    break;
            }
            return servicio;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return servicio;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idServicio);
        dest.writeInt(dniChofer);
        dest.writeInt(idRecorrido);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(descripcio);
        dest.writeString(patente);
        dest.writeInt(dia);
        dest.writeInt(mes);
        dest.writeInt(anio);
        dest.writeString(fechaInicio);
        dest.writeString(fechaFin);
        dest.writeInt(estado);
    }
}
