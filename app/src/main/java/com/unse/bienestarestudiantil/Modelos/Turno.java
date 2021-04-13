package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Turno implements Parcelable {


    public static final int LOW = 0;
    public static final int COMPLETE = 1;
    public static final int BASIC = 2;
    public static final int MEDIUM = 3;
    public static final int LOW_2 = 4;
    public static final int UAPU = 5;
    public static final int UAPU_TURNOS = 6;
    public static final int TIPO_UPA = 2;
    public static final int TIPO_UPA_TURNOS = 3;

    int idUsuario, id, idBeca, idReceptor, dia, mes, anio, estado, validez, dni, receptor, tipo;
    String horario, fechaRegistro, fechaModificacion, tipoBeca, nom, ape, nomBeca, descBeca, carrera, facultad, receptorString;
    String fechaInicio, fechaFin, fecha, descripcion, titulo, estado2;

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
        id = in.readInt();
        idBeca = in.readInt();
        idReceptor = in.readInt();
        dia = in.readInt();
        mes = in.readInt();
        anio = in.readInt();
        estado = in.readInt();
        validez = in.readInt();
        dni = in.readInt();
        receptor = in.readInt();
        tipo = in.readInt();
        horario = in.readString();
        fechaRegistro = in.readString();
        fechaModificacion = in.readString();
        tipoBeca = in.readString();
        nom = in.readString();
        ape = in.readString();
        nomBeca = in.readString();
        descBeca = in.readString();
        carrera = in.readString();
        facultad = in.readString();
        receptorString = in.readString();
        fechaInicio = in.readString();
        fechaFin = in.readString();
        fecha = in.readString();
        descripcion = in.readString();
        titulo = in.readString();
        estado2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idUsuario);
        dest.writeInt(id);
        dest.writeInt(idBeca);
        dest.writeInt(idReceptor);
        dest.writeInt(dia);
        dest.writeInt(mes);
        dest.writeInt(anio);
        dest.writeInt(estado);
        dest.writeInt(validez);
        dest.writeInt(dni);
        dest.writeInt(receptor);
        dest.writeInt(tipo);
        dest.writeString(horario);
        dest.writeString(fechaRegistro);
        dest.writeString(fechaModificacion);
        dest.writeString(tipoBeca);
        dest.writeString(nom);
        dest.writeString(ape);
        dest.writeString(nomBeca);
        dest.writeString(descBeca);
        dest.writeString(carrera);
        dest.writeString(facultad);
        dest.writeString(receptorString);
        dest.writeString(fechaInicio);
        dest.writeString(fechaFin);
        dest.writeString(fecha);
        dest.writeString(descripcion);
        dest.writeString(titulo);
        dest.writeString(estado2);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getTitulo() {
        if (tipo == TIPO_UPA) {
            return String.format("Retiro Medicamentos: %s", getMedicamentos(Integer.parseInt(descripcion)));
        } else if (tipo == TIPO_UPA_TURNOS) {
            return descripcion;
        }
        return titulo;
    }

    public String getMedicamentos(int i) {
        return i == 0 ? "Clínica Médica" : "Salud Sexual y Reprod.";
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public Turno(int receptor, String fechaInicio) {
        this.receptor = receptor;
        this.fechaInicio = fechaInicio;
    }

    public String getEstado2() {
        return estado2;
    }

    public void setEstado2(String estado2) {
        this.estado2 = estado2;
    }

    public Turno(int id, String descripcion, String estado, String fechaRegistro) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado2 = estado;
        this.fechaRegistro = fechaRegistro;
    }


    public static Turno mapper(JSONObject o, int tipo) {
        Turno turno = null;
        int idUsuario, idBeca, idReceptor, dia, mes, anio, estado, validez, dni, receptor, id;
        String horario, descripcion, fechaRegistro, fechaModificacion, tipoBeca, nom, ape,
                titulo, nomBeca, descBeca, facultad, carrera;
        try {
            switch (tipo) {
                case UAPU:
                    id = Integer.parseInt(o.getString("idusuario"));
                    String estado2 = o.getString("descripcion");
                    descripcion = o.getString("tipomedicamento");
                    fechaRegistro = o.getString("fecharegistro");
                    turno = new Turno(id, descripcion, estado2, fechaRegistro);
                    break;
                case UAPU_TURNOS:
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));
                    String estado3 = o.getString("estado");
                    titulo = o.getString("titulo");
                    horario = o.getString("horario");
                    turno = new Turno(0, titulo, estado3, null);
                    turno.setDia(dia);
                    turno.setMes(mes);
                    turno.setAnio(anio);
                    turno.setFechaInicio(horario);
                    break;
                case LOW_2:
                    horario = o.getString("horario");
                    turno = new Turno(0, horario);
                    break;
                case MEDIUM:
                    idUsuario = Integer.parseInt(o.getString("idusuario"));
                    horario = o.getString("horario");
                    nom = o.getString("nombre");
                    ape = o.getString("apellido");
                    nomBeca = o.getString("nombrebeca");
                    descBeca = o.getString("descripcion");
                    estado = Integer.parseInt(o.getString("estado"));
                    carrera = o.has("carrera") && !o.isNull("carrera") ? o.getString("carrera") : "NO ASIGNADO";
                    facultad = o.has("facultad") && !o.isNull("facultad") ? o.getString("facultad") : "NO ASIGNADO";

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


    public String getReceptorString() {
        return receptorString;
    }

    public void setReceptorString(String receptorString) {
        this.receptorString = receptorString;
    }
}
