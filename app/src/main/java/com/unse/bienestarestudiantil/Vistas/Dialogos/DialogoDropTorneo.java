package com.unse.bienestarestudiantil.Vistas.Dialogos;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Torneo;
import com.unse.bienestarestudiantil.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DialogoDropTorneo extends DialogFragment implements View.OnClickListener {
    View view;
    Button btnConfirm, btnCancel;
    Torneo mTorneo;
    DialogoProcesamiento dialog;

    public void loadData(Torneo torneo) {
        this.mTorneo = torneo;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogo_drop_torneo, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loadViews();

        loadListener();

        return view;
    }

    private void loadListener() {
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void loadViews() {
        btnConfirm = view.findViewById(R.id.btnConfirm);
        btnCancel = view.findViewById(R.id.btnCancel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:
                save();
                break;
            case R.id.btnCancel:
                dismiss();
                break;
        }
    }


    private void save() {
        String token = new PreferenceManager(getContext()).getValueString(Utils.TOKEN);
        sendServer(processString(token));

    }

    private String processString(String token) {
        String data = "?idT=%s&key=%s";
        return String.format(data, mTorneo.getId(), token);
    }

    public void sendServer(String data) {
        String URL = Utils.URL_TORNEOS_BAJA + data;
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getContext(), "Error de conexión o servidor fuera de rango");
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case 1:
                    //Exito
                    Utils.showToast(getContext(), getString(R.string.torneoEliminado));
                    break;
                case 3:
                    Utils.showToast(getContext(), "No se puede procesar la tarea solicitada");
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getContext(), "No está autorizado para realizar ésta operación");
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getContext(), "Error desconocido, contacta al Administrador");
        }
    }

}
