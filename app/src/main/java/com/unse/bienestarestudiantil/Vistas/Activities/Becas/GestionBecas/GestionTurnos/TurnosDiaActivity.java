package com.unse.bienestarestudiantil.Vistas.Activities.Becas.GestionBecas.GestionTurnos;

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
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.Turno;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.TurnosDiasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogAtenderBecas;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TurnosDiaActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    TurnosDiasAdapter mAdapter;
    ArrayList<Turno> mTurnos;
    DialogoProcesamiento dialog;
    ImageView imgRefresh;
    ImageView imgIcono;
    OnClickOptionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnos_dia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
                openDialogoConfirmar(mTurnos.get(pos));
            }
        };
    }

    private void openDialogoConfirmar(final Turno pos) {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(R.string.advertencia))
                .setDescripcion(pos.getEstado() == 5 ?
                        String.format(getString(R.string.turnoConfirmacion), pos.getNom(), pos.getApe())
                        : String.format(getString(R.string.turnoModificar), pos.getNom(), pos.getApe()))
                .setTipo(DialogoGeneral.TIPO_SI_NO)
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {
                        atenderTurno(pos);
                    }

                    @Override
                    public void no() {

                    }
                });
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getSupportFragmentManager(), "dialogo_confirmar");
    }

    private void atenderTurno(final Turno pos) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        final String key = manager.getValueString(Utils.TOKEN);
        final int id = manager.getValueInt(Utils.MY_ID);
        String URL = Utils.URL_TURNOS_ATENDER;
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
                param.put("di", String.valueOf(pos.getDia()));
                param.put("me", String.valueOf(pos.getMes()));
                param.put("an", String.valueOf(pos.getAnio()));
                param.put("ho", pos.getHorario());
                param.put("ir", String.valueOf(id));
                param.put("es", String.valueOf(pos.getEstado()));
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
        mTurnos = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TurnosDiasAdapter(mTurnos, getApplicationContext(), mListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgRefresh = findViewById(R.id.imgRefresh);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s", Utils.URL_TURNOS_DIA, id, key);
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
                JSONArray fecha = jsonObject.getJSONArray("datos");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    Turno turno = Turno.mapper(o, Turno.BASIC);
                    turno.setDia(Integer.parseInt(fecha.getString(0)));
                    turno.setMes(Integer.parseInt(fecha.getString(1)));
                    turno.setAnio(Integer.parseInt(fecha.getString(2)));

                    mTurnos.add(turno);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();

    }

    private void procesarRespuestaTurno(String response, Turno turno) {
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
                    loadInfoTurno(jsonObject, turno);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.turnoErrorAtender));
                    break;
                case 5:
                    Utils.showToast(getApplicationContext(), getString(R.string.turnoAtendido));
                    dialogoYaAtendido(jsonObject, turno);
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

    private void dialogoYaAtendido(final JSONObject jsonObject, final Turno tu) {
        Turno turno = null;
        try {
            turno = Turno.mapper(jsonObject.getJSONObject("dato"), Turno.MEDIUM);
            turno.setDia(tu.getDia());
            turno.setMes(tu.getMes());
            turno.setAnio(tu.getAnio());
            turno.setHorario(tu.getHorario());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Turno finalTurno = turno;
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(R.string.advertencia))
                .setDescripcion((finalTurno != null && finalTurno.getEstado() == 5) ? getString(R.string.turnoEnProceso)
                        : getString(R.string.turnoModificarAtendido))
                .setTipo(DialogoGeneral.TIPO_SI_NO)
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {
                        openDialogo(finalTurno);

                    }

                    @Override
                    public void no() {

                    }
                });
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getSupportFragmentManager(), "dialogo_confirmar");
    }

    private void loadInfoTurno(JSONObject jsonObject, Turno tu) {
        try {
            if (jsonObject.has("dato")) {

                Turno turno = Turno.mapper(jsonObject.getJSONObject("dato"), Turno.MEDIUM);
                turno.setDia(tu.getDia());
                turno.setMes(tu.getMes());
                turno.setAnio(tu.getAnio());
                turno.setHorario(tu.getHorario());

                openDialogo(turno);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void openDialogo(Turno turno) {
        DialogAtenderBecas dialogAtender = new DialogAtenderBecas();
        dialogAtender.loadData(turno);
        dialogAtender.setFragmentManager(getSupportFragmentManager());
        dialogAtender.setContext(getApplicationContext());
        dialogAtender.show(getSupportFragmentManager(), "dialog_turnos");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.imgRefresh:
                mTurnos.clear();
                loadInfo();
                break;
        }
    }

}
