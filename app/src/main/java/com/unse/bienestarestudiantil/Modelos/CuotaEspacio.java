package com.unse.bienestarestudiantil.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class CuotaEspacio implements Parcelable {

    private int idPago, idReserva, idPagoCuota, tipoPago;
    private String fechaPago, cant, desc, fechaPagoCuota;

    public CuotaEspacio(int idPago, int idReserva, int idPagoCuota, int tipoPago, String fechaPago,
                        String cant, String desc, String fechaPagoCuota) {
        this.idPago = idPago;
        this.idReserva = idReserva;
        this.idPagoCuota = idPagoCuota;
        this.tipoPago = tipoPago;
        this.fechaPago = fechaPago;
        this.cant = cant;
        this.desc = desc;
        this.fechaPagoCuota = fechaPagoCuota;
    }

    public CuotaEspacio() {
        this.idPago = -1;
        this.idReserva = -1;
        this.idPagoCuota = -1;
        this.tipoPago = -1;
        this.fechaPago = "";
        this.cant = "";
        this.desc = "";
        this.fechaPagoCuota = "";
    }

    protected CuotaEspacio(Parcel in) {
        idPago = in.readInt();
        idReserva = in.readInt();
        idPagoCuota = in.readInt();
        tipoPago = in.readInt();
        fechaPago = in.readString();
        cant = in.readString();
        desc = in.readString();
        fechaPagoCuota = in.readString();
    }

    public static final Creator<CuotaEspacio> CREATOR = new Creator<CuotaEspacio>() {
        @Override
        public CuotaEspacio createFromParcel(Parcel in) {
            return new CuotaEspacio(in);
        }

        @Override
        public CuotaEspacio[] newArray(int size) {
            return new CuotaEspacio[size];
        }
    };

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdPagoCuota() {
        return idPagoCuota;
    }

    public void setIdPagoCuota(int idPagoCuota) {
        this.idPagoCuota = idPagoCuota;
    }

    public int getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(int tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFechaPagoCuota() {
        return fechaPagoCuota;
    }

    public void setFechaPagoCuota(String fechaPagoCuota) {
        this.fechaPagoCuota = fechaPagoCuota;
    }

    public static CuotaEspacio mapper(JSONObject object) {
        CuotaEspacio cuota = new CuotaEspacio();

        int idPago = -1, idReserva = -1, idPagoCuota = -1, tipoPago = -1;
        String fechaPago = "", cant = "", desc = "", fechaPagoCuota = "";

        try {
            idPago = Integer.parseInt(object.getString("idpago"));
            idReserva = Integer.parseInt(object.getString("idreserva"));
            fechaPago = object.getString("fechapagofinal");
            if(object.has("idpagocuota") && !object.isNull("idpagocuota")){
                idPagoCuota = Integer.parseInt(object.getString("idpagocuota"));
            }
            if(object.has("cantidad") && !object.isNull("cantidad")){
                cant = object.getString("cantidad");
            }
            if(object.has("tipopago") && !object.isNull("tipopago")){
                tipoPago = Integer.parseInt(object.getString("tipopago"));
            }
            if(object.has("descripcion") && !object.isNull("descripcion")){
                desc = object.getString("descripcion");
            }
            if(object.has("fechapagocuota") && !object.isNull("fechapagocuota")){
                fechaPagoCuota = object.getString("fechapagocuota");
            }

            cuota = new CuotaEspacio(idPago, idReserva, idPagoCuota, tipoPago, fechaPago, cant,
                    desc, fechaPagoCuota);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cuota;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPago);
        dest.writeInt(idReserva);
        dest.writeInt(idPagoCuota);
        dest.writeInt(tipoPago);
        dest.writeString(fechaPago);
        dest.writeString(cant);
        dest.writeString(desc);
        dest.writeString(fechaPagoCuota);
    }
}
