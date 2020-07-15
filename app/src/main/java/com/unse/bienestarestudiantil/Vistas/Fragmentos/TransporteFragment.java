package com.unse.bienestarestudiantil.Vistas.Fragmentos;

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
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Transporte;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Becas.TurnosActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.RecorridoActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.TransporteAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransporteFragment extends Fragment implements View.OnClickListener  {

    View view;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView reciclerTrans;
    ArrayList<Transporte> mTransportes;
    TransporteAdapter mTransporteAdapter;
    DialogoProcesamiento dialog;
    FragmentManager mFragmentManager;
    Context mContext;
    boolean isOff = false;
    CardView cardHorarios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transporte, container, false);

        loadViews();

        loadData();

        return view;
    }

    private void loadData() {
        mTransportes = new ArrayList<>();
        mTransporteAdapter = new TransporteAdapter(mTransportes, getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        reciclerTrans.setNestedScrollingEnabled(true);
        reciclerTrans.setLayoutManager(mLayoutManager);
        reciclerTrans.setAdapter(mTransporteAdapter);

        loadInfo();

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(reciclerTrans);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), RecorridoActivity.class);
                i.putExtra(Utils.LINEA_NAME, mTransportes.get(position));
                startActivity(i);
            }
        });

        cardHorarios.setOnClickListener(this);
    }

    private void loadViews() {
        cardHorarios = view.findViewById(R.id.cardHorarios);
        reciclerTrans = view.findViewById(R.id.recycler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardHorarios:
                startActivity(new Intent(getContext(), TurnosActivity.class));
                break;

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

    public void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValue(Utils.IS_VISIT) ? 1 : 0;
        String URL = String.format("%s?key=%s&idT=%s", Utils.URL_LINEAS, key, id);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getContext(), getString(R.string.servidorOff));
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getManagerFragment(), "dialog_process");
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    loadInfoTransporte(jsonObject.getJSONArray("mensaje"));
                    break;
                case 2:
                    Utils.showToast(getContext(), getString(R.string.noCredenciales));
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

    private void loadInfoTransporte(JSONArray mensaje) {
        for (int i = 0; i < mensaje.length(); i++) {
            try {
                JSONObject j = mensaje.getJSONObject(i);

                Transporte transporte = Transporte.mapper(j);
                mTransportes.add(transporte);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        mTransporteAdapter.notifyDataSetChanged();
    }

}
