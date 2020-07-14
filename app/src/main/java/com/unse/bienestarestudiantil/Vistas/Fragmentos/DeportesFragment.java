package com.unse.bienestarestudiantil.Vistas.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.PerfilDeporteActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.DeportesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DeportesFragment extends Fragment {

    View view;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView reciclerDeportes;
    ArrayList<Deporte> mDeportes;
    DeportesAdapter mDeportesAdapter;
    RelativeLayout mImageDeportes;
    DialogoProcesamiento dialog;
    FragmentManager mFragmentManager;
    LinearLayout layoutFondo;
    Context mContext;
    boolean isOff = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_deportes, container, false);

        layoutFondo = view.findViewById(R.id.backgroundDep);

        Glide.with(this).load(R.drawable.imgdeportes)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        layoutFondo.setBackground(resource);
                    }
                });

        loadViews();

        loadData();

        return view;
    }

    private void loadViews() {
        reciclerDeportes = view.findViewById(R.id.recyclerDeportes);
    }

    private void loadData() {
        mDeportes = new ArrayList<>();
        mDeportesAdapter = new DeportesAdapter(mDeportes, getContext(), false);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        reciclerDeportes.setNestedScrollingEnabled(true);
        reciclerDeportes.setLayoutManager(mLayoutManager);
        reciclerDeportes.setAdapter(mDeportesAdapter);

        loadInfo();

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(reciclerDeportes);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                if (!isOff) {
                    Intent i = new Intent(getContext(), PerfilDeporteActivity.class);
                    i.putExtra(Utils.DEPORTE_NAME, mDeportes.get(position));
                    startActivity(i);
                }else Utils.showToast(getContext(), getString(R.string.noConexion));
            }
        });
    }

    public void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValue(Utils.IS_VISIT) ? 1 : 0;
        String URL = String.format("%s?key=%s&id=%s", Utils.URL_DEPORTE_LISTA, key, id);
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
                updateView(1);
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getManagerFragment(), "dialog_process");
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
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

    private void updateView(int i) {
        switch (i) {
            case 2:
                isOff = false;
                mDeportes.clear();
                break;
            case 1:
                isOff = true;
                mDeportes.clear();
                mDeportes.add(new Deporte(0,1, "Ajedréz", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,2, "Básquet", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,3, "Cestobal", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,4, "Fútbol 11 Masculino", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,5, "Fútbol 11 Femenino", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,6, "Fútbol Sala Masculino", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,7, "Fútbol Sala Femenino", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,8, "Hockey", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,9, "Natación", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,10, "Rugby", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,11, "Tenis de Mesa", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,12, "Voleibol Masculino", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,13, "Voleibol Femenino", "", "", "", "", "", "", 0, null));
                mDeportesAdapter.notifyDataSetChanged();
                break;
        }
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
                    updateView(2);
                    loadInfoDeportes(jsonObject.getJSONArray("mensaje"));
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
            updateView(2);
            Utils.showToast(getContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadInfoDeportes(JSONArray mensaje) {
        for (int i = 0; i < mensaje.length(); i++) {
            try {
                JSONObject j = mensaje.getJSONObject(i);

                Deporte deporte = Deporte.mapper(j);
                mDeportes.add(deporte);

            } catch (JSONException e) {
                e.printStackTrace();
                updateView(1);
            }

        }
        mDeportesAdapter.notifyDataSetChanged();
    }

}