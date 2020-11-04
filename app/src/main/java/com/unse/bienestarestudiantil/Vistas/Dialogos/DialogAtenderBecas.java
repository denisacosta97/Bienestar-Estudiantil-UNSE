package com.unse.bienestarestudiantil.Vistas.Dialogos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Turno;
import com.unse.bienestarestudiantil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class DialogAtenderBecas extends DialogFragment implements View.OnClickListener {

    View view;
    Button btnConfirm, btnAusente;
    Turno mTurno;
    TextView txtNombre, txtFacultad, txtDni, txtHorario;
    DialogoProcesamiento dialog;
    FragmentManager fragmentManager;
    Context context;

    public void loadData(Turno turno) {
        this.mTurno = turno;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_atender_becas, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loadViews();

        loadData();

        loadListener();

        return view;
    }

    private void loadData() {
        txtNombre.setText(String.format("%s %s", mTurno.getNom(), mTurno.getApe()));
        txtHorario.setText("-\n" + mTurno.getHorario());
        txtDni.setText(String.valueOf(mTurno.getIdUsuario()));
        txtFacultad.setText(String.format("%s,%s",mTurno.getCarrera(), mTurno.getFacultad()));
    }

    private void loadListener() {
        btnConfirm.setOnClickListener(this);
        btnAusente.setOnClickListener(this);
    }

    private void loadViews() {
        btnConfirm = view.findViewById(R.id.btnConfirm);
        btnAusente = view.findViewById(R.id.btnAusente);
        txtNombre = view.findViewById(R.id.txtNombre);
        txtDni = view.findViewById(R.id.txtDni);
        txtFacultad = view.findViewById(R.id.txtFacultad);
        txtHorario = view.findViewById(R.id.txtHorario);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:
                sendServer(6);
                break;
            case R.id.btnAusente:
                sendServer(7);
                break;
        }
    }

    public void sendServer(final int op) {
        PreferenceManager manager = new PreferenceManager(context);
        final String key = manager.getValueString(Utils.TOKEN);
        final int id = manager.getValueInt(Utils.MY_ID);
        String URL = Utils.URL_TURNOS_ACTUALIZAR;
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
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("key", key);
                param.put("idU", String.valueOf(id));
                param.put("di", String.valueOf(mTurno.getDia()));
                param.put("me", String.valueOf(mTurno.getMes()));
                param.put("an", String.valueOf(mTurno.getAnio()));
                param.put("ho", mTurno.getHorario());
                param.put("ir", String.valueOf(id));
                param.put("es", String.valueOf(op));
                return param;
            }
        };
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
                case 2:
                    Utils.showToast(getContext(), getString(R.string.turnoErrorAtender));
                    break;
                case 3:
                    Utils.showToast(getContext(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getContext(), getString(R.string.camposInvalidos));
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getContext(), getString(R.string.errorInternoAdmin));
        }
    }
}