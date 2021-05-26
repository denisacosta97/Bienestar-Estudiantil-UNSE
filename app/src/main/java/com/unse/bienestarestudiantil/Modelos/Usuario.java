package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;


import org.json.JSONException;
import org.json.JSONObject;


public class Usuario implements Parcelable {


    public static final int COMPLETE = 1;

    public static final int BASIC = 2;

    public static final int MEDIUM = 3;

    public static final String TABLE_USER = "usuario";

    public static final String KEY_ID_USER = "idUsuario";

    private int idUsuario;

    private String nombre;

    private String apellido;

    private String fechaNac;

    private String pais;

    private String provincia;

    private String localidad;

    private String domicilio;

    private String barrio;

    private String telefono;

    private String sexo;

    private String mail;

    private int tipoUsuario;

    private String fechaRegistro;

    private String fechaModificacion;

    private int validez;


    public Usuario(int idUsuario, String nombre, String apellido,
                   String fechaNac, String pais, String provincia,
                   String localidad, String domicilio, String barrio,
                   String telefono, String mail,
                   String fechaRegistro, String fechaModificacion,
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
        this.mail = mail;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
        this.validez = validez;
    }


    public Usuario(int idUsuario, String nombre, String apellido,
                   String fechaNac, String pais, String provincia,
                   String localidad, String domicilio, String barrio,
                   String telefono, String sexo, String mail,
                   int tipoUsuario, String fechaRegistro, String fechaModificacion,
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


    public Usuario(int idUsuario, String nombre, String apellido, int tipoUsuario) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoUsuario = tipoUsuario;
    }


    public Usuario() {
    }


    protected Usuario(Parcel in) {
        idUsuario = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        fechaNac = in.readString();
        pais = in.readString();
        provincia = in.readString();
        localidad = in.readString();
        domicilio = in.readString();
        barrio = in.readString();
        telefono = in.readString();
        sexo = in.readString();
        mail = in.readString();
        tipoUsuario = in.readInt();
        fechaRegistro = in.readString();
        fechaModificacion = in.readString();
        validez = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idUsuario);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(fechaNac);
        dest.writeString(pais);
        dest.writeString(provincia);
        dest.writeString(localidad);
        dest.writeString(domicilio);
        dest.writeString(barrio);
        dest.writeString(telefono);
        dest.writeString(sexo);
        dest.writeString(mail);
        dest.writeInt(tipoUsuario);
        dest.writeString(fechaRegistro);
        dest.writeString(fechaModificacion);
        dest.writeInt(validez);
    }

    @Override
    public int describeContents() {
        return 0;
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

            switch (tipe) {
                case COMPLETE:
                    JSONObject datos = object.getJSONObject("mensaje");
                    tipoUsuario = datos.has("tipousuario") ?
                    Integer.parseInt(datos.getString("tipousuario")) : 1;
                    sexo = datos.has("sexo") ? datos.getString("sexo") : "";
                    idUsuario = Integer.parseInt(datos.getString("idusuario"));
                    nombre = datos.getString("nombre");
                    apellido = datos.getString("apellido");
                    pais = datos.getString("pais").equals("null") ? "" : datos.getString("pais");
                    provincia = datos.getString("provincia").equals("null") ? "" : datos.getString("provincia");
                    localidad = datos.getString("localidad").equals("null") ? "" : datos.getString("localidad");
                    domicilio = datos.getString("domicilio").equals("null") ? "" : datos.getString("domicilio");
                    barrio = datos.getString("barrio").equals("null") ? "" : datos.getString("barrio");
                    telefono = datos.getString("telefono").equals("null") ? "" : datos.getString("telefono");
                    ;
                    mail = datos.getString("mail").equals("null") ? "" : datos.getString("mail");
                    fechaRegistro = datos.getString("fecharegistro").equals("null") ? "" : datos.getString("fecharegistro");
                    fechaNac = datos.getString("fechanac").equals("null") ? "" : datos.getString("fechanac");
                    fechaModificacion = datos.getString("fechamodificacion").equals("null") ? "" : datos.getString("fechamodificacion");
                    validez = Integer.parseInt(datos.getString("validez"));
                    usuario = new Usuario(idUsuario, nombre, apellido, fechaNac, pais, provincia,
                            localidad, domicilio, barrio, telefono, mail, fechaRegistro,
                            fechaModificacion, validez);
                    usuario.setTipoUsuario(tipoUsuario);
                    usuario.setSexo(sexo);
                    break;
                case BASIC:
                    idUsuario = Integer.parseInt(object.getString("idusuario"));
                    tipoUsuario = object.has("tipousuario") ?
                            Integer.parseInt(object.getString("tipousuario"))
                            : 0;
                    nombre = object.getString("nombre");
                    apellido = object.getString("apellido");
                    validez = object.has("validez") ? object.getInt("validez") : 0;
                    String anio = object.has("anio") ? object.getString("anio") : "";
                    usuario = new Usuario(idUsuario, nombre, apellido, tipoUsuario);
                    usuario.setValidez(validez);
                    usuario.setDomicilio(anio);
                    break;
                case MEDIUM:
                    idUsuario = Integer.parseInt(object.getString("idusuario"));
                    tipoUsuario = Integer.parseInt(object.getString("tipousuario"));
                    telefono = object.getString("telefono");
                    nombre = object.getString("nombre");
                    apellido = object.getString("apellido");
                    fechaNac = object.getString("fechaNac");
                    pais = object.getString("pais");
                    provincia = object.getString("provincia");
                    localidad = object.getString("localidad");
                    domicilio = object.getString("domicilio");
                    barrio = object.getString("barrio");
                    mail = object.getString("mail");
                    usuario = new Usuario(idUsuario, nombre, apellido, fechaNac, pais, provincia, localidad, domicilio,
                            barrio, telefono, null, mail, tipoUsuario, null, null, -1);
                    break;
            }

            return usuario;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usuario;
    }


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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


    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }


    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }


    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }


    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }


    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }


    public String getFechaRegistro() {
        return fechaRegistro;
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

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

}
