package com.unse.bienestarestudiantil.Vistas.Dialogos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.SuscripcionSocio;
import com.unse.bienestarestudiantil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class DialogoBuscaSuscripcion extends DialogFragment {

    View view;
    TextView txtAnio;
    Button btnOk, btnBuscar;
    EditText edtDNI;
    Context mContext;
    DialogoProcesamiento dialog;
    FragmentManager mFragmentManager;
    ArrayList<SuscripcionSocio> mSuscripcionSocios;
    int idSuscrip = 0, validez = 0, idSocio = 0;
    boolean isCred = false;

    public ArrayList<SuscripcionSocio> getSuscripcionSocios() {
        return mSuscripcionSocios;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    public void setSuscripcionSocios(ArrayList<SuscripcionSocio> suscripcionSocios) {
        mSuscripcionSocios = suscripcionSocios;
    }

    public Context getContextDialog() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public FragmentManager getFragmentManagerDialog() {
        return mFragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_busca_suscripcion, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loadViews();

        loadData();

        loadListener();

        return view;
    }

    private void loadListener() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (idSuscrip != 0) {

                    if (validez == 1) {
                        boolean isIgual = false;
                        for (SuscripcionSocio s : mSuscripcionSocios) {
                            if (s.getIdSuscripcion() == idSuscrip) {
                                isIgual = true;
                                break;
                            }
                        }
                        if (!isIgual) {
                            otorgarCredencial(idSuscrip);
                        } else
                            Utils.showToast(getContextDialog(), getString(R.string.suscripcionInscripto));

                    } else
                        Utils.showToast(getContextDialog(), getString(R.string.suscripcionInvalida));

                } else
                    Utils.showToast(getContextDialog(), getString(R.string.primeroBuscarSuscrip));
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtDNI.getText().toString().equals("")) {
                    try {
                        Integer.parseInt(edtDNI.getText().toString());
                        buscar(edtDNI.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        edtDNI.setError(getString(R.string.anioInvalido));
                    }
                } else Utils.showToast(getContextDialog(), getString(R.string.campoVacio));
            }
        });
    }

    private void otorgarCredencial(int idSuscrip) {
        PreferenceManager manager = new PreferenceManager(getContextDialog());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String fecha = Utils.getFechaName(new Date(System.currentTimeMillis()));
        String URL = String.format("%s?id=%s&key=%s&idU=%s&idS=%s&ff=%s", Utils.URL_SOCIO_SUSCRIPCION_AGREGAR,
                id, key, idSocio, idSuscrip, fecha);
        StringRequest request = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                isCred = true;
                procesarRespuesta(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getContextDialog(), getString(R.string.servidorOff));
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getFragmentManagerDialog(), "dialog_process");
        VolleySingleton.getInstance(getContextDialog()).addToRequestQueue(request);
    }

    private void buscar(String anio) {
        PreferenceManager manager = new PreferenceManager(getContextDialog());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?id=%s&key=%s&anio=%s", Utils.URL_SOCIO_SUSCRIPCION,
                id, key, anio);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getContextDialog(), getString(R.string.servidorOff));
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getFragmentManagerDialog(), "dialog_process");
        VolleySingleton.getInstance(getContextDialog()).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getContextDialog(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    if (isCred) {
                        Utils.showToast(getContextDialog(), getString(R.string.credencialCreada));
                        dismiss();
                    } else
                        loadInfo(jsonObject);
                    break;
                case 2:
                    if (isCred)
                        Utils.showToast(getContextDialog(), getString(R.string.errorCrearCredencial));
                    else  Utils.showToast(getContextDialog(), getString(R.string.suscripcionNoExiste));
                    break;
                case 3:
                    Utils.showToast(getContextDialog(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getContextDialog(), getString(R.string.camposInvalidos));
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getContextDialog(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getContextDialog(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                JSONObject datos = jsonObject.getJSONObject("mensaje");
                idSuscrip = Integer.parseInt(datos.getString("idSuscripcion"));
                validez = Integer.parseInt(datos.getString("validez"));

                txtAnio.setText(String.format("%s", datos.getString("anio")));
                txtAnio.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        btnOk.setEnabled(true);
    }

    private void loadData() {
        btnOk.setEnabled(false);
        txtAnio.setVisibility(View.GONE);
        //txtAnio.setBackgroundColor(mContext.getResources().getColor(R.color.transparente));
    }

    private void loadViews() {
        edtDNI = view.findViewById(R.id.edtBuscar);
        btnOk = view.findViewById(R.id.btnAceptar);
        txtAnio = view.findViewById(R.id.txtAnio);
        btnBuscar = view.findViewById(R.id.btnBuscar);
    }

}