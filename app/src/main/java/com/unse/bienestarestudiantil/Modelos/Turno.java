package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.room.Ignore;

public class Turno implements Parcelable {

    @Ignore
    public static final int LOW = 0;
    @Ignore
    public static final int COMPLETE = 1;
    @Ignore
    public static final int BASIC = 2;
    @Ignore
    public static final int MEDIUM = 3;

    int idUsuario, idBeca, idReceptor, dia, mes, anio, estado, validez, dni, receptor;
    String horario, fechaRegistro, fechaModificacion, tipoBeca, nom, ape, nomBeca, descBeca, carrera, facultad, receptorString;

    public Turno(int idUsuario, int idBeca, int idReceptor, int dia, int mes, int anio, int estado,
                 int validez, int dni, String horario, String fechaRegistro,
                 String fechaModificacion, String tipoBeca, String nom, String ape) {
        this.idUsuario = idUsuario;
        this.idBeca = idBeca;
        this.idReceptor = idReceptor;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.estado = estado;
        this.validez = validez;
        this.dni = dni;
        this.horario = horario;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
        this.tipoBeca = tipoBeca;
        this.nom = nom;
        this.ape = ape;
    }

    public Turno(int idUsuario, String horario, String nom, String ape, String nomBeca,
                 String descBeca) {
        this.idUsuario = idUsuario;
        this.horario = horario;
        this.nom = nom;
        this.ape = ape;
        this.nomBeca = nomBeca;
        this.descBeca = descBeca;
    }

    public Turno(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    protected Turno(Parcel in) {
        idUsuario = in.readInt();
        idBeca = in.readInt();
        idReceptor = in.readInt();
        dia = in.readInt();
        mes = in.readInt();
        anio = in.readInt();
        estado = in.readInt();
        validez = in.readInt();
        dni = in.readInt();
        horario = in.readString();
        fechaRegistro = in.readString();
        fechaModificacion = in.readString();
        tipoBeca = in.readString();
        nom = in.readString();
        ape = in.readString();
        carrera = in.readString();
        facultad = in.readString();
        receptor = in.readInt();
        receptorString = in.readString();
    }

    public static final Creator<Turno> CREATOR = new Creator<Turno>() {
        @Override
        public Turno createFromParcel(Parcel in) {
            return new Turno(in);
        }

        @Override
        public Turno[] newArray(int size) {
            return new Turno[size];
        }
    };

    public int getReceptor() {
        return receptor;
    }

    public void setReceptor(int receptor) {
        this.receptor = receptor;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdBeca() {
        return idBeca;
    }

    public void setIdBeca(int idBeca) {
        this.idBeca = idBeca;
    }

    public int getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(int idReceptor) {
        this.idReceptor = idReceptor;
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

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getFechaRegistro() {
        return fechaRegistro != null ? fechaRegistro : String.format("%s-%s-%s", anio, mes, dia);
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getTipoBeca() {
        return tipoBeca;
    }

    public void setTipoBeca(String tipoBeca) {
        this.tipoBeca = tipoBeca;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApe() {
        return ape;
    }

    public void setApe(String ape) {
        this.ape = ape;
    }

    public String getNomBeca() {
        return nomBeca;
    }

    public void setNomBeca(String nomBeca) {
        this.nomBeca = nomBeca;
    }

    public String getDescBeca() {
        return descBeca;
    }

    public void setDescBeca(String descBeca) {
        this.descBeca = descBeca;
    }

    public static Turno mapper(JSONObject o, int tipo) {
        Turno turno = null;
        int idUsuario, idBeca, idReceptor, dia, mes, anio, estado, validez, dni, receptor;
        String horario, fechaRegistro, fechaModificacion, tipoBeca, nom, ape, nomBeca, descBeca, facultad, carrera;
        try {
            switch (tipo) {
                case MEDIUM:
                    idUsuario = Integer.parseInt(o.getString("idusuario"));
                    horario = o.getString("horario");
                    nom = o.getString("nombre");
                    ape = o.getString("apellido");
                    nomBeca = o.getString("nombrebeca");
                    descBeca = o.getString("descripcion");
                    estado = Integer.parseInt(o.getString("estado"));
                    carrera = o.has("carrera") ? o.getString("carrera") : "NO ASIGNADO";
                    facultad = o.has("facultad") ? o.getString("facultad") : "NO ASIGNADO";
                    receptor = Integer.parseInt(o.getString("receptor"));

                    turno = new Turno(idUsuario, horario, nom, ape, nomBeca, descBeca);
                    turno.setCarrera(carrera);
                    turno.setFacultad(facultad);
                    turno.setEstado(estado);
                    turno.setReceptor(receptor);
                    break;
                case LOW:
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));

                    turno = new Turno(dia, mes, anio);

                    break;
                case BASIC:
                    idUsuario = Integer.parseInt(o.getString("idusuario"));
                    horario = o.getString("horario");
                    nom = o.getString("nombre");
                    ape = o.getString("apellido");
                    nomBeca = o.getString("nombrebeca");
                    descBeca = o.getString("descripcion");
                    estado = Integer.parseInt(o.getString("estado"));
                    receptor = Integer.parseInt(o.getString("receptor"));
                    turno = new Turno(idUsuario, horario, nom, ape, nomBeca, descBeca);
                    turno.setEstado(estado);
                    turno.setReceptor(receptor);
                    turno.setReceptorString(String.format("Receptor %s", receptor));
                    break;
                case COMPLETE:
                    idUsuario = Integer.parseInt(o.getString("idusuario"));
                    idBeca = Integer.parseInt(o.getString("idbeca"));
                    idReceptor = Integer.parseInt(o.getString("idreceptor"));
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));
                    estado = Integer.parseInt(o.getString("estado"));
                    validez = Integer.parseInt(o.getString("validez"));
                    dni = Integer.parseInt(o.getString("dni"));
                    horario = o.getString("horario");
                    fechaRegistro = o.getString("fecharegistro");
                    fechaModificacion = o.getString("fechamodificacion");
                    tipoBeca = o.getString("tipobeca");
                    nom = o.getString("nombre");
                    ape = o.getString("apellido");

                    turno = new Turno(idUsuario, idBeca, idReceptor, dia, mes, anio, estado,
                            validez, dni, horario, fechaRegistro, fechaModificacion, tipoBeca, nom, ape);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return turno;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idUsuario);
        dest.writeInt(idBeca);
        dest.writeInt(idReceptor);
        dest.writeInt(dia);
        dest.writeInt(mes);
        dest.writeInt(anio);
        dest.writeInt(estado);
        dest.writeInt(validez);
        dest.writeInt(dni);
        dest.writeString(horario);
        dest.writeString(fechaRegistro);
        dest.writeString(fechaModificacion);
        dest.writeString(tipoBeca);
        dest.writeString(nom);
        dest.writeString(ape);
        dest.writeString(carrera);
        dest.writeString(facultad);
        dest.writeInt(receptor);
        dest.writeString(receptorString);
    }

    public String getReceptorString() {
        return receptorString;
    }

    public void setReceptorString(String receptorString) {
        this.receptorString = receptorString;
    }
}
