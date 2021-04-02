package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class AtencionDiaria implements Parcelable {

    public static final int BASIC = 1;
    public static final int FECHA = 2;

    private String nombre, apellido, facultad, consulta, fechaRegistro;
    int idUsuario, dia, mes, anio;

    public AtencionDiaria(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public AtencionDiaria(String nombre, String apellido, String facultad, String consulta, String fechaRegistro, int idUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.facultad = facultad;
        this.consulta = consulta;
        this.fechaRegistro = fechaRegistro;
        this.idUsuario = idUsuario;
    }

    protected AtencionDiaria(Parcel in) {

        nombre = in.readString();
        apellido = in.readString();
        facultad = in.readString();
        consulta = in.readString();
        fechaRegistro = in.readString();
        idUsuario = in.readInt();
        dia = in.readInt();
        mes = in.readInt();
        anio = in.readInt();
    }


    public static final Creator<AtencionDiaria> CREATOR = new Creator<AtencionDiaria>() {
        @Override
        public AtencionDiaria createFromParcel(Parcel in) {
            return new AtencionDiaria(in);
        }

        @Override
        public AtencionDiaria[] newArray(int size) {
            return new AtencionDiaria[size];
        }
    };

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

    public String getNombre() {
        return nombre != null && !nombre.equals("") ? nombre : "NO ";
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido != null && !apellido.equals("") ? apellido : "REGISTRADO ";
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFacultad() {

        return facultad != null && !facultad.equals("") ? facultad : "NO REGISTRADO";
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public static AtencionDiaria mapper(JSONObject object, int tipo) {
        AtencionDiaria atencionDiaria = null;
        String nombre, apellido, facultad, consulta, fechaRegistro;
        int idUsuario, dia, mes, anio;
        try {
            switch (tipo) {
                case BASIC:

                    nombre = !object.isNull("nombre") ? object.getString("nombre") : "";
                    apellido = object.isNull("apellido") ? "" : object.getString("apellido");
                    facultad = !object.isNull("facultad") ? object.getString("facultad") : "";
                    consulta = object.getString("motivo");
                    fechaRegistro = object.getString("fechaconsulta");
                    idUsuario = Integer.parseInt(object.getString("idusuario"));
                    atencionDiaria = new AtencionDiaria(nombre, apellido, facultad, consulta, fechaRegistro, idUsuario);

                    break;
                case FECHA:
                    dia = Integer.parseInt(object.getString("dia"));
                    mes = Integer.parseInt(object.getString("mes"));
                    anio = Integer.parseInt(object.getString("anio"));
                    atencionDiaria = new AtencionDiaria(dia, mes, anio);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return atencionDiaria;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(facultad);
        dest.writeString(consulta);
        dest.writeString(fechaRegistro);
        dest.writeInt(idUsuario);
        dest.writeInt(dia);
        dest.writeInt(mes);
        dest.writeInt(anio);
    }
}
