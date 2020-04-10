package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionIngreso;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.UsuariosAdapters;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoIngresoPolideportivo;

import java.util.ArrayList;

public class IngresoPolideportivoActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    UsuariosAdapters mAdapter;
    ArrayList<Usuario> mUsuarios;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_polideportivo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadData();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));

    }


    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Utils.showToast(getApplicationContext(), "Item: "+mUsuarios.get(position).getNombre());
                DialogoIngresoPolideportivo dialogoIngresoPolideportivo = new DialogoIngresoPolideportivo();
                dialogoIngresoPolideportivo.loadDataFromUser(mUsuarios.get(position));
                dialogoIngresoPolideportivo.show(getSupportFragmentManager(),"dialog_ingreso");
            }
        });
        imgIcono.setOnClickListener(this);
    }

    private void loadData() {
        mUsuarios = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new UsuariosAdapters(mUsuarios, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
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
