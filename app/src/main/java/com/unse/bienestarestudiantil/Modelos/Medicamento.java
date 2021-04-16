package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Medicamento implements Parcelable {

    public static final int COMPLETE = 1;
    public static final int LOW = 2;
    public static final int LOW2 = 3;

    private int idUsuario, estado, dia, mes, anio;
    private String tipoMedicamento, fechaRegistro, fechaHora, nombre, apellido, facultad, carrera, descripcion, fechaModificacion;

    public Medicamento(int idUsuario, int estado, String tipoMedicamento, String fechaRegistro,
                       String fechaHora, String nombre, String apellido, String facultad,
                       String carrera, String descripcion) {
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.tipoMedicamento = tipoMedicamento;
        this.fechaRegistro = fechaRegistro;
        this.fechaHora = fechaHora;
        this.nombre = nombre;
        this.apellido = apellido;
        this.facultad = facultad;
        this.carrera = carrera;
        this.descripcion = descripcion;
    }

    public Medicamento(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public String getMedicamentos(String i) {
        return i.equals("0") ? "Clínica Médica" : "Salud Sexual y Reprod.";
    }

    protected Medicamento(Parcel in) {
        idUsuario = in.readInt();
        estado = in.readInt();
        dia = in.readInt();
        mes = in.readInt();
        anio = in.readInt();
        tipoMedicamento = in.readString();
        fechaRegistro = in.readString();
        fechaHora = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        facultad = in.readString();
        carrera = in.readString();
        descripcion = in.readString();
        fechaModificacion = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idUsuario);
        dest.writeInt(estado);
        dest.writeInt(dia);
        dest.writeInt(mes);
        dest.writeInt(anio);
        dest.writeString(tipoMedicamento);
        dest.writeString(fechaRegistro);
        dest.writeString(fechaHora);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(facultad);
        dest.writeString(carrera);
        dest.writeString(descripcion);
        dest.writeString(fechaModificacion);
    }

    public static final Creator<Medicamento> CREATOR = new Creator<Medicamento>() {
        @Override
        public Medicamento createFromParcel(Parcel in) {
            return new Medicamento(in);
        }

        @Override
        public Medicamento[] newArray(int size) {
            return new Medicamento[size];
        }
    };

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getTipoMedicamento() {
        return tipoMedicamento;
    }

    public void setTipoMedicamento(String tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
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

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    public static Medicamento mapper(JSONObject o, int tipo) {
        Medicamento med = null;
        int idUsuario, estado, dia, mes, anio;
        String tipoMedicamento, fechaRegistro, fechaModificacion, fechaHora, nombre, apellido, facultad, carrera, descripcion;
        try {
            switch (tipo) {
                case COMPLETE:
                    idUsuario = Integer.parseInt(o.getString("idusuario"));
                    estado = Integer.parseInt(o.getString("estado"));
                    tipoMedicamento = o.getString("tipomedicamento");
                    fechaRegistro = o.getString("fecharegistro");
                    fechaModificacion = o.getString("fechamodificacion");
                    fechaHora = o.has("horario") ? o.getString("horario") : "";
                    nombre = o.getString("nombre");
                    apellido = o.getString("apellido");
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));
                    carrera = o.has("carrera") ? o.getString("carrera") : "NO ASIGNADO";
                    facultad = o.has("facultad") ? o.getString("facultad") : "NO ASIGNADO";
                    descripcion = o.getString("descripcion");

                    med = new Medicamento(idUsuario, estado, tipoMedicamento, fechaRegistro, fechaHora,
                            nombre, apellido, facultad, carrera, descripcion);
                    med.setDia(dia);
                    med.setMes(mes);
                    med.setAnio(anio);
                    med.setFechaModificacion(fechaModificacion);

                    break;
                case LOW2:
                    tipoMedicamento = o.getString("tipomedicamento");
                    fechaRegistro = o.getString("fecharegistro");
                    fechaHora = o.getString("fechamodificacion");
                    med = new Medicamento(0, 0, 0);
                    med.setTipoMedicamento(tipoMedicamento);
                    med.setFechaRegistro(fechaRegistro);
                    med.setFechaHora(fechaHora);
                    break;
                case LOW:
                    dia = Integer.parseInt(o.getString("dia"));
                    mes = Integer.parseInt(o.getString("mes"));
                    anio = Integer.parseInt(o.getString("anio"));

                    med = new Medicamento(dia, mes, anio);

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return med;
    }


}
