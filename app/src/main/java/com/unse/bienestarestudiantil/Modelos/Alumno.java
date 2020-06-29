package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "alumno")
public class Alumno extends Usuario implements Parcelable {

    @Ignore
    public static final String TABLE_ALUMNO = "alumno";
    @Ignore
    public static final String KEY_ID_ALU = "idAlumno";

    @NonNull
    private int idAlumno;
    @NonNull
    private String carrera;
    @NonNull
    private String facultad;
    @NonNull
    private String anio;
    @NonNull
    private String legajo;
    @NonNull
    private int idRegularidad;

    public Alumno(int idUsuario, @NonNull String nombre, @NonNull String apellido,
                  @NonNull String fechaNac, @NonNull String pais, @NonNull String provincia,
                  @NonNull String localidad, @NonNull String domicilio, @NonNull String barrio,
                  @NonNull String telefono, @NonNull String sexo, @NonNull String mail,
                  int tipoUsuario, @NonNull String fechaRegistro, @NonNull String fechaModificacion,
                  int validez, int idAlumno, @NonNull String carrera, @NonNull String facultad,
                  @NonNull String anio, @NonNull String legajo, int idRegularidad) {
        super(idUsuario, nombre, apellido, fechaNac, pais, provincia, localidad, domicilio,
                barrio, telefono, sexo, mail, tipoUsuario, fechaRegistro,
                fechaModificacion, validez);
        this.idAlumno = idAlumno;
        this.carrera = carrera;
        this.facultad = facultad;
        this.anio = anio;
        this.legajo = legajo;
        this.idRegularidad = idRegularidad;
    }

    @Ignore
    public Alumno() {

    }

    @Ignore
    protected Alumno(Parcel in) {
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

        carrera = in.readString();
        facultad = in.readString();
        legajo = in.readString();
        anio = in.readString();
        idAlumno = in.readInt();
        idRegularidad = in.readInt();
    }

    public static final Creator<Alumno> CREATOR = new Creator<Alumno>() {
        @Override
        public Alumno createFromParcel(Parcel in) {
            return new Alumno(in);
        }

        @Override
        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };

    public static Alumno mapper(JSONObject o, Usuario usuario) {
        Alumno alumno = new Alumno();
        try {
            if (o.get("datos") != null) {
                JSONObject object = o.getJSONObject("datos");
                String carrera = object.getString("carrera");
                String facultad = object.getString("facultad");
                String anio = object.getString("anio");
                String legajo = object.getString("legajo");
                int idRegularidad = Integer.parseInt(
                        object.getString("idregularidad"));

                alumno = new Alumno(usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(),
                        usuario.getFechaNac(), usuario.getPais(), usuario.getProvincia(), usuario.getLocalidad(),
                        usuario.getDomicilio(), usuario.getBarrio(), usuario.getTelefono(), usuario.getSexo(),
                        usuario.getMail(), usuario.getTipoUsuario(), usuario.getFechaRegistro(), usuario.getFechaModificacion(),
                        usuario.getValidez(), usuario.getIdUsuario(), carrera, facultad, anio, legajo, idRegularidad
                );
            }
            return alumno;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return alumno;
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

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdRegularidad() {
        return idRegularidad;
    }

    public void setIdRegularidad(int idRegularidad) {
        this.idRegularidad = idRegularidad;
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

        dest.writeString(carrera);
        dest.writeString(facultad);
        dest.writeString(legajo);
        dest.writeString(anio);
        dest.writeInt(idAlumno);
        dest.writeInt(idRegularidad);
    }
}
