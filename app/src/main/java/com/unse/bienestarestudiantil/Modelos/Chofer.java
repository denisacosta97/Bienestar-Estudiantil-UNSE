package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class Chofer extends Usuario implements Parcelable {

    public static final int MEDIUM = 1;
    public static final int COMPLETE = 2;

    private String[] recorridos;
    private int[] cantidades;
    private ArrayList<TemporadaArea> temporadas;

    public Chofer(int idUsuario, @NonNull String nombre, @NonNull String apellido, int tipoUsuario,
                  String[] recorridos, int[] cantidades) {
        super(idUsuario, nombre, apellido, tipoUsuario);
        this.recorridos = recorridos;
        this.cantidades = cantidades;
    }

    public ArrayList<TemporadaArea> getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(ArrayList<TemporadaArea> temporadas) {
        this.temporadas = temporadas;
    }

    public Chofer() {
    }

    protected Chofer(Parcel in) {
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

        recorridos = in.createStringArray();
        cantidades = in.createIntArray();
        int size = in.readInt();
        temporadas = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TemporadaArea temporadaArea = in.readParcelable(TemporadaArea.class.getClassLoader());
            temporadas.add(temporadaArea);
        }
    }

    public static Chofer mapper(JSONObject o, int tipo) {
        Chofer chofer = new Chofer();
        String nombre, apellido, fechaIngreso, fechaRegistro, fechaNac;
        int dni, validez;
        try {
            switch (tipo) {
                case MEDIUM:
                    dni = Integer.parseInt(o.getString("idchofer"));
                    nombre = o.getString("nombre");
                    apellido = o.getString("apellido");
                    validez = Integer.parseInt(o.getString("validez"));
                    chofer = new Chofer(dni, nombre, apellido, 0, null, null);
                    chofer.setValidez(validez);
                    break;
                case COMPLETE:
                    JSONObject mensaje = o.getJSONObject("mensaje");
                    dni = Integer.parseInt(mensaje.getString("idchofer"));
                    fechaIngreso = mensaje.getString("fechaingreso");
                    fechaRegistro = mensaje.getString("fecharegistro");
                    validez = Integer.parseInt(mensaje.getString("validez"));
                    JSONObject datos = o.getJSONObject("datos");
                    nombre = datos.getString("nombre");
                    apellido = datos.getString("apellido");
                    fechaNac = datos.getString("fechanac");
                    JSONArray servicios = o.getJSONArray("servicios");
                    String[] recorridos = new String[servicios.length()];
                    int[] cantidades = new int[servicios.length()];
                    for (int i = 0; i < servicios.length(); i++) {
                        JSONObject serv = servicios.getJSONObject(i);
                        //Integer idRecorrido = Integer.parseInt(serv.getString("idrecorrido"));
                        int cantidad = Integer.parseInt(serv.getString("cantidadservicio"));
                        String descripcion = serv.getString("descripcion");
                        recorridos[i] = descripcion;
                        cantidades[i] = cantidad;
                    }
                    JSONArray temporadas = o.getJSONArray("temporadas");
                    ArrayList<TemporadaArea> temporadaAreas = new ArrayList<>();
                    for (int i = 0; i < temporadas.length(); i++) {
                        JSONObject temp = temporadas.getJSONObject(i);
                        int anioTemp = Integer.parseInt(temp.getString("anio"));
                        int validezTemp = Integer.parseInt(temp.getString("validez"));
                        String fechaRegistroTemp = temp.getString("fecharegistro");
                        TemporadaArea temporadaArea = new TemporadaArea(dni, anioTemp, validezTemp, fechaRegistroTemp);
                        temporadaAreas.add(temporadaArea);
                    }
                    chofer = new Chofer(dni, nombre, apellido, 0, recorridos, cantidades);
                    chofer.setFechaNac(fechaNac);
                    chofer.setFechaRegistro(fechaIngreso);
                    chofer.setFechaModificacion(fechaRegistro);
                    chofer.setTemporadas(temporadaAreas);
                    chofer.setValidez(validez);
                    break;
            }
            return chofer;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chofer;
    }

    public String[] getRecorridos() {
        return recorridos;
    }

    public void setRecorridos(String[] recorridos) {
        this.recorridos = recorridos;
    }

    public int[] getCantidades() {
        return cantidades;
    }

    public void setCantidades(int[] cantidades) {
        this.cantidades = cantidades;
    }

    public static final Creator<Chofer> CREATOR = new Creator<Chofer>() {
        @Override
        public Chofer createFromParcel(Parcel in) {
            return new Chofer(in);
        }

        @Override
        public Chofer[] newArray(int size) {
            return new Chofer[size];
        }
    };

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

        dest.writeStringArray(recorridos);
        dest.writeIntArray(cantidades);
        dest.writeInt(temporadas.size());
        for (TemporadaArea temporadaArea : temporadas) {
            dest.writeParcelable(temporadaArea, flags);
        }
    }
}
