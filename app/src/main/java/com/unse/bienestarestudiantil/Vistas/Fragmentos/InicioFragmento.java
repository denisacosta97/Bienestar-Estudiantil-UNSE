package com.unse.bienestarestudiantil.Vistas.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Categoria;
import com.unse.bienestarestudiantil.Modelos.Noticia;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.NoticiaLectorActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.CategoriasAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.NoticiasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InicioFragmento extends Fragment {

    RecyclerView.LayoutManager mLayoutManager, mLayoutManager2;
    RecyclerView recyclerCategorias, recyclerNoticias;
    ArrayList<Categoria> mCategorias;
    CategoriasAdapter mAdapter;
    View view;
    NoticiasAdapter mNoticiasAdapter;
    ArrayList<Noticia> mListNoticias;
    Context mContext;
    FragmentManager mFragmentManager;

    public void setContext(Context context) {
        mContext = context;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    DialogoProcesamiento dialog;

    public InicioFragmento() {
        // Metodo necesario
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Crea la vista de Inicio
        view = inflater.inflate(R.layout.fragmento_inicio, container, false);

        loadViews();

        loadDataRecycler();

        return view;
    }

    private void loadViews() {
        recyclerCategorias = view.findViewById(R.id.recyclerCategorias);
        recyclerNoticias = view.findViewById(R.id.recyclerNoticias);
    }

    private void loadDataRecycler() {
        loadCategorias();
        mAdapter = new CategoriasAdapter(mCategorias, getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerCategorias.setLayoutManager(mLayoutManager);
        recyclerCategorias.setAdapter(mAdapter);

        recyclerCategorias.setVisibility(View.GONE);

        loadInfo();

        mListNoticias = new ArrayList<>();
        mNoticiasAdapter = new NoticiasAdapter(mListNoticias, getContext(), 0);
        mLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerNoticias.setLayoutManager(mLayoutManager2);

        recyclerNoticias.setAdapter(mNoticiasAdapter);
        recyclerNoticias.setNestedScrollingEnabled(false);

        ItemClickSupport itemClickSupport2 = ItemClickSupport.addTo(recyclerNoticias);
        itemClickSupport2.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), NoticiaLectorActivity.class);
                i.putExtra(Utils.NOTICIA, mListNoticias.get(position));
                startActivity(i);
            }
        });

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerCategorias);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                resetear();
                mCategorias.get(position).setEstado(true);
                mNoticiasAdapter.filtrarNoticias((int) id);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void resetear() {
        for (Categoria c : mCategorias) {
            c.setEstado(false);
        }
    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(mContext);
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s", Utils.URL_LISTA_NOTICIA, id, key);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(mContext, getString(R.string.servidorOff));
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(mFragmentManager, "dialog_process");
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(mContext, getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    loadInfo(jsonObject);
                    break;
                case 2:
                    Utils.showToast(mContext, getString(R.string.noData));
                    //updateView(0);
                    break;
                case 3:
                    Utils.showToast(mContext, getString(R.string.tokenInvalido));
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(mContext, getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(mContext, getString(R.string.errorInternoAdmin));
            //updateView(2);
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");

                mListNoticias = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    Noticia noticia = Noticia.mapper(o, Noticia.COMPLETE);

                    mListNoticias.add(noticia);

                }
                if (mListNoticias.size() >0){
                    recyclerCategorias.setVisibility(View.VISIBLE);
                }
                mNoticiasAdapter.setList(mListNoticias);
                //updateView(1);


            }
        } catch (JSONException e) {
            e.printStackTrace();
            //updateView(2);
        }

    }


    private void loadCategorias() {
        mCategorias = new ArrayList<>();
        mCategorias.add(new Categoria(9, "Todo"));
        mCategorias.add(new Categoria(1, "Comedor Universitario"));
        mCategorias.add(new Categoria(2, "Deportes"));
        mCategorias.add(new Categoria(3, "Transporte"));
        mCategorias.add(new Categoria(4, "Becas"));
        mCategorias.add(new Categoria(5, "Residencia"));
        mCategorias.add(new Categoria(6, "Ciber Estudiantil"));
        mCategorias.add(new Categoria(7, "UPA"));
        mCategorias.add(new Categoria(8, "Polideportivo"));
        mCategorias.get(0).setEstado(true);
    }
}
