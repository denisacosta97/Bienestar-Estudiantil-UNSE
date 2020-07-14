package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Asistencia implements Parcelable {

    private int id, idAlumno, idDeporte;
    private String asistencia, fechaFalta;

    public Asistencia(int id, int idAlumno, int idDeporte, String asistencia, String fechaFalta) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.idDeporte = idDeporte;
        this.asistencia = asistencia;
        this.fechaFalta = fechaFalta;
    }

    public Asistencia() {
        this.id = -1;
        this.idAlumno = -1;
        this.idDeporte = -1;
        this.asistencia = "";
        this.fechaFalta = "";
    }

    protected Asistencia(Parcel in) {
        id = in.readInt();
        idAlumno = in.readInt();
        idDeporte = in.readInt();
        asistencia = in.readString();
        fechaFalta = in.readString();
    }

    public static final Creator<Asistencia> CREATOR = new Creator<Asistencia>() {
        @Override
        public Asistencia createFromParcel(Parcel in) {
            return new Asistencia(in);
        }

        @Override
        public Asistencia[] newArray(int size) {
            return new Asistencia[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdDeporte() {
        return idDeporte;
    }

    public void setIdDeporte(int idDeporte) {
        this.idDeporte = idDeporte;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }

    public String getFechaFalta() {
        return fechaFalta;
    }

    public void setFechaFalta(String fechaFalta) {
        this.fechaFalta = fechaFalta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(idAlumno);
        dest.writeInt(idDeporte);
        dest.writeString(asistencia);
        dest.writeString(fechaFalta);
    }
}
