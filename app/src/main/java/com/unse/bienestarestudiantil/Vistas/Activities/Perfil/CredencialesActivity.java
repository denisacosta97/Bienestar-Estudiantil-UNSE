package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;

import java.util.ArrayList;

public class CredencialesActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Opciones> mList;
    OpcionesAdapter mAdapter;
    ImageView btnBack;

    TextView mDeporte, mApellido, mNombre, mFacultad, mLegajo, mNombreEquipo, mAnio;
    ImageView mFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credencial);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        setToolbar();

        loadData();

        loadListener();

      /*  mDeporte = findViewById(R.id.txtDeporte);
        mApellido = findViewById(R.id.txtApellido);
        mNombre = findViewById(R.id.txtNombre);
        mFacultad = findViewById(R.id.txtFacultad);
        mLegajo = findViewById(R.id.txtLegajo);
        mNombreEquipo = findViewById(R.id.txtNombreEquipo);
        mAnio = findViewById(R.id.txtAnio);
        mFoto = findViewById(R.id.imgUser);

        mDeporte.setText("Fútbol Sala Masculino");
        mApellido.setText("Ledesma");
        mNombre.setText("Cristian Santiago");
        mFacultad.setText("FCEyT");
        mLegajo.setText("207/15");
        mNombreEquipo.setText("Santiago Lawn Tennis Club");
        mAnio.setText("2019");
        mFoto.setImageResource(R.drawable.imgdeportes);*/

    }

    private void loadListener() {
        btnBack.setOnClickListener(this);
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                procesarClick(parent, view, position, id);
            }
        });
    }

    private void procesarClick(RecyclerView parent, View view, int position, long id) {
        Intent intent = null;
        switch ((int) id){
            case 1:
                intent = new Intent(getApplicationContext(), ListaCredencialesActivity.class);
                intent.putExtra(Utils.TIPO_CREDENCIAL, 1);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(getApplicationContext(), ListaCredencialesActivity.class);
                intent.putExtra(Utils.TIPO_CREDENCIAL, 2);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(getApplicationContext(), ListaCredencialesActivity.class);
                intent.putExtra(Utils.TIPO_CREDENCIAL, 3);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(getApplicationContext(), ListaCredencialesActivity.class);
                intent.putExtra(Utils.TIPO_CREDENCIAL, 4);
                startActivity(intent);
                break;
        }
    }

    private void loadData() {
        mList = new ArrayList<>();

        mList.add(new Opciones(LinearLayout.HORIZONTAL, 1, "CREDENCIALES BECAS", R.drawable.ic_becas, R.color.colorWhite, R.color.colorTextDefault));
        mList.add(new Opciones(LinearLayout.HORIZONTAL, 2, "CREDENCIALES DEPORTES", R.drawable.ic_zapatilla, R.color.colorWhite, R.color.colorTextDefault));
        mList.add(new Opciones(LinearLayout.HORIZONTAL, 3, "CREDENCIALES TORNEOS", R.drawable.ic_trofeo, R.color.colorWhite, R.color.colorTextDefault));
        mList.add(new Opciones(LinearLayout.HORIZONTAL, 4, "CREDENCIAL SOCIO", R.drawable.ic_socio, R.color.colorWhite, R.color.colorTextDefault));

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new OpcionesAdapter(mList, getApplicationContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        btnBack = findViewById(R.id.imgFlecha);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Credenciales");
        Utils.changeColorDrawable(btnBack, getApplicationContext(), R.color.colorPrimaryDark);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }

    }
}


