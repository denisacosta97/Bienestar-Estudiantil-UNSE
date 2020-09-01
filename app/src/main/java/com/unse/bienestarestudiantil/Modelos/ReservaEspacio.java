package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReservaEspacio implements Parcelable {

    private int idReserva, idEspacio, temporada, idEmpleado, idUsuario, tipoU, estado, validez;
    private String nombre, fechaEv, horaI, horaF, nombreEspacio, apellido, descTipo, fechaRes, desc,
            precio, descRes, categ;
    private ArrayList<CuotaEspacio> cuota;

    public ReservaEspacio(int idReserva, int idEspacio, String nombre, String fechaEv, String horaI, String horaF) {
        this.idReserva = idReserva;
        this.idEspacio = idEspacio;
        this.nombre = nombre;
        this.fechaEv = fechaEv;
        this.horaI = horaI;
        this.horaF = horaF;
    }

    public ReservaEspacio(int idReserva, int idEspacio, int temporada, int idEmpleado, int idUsuario,
                          int tipoU, int estado, int validez, String nombre, String fechaEv,
                          String horaI, String horaF, String nombreEspacio, String apellido,
                          String descTipo, String fechaRes, String desc, String precio, String descRes) {
        this.idReserva = idReserva;
        this.idEspacio = idEspacio;
        this.temporada = temporada;
        this.idEmpleado = idEmpleado;
        this.idUsuario = idUsuario;
        this.tipoU = tipoU;
        this.estado = estado;
        this.validez = validez;
        this.nombre = nombre;
        this.fechaEv = fechaEv;
        this.horaI = horaI;
        this.horaF = horaF;
        this.nombreEspacio = nombreEspacio;
        this.apellido = apellido;
        this.descTipo = descTipo;
        this.fechaRes = fechaRes;
        this.desc = desc;
        this.precio = precio;
        this.descRes = descRes;
        cuota = new ArrayList<>();
    }

    public ReservaEspacio(int idReserva, int idUsuario, String descR, String nombre, String fechaEv,
                          String horaI, String horaF, String nombreEspacio, String apellido,
                          String desc, String categ) {
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.descRes = descR;
        this.nombre = nombre;
        this.fechaEv = fechaEv;
        this.horaI = horaI;
        this.horaF = horaF;
        this.nombreEspacio = nombreEspacio;
        this.apellido = apellido;
        this.desc = desc;
        this.categ = categ;
    }

    public ReservaEspacio() {
        this.idReserva = -1;
        this.idEspacio = -1;
        this.nombre = "";
        this.fechaEv = "";
        this.horaI = "";
        this.horaF = "";
    }

    protected ReservaEspacio(Parcel in) {
        idReserva = in.readInt();
        idEspacio = in.readInt();
        temporada = in.readInt();
        idEmpleado = in.readInt();
        idUsuario = in.readInt();
        tipoU = in.readInt();
        estado = in.readInt();
        validez = in.readInt();
        nombre = in.readString();
        fechaEv = in.readString();
        horaI = in.readString();
        horaF = in.readString();
        nombreEspacio = in.readString();
        apellido = in.readString();
        descTipo = in.readString();
        fechaRes = in.readString();
        desc = in.readString();
        precio = in.readString();
        descRes = in.readString();
        categ = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idReserva);
        dest.writeInt(idEspacio);
        dest.writeInt(temporada);
        dest.writeInt(idEmpleado);
        dest.writeInt(idUsuario);
        dest.writeInt(tipoU);
        dest.writeInt(estado);
        dest.writeInt(validez);
        dest.writeString(nombre);
        dest.writeString(fechaEv);
        dest.writeString(horaI);
        dest.writeString(horaF);
        dest.writeString(nombreEspacio);
        dest.writeString(apellido);
        dest.writeString(descTipo);
        dest.writeString(fechaRes);
        dest.writeString(desc);
        dest.writeString(precio);
        dest.writeString(descRes);
        dest.writeString(categ);
    }

    public static final Creator<ReservaEspacio> CREATOR = new Creator<ReservaEspacio>() {
        @Override
        public ReservaEspacio createFromParcel(Parcel in) {
            return new ReservaEspacio(in);
        }

        @Override
        public ReservaEspacio[] newArray(int size) {
            return new ReservaEspacio[size];
        }
    };

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(int idEspacio) {
        this.idEspacio = idEspacio;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getTipoU() {
        return tipoU;
    }

    public void setTipoU(int tipoU) {
        this.tipoU = tipoU;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaEv() {
        return fechaEv;
    }

    public void setFechaEv(String fechaEv) {
        this.fechaEv = fechaEv;
    }

    public String getHoraI() {
        return horaI;
    }

    public void setHoraI(String horaI) {
        this.horaI = horaI;
    }

    public String getHoraF() {
        return horaF;
    }

    public void setHoraF(String horaF) {
        this.horaF = horaF;
    }

    public String getNombreEspacio() {
        return nombreEspacio;
    }

    public void setNombreEspacio(String nombreEspacio) {
        this.nombreEspacio = nombreEspacio;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDescTipo() {
        return descTipo;
    }

    public void setDescTipo(String descTipo) {
        this.descTipo = descTipo;
    }

    public String getFechaRes() {
        return fechaRes;
    }

    public void setFechaRes(String fechaRes) {
        this.fechaRes = fechaRes;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescRes() {
        return descRes;
    }

    public void setDescRes(String descRes) {
        this.descRes = descRes;
    }

    public String getCateg() {
        return categ;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }

    public ArrayList<CuotaEspacio> getCuota() {
        return cuota;
    }

    public void setCuota (ArrayList<CuotaEspacio> cuota) {
        this.cuota = cuota;
    }

    public static ReservaEspacio mapper(JSONObject object, int mode) {
        ReservaEspacio reserva = new ReservaEspacio();

        int idReserva, idEspacio, temporada, idEmpleado, idUsuario, tipoU, estado, validez;
        String nombre, fechaEv, horaI, horaF, nombreEspacio, apellido, descTipo, fechaRes, desc,
                precio, descRes, categ;

        try {
            switch (mode){
                case 0:
                    idReserva = Integer.parseInt(object.getString("idreserva"));
                    idEspacio = Integer.parseInt(object.getString("idespacio"));
                    nombre = object.getString("nombre");
                    fechaEv = object.getString("fechaevento");
                    horaI = object.getString("horarioinicio");
                    horaF = object.getString("horariofin");

                    reserva = new ReservaEspacio(idReserva, idEspacio, nombre, fechaEv, horaI, horaF);
                    break;
                case 1:
                    idReserva = Integer.parseInt(object.getString("idreserva"));
                    idEspacio = Integer.parseInt(object.getString("idespacio"));
                    nombreEspacio = object.getString("nombreespacio");
                    temporada = Integer.parseInt(object.getString("temporada"));
                    idEmpleado = Integer.parseInt(object.getString("idempleado"));
                    idUsuario = Integer.parseInt(object.getString("idusuario"));
                    nombre = object.getString("nombre");
                    apellido = object.getString("apellido");
                    tipoU = Integer.parseInt(object.getString("tipousuario"));
                    descTipo = object.getString("descripciontipo");
                    fechaRes = object.getString("fechareserva");
                    fechaEv = object.getString("fechaevento");
                    horaI = object.getString("horarioinicio");
                    horaF = object.getString("horariofin");
                    desc = object.getString("descripcion");
                    precio = object.getString("precio");
                    estado = Integer.parseInt(object.getString("estadoreserva"));
                    descRes = object.getString("descripcionreserva");
                    validez = Integer.parseInt(object.getString("validez"));

                    reserva = new ReservaEspacio(idReserva, idEspacio, temporada, idEmpleado,
                            idUsuario, tipoU, estado, validez, nombre, fechaEv, horaI, horaF,
                            nombreEspacio, apellido, descTipo, fechaRes, desc, precio, descRes);

                    break;
                case 2:
                    idReserva = Integer.parseInt(object.getString("idreserva"));
                    nombreEspacio = object.getString("nombreespacio");
                    idUsuario = Integer.parseInt(object.getString("idusuario"));
                    nombre = object.getString("nombre");
                    apellido = object.getString("apellido");
                    categ = object.getString("categoria");
                    fechaEv = object.getString("fechaevento");
                    horaI = object.getString("horarioinicio");
                    horaF = object.getString("horariofin");
                    desc = object.getString("descripcion");
                    descRes = object.getString("estadoreserva");

                    reserva = new ReservaEspacio(idReserva, idUsuario, descRes, nombre, fechaEv, horaI, horaF,
                            nombreEspacio, apellido, desc, categ);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reserva;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
