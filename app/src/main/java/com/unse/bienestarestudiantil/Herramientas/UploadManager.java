package com.unse.bienestarestudiantil.Herramientas;

import android.app.AlertDialog;
import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.unse.bienestarestudiantil.Vistas.Activities.Inicio.MainActivity;

import java.util.Map;

public class UploadManager {

    public static final int IMAGE = 1;

    private int metodo;
    private int tipoDato;
    private String url;
    private Response.Listener<NetworkResponse> okListener;
    private Response.ErrorListener errorListener;
    private VolleyMultipartRequest.DataPart datos;
    private VolleyMultipartRequest mRequest;
    private Context mContext;
    private Map<String, String> mMap;

    public UploadManager(){

    }

    public int getMetodo() {
        return metodo;
    }

    public void setMetodo(int metodo) {
        this.metodo = metodo;
    }

    public int getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(int tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Response.Listener<NetworkResponse> getOkListener() {
        return okListener;
    }

    public void setOkListener(Response.Listener<NetworkResponse> okListener) {
        this.okListener = okListener;
    }

    public Response.ErrorListener getErrorListener() {
        return errorListener;
    }

    public void setErrorListener(Response.ErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    public VolleyMultipartRequest.DataPart getDatos() {
        return datos;
    }

    public void setDatos(VolleyMultipartRequest.DataPart datos) {
        this.datos = datos;
    }

    public VolleyMultipartRequest getRequest() {
        return mRequest;
    }

    public void setRequest(VolleyMultipartRequest request) {
        mRequest = request;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public Map<String, String> getMap() {
        return mMap;
    }

    public void setMap(Map<String, String> map) {
        mMap = map;
    }

    public static class Builder{
        UploadManager mUploadManager;

        public Builder(Context context){
            mUploadManager = new UploadManager();
            mUploadManager.setContext(context);
        }

        public Builder setURL(String url){
            this.mUploadManager.setUrl(url);
            return this;
        }

        public Builder setMetodo(int metodo) {
            this.mUploadManager.setMetodo(metodo);
            return this;
        }

        public Builder setTipoDato(int tipoDato) {
            this.mUploadManager.setTipoDato(tipoDato);
            return this;
        }

        public Builder setOkListener(Response.Listener<NetworkResponse> okListener) {
            this.mUploadManager.setOkListener(okListener);
            return this;
        }

        public Builder setParams(Map<String, String> map){
            this.mUploadManager.setMap(map);
            return this;
        }

        public Builder setErrorListener(Response.ErrorListener errorListener) {
            this.mUploadManager.setErrorListener(errorListener);
            return this;
        }

        public Builder setDato(VolleyMultipartRequest.DataPart datos) {
            this.mUploadManager.setDatos(datos);
            return this;
        }

        public VolleyMultipartRequest build(){
            VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(mUploadManager.getMetodo(),
                    mUploadManager.getUrl(), mUploadManager.getOkListener(), mUploadManager.getErrorListener(),
                    mUploadManager.getMap(), mUploadManager.getDatos(), mUploadManager.getTipoDato());
            return multipartRequest;
        }
    }


}
