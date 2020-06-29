package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class CredencialBeca extends Credencial implements Parcelable {

    private int idUsuario, idConvocatoria, tipoBeca, anio, tipoUsuario;
    private String nombreBeca, nombreU, apellidoU, fecha;
    private String legajo, carrera, facultad;

    public CredencialBeca(int id, String titulo, int validez, int idUsuario, int idConvocatoria,
                          int tipoBeca, int anio, int tipoUsuario, String nombreBeca, String nombreU,
                          String apellidoU, String fecha) {
        super(id, validez, 0, titulo, null);
        this.idUsuario = idUsuario;
        this.idConvocatoria = idConvocatoria;
        this.tipoBeca = tipoBeca;
        this.anio = anio;
        this.tipoUsuario = tipoUsuario;
        this.nombreBeca = nombreBeca;
        this.nombreU = nombreU;
        this.apellidoU = apellidoU;
        this.fecha = fecha;
    }

    protected CredencialBeca(Parcel in) {
        super(in.readInt(), in.readInt(), in.readInt() ,in.readString(), in.readString());
        idUsuario = in.readInt();
        idConvocatoria = in.readInt();
        tipoBeca = in.readInt();
        anio = in.readInt();
        tipoUsuario = in.readInt();
        nombreBeca = in.readString();
        nombreU = in.readString();
        apellidoU = in.readString();
        fecha = in.readString();
        carrera = in.readString();
        facultad = in.readString();
        legajo = in.readString();
    }

    public static final Creator<CredencialBeca> CREATOR = new Creator<CredencialBeca>() {
        @Override
        public CredencialBeca createFromParcel(Parcel in) {
            return new CredencialBeca(in);
        }

        @Override
        public CredencialBeca[] newArray(int size) {
            return new CredencialBeca[size];
        }
    };

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
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

    public int getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(int idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    public int getTipoBeca() {
        return tipoBeca;
    }

    public void setTipoBeca(int tipoBeca) {
        this.tipoBeca = tipoBeca;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombreBeca() {
        return nombreBeca;
    }

    public void setNombreBeca(String nombreBeca) {
        this.nombreBeca = nombreBeca;
    }

    public String getNombreU() {
        return nombreU;
    }

    public void setNombreU(String nombreU) {
        this.nombreU = nombreU;
    }

    public String getApellidoU() {
        return apellidoU;
    }

    public void setApellidoU(String apellidoU) {
        this.apellidoU = apellidoU;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeInt(getValidez());
        dest.writeInt(getAnio());
        dest.writeString(getTitulo());
        dest.writeString(getFecha());
        dest.writeInt(idUsuario);
        dest.writeInt(idConvocatoria);
        dest.writeInt(tipoBeca);
        dest.writeInt(anio);
        dest.writeInt(tipoUsuario);
        dest.writeString(nombreBeca);
        dest.writeString(nombreU);
        dest.writeString(apellidoU);
        dest.writeString(fecha);
        dest.writeString(carrera);
        dest.writeString(facultad);
        dest.writeString(legajo);
    }
}
