package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;


import org.json.JSONException;
import org.json.JSONObject;

public class Reserva implements Parcelable {

    
    public static final int COMPLETE = 1;
    
    public static final int MEDIUM = 2;
    
    public static final int HISTORIAL = 3;
    
    public static final int HISTORIAL_TOTAL = 4;
    
    public static final int ESTADISTICA = 5;
    
    public static final int REPORTE_MENSUAL = 6;
    
    public static final int ESPECIALES = 7;

    private int mIcon, idReserva;
    private String mTitulo;
    private ReservaHorario mRangoHora;
    
    private int idUsuario, idEmpleado, idMenu, estado, validez, porcion;
    private String fechaReserva, fechaModificacion, nombre, apellido, menu;
    private String descripcion, tipoEntrega;
    private int dia, mes, anio;

    public Reserva(int idReserva, int idUsuario, int idEmpleado, int idMenu, int estado, int porcion,
                   String fechaReserva, String fechaModificacion, int validez, String descripcion, String menu) {
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.idEmpleado = idEmpleado;
        this.idMenu = idMenu;
        this.descripcion = descripcion;
        this.estado = estado;
        this.validez = validez;
        this.fechaReserva = fechaReserva;
        this.fechaModificacion = fechaModificacion;
        this.porcion = porcion;
        this.menu = menu;
    }

    
    public Reserva(int idReserva, int idUsuario, int idMenu, String estado, int validez, String nombre, String apellido) {
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.idMenu = idMenu;
        this.descripcion = estado;
        this.validez = validez;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Reserva(int idReserva, int icon, String titulo, ReservaHorario rangoHora) {
        this.idReserva = idReserva;
        mIcon = icon;
        mTitulo = titulo;
        mRangoHora = rangoHora;
    }

    public Reserva() {
        this.idReserva = 0;
        mIcon = 0;
        mTitulo = "";
        mRangoHora = null;
    }


    protected Reserva(Parcel in) {
        mIcon = in.readInt();
        idReserva = in.readInt();
        mTitulo = in.readString();
        idUsuario = in.readInt();
        idEmpleado = in.readInt();
        idMenu = in.readInt();
        estado = in.readInt();
        validez = in.readInt();
        porcion = in.readInt();
        fechaReserva = in.readString();
        fechaModificacion = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        menu = in.readString();
        descripcion = in.readString();
        tipoEntrega = in.readString();
        dia = in.readInt();
        mes = in.readInt();
        anio = in.readInt();
    }

    public static final Creator<Reserva> CREATOR = new Creator<Reserva>() {
        @Override
        public Reserva createFromParcel(Parcel in) {
            return new Reserva(in);
        }

        @Override
        public Reserva[] newArray(int size) {
            return new Reserva[size];
        }
    };

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getTitulo() {
        return mTitulo;
    }

    public void setTitulo(String titulo) {
        mTitulo = titulo;
    }

    public ReservaHorario getRangoHora() {
        return mRangoHora;
    }

    public void setRangoHora(ReservaHorario rangoHora) {
        mRangoHora = rangoHora;
    }

    public int getmIcon() {
        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public String getmTitulo() {
        return mTitulo;
    }

    public void setmTitulo(String mTitulo) {
        this.mTitulo = mTitulo;
    }

    public ReservaHorario getmRangoHora() {
        return mRangoHora;
    }

    public void setmRangoHora(ReservaHorario mRangoHora) {
        this.mRangoHora = mRangoHora;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public int getPorcion() {
        return porcion;
    }

    public void setPorcion(int porcion) {
        this.porcion = porcion;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIcon);
        dest.writeInt(idReserva);
        dest.writeString(mTitulo);
        dest.writeInt(idUsuario);
        dest.writeInt(idEmpleado);
        dest.writeInt(idMenu);
        dest.writeInt(estado);
        dest.writeInt(validez);
        dest.writeInt(porcion);
        dest.writeString(fechaReserva);
        dest.writeString(fechaModificacion);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(menu);
        dest.writeString(descripcion);
        dest.writeString(tipoEntrega);
        dest.writeInt(dia);
        dest.writeInt(mes);
        dest.writeInt(anio);
    }

    public static Reserva mapper(JSONObject object, int tipo) {
        int idReserva, idUsuario, idEmpleado, idMenu, estado, validez, dia, mes, anio, porcion;
        String fechaReserva, fechaModificacion, nombre, apellido, estadoDescripcion, tipoEntrega, menu;
        Reserva reserva = new Reserva();
        try {
            switch (tipo) {
                case COMPLETE:
                    idReserva = Integer.parseInt(object.getString("idreserva"));
                    idUsuario = Integer.parseInt(object.getString("idusuario"));
                    idMenu = Integer.parseInt(object.getString("idmenu"));
                    estado = Integer.parseInt(object.getString("estado"));
                    fechaReserva = object.getString("fechareserva");
                    fechaModificacion = object.getString("fechamodificacion");
                    validez = Integer.parseInt(object.getString("validez"));
                    reserva = new Reserva(idReserva, idUsuario, 0, idMenu, estado, 0, fechaReserva,
                            fechaModificacion, validez, "", "");
                    break;
                case MEDIUM:
                    idReserva = Integer.parseInt(object.getString("idreserva"));
                    idUsuario = Integer.parseInt(object.getString("idusuario"));
                    idMenu = Integer.parseInt(object.getString("idmenu"));
                    estadoDescripcion = object.getString("descripcion");
                    validez = Integer.parseInt(object.getString("validez"));
                    fechaReserva = object.getString("fechareserva");
                    nombre = object.getString("nombre");
                    apellido = object.getString("apellido");
                    reserva = new Reserva(idReserva, idUsuario, idMenu, estadoDescripcion, validez, nombre, apellido);
                    reserva.setFechaReserva(fechaReserva);
                    break;
                case HISTORIAL:
                    idReserva = Integer.parseInt(object.getString("idreserva"));
                    idUsuario = Integer.parseInt(object.getString("idusuario"));
                    idMenu = Integer.parseInt(object.getString("idmenu"));
                    porcion = Integer.parseInt(object.getString("porcion"));
                    dia = Integer.parseInt(object.getString("dia"));
                    mes = Integer.parseInt(object.getString("mes"));
                    anio = Integer.parseInt(object.getString("anio"));
                    fechaReserva = object.getString("fechareserva");
                    fechaModificacion = object.getString("fechamodificacion");
                    estadoDescripcion = object.getString("descripcion");
                    menu = object.getString("descripcionmenu");
                    tipoEntrega = object.getString("tiporeserva");
                    reserva = new Reserva(idReserva, idMenu, fechaReserva, estadoDescripcion, tipoEntrega, dia, mes, anio);
                    reserva.setFechaModificacion(fechaModificacion);
                    reserva.setPorcion(porcion);
                    reserva.setMenu(menu);
                    reserva.setIdUsuario(idUsuario);
                    break;
                case HISTORIAL_TOTAL:
                    idReserva = Integer.parseInt(object.getString("idreserva"));
                    idMenu = Integer.parseInt(object.getString("idmenu"));
                    estadoDescripcion = object.getString("descripcion");
                    tipoEntrega = object.getString("tiporeserva");
                    fechaReserva = object.getString("fechareserva");
                    validez = Integer.parseInt(object.getString("validez"));
                    nombre = object.getString("nombre");
                    apellido = object.getString("apellido");
                    reserva = new Reserva(idReserva, idMenu, validez, "", nombre, apellido, estadoDescripcion, tipoEntrega);
                    reserva.setFechaReserva(fechaReserva);
                    break;
                case ESTADISTICA:
                    idReserva = Integer.parseInt(object.getString("idreserva"));
                    idUsuario = Integer.parseInt(object.getString("idusuario"));
                    idMenu = Integer.parseInt(object.getString("idmenu"));
                    fechaReserva = object.getString("fechareserva");
                    fechaModificacion = object.getString("fechamodificacion");
                    estado = Integer.parseInt(object.getString("estado"));
                    reserva = new Reserva(idReserva, idUsuario, 0,
                            idMenu, estado, 0, fechaReserva, fechaModificacion, -1, "", "");
                    break;
                case REPORTE_MENSUAL:
                    idReserva = Integer.parseInt(object.getString("idreserva"));
                    idUsuario = Integer.parseInt(object.getString("idusuario"));
                    idMenu = Integer.parseInt(object.getString("idmenu"));
                    estadoDescripcion = object.getString("estado");
                    reserva = new Reserva(idReserva, idUsuario, 0,
                            idMenu, 0, 0, "",
                            "", -1, estadoDescripcion, "");
                    break;
                case ESPECIALES:
                    idReserva = Integer.parseInt(object.getString("idreservaespecial"));
                    idUsuario = Integer.parseInt(object.getString("idusuario"));
                    idMenu = Integer.parseInt(object.getString("idmenu"));
                    fechaReserva = object.getString("fechareserva");
                    fechaModificacion = object.getString("fechamodificacion");
                    estadoDescripcion = object.getString("descripcion");
                    porcion = Integer.parseInt(object.getString("porcion"));
                    estado = Integer.parseInt(object.getString("estado"));
                    reserva = new Reserva(idReserva, idUsuario, 0,
                            idMenu, estado, porcion, fechaReserva,
                            fechaModificacion, -1, estadoDescripcion, "");
                    reserva.setNombre(estadoDescripcion);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reserva;

    }

    public Reserva(int idReserva, int idMenu, int validez, String fechaReserva, String nombre, String apellido, String descripcion, String tipoEntrega) {
        this.idReserva = idReserva;
        this.idMenu = idMenu;
        this.validez = validez;
        this.fechaReserva = fechaReserva;
        this.nombre = nombre;
        this.apellido = apellido;
        this.descripcion = descripcion;
        this.tipoEntrega = tipoEntrega;
    }

    public Reserva(int idReserva, int idMenu, String fechaReserva, String descripcion,
                   String tipoEntrega, int dia, int mes, int anio) {
        this.idReserva = idReserva;
        this.idMenu = idMenu;
        this.fechaReserva = fechaReserva;
        this.descripcion = descripcion;
        this.tipoEntrega = tipoEntrega;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }
}
