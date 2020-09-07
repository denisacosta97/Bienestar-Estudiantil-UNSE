package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Deporte implements Parcelable {

    public static final int COMPLETE = 1;
    private int mIconDeporte, idDep, validez;
    private String mName, desc, lugar, dias, horario, lat, lon;
    private Profesor mProfesor;

    public Deporte(int iconDeporte, int idDep, String name, String desc, String lugar, String dias,
                   String horario, String lat, String lon, int validez, Profesor profesor) {
        mIconDeporte = iconDeporte;
        this.idDep = idDep;
        mName = name;
        this.desc = desc;
        this.lugar = lugar;
        this.dias = dias;
        this.horario = horario;
        this.lat = lat;
        this.lon = lon;
        this.validez = validez;
        mProfesor = profesor;
    }

    public Deporte() {
        mIconDeporte = -1;
        this.idDep = -1;
        mName = "";
        this.desc = "";
        this.lugar = "";
        this.dias = "";
        this.horario = "";
        this.lat = "";
        this.lon = "";
        mProfesor = new Profesor();
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    protected Deporte(Parcel in) {
        mIconDeporte = in.readInt();
        idDep = in.readInt();
        mName = in.readString();
        desc = in.readString();
        lugar = in.readString();
        dias = in.readString();
        horario = in.readString();
        lat = in.readString();
        lon = in.readString();
        validez = in.readInt();
        mProfesor = in.readParcelable(Profesor.class.getClassLoader());
    }

    public static final Creator<Deporte> CREATOR = new Creator<Deporte>() {
        @Override
        public Deporte createFromParcel(Parcel in) {
            return new Deporte(in);
        }

        @Override
        public Deporte[] newArray(int size) {
            return new Deporte[size];
        }
    };

    public void setIcon() {
        if (getName().contentEquals("Ajedrez")) {
            mIconDeporte = R.drawable.ic_ajedrez;
        } else if (getName().contentEquals("Basquet")) {
            mIconDeporte = R.drawable.ic_basquet;
        } else if (getName().contentEquals("Cestobal")) {
            mIconDeporte = R.drawable.ic_pelota;
        } else if (getName().contentEquals("Futbol")) {
            mIconDeporte = R.drawable.ic_futbol;
        } else if (getName().contentEquals("Hockey")) {
            mIconDeporte = R.drawable.ic_hockey;
        } else if (getName().contentEquals("Natacion")) {
            mIconDeporte = R.drawable.ic_natacion;
        } else if (getName().contentEquals("Rugby")) {
            mIconDeporte = R.drawable.ic_rugby;
        } else if (getName().contentEquals("Tenis")) {
            mIconDeporte = R.drawable.ic_tenis_mesa;
        } else if (getName().contentEquals("Voleibol")) {
            mIconDeporte = R.drawable.ic_voley;
        } else {
            mIconDeporte = R.drawable.ic_deportes;
        }
    }

    public int getIconDeporte() {
        return mIconDeporte;
    }

    public void setIconDeporte(int iconDeporte) {
        mIconDeporte = iconDeporte;
    }

    public int getIdDep() {
        return idDep;
    }

    public void setIdDep(int idDep) {
        this.idDep = idDep;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String latlong) {
        this.lat = latlong;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public Profesor getProfesor() {
        return mProfesor;
    }

    public void setProfesor(Profesor profesor) {
        mProfesor = profesor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIconDeporte);
        dest.writeInt(idDep);
        dest.writeString(mName);
        dest.writeString(desc);
        dest.writeString(lugar);
        dest.writeString(dias);
        dest.writeString(horario);
        dest.writeString(lat);
        dest.writeString(lon);
        dest.writeInt(validez);
        dest.writeParcelable(mProfesor, flags);
    }

    public static Deporte mapper(JSONObject object, int tipo) {
        Deporte deporte = new Deporte();
        try {
            String nombre, descripcion, diasEntreno, horario, lugar, lat, lon, fechaIngreso, nombreP, apellido;
            int valiz, idDeporte, idEntrenador;
            switch (tipo) {
                case COMPLETE:
                    idDeporte = Integer.parseInt(object.getString("iddeporte"));
                    nombre = object.getString("nombredeporte");
                    descripcion = object.getString("descripcion");
                    diasEntreno = object.getString("diaentreno");
                    horario = object.getString("horario");
                    lugar = object.getString("lugar");
                    lat = object.getString("lat");
                    lon = object.getString("lon");
                    //valiz = Integer.parseInt(object.getString("validez"));


                    idEntrenador = Integer.parseInt(object.isNull("identrenador") ? "0" :
                            object.getString("identrenador"));
                    fechaIngreso = object.isNull("fechaingreso") ? "" :
                            object.getString("fechaingreso");
                    nombreP = object.isNull("nombre") ? "" : object.getString("nombre");
                    apellido = object.isNull("apellido") ? "" : object.getString("apellido");

                    Usuario usuario = new Usuario(idEntrenador, nombreP, apellido, Utils.TIPO_PROFESOR);

                    Profesor profesor = new Profesor(usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(),
                            usuario.getFechaNac(), usuario.getPais(), usuario.getProvincia(), usuario.getLocalidad(),
                            usuario.getDomicilio(), usuario.getBarrio(), usuario.getTelefono(), usuario.getSexo(),
                            usuario.getMail(), usuario.getTipoUsuario(), usuario.getFechaRegistro(), usuario.getFechaModificacion(),
                            usuario.getValidez(), idEntrenador, "", fechaIngreso);
                    deporte = new Deporte(0, idDeporte, nombre, descripcion, lugar, diasEntreno,
                            horario, lat, lon, -1, profesor);
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return deporte;

    }
}
