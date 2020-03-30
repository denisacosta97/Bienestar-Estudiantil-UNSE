package com.unse.bienestarestudiantil.Herramientas;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.v4.app.FragmentManager;
import android.widget.ProgressBar;

import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadPDF extends AsyncTask<String, Integer, String> {


    private Context context;
    private PowerManager.WakeLock mWakeLock;
    private String nombreArchivo;
    private ProgressBar mProgressBar;
    YesNoDialogListener mListener;

    DialogoProcesamiento mDialogoProcesamiento;
    FragmentManager mFragmentManager;

    public DownloadPDF(Context context, String nombre, FragmentManager fragmentManager, YesNoDialogListener listener) {
        this.context = context;
        this.nombreArchivo = nombre;
        this.mListener = listener;
        this.mFragmentManager = fragmentManager;
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // esperar HTTP 200 OK, por lo que no guardamos por error el informe de error
            // en lugar del archivo
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Servidor Retorna HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // esto será útil para mostrar el porcentaje de descarga
            // podría ser -1: el servidor no informó la longitud
            int fileLength = connection.getContentLength();

            // Descargar archivo
            input = connection.getInputStream();
            output = new FileOutputStream(Utils.getDirectoryPath()+nombreArchivo);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // tomar el bloqueo de la CPU para evitar que la CPU se apague si el usuario
        // presiona el botón de encendido durante la descarga
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();

        mDialogoProcesamiento = new DialogoProcesamiento();
        mDialogoProcesamiento.setCancelable(false);
        mDialogoProcesamiento.show(mFragmentManager, "dialog_process");

    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
        mProgressBar = mDialogoProcesamiento.getProgressBar();
        mProgressBar.setIndeterminate(false);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();
        mDialogoProcesamiento.dismiss();
        if (result != null) {
            mListener.no();
        } else {
            mListener.yes();
        }
    }
}
