package com.unse.bienestarestudiantil.Modelos;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ReservaEspecial extends Reserva implements Parcelable {

    int porcion, cantidad;
    String nombre;
    String descripcionEspecial;


    public ReservaEspecial(int idReserva, int idMenu, int validez, String fechaReserva, String nombre,
                           String apellido, String descripcion, String tipoEntrega, int porcion,
                           String descripcionEspecial, int cantidad) {
        super(idReserva, idMenu, validez, fechaReserva, nombre, apellido, descripcion, tipoEntrega);
        this.porcion = porcion;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.descripcionEspecial = descripcionEspecial;
    }

    protected ReservaEspecial(Parcel in) {
        setIdReserva(in.readInt());
        setIdUsuario(in.readInt());
        setIdEmpleado(in.readInt());
        setIdMenu(in.readInt());
        setEstado(in.readInt());
        setFechaReserva(in.readString());
        setFechaModificacion(in.readString());
        setValidez(in.readInt());
        nombre = in.readString();
        setApellido(in.readString());
        setDescripcion(in.readString());
        descripcionEspecial = in.readString();
        setTipoEntrega(in.readString());
        setDia(in.readInt());
        setMes(in.readInt());
        setAnio(in.readInt());
        setMenu(in.readString());
        porcion = in.readInt();
        cantidad = in.readInt();

    }

    public static final Creator<ReservaEspecial> CREATOR = new Creator<ReservaEspecial>() {
        @Override
        public ReservaEspecial createFromParcel(Parcel in) {
            return new ReservaEspecial(in);
        }

        @Override
        public ReservaEspecial[] newArray(int size) {
            return new ReservaEspecial[size];
        }
    };

    @Override
    
    public int describeContents() {
        return 0;
    }

    @Override
    
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getIdReserva());
        dest.writeInt(getIdUsuario());
        dest.writeInt(getIdEmpleado());
        dest.writeInt(getIdMenu());
        dest.writeInt(getEstado());
        dest.writeString(getFechaReserva());
        dest.writeString(getFechaModificacion());
        dest.writeInt(getValidez());
        dest.writeString(nombre);
        dest.writeString(getApellido());
        dest.writeString(getDescripcion());
        dest.writeString(getDescripcionEspecial());
        dest.writeString(getTipoEntrega());
        dest.writeInt(getDia());
        dest.writeInt(getMes());
        dest.writeInt(getAnio());
        dest.writeString(getMenu());
        dest.writeInt(porcion);
        dest.writeInt(cantidad);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int getPorcion() {
        return porcion;
    }

    @Override
    public void setPorcion(int porcion) {
        this.porcion = porcion;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionEspecial() {
        return descripcionEspecial;
    }

    public void setDescripcionEspecial(String descripcionEspecial) {
        this.descripcionEspecial = descripcionEspecial;
    }

    public static ReservaEspecial mapper(JSONObject object, int tipo) {
        int idReserva, idUsuario, cantidad, validez, porcion;
        String fechaReserva, fechaModificacion, nombre, estadoDescripcion, estado;
        ReservaEspecial reserva = null;
        try {
            switch (tipo) {
                case HISTORIAL:
                    idReserva = Integer.parseInt(object.getString("idreservaespecial"));
                    int idMenu = Integer.parseInt(object.getString("idmenu"));
                    porcion = Integer.parseInt(object.getString("porcion"));
                    cantidad = object.getInt("cantidad");
                    fechaReserva = object.getString("fechareserva");
                    estadoDescripcion = object.getString("estado");
                    String descripcion = object.getString("descripcion");
                    reserva = new ReservaEspecial(idReserva, idMenu, 0, fechaReserva,
                            "", "", descripcion,
                            "", porcion, estadoDescripcion, cantidad);
                    break;
                case MEDIUM:
                    idReserva = Integer.parseInt(object.getString("idreservaespecial"));
                    porcion = Integer.parseInt(object.getString("porcion"));
                    cantidad = object.getInt("cantidad");
                    estado = object.getString("estadodescripcion");
                    fechaReserva = object.getString("fechareserva");
                    estadoDescripcion = object.getString("descripcion");
                    reserva = new ReservaEspecial(idReserva, 0, 0, fechaReserva,
                            "", "", estado,
                            "", porcion, estadoDescripcion, cantidad);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reserva;

    }
}

