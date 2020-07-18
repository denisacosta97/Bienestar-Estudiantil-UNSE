package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;

import java.util.ArrayList;

public class TiposCredencialesActivity extends AppCompatActivity implements View.OnClickListener {

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

        mList.add(new Opciones(true,LinearLayout.HORIZONTAL, 1, "CREDENCIALES BECAS", R.drawable.ic_becas, R.color.colorWhite, R.color.colorTextDefault));
        mList.add(new Opciones(true,LinearLayout.HORIZONTAL, 2, "CREDENCIALES DEPORTES", R.drawable.ic_zapatilla, R.color.colorWhite, R.color.colorTextDefault));
        mList.add(new Opciones(true,LinearLayout.HORIZONTAL, 3, "CREDENCIALES TORNEOS", R.drawable.ic_trofeo, R.color.colorWhite, R.color.colorTextDefault));
        mList.add(new Opciones(true,LinearLayout.HORIZONTAL, 4, "CREDENCIAL SOCIO", R.drawable.ic_socio, R.color.colorWhite, R.color.colorTextDefault));

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new OpcionesAdapter(mList, getApplicationContext(),1);

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


