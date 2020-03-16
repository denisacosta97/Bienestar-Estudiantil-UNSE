package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Profesor extends Usuario implements Parcelable {

    public static final String TAG = Usuario.class.getSimpleName();
    public static final String TABLE = "profesor";
    // Labels Table Columns names
    public static final String KEY_ID_PRO = "idProfesor";
    public static final String KEY_PRFN = "profesion";
    public static final String KEY_FECHA_ING = "fechaIngreso";
    public static final String KEY_CHK_DATA = "checkData";

    private String profesion, checkData;
    private int idProfesor;
    private String fechaIngreso;

    public Profesor(Usuario usuario, String profesion, String checkData1, int idProfesor, String fechaIngreso) {
        super(usuario.getIdUsuario(), usuario.getTipoUsuario(), usuario.getNombre(), usuario.getApellido(), usuario.getPais(),
                usuario.getProvincia(), usuario.getLocalidad(), usuario.getDomicilio(), usuario.getBarrio(), usuario.getTelefono(),
                usuario.getSexo(), usuario.getMail(), usuario.getCheckData(),
                usuario.getFoto(), usuario.getFechaNac(), usuario.getFechaRegistro());
        this.profesion = profesion;
        this.checkData = checkData1;
        this.idProfesor = idProfesor;
        this.fechaIngreso = fechaIngreso;
    }

    public Profesor() {
        super(-1, -1, "", "", "", "",
                "", "", "", "", "", "", "", "", new Date(), new Date());
        this.profesion = "";
        this.checkData = "";
        this.idProfesor = -1;
        this.fechaIngreso = "";
    }

    protected Profesor(Parcel in) {
        setIdUsuario(in.readInt());
        setNombre(in.readString());
        setApellido(in.readString());
        profesion = in.readString();
        checkData = in.readString();
        idProfesor = in.readInt();
    }

    public static final Creator<Profesor> CREATOR = new Creator<Profesor>() {
        @Override
        public Profesor createFromParcel(Parcel in) {
            return new Profesor(in);
        }

        @Override
        public Profesor[] newArray(int size) {
            return new Profesor[size];
        }
    };

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getCheckData() {
        return checkData;
    }

    public void setCheckData(String checkData) {
        this.checkData = checkData;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getIdUsuario());
        dest.writeString(getNombre());
        dest.writeString(getApellido());
        dest.writeString(profesion);
        dest.writeString(checkData);
        dest.writeInt(idProfesor);
    }
}
