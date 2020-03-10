package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.unse.bienestarestudiantil.Herramientas.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.CredencialActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.GestionArchivosActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.InfoAlumnoActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Inicio.LoginActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogYesNoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.YesNoDialogListener;

import java.util.ArrayList;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {

    CardView mHistoriaC, mCredenciales, mInfo, mMensajes, mConfig, mCerrarS;
    ImageView btnBack;

    ArrayList<Opciones> mList;
    OpcionesAdapter mAdapter;
    RecyclerView mRecyclerViewFunciones;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadData();

        loadListener();
    }

    private void loadData() {
        mList = new ArrayList<>();
        mList.add(new Opciones(1, "Mis Datos", R.drawable.ic_becas, R.color.colorWhite, R.color.colorTextDefault,12));
        mList.add(new Opciones(3, "Credenciales", R.drawable.ic_deportes, R.color.colorWhite, R.color.colorTextDefault,12));
        mList.add(new Opciones(4, "Gestión de Archivos", R.drawable.ic_pdf, R.color.colorWhite, R.color.colorTextDefault,12));
        mList.add(new Opciones(5, "¿Mensajes?", R.drawable.ic_becas, R.color.colorWhite, R.color.colorTextDefault,12));
        mList.add(new Opciones(6, "Cerrar Sesión", R.drawable.ic_apagar, R.color.colorWhite, R.color.colorTextDefault, 12));
        mList.add(new Opciones(2, "Configuraciones", R.drawable.ic_becas, R.color.colorWhite, R.color.colorTextDefault,12));


        mList.add(new Opciones(1, "Inscripciones", R.drawable.ic_deportes, R.color.colorWhite, R.color.colorTextDefault,12));
        mAdapter = new OpcionesAdapter(mList, getApplicationContext());

        mLayoutManager = new GridLayoutManager(getApplicationContext(),3);

        mRecyclerViewFunciones.setLayoutManager(mLayoutManager);
        mRecyclerViewFunciones.setHasFixedSize(true);
        mRecyclerViewFunciones.setAdapter(mAdapter);

    }


    private void loadListener() {
        mHistoriaC.setOnClickListener(this);
        mCredenciales.setOnClickListener(this);
        mInfo.setOnClickListener(this);
        mMensajes.setOnClickListener(this);
        mConfig.setOnClickListener(this);
        mCerrarS.setOnClickListener(this);

        btnBack.setOnClickListener(this);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerViewFunciones);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Utils.showToast(getApplicationContext(), "Tocaste: "+mList.get(position).getTitulo());
                procesarClick(parent, view, position, (int) id);
            }
        });
    }

    private void procesarClick(RecyclerView parent, View view, int position, int id) {
        switch (id){
            case 6:
                logout();
                break;
            case 2:
                startActivity(new Intent(getApplicationContext(), ConfiguracionesActivities.class));
                break;

        }
    }

    private void logout() {
        final DialogYesNoGeneral dialog = new DialogYesNoGeneral();
        YesNoDialogListener listener = new YesNoDialogListener() {
            @Override
            public void yes() {
                dialog.dismiss();
                finishAffinity();
                PreferenceManager manager = new PreferenceManager(getApplicationContext());
                manager.setValue(Utils.IS_LOGIN, false);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void no() {
                dialog.dismiss();
            }
        };

        dialog.loadData("¡Advertencia!", "¿Seguro que desea cerrar sesión?", listener, getApplicationContext());
        dialog.show(getSupportFragmentManager(), "dialog_yesno");
    }

    private void loadViews() {
        mHistoriaC = findViewById(R.id.btnHistoriac);
        mCredenciales = findViewById(R.id.btnCredenciales);
        mInfo = findViewById(R.id.btnInfo);
        mMensajes = findViewById(R.id.btnMensajes);
        mConfig = findViewById(R.id.btnConfig);
        mCerrarS = findViewById(R.id.btnCerrarses);

        btnBack = findViewById(R.id.btnBack);
        mRecyclerViewFunciones = findViewById(R.id.recycler);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnHistoriac:
                break;
            case R.id.btnCredenciales:
                startActivity(new Intent(PerfilActivity.this, CredencialActivity.class));
                break;
            case R.id.btnInfo:
                startActivity(new Intent(PerfilActivity.this, InfoAlumnoActivity.class));
                break;
            case R.id.btnMensajes:
                break;
            case R.id.btnConfig:
                break;
            case R.id.btnCerrarses:
                startActivity(new Intent(PerfilActivity.this, GestionArchivosActivity.class));
                break;
        }
    }
}
