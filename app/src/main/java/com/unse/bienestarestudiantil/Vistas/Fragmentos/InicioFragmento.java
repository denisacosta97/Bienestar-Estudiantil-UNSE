package com.unse.bienestarestudiantil.Vistas.Fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Categoria;
import com.unse.bienestarestudiantil.Modelos.Noticia;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.NoticiaLectorActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.CategoriasAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.NoticiasAdapter;

import java.util.ArrayList;

public class InicioFragmento extends Fragment {

    RecyclerView.LayoutManager mLayoutManager, mLayoutManager2;
    RecyclerView recyclerCategorias, recyclerNoticias;
    ArrayList<Categoria> mCategorias;
    CategoriasAdapter mAdapter;
    View view;
    NoticiasAdapter mNoticiasAdapter;
    ArrayList<Noticia> mListNoticias;

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
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false);
        recyclerCategorias.setLayoutManager(mLayoutManager);
        recyclerCategorias.setAdapter(mAdapter);

        loadNoticias();

        mNoticiasAdapter = new NoticiasAdapter(mListNoticias, getContext());
        mLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
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
                if (id == -1){
                    mNoticiasAdapter.filtrarNoticias(-1);
                }else{
                    mNoticiasAdapter.filtrarNoticias((int) id);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void resetear() {
        for (Categoria c :mCategorias){
            c.setEstado(false);
        }
    }

    private void loadNoticias() {
        mListNoticias = new ArrayList<>();
        Noticia noticia = new Noticia("Fiesta del Estudiante",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor\n incididunt ut labore et dolore magna aliqua.",
                "25/10/19","https://www.unse.edu.ar/images/CEOMI2019/UAPU%20redes.jpg",
                1, Utils.NOTICIA_NORMAL, Utils.TIPO_COMEDOR, "COMEDOR UNIVERSITARIO");
        mListNoticias.add(noticia);
        noticia = new Noticia("Fiesta del Estudiante 2",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor\n incididunt ut labore et dolore magna aliqua.",
                "26/10/19","https://fce.unse.edu.ar/sites/default/files/styles/large/public/400x250concursodocente.jpg",
                1,Utils.NOTICIA_BUTTON_WEB, Utils.TIPO_BECA, "ÁREA BECAS");
        noticia.setUrlWeb("www.google.com");
        mListNoticias.add(noticia);
        noticia = new Noticia("Fiesta del Estudiante 3",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor\n incididunt ut labore et dolore magna aliqua.",
                "27/10/19","https://fcf.unse.edu.ar/wp-content/uploads/2018/11/becas-destacado.jpg",
                1,Utils.NOTICIA_BUTTON_TIENDA, Utils.TIPO_DEPORTE, "ÁREA DEPORTES");
        mListNoticias.add(noticia);
        mListNoticias.add(new Noticia("Fiesta del Estudiante 4",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor\n incididunt ut labore et dolore magna aliqua.",
                "28/10/19","https://fcf.unse.edu.ar/wp-content/uploads/2018/11/becas-destacado.jpg",
                1,Utils.NOTICIA_BUTTON_APP, Utils.TIPO_UPA, "UPA"));

    }


    private void loadCategorias() {
        mCategorias = new ArrayList<>();
        mCategorias.add(new Categoria(-1, "Todo"));
        mCategorias.add(new Categoria(1, "Comedor Universitario"));
        mCategorias.add(new Categoria(2, "Deporte"));
        mCategorias.add(new Categoria(3, "Transporte"));
        mCategorias.add(new Categoria(4, "Becas"));
        mCategorias.add(new Categoria(5, "Residencia"));
        mCategorias.add(new Categoria(6, "Cyber Estudiantil"));
        mCategorias.add(new Categoria(7, "UPA"));
        mCategorias.get(0).setEstado(true);
    }
}
