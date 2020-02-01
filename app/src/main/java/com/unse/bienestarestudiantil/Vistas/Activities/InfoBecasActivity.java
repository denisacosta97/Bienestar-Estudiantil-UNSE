package com.unse.bienestarestudiantil.Vistas.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.InfoBecas;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.InfoBecasAdapter;

import java.util.ArrayList;

public class InfoBecasActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recyclerBecas;
    ArrayList<InfoBecas> mInfoBecas;
    InfoBecasAdapter mInfoBecasAdapter;
    ImageView mIconBecas, imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_becas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadDataRecycler();

    }

    private void loadViews() {
        recyclerBecas = findViewById(R.id.recyclerBecas);
        mIconBecas = findViewById(R.id.imgvIconB);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    private void loadDataRecycler() {
        mInfoBecas = new ArrayList<>();

        mInfoBecas.add(new InfoBecas(0, R.drawable.ic_becas, "Beca Comedor", "Servicio de almuerzo para los estudiantes de la UNSE, tiene por finalidad cubrir una necesidad inmediata y a la vez fomentar un ambiente de camaradería, solidaridad y respeto entre los estudiantes.", "02/02/2020", "30/03/2020", "pdf_comedor"));
        mInfoBecas.add(new InfoBecas(0, R.drawable.ic_becas, "Beca Residencia", "Servicio de almuerzo para los estudiantes de la UNSE, tiene por finalidad cubrir una necesidad inmediata y a la vez fomentar un ambiente de camaradería, solidaridad y respeto entre los estudiantes.", "02/02/2020", "30/03/2020", "pdf_comedor"));
        mInfoBecas.add(new InfoBecas(0, R.drawable.ic_becas, "Beca Ayuda Económica", "Servicio de almuerzo para los estudiantes de la UNSE, tiene por finalidad cubrir una necesidad inmediata y a la vez fomentar un ambiente de camaradería, solidaridad y respeto entre los estudiantes.", "02/02/2020", "30/03/2020", "pdf_comedor"));
        mInfoBecas.add(new InfoBecas(0, R.drawable.ic_becas, "Beca Movilidad", "Servicio de almuerzo para los estudiantes de la UNSE, tiene por finalidad cubrir una necesidad inmediata y a la vez fomentar un ambiente de camaradería, solidaridad y respeto entre los estudiantes.", "02/02/2020", "30/03/2020", "pdf_comedor"));

        mInfoBecasAdapter = new InfoBecasAdapter(mInfoBecas, getApplicationContext());
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        recyclerBecas.setNestedScrollingEnabled(true);
        recyclerBecas.setLayoutManager(mLayoutManager);
        recyclerBecas.setAdapter(mInfoBecasAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerBecas);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), PerfilBecasActivity.class);
                i.putExtra(Utils.BECA_NAME, mInfoBecas.get(position));
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

}
