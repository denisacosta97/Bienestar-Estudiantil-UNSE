package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionReservasInst;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.ReservaEspacio;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ReservaEspacioAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class DialogBuscarReservas extends DialogFragment {

    View view;
    Button btnBuscar, btnAceptar;
    EditText edtDni;
    TextView txtNomAp, txtDni, txtTurno, txtEspacio, txtFecha, txtEstado;
    LinearLayout linlayNo, linlayInfo, linlayEdt;
    Context mContext;
    DialogoProcesamiento dialog;
    FragmentManager fragmentManager;
    ReservaEspacio reservaEspacio;

    public void setContextEstado(Context contextEstado){
        this.mContext = contextEstado;
    }

    public void setFragmentManager(FragmentManager fragmentManager1) {
        fragmentManager = fragmentManager1;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_buscar_reservas, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loadViews();

        loadListener();

        return view;
    }

    private void loadData() {
        linlayInfo.setVisibility(View.VISIBLE);
        String name = reservaEspacio.getNombre() + " " + reservaEspacio.getApellido();
        txtNomAp.setText(name);
        txtDni.setText(String.valueOf(reservaEspacio.getIdUsuario()));
        String turno = reservaEspacio.getHoraI() + "|" + reservaEspacio.getHoraF();
        txtTurno.setText(turno);
        txtFecha.setText(reservaEspacio.getFechaEv());
        txtEspacio.setText(reservaEspacio.getNombreEspacio());
        txtEstado.setText(reservaEspacio.getDescRes());

    }

    private void loadListener() {
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linlayInfo.setVisibility(View.GONE);
                linlayNo.setVisibility(View.GONE);
                String dni = edtDni.getText().toString().trim();
                buscarReserva(dni);
            }
        });
    }

    private void buscarReserva(String dni) {
        Validador validador = new Validador(mContext);
        if(validador.validarDNI(dni)){
            loadInfo(dni);
        }
        else
            Toast.makeText(mContext, "Ingrese un DNI válido", Toast.LENGTH_SHORT).show();
    }

    private void loadViews() {
        linlayEdt = view.findViewById(R.id.linlayEdt);
        linlayInfo = view.findViewById(R.id.linlayInfo);
        linlayNo = view.findViewById(R.id.linlayNo);
        btnAceptar = view.findViewById(R.id.btnAceptar);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        txtNomAp = view.findViewById(R.id.txtNomAp);
        txtDni = view.findViewById(R.id.txtDni);
        txtTurno = view.findViewById(R.id.txtTurno);
        txtFecha = view.findViewById(R.id.txtFecha);
        txtEspacio = view.findViewById(R.id.txtEspacio);
        txtEstado = view.findViewById(R.id.txtEstado);
        edtDni = view.findViewById(R.id.edtDniImput);

    }

    private void loadInfo(String dni) {
        PreferenceManager manager = new PreferenceManager(mContext);
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&iu=%s", Utils.URL_RESERVAS_ESPACIOS_ID, id, key, dni);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //mProgressBar.setVisibility(View.GONE);
                Toast.makeText(mContext, R.string.servidorOff, Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(fragmentManager, "dialog_process");
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Toast.makeText(mContext, R.string.errorInternoAdmin, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //Exito
                    linlayNo.setVisibility(View.GONE);
                    loadInfo(jsonObject);
                    break;
                case 2:
                    linlayNo.setVisibility(View.VISIBLE);
                    linlayInfo.setVisibility(View.GONE);
//                    Utils.showCustomToast(GestionRecorridosActivity.this, getApplicationContext(),
//                            getString(R.string.noReservas), R.drawable.ic_error);
                    break;
                case 4:
                    Toast.makeText(mContext, R.string.camposInvalidos, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(mContext, R.string.tokenInvalido, Toast.LENGTH_SHORT).show();
                    break;
                case 100:
                    Toast.makeText(mContext, R.string.tokenInexistente, Toast.LENGTH_SHORT).show();
                    //No autorizado
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, R.string.errorInternoAdmin, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                JSONObject json = jsonObject.getJSONObject("mensaje");

                reservaEspacio = ReservaEspacio.mapper(json, 2);

                if (reservaEspacio != null){
                    loadData();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
