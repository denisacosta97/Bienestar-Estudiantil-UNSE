package com.unse.bienestarestudiantil.Vistas.Fragmentos;

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
import com.unse.bienestarestudiantil.Modelos.Noticias;
import com.unse.bienestarestudiantil.R;
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
    ArrayList<Noticias> mListNoticias;

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

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerCategorias);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                if (id == -1){
                    mNoticiasAdapter.filtrarNoticias(-1);
                }else{
                    mNoticiasAdapter.filtrarNoticias((int) id);
                }
            }
        });
    }

    private void loadNoticias() {
        mListNoticias = new ArrayList<>();
        Noticias noticias = new Noticias("Fiesta del Estudiante",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor\n incididunt ut labore et dolore magna aliqua.",
                "25/10/19","https://fcf.unse.edu.ar/wp-content/uploads/2018/11/becas-destacado.jpg",
                1, Utils.NOTICIA_NORMAL, Utils.TIPO_COMEDOR);
        mListNoticias.add(noticias);
        noticias = new Noticias("Fiesta del Estudiante 2",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor\n incididunt ut labore et dolore magna aliqua.",
                "26/10/19","https://fcf.unse.edu.ar/wp-content/uploads/2018/11/becas-destacado.jpg",
                1,Utils.NOTICIA_BUTTON_WEB, Utils.TIPO_BECA);
        noticias.setUrlWeb("www.google.com");
        mListNoticias.add(noticias);
        noticias = new Noticias("Fiesta del Estudiante 3",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor\n incididunt ut labore et dolore magna aliqua.",
                "27/10/19","https://fcf.unse.edu.ar/wp-content/uploads/2018/11/becas-destacado.jpg",
                1,Utils.NOTICIA_BUTTON_TIENDA, Utils.TIPO_DEPORTE);
        mListNoticias.add(noticias);
        mListNoticias.add(new Noticias("Fiesta del Estudiante 4",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor\n incididunt ut labore et dolore magna aliqua.",
                "28/10/19","https://fcf.unse.edu.ar/wp-content/uploads/2018/11/becas-destacado.jpg",
                1,Utils.NOTICIA_BUTTON_APP, Utils.TIPO_UPA));



    }


    private void loadCategorias() {
        mCategorias = new ArrayList<>();
        mCategorias.add(new Categoria(-1, "Todo"));
        mCategorias.add(new Categoria(1, "Comedor Universitario"));
        mCategorias.add(new Categoria(2, "Deportes"));
        mCategorias.add(new Categoria(3, "Transporte"));
        mCategorias.add(new Categoria(4, "Becas"));
        mCategorias.add(new Categoria(5, "Residencia"));
        mCategorias.add(new Categoria(6, "Cyber Estudiantil"));
        mCategorias.add(new Categoria(7, "UPA"));
    }
}
