package com.unse.bienestarestudiantil.Vistas.Fragmentos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.integration.android.IntentIntegrator;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Modelos.Colectivo;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.Modelos.Punto;
import com.unse.bienestarestudiantil.Modelos.Recorrido;
import com.unse.bienestarestudiantil.Modelos.Servicio;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Becas.TurnosActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.Chofer.NuevoServicioActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.GestionRecorridosActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.RecorridoActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.RecorridoRealActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.RecorridoAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoOpciones;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransporteFragment extends Fragment implements View.OnClickListener  {

    View view;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView reciclerTrans;
    ArrayList<Recorrido> mRecorridos;
    ArrayList<Servicio> mServicios;
    RecorridoAdapter mTransporteAdapter;
    DialogoProcesamiento dialog;
    FragmentManager mFragmentManager;
    Context mContext;
    CardView cardServicios, cardScanner;
    Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transporte, container, false);

        loadViews();

        loadData();

        return view;
    }

    private void loadData() {
        mRecorridos = new ArrayList<>();
        mTransporteAdapter = new RecorridoAdapter(mRecorridos, getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        reciclerTrans.setNestedScrollingEnabled(true);
        reciclerTrans.setLayoutManager(mLayoutManager);
        reciclerTrans.setAdapter(mTransporteAdapter);

        loadInfo();

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(reciclerTrans);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), RecorridoActivity.class);
                i.putExtra(Utils.RECORRIDO, mRecorridos.get(position));
                startActivity(i);
            }
        });

        cardServicios.setOnClickListener(this);
        cardScanner.setOnClickListener(this);
    }

    private void loadViews() {
        cardServicios = view.findViewById(R.id.cardServicios);
        reciclerTrans = view.findViewById(R.id.recycler);
        cardScanner = view.findViewById(R.id.cardScanner);
    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s", Utils.URL_GET_RECORRIDOS, id, key);
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
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(mFragmentManager, "dialog_process");
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Toast.makeText(mContext, getString(R.string.errorInternoAdmin), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //Exito
                    loadInfo(jsonObject);
                    break;
                case 2:
//                    Utils.showCustomToast(GestionRecorridosActivity.this, getApplicationContext(),
//                            getString(R.string.noReservas), R.drawable.ic_error);
                    break;
                case 4:
                    Toast.makeText(mContext, getString(R.string.camposInvalidos), Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(mContext, getString(R.string.tokenInvalido), Toast.LENGTH_SHORT).show();
                    break;
                case 100:
                    //No autorizado
                    Toast.makeText(mContext, getString(R.string.tokenInexistente), Toast.LENGTH_SHORT).show();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, getString(R.string.errorInternoAdmin), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                mRecorridos = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    Recorrido recorrido = Recorrido.mapper(o);

                    mRecorridos.add(recorrido);

                }
                mTransporteAdapter = new RecorridoAdapter(mRecorridos, getContext());
                reciclerTrans.setAdapter(mTransporteAdapter);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardServicios:
                getServicios();
                break;
            case R.id.cardScanner:
                scanQR();
                break;
        }
    }

    public void scanQR() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(mActivity);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("Scan");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();
    }

    private void getServicios() {
        PreferenceManager manager = new PreferenceManager(getContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s", Utils.URL_SERVICIO_ALUMNOS, key,
                idLocal);
        StringRequest requestImage = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuestaServicios(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.showToast(getContext(), getString(R.string.servidorOff));
                dialog.dismiss();
            }
        });
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(mFragmentManager, "dialog_process");
        VolleySingleton.getInstance(getContext()).addToRequestQueue(requestImage);
    }

    private void procesarRespuestaServicios(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Toast.makeText(mContext, getString(R.string.errorInternoAdmin), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //Exito
                    loadInfoServicios(jsonObject);
                    break;
                case 2:
//                    Utils.showCustomToast(GestionRecorridosActivity.this, getApplicationContext(),
//                            getString(R.string.noReservas), R.drawable.ic_error);
                    break;
                case 4:
                    Toast.makeText(mContext, getString(R.string.camposInvalidos), Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(mContext, getString(R.string.tokenInvalido), Toast.LENGTH_SHORT).show();
                    break;
                case 100:
                    //No autorizado
                    Toast.makeText(mContext, getString(R.string.tokenInexistente), Toast.LENGTH_SHORT).show();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, getString(R.string.errorInternoAdmin), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadInfoServicios(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                mServicios = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    Servicio servicio = Servicio.mapper(o, Servicio.SERVICIO);
                    mServicios.add(servicio);
                }

                ArrayList<Opciones> opciones = new ArrayList<>();
                Opciones op = null;

                for (int i = 0; i < mServicios.size(); i++) {
                    op = new Opciones(mServicios.get(i).getDescripcio());
                    opciones.add(op);
                }

                openDialogServicios(opciones);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void openDialogServicios(ArrayList<Opciones> list) {
        DialogoOpciones dialogoOpciones = new DialogoOpciones(new OnClickOptionListener() {
            @Override
            public void onClick(int pos) {
                Servicio servicio = mServicios.get(pos);
                loadInfoServicio(servicio);

            }
        }, list, getContext());
        dialogoOpciones.show(mFragmentManager, "opciones_colectivos");
    }

    private void loadInfoServicio(Servicio servicio) {
        PreferenceManager manager = new PreferenceManager(getContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&is=%s", Utils.URL_GET_SERVICIO, id, key, servicio.getIdServicio());
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuestaInfoServicio(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(mFragmentManager, "dialog_process");
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    private void procesarRespuestaInfoServicio(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Toast.makeText(mContext, getString(R.string.errorInternoAdmin), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //Exito
                    loadInfoServicio(jsonObject);
                    break;
                case 2:
//                    Utils.showCustomToast(GestionRecorridosActivity.this, getApplicationContext(),
//                            getString(R.string.noReservas), R.drawable.ic_error);
                    break;
                case 4:
                    Toast.makeText(mContext, getString(R.string.camposInvalidos), Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(mContext, getString(R.string.tokenInvalido), Toast.LENGTH_SHORT).show();
                    break;
                case 100:
                    //No autorizado
                    Toast.makeText(mContext, getString(R.string.tokenInexistente), Toast.LENGTH_SHORT).show();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, getString(R.string.errorInternoAdmin), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadInfoServicio(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                if (jsonObject.get("mensaje") instanceof Boolean){
                    Toast.makeText(mContext, getString(R.string.errorLocation), Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("mensaje");
                    JSONObject punto = jsonObject.getJSONObject("last");
                    Servicio servicio = Servicio.mapper(jsonObject1, Servicio.COMPLETE);
                    Punto punto1 = Punto.mapper(punto, Punto.COMPLETE);

                    Intent i = new Intent(getContext(), RecorridoRealActivity.class);
                    i.putExtra(Utils.SERVICIO, servicio);
                    i.putExtra(Utils.PUNTO, punto1);
                    startActivity(i);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    public FragmentManager getManagerFragment() {
        return this.mFragmentManager;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

}
