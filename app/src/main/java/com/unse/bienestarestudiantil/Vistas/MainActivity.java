package com.unse.bienestarestudiantil.Vistas;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Categoria;
import com.unse.bienestarestudiantil.Modelos.Noticias;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.CategoriasAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.NoticiasAdapter;
import com.unse.bienestarestudiantil.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView.LayoutManager mLayoutManager, mLayoutManager2;
    ArrayList<Categoria> mCategorias;
    CategoriasAdapter mAdapter;
    Toolbar mToolbar;
    NoticiasAdapter mNoticiasAdapter;
    ArrayList<Noticias> mListNoticias;

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);


        setToolbar();

        loadDataRecycler();


    }

    private void loadDataRecycler() {
        loadCategorias();
        mAdapter = new CategoriasAdapter(mCategorias, getApplicationContext());

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL, false);
        mBinding.recycler.setLayoutManager(mLayoutManager);
        mBinding.recycler.setAdapter(mAdapter);

        loadNoticias();

        mNoticiasAdapter = new NoticiasAdapter(mListNoticias, getApplicationContext());
        mLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mBinding.recyclerNews.setLayoutManager(mLayoutManager2);


        mBinding.recyclerNews.setAdapter(mNoticiasAdapter);
        mBinding.recyclerNews.setNestedScrollingEnabled(false);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mBinding.recycler);
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
                1,Utils.NOTICIA_NORMAL, Utils.TIPO_COMEDOR);
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

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(false);
            ab.setTitle("Bienestar Estudiantíl");
            mToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

}
