package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import com.unse.bienestarestudiantil.Herramientas.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.spec.ECGenParameterSpec;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuario")
public class Usuario implements Parcelable {

    @Ignore
    public static final int COMPLETE = 1;
    @Ignore
    public static final String TABLE_USER = "usuario";
    @Ignore
    public static final String KEY_ID_USER = "idUsuario";

    @PrimaryKey
    @NonNull
    private int idUsuario;
    @NonNull
    private String nombre;
    @NonNull
    private String apellido;
    @NonNull
    private String fechaNac;
    @NonNull
    private String pais;
    @NonNull
    private String provincia;
    @NonNull
    private String localidad;
    @NonNull
    private String domicilio;
    @NonNull
    private String barrio;
    @NonNull
    private String telefono;
    @NonNull
    private String sexo;
    @NonNull
    private String mail;
    @NonNull
    private int tipoUsuario;
    @NonNull
    private String fechaRegistro;
    @NonNull
    private String fechaModificacion;
    @NonNull
    private int validez;

    public Usuario(int idUsuario, @NonNull String nombre, @NonNull String apellido,
                   @NonNull String fechaNac, @NonNull String pais, @NonNull String provincia,
                   @NonNull String localidad, @NonNull String domicilio, @NonNull String barrio,
                   @NonNull String telefono, @NonNull String sexo, @NonNull String mail,
                   int tipoUsuario, @NonNull String fechaRegistro, @NonNull String fechaModificacion,
                   int validez) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.pais = pais;
        this.provincia = provincia;
        this.localidad = localidad;
        this.domicilio = domicilio;
        this.barrio = barrio;
        this.telefono = telefono;
        this.sexo = sexo;
        this.mail = mail;
        this.tipoUsuario = tipoUsuario;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
        this.validez = validez;
    }

    @Ignore
    public Usuario(int idUsuario, @NonNull String nombre, @NonNull String apellido, int tipoUsuario) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoUsuario = tipoUsuario;
    }

    @Ignore
    public Usuario() {
    }

    @Ignore
    protected Usuario(Parcel in) {
        idUsuario = in.readInt();
        tipoUsuario = in.readInt();
        validez = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        pais = in.readString();
        provincia = in.readString();
        localidad = in.readString();
        domicilio = in.readString();
        barrio = in.readString();
        telefono = in.readString();
        sexo = in.readString();
        mail = in.readString();
        fechaNac = in.readString();
        fechaRegistro = in.readString();
        fechaModificacion = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public static Usuario mapper(JSONObject object, int tipe) {
        Usuario usuario = new Usuario();
        String nombre, apellido, pais, provincia, localidad, domicilio,
                barrio, telefono, sexo, mail, fechaRegistro, fechaNac, fechaModificacion;
        int idUsuario, tipoUsuario, validez;

        try {
            JSONObject datos = object.getJSONObject("mensaje");
            switch (tipe) {
                case COMPLETE:
                    idUsuario = Integer.parseInt(datos.getString("idUsuario"));
                    tipoUsuario = Integer.parseInt(datos.getString("tipoUsuario"));
                    nombre = datos.getString("nombre");
                    apellido = datos.getString("apellido");
                    pais = datos.getString("pais");
                    provincia = datos.getString("provincia");
                    localidad = datos.getString("localidad");
                    domicilio = datos.getString("domicilio");
                    barrio = datos.getString("barrio");
                    telefono = datos.getString("telefono");
                    sexo = datos.getString("sexo");
                    mail = datos.getString("mail");
                    fechaRegistro = datos.getString("fechaRegistro");
                    fechaNac = datos.getString("fechaNac");
                    fechaModificacion = datos.getString("fechaModificacion");
                    validez = Integer.parseInt(datos.getString("validez"));

                    usuario = new Usuario(idUsuario, nombre, apellido,fechaNac, pais, provincia,
                            localidad, domicilio, barrio,telefono, sexo, mail, tipoUsuario, fechaRegistro,
                            fechaModificacion, validez);
                    break;
            }

            return usuario;
            /*Alumno alumno;
            Profesor profesor;
            Egresado egresado;
            if (object.get("datos") != null) {
                switch (usuario.getTipoUsuario()) {
                    case Utils.TIPO_ALUMNO:
                        alumno = Alumno.mapper(object.getJSONObject("datos"), usuario);
                        usuario = alumno;
                        break;
                    case Utils.TIPO_PROFESOR:
                        profesor = Profesor.mapper(object.getJSONObject("datos"), usuario);
                        usuario = profesor;
                        break;
                    case Utils.TIPO_EGRESADO:
                        egresado = Egresado.mapper(object.getJSONObject("datos"), usuario);
                        usuario = egresado;
                        break;
                }
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idUsuario);
        dest.writeInt(tipoUsuario);
        dest.writeInt(validez);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(pais);
        dest.writeString(provincia);
        dest.writeString(localidad);
        dest.writeString(domicilio);
        dest.writeString(barrio);
        dest.writeString(telefono);
        dest.writeString(sexo);
        dest.writeString(mail);
        dest.writeString(fechaNac);
        dest.writeString(fechaRegistro);
        dest.writeString(fechaModificacion);
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getApellido() {
        return apellido;
    }

    public void setApellido(@NonNull String apellido) {
        this.apellido = apellido;
    }

    @NonNull
    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(@NonNull String fechaNac) {
        this.fechaNac = fechaNac;
    }

    @NonNull
    public String getPais() {
        return pais;
    }

    public void setPais(@NonNull String pais) {
        this.pais = pais;
    }

    @NonNull
    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(@NonNull String provincia) {
        this.provincia = provincia;
    }

    @NonNull
    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(@NonNull String localidad) {
        this.localidad = localidad;
    }

    @NonNull
    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(@NonNull String domicilio) {
        this.domicilio = domicilio;
    }

    @NonNull
    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(@NonNull String barrio) {
        this.barrio = barrio;
    }

    @NonNull
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NonNull String telefono) {
        this.telefono = telefono;
    }

    @NonNull
    public String getSexo() {
        return sexo;
    }

    public void setSexo(@NonNull String sexo) {
        this.sexo = sexo;
    }

    @NonNull
    public String getMail() {
        return mail;
    }

    public void setMail(@NonNull String mail) {
        this.mail = mail;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @NonNull
    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(@NonNull String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @NonNull
    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(@NonNull String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }
}
