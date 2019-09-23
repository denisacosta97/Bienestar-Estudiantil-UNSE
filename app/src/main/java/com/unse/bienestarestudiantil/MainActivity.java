package com.unse.bienestarestudiantil;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView, mRecyclerView2;
    RecyclerView.LayoutManager mLayoutManager, mLayoutManager2;
    ArrayList<Categoria> mCategorias;
    CategoriasAdapter mAdapter;
    Toolbar mToolbar;
    NewsAdapter mNewsAdapter;
    ArrayList<News> mNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);

        mRecyclerView = findViewById(R.id.recycler);
        mCategorias = new ArrayList<>();
        mCategorias.add(new Categoria("Comedor Universitario"));
        mCategorias.add(new Categoria("Deportes"));
        mCategorias.add(new Categoria("Transporte"));
        mCategorias.add(new Categoria("Becas"));
        mCategorias.add(new Categoria("Polideportivo"));
        mCategorias.add(new Categoria("Residencia"));
        mCategorias.add(new Categoria("Cyber Estudiantil"));
        mCategorias.add(new Categoria("UPA"));

        mAdapter = new CategoriasAdapter(mCategorias, getApplicationContext());

        mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayout.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);
        setToolbar();

        mRecyclerView2 = findViewById(R.id.recyclerNews);
        mNews = new ArrayList<>();
        mNews.add(new News("Fiesta del día del estudiante", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", "21/09/2019", 0, "https://www.ellitoral.com/um/fotos/190166_fde.jpg"));
        mNews.add(new News("Convocatoria a becas UNSE 2019", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", "15/09/2019", 1, "https://fcf.unse.edu.ar/wp-content/uploads/2018/11/becas-destacado.jpg"));
        mNews.add(new News("Jornada de convivencia y ambientación estudiantíl a la vida universitaria", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "23/09/2019", 2, "http://www.mdcinfo.com.ar/img/noticias/UNSE.jpg"));

        mNewsAdapter = new NewsAdapter(mNews, getApplicationContext());
        mLayoutManager2 = new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL, false);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        mRecyclerView2.setAdapter(mNewsAdapter);
        mRecyclerView2.setNestedScrollingEnabled(false);
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
