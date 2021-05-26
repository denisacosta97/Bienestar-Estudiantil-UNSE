package com.unse.bienestarestudiantil.Herramientas;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.unse.bienestarestudiantil.Modelos.Maraton;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneratePDFTask extends AsyncTask<String, String, String> {

    List<Maraton> mInscriptosMaraton;
    DialogoProcesamiento dialogoProcesamiento;
    Context mContext;
    int tipo = 0;
    Bitmap graficoBarra;

    ArrayList<Usuario> mUsuarios;

    String mes;
    int diasMes;
    int[] reservas;
    HashMap<String, Integer[]> reservasAlumnosDias;
    Bitmap graficoTorta;

    public GeneratePDFTask(int tipo, DialogoProcesamiento dialogoProcesamiento, Context context) {
        this.dialogoProcesamiento = dialogoProcesamiento;
        this.tipo = tipo;
        mContext = context;
    }

    public GeneratePDFTask(int tipo, ArrayList<Usuario> usuarios, DialogoProcesamiento dialogoProcesamiento, Context context) {
        mUsuarios = usuarios;
        this.dialogoProcesamiento = dialogoProcesamiento;
        mContext = context;
        this.tipo = tipo;
    }


    public GeneratePDFTask(int tipo, ArrayList<Usuario> usuarios, String mes,
                           Bitmap graficoBarra, Bitmap graficoTorta,
                           int diasMes, HashMap<String, Integer[]> reservasAlumnosDias,
                           DialogoProcesamiento dialogoProcesamiento, Context context, int[] reservas) {
        this.tipo = tipo;
        mUsuarios = usuarios;
        this.graficoBarra = graficoBarra;
        this.graficoTorta = graficoTorta;
        this.mes = mes;
        this.dialogoProcesamiento = dialogoProcesamiento;
        mContext = context;
        this.diasMes = diasMes;
        this.reservasAlumnosDias = reservasAlumnosDias;
        this.reservas = reservas;
    }


    public void setmInscriptosMaraton(List<Maraton> mInscriptosMaraton) {
        this.mInscriptosMaraton = mInscriptosMaraton;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        if (tipo == 1)
            Utils.createPDFMaraton(mInscriptosMaraton, mContext);
        else if (tipo == 2)
            Utils.createReportMensualPDF(mUsuarios, mContext, mes, diasMes, reservasAlumnosDias, graficoBarra, graficoTorta, reservas);
        else if (tipo == 3)
            Utils.createPDF(mUsuarios, mContext);
        return null;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dialogoProcesamiento.dismiss();
        Utils.showToast(mContext, "¡Archivo creado!");
    }
}

