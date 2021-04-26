package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionMedicamentos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.Medicamento;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.Modelos.Turno;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.MedicamentoAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogAtenderBecas;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoOpciones;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MedicamentosDiaActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MedicamentoAdapter mAdapter;
    ArrayList<Medicamento> mMedicamentos;
    Medicamento mMed;
    DialogoProcesamiento dialog;
    ImageView imgRefresh;
    ImageView imgIcono;
    OnClickOptionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos_dia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.DATA_TURNO) != null) {
            mMed = getIntent().getParcelableExtra(Utils.DATA_TURNO);
        }

        loadViews();

        setToolbar();

        loadListener();

        loadData();

        loadInfo();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Turnos del día");
        imgRefresh.setVisibility(View.VISIBLE);
    }

    private void loadListener() {
        imgRefresh.setOnClickListener(this);
        imgIcono.setOnClickListener(this);
        mListener = new OnClickOptionListener() {
            @Override
            public void onClick(int pos) {
                openDialogoConfirmar(mMedicamentos.get(pos));
            }
        };
    }

    private void openDialogoConfirmar(final Medicamento pos) {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(R.string.advertencia))
                .setDescripcion(pos.getEstado() == 5 ?
                        String.format(getString(R.string.turnoConfirmacion2), pos.getNombre(), pos.getApellido())
                        : String.format(getString(R.string.turnoModificar), pos.getNombre(), pos.getApellido()))
                .setTipo(DialogoGeneral.TIPO_SI_NO)
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {
                        showOptions(pos);
                    }

                    @Override
                    public void no() {

                    }
                });
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getSupportFragmentManager(), "dialogo_confirmar");
    }

    private void showOptions(final Medicamento pos) {
        final ArrayList<Opciones> opciones = new ArrayList<>();
        opciones.add(new Opciones("CANCELADO", 2));
        opciones.add(new Opciones("AUSENTE", 3));
        opciones.add(new Opciones("CONFIRMADO", 4));
        DialogoOpciones dialogoOpciones = new DialogoOpciones(new OnClickOptionListener() {
            @Override
            public void onClick(int p) {
                atenderTurno(pos, opciones.get(p));

            }
        }, opciones, getApplicationContext());
        dialogoOpciones.show(getSupportFragmentManager(), "opciones");
    }

    private void atenderTurno(final Medicamento pos, final Opciones op) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        final String key = manager.getValueString(Utils.TOKEN);
        final int id = manager.getValueInt(Utils.MY_ID);
        String URL = Utils.URL_MEDICAM_UPDATE;
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuestaTurno(response, pos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
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
                param.put("iu", String.valueOf(pos.getIdUsuario()));
                param.put("fr", String.valueOf(pos.getFechaRegistro()));
                param.put("es", String.valueOf(op.getId()));

                return param;
            }
        };
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void loadData() {
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgRefresh = findViewById(R.id.imgRefresh);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    private void loadInfo() {
        if (mMedicamentos != null)
            mMedicamentos.clear();
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = null;
        if (mMed != null)
            URL = String.format("%s?idU=%s&key=%s&di=%s&me=%s&an=%s", Utils.URL_MEDICAM_DAY, id, key,
                    mMed.getDia(), mMed.getMes(), mMed.getAnio());
        else
            URL = String.format("%s?idU=%s&key=%s&di=%s&me=%s&an=%s", Utils.URL_MEDICAM_DAY, id, key, -1, -1, -1);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    loadInfo(jsonObject);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.noData));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");
                //JSONArray fecha = jsonObject.getJSONArray("datos");
                mMedicamentos = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    Medicamento med = Medicamento.mapper(o, Medicamento.COMPLETE);
                    mMedicamentos.add(med);
                }

                if (mMedicamentos.size() > 0) {
                    mAdapter = new MedicamentoAdapter(mMedicamentos, getApplicationContext(), mListener);
                    mRecyclerView.setAdapter(mAdapter);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void procesarRespuestaTurno(String response, Medicamento turno) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    loadInfo();
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.turnoErrorAtender));
                    break;
                case 5:
                    Utils.showToast(getApplicationContext(), getString(R.string.turnoAtendido));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.imgRefresh:
                mMedicamentos.clear();
                loadInfo();
                break;
        }
    }

}