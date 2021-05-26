package com.unse.bienestarestudiantil.Modelos;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;


public class Menu implements Parcelable {


    public static final int COMPLETE = 1;

    public static final int SIMPLE = 2;

    public static final int ESTADISTICA = 3;

    public static final int REPORTE = 4;


    private int idMenu;
    private int dia, mes, anio, validez, disponible, porcion;
    private String fechaRegistro, fechaModificacion, descripcion;


    public Menu(int idMenu, int dia, int mes, int anio) {
        this.idMenu = idMenu;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public Menu(int idMenu, int dia, int mes, int anio, int validez, int disponible,
                String fechaRegistro, String fechaModificacion, String descripcion, int porcion) {
        this.idMenu = idMenu;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.validez = validez;
        this.disponible = disponible;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
        this.descripcion = descripcion;
        this.porcion = porcion;
    }

    public Menu() {
        this.idMenu = -1;
        this.dia = -1;
        this.mes = -1;
        this.anio = -1;
        this.validez = -1;
        this.disponible = -1;
        this.fechaRegistro = "";
        this.fechaModificacion = "";
        this.descripcion = "";
    }


    protected Menu(Parcel in) {
        idMenu = in.readInt();
        dia = in.readInt();
        mes = in.readInt();
        anio = in.readInt();
        validez = in.readInt();
        disponible = in.readInt();
        fechaRegistro = in.readString();
        fechaModificacion = in.readString();
        descripcion = in.readString();
        porcion = in.readInt();
    }


    public static Menu mapper(JSONObject datos, int tipo) {
        Menu menu = new Menu();
        int idMenu, dia, mes, anio, validez, disponible, porcion;
        String fechaRegistro, fechaModificacion, descripcion;
        try {
            switch (tipo) {
                case COMPLETE:
                    idMenu = Integer.parseInt(datos.getString("idmenu"));
                    dia = Integer.parseInt(datos.getString("dia"));
                    mes = Integer.parseInt(datos.getString("mes"));
                    anio = Integer.parseInt(datos.getString("anio"));
                    validez = Integer.parseInt(datos.getString("validez"));
                    disponible = Integer.parseInt(datos.getString("disponible"));
                    porcion = Integer.parseInt(datos.getString("porcion"));
                    descripcion = datos.getString("descripcion");
                    menu = new Menu(idMenu, dia, mes, anio, validez, disponible, "",
                            "", descripcion, porcion);
                    break;
                case SIMPLE:
                    idMenu = Integer.parseInt(datos.getString("idmenu"));
                    dia = Integer.parseInt(datos.getString("dia"));
                    mes = Integer.parseInt(datos.getString("mes"));
                    anio = Integer.parseInt(datos.getString("anio"));
                    menu = new Menu(idMenu, dia, mes, anio);
                    break;
                case ESTADISTICA:
                    idMenu = Integer.parseInt(datos.getString("idmenu"));
                    dia = Integer.parseInt(datos.getString("dia"));
                    mes = Integer.parseInt(datos.getString("mes"));
                    anio = Integer.parseInt(datos.getString("anio"));
                    porcion = Integer.parseInt(datos.getString("porcion"));
                    menu = new Menu(idMenu, dia, mes, anio);
                    menu.setPorcion(porcion);
                    break;
                case REPORTE:
                    idMenu = Integer.parseInt(datos.getString("idmenu"));
                    dia = Integer.parseInt(datos.getString("dia"));
                    mes = Integer.parseInt(datos.getString("mes"));
                    anio = Integer.parseInt(datos.getString("anio"));
                    porcion = Integer.parseInt(datos.getString("porcion"));
                    menu = new Menu(idMenu, dia, mes, anio);
                    menu.setPorcion(porcion);
                    menu.setDisponible(1);
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menu;
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
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

    public int getValidez() {
        return validez;
    }

    public void setValidez(int validez) {
        this.validez = validez;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
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

    public int getPorcion() {
        return porcion;
    }

    public void setPorcion(int porcion) {
        this.porcion = porcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String[] getComidas() {
        String[] food = new String[]{"NO INFO", "NO INFO", "NO INFO"};
        if (descripcion != null && descripcion.contains("$")) {

            String[] data = descripcion.split("\\$");
            food[0] = data[0];
            food[1] = data[1];
            if (data.length == 3)
                food[2] = data[2];
            /*int index = descripcion.indexOf("$");
            food[0] = descripcion.substring(0, index).trim();

            Pattern pattern = Pattern.compile("\\$[a-zA-Z  ]+");
            Matcher matcher = pattern.matcher(descripcion);
            int i = 1;
            while (matcher.find()) {
                food[i] = matcher.group().substring(1);
                i++;
            }*/

        }
        return food;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idMenu);
        dest.writeInt(dia);
        dest.writeInt(mes);
        dest.writeInt(anio);
        dest.writeInt(validez);
        dest.writeInt(disponible);
        dest.writeString(fechaRegistro);
        dest.writeString(fechaModificacion);
        dest.writeString(descripcion);
        dest.writeInt(porcion);
    }
}

