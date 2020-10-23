package com.unse.bienestarestudiantil.Vistas.Dialogos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.unse.bienestarestudiantil.Modelos.Turno;
import com.unse.bienestarestudiantil.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DialogAtenderBecas extends DialogFragment implements View.OnClickListener {

    View view;
    Button btnConfirm, btnAusente;
    Turno mTurno;
    DialogoProcesamiento dialog;
    FragmentManager fragmentManager;
    Context context;

    public void loadData(Turno turno){
        this.mTurno = turno;
    }

    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_atender_becas, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loadViews();

        loadListener();

        return view;
    }

    private void loadListener() {
        btnConfirm.setOnClickListener(this);
        btnAusente.setOnClickListener(this);
    }

    private void loadViews() {
        btnConfirm = view.findViewById(R.id.btnConfirm);
        btnAusente = view.findViewById(R.id.btnAusente);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnConfirm:
                sendServer(6);
                break;
            case R.id.btnAusente:
                sendServer(7);
                break;
        }
    }

    public void sendServer(int op) {
        PreferenceManager manager = new PreferenceManager(context);
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);

        String URL = String.format("%s?idU=%s&key=%s&di=%s&me=%s&an=%s&ho=%s&es=%s&ir=%s",
                Utils.URL_TURNOS_ACT, id, key, mTurno.getDia(), mTurno.getMes(), mTurno.getAnio(),
                mTurno.getHorario(), op, id);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(context, getString(R.string.servidorOff));
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(fragmentManager, "dialog_process");
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case 1:
                    //Exito
                    Utils.showToast(getContext(), getString(R.string.turnoAct));
                    dismiss();
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