package com.unse.bienestarestudiantil.Vistas.Activities.Ciber.GestionCiber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Impresion;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.PerfilPasajeroActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ColectivoAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ImpresionesAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.UsuariosAdapter;

import java.util.ArrayList;

public class GestionImpresionesActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgIcono;
    EditText mEditText;
    FloatingActionButton fabAgregar;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ImpresionesAdapter impresionesAdapter;
    ArrayList<Impresion> mImpresiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_impresiones);

        setToolbar();

        loadViews();

        loadListener();

        loadDataRecycler();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de impresiones");
    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        fabAgregar.setOnClickListener(this);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
        mEditText = findViewById(R.id.edtBuscar);
        fabAgregar = findViewById(R.id.fabAdd);
    }

    private void loadDataRecycler() {
        mImpresiones = new ArrayList<>();

        impresionesAdapter = new ImpresionesAdapter(mImpresiones, getApplicationContext());
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(impresionesAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), PerfilPasajeroActivity.class);
                i.putExtra(Utils.IMPRESION, mImpresiones.get(position));
                startActivity(i);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.fabAdd:
                startActivity(new Intent(getApplicationContext(), AgregarImpresionActivity.class));
                break;
        }
    }
}