package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;


public class Profesor extends Usuario implements Parcelable {

    
    public static final String TABLE_PROFESOR = "profesor";
    
    public static final String KEY_ID_PRO = "idProfesor";

    
    private int idProfesor;
    
    private String profesion;
    
    private String fechaIngreso;

    public Profesor(int idUsuario,  String nombre,  String apellido,
                     String fechaNac,  String pais,  String provincia,
                     String localidad,  String domicilio,  String barrio,
                     String telefono,  String sexo,  String mail,
                    int tipoUsuario,  String fechaRegistro,  String fechaModificacion,
                    int validez, int idProfesor,  String profesion,  String fechaIngreso) {
        super(idUsuario, nombre, apellido, fechaNac, pais, provincia, localidad, domicilio,
                barrio, telefono, sexo, mail, tipoUsuario, fechaRegistro, fechaModificacion, validez);
        this.idProfesor = idProfesor;
        this.profesion = profesion;
        this.fechaIngreso = fechaIngreso;
    }

    
    public Profesor() {
    }


    
    protected Profesor(Parcel in) {
        setIdUsuario(in.readInt());
        setTipoUsuario(in.readInt());
        setValidez(in.readInt());
        setNombre(in.readString());
        setApellido(in.readString());
        setPais(in.readString());
        setProvincia(in.readString());
        setLocalidad(in.readString());
        setDomicilio(in.readString());
        setBarrio(in.readString());
        setTelefono(in.readString());
        setSexo(in.readString());
        setMail(in.readString());
        setFechaNac(in.readString());
        setFechaRegistro(in.readString());
        setFechaModificacion(in.readString());

        profesion = in.readString();
        idProfesor = in.readInt();
        fechaIngreso = in.readString();
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

    public static Profesor mapper(JSONObject o, Usuario usuario) {
        Profesor profesor = new Profesor();
        try {
            if (o.get("datos") != null) {
                JSONObject object = o.getJSONObject("datos");
                String profesion = object.getString("profesion");
                String anioIng = object.getString("fechaingreso");
                profesor = new Profesor(usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(),
                        usuario.getFechaNac(), usuario.getPais(), usuario.getProvincia(), usuario.getLocalidad(),
                        usuario.getDomicilio(), usuario.getBarrio(), usuario.getTelefono(), usuario.getSexo(),
                        usuario.getMail(), usuario.getTipoUsuario(), usuario.getFechaRegistro(), usuario.getFechaModificacion(),
                        usuario.getValidez(), usuario.getIdUsuario(), profesion, anioIng);
            }
            return profesor;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return profesor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getIdUsuario());
        dest.writeInt(getTipoUsuario());
        dest.writeInt(getValidez());
        dest.writeString(getNombre());
        dest.writeString(getApellido());
        dest.writeString(getPais());
        dest.writeString(getProvincia());
        dest.writeString(getLocalidad());
        dest.writeString(getDomicilio());
        dest.writeString(getBarrio());
        dest.writeString(getTelefono());
        dest.writeString(getSexo());
        dest.writeString(getMail());
        dest.writeString(getFechaNac());
        dest.writeString(getFechaRegistro());
        dest.writeString(getFechaModificacion());
        dest.writeString(profesion);
        dest.writeInt(idProfesor);
        dest.writeString(fechaIngreso);
    }
}
