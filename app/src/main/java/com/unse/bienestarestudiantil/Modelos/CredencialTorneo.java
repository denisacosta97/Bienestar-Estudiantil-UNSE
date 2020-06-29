package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class CredencialTorneo extends Credencial implements Parcelable {

    private int idTorneo, idDeporte, anio, tipoUsuario, idUsuario;
    private String nombreTorneo, lugar, fechaInicio, fechaFin, nombreDeporte, descripcion,
            nombreUsuario, apellido, fechaNac, sexo;

    public CredencialTorneo(int id, int idUsuario,String titulo, int validez, int idTorneo, int idDeporte,
                            int anio, int tipoUsuario, String nombreTorneo, String lugar,
                            String fechaInicio, String fechaFin, String nombreDeporte,
                            String descripcion, String nombreUsuario, String apellido,
                            String fechaNac, String sexo) {
        super(id, validez, 0, titulo, null);
        this.idTorneo = idTorneo;
        this.idUsuario = idUsuario;
        this.idDeporte = idDeporte;
        this.anio = anio;
        this.tipoUsuario = tipoUsuario;
        this.nombreTorneo = nombreTorneo;
        this.lugar = lugar;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nombreDeporte = nombreDeporte;
        this.descripcion = descripcion;
        this.nombreUsuario = nombreUsuario;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.sexo = sexo;
    }

    protected CredencialTorneo(Parcel in) {
        super(in.readInt(), in.readInt(), in.readInt() ,in.readString(), in.readString());
        idTorneo = in.readInt();
        idDeporte = in.readInt();
        anio = in.readInt();
        tipoUsuario = in.readInt();
        nombreTorneo = in.readString();
        lugar = in.readString();
        fechaInicio = in.readString();
        fechaFin = in.readString();
        nombreDeporte = in.readString();
        descripcion = in.readString();
        nombreUsuario = in.readString();
        apellido = in.readString();
        fechaNac = in.readString();
        sexo = in.readString();
        idUsuario = in.readInt();
    }

    public static final Creator<CredencialTorneo> CREATOR = new Creator<CredencialTorneo>() {
        @Override
        public CredencialTorneo createFromParcel(Parcel in) {
            return new CredencialTorneo(in);
        }

        @Override
        public CredencialTorneo[] newArray(int size) {
            return new CredencialTorneo[size];
        }
    };

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public int getIdDeporte() {
        return idDeporte;
    }

    public void setIdDeporte(int idDeporte) {
        this.idDeporte = idDeporte;
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

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
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

    public String getNombreDeporte() {
        return nombreDeporte;
    }

    public void setNombreDeporte(String nombreDeporte) {
        this.nombreDeporte = nombreDeporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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
        dest.writeInt(idTorneo);
        dest.writeInt(idDeporte);
        dest.writeInt(anio);
        dest.writeInt(tipoUsuario);
        dest.writeString(nombreTorneo);
        dest.writeString(lugar);
        dest.writeString(fechaInicio);
        dest.writeString(fechaFin);
        dest.writeString(nombreDeporte);
        dest.writeString(descripcion);
        dest.writeString(nombreUsuario);
        dest.writeString(apellido);
        dest.writeString(fechaNac);
        dest.writeString(sexo);
        dest.writeInt(idUsuario);
    }
}
