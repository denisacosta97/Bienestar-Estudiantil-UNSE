package com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.UsuariosAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import java.util.ArrayList;

public class GestionChoferActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerChofer;
    ArrayList<Usuario> mUsuarios;
    ArrayList<Profesor> mProfesors;
    UsuariosAdapter mUsuariosAdapter;
    ImageView imgIcono;
    DialogoProcesamiento dialog;
    TextView txtEstado;
    Button btnOn, btnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_chofer);

        setToolbar();

        loadViews();

        loadListener();

        loadDataRecycler();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de chofer");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnOn.setOnClickListener(this);
        btnOff.setOnClickListener(this);

    }

    private void loadDataRecycler() {
        mProfesors = new ArrayList<>();
        mUsuarios = new ArrayList<>();

        mUsuariosAdapter = new UsuariosAdapter(mUsuarios, getApplicationContext(),  Utils.TIPO_ESTUDIANTE);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mRecyclerChofer.setNestedScrollingEnabled(true);
        mRecyclerChofer.setLayoutManager(mLayoutManager);
        mRecyclerChofer.setAdapter(mUsuariosAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerChofer);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), PerfilChoferActivity.class);
                i.putExtra(Utils.USER_NAME, mUsuarios.get(position));
                startActivity(i);
            }
        });

    }

    private void loadViews() {
        mRecyclerChofer = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);

        txtEstado = findViewById(R.id.txtEstado);
        btnOn = findViewById(R.id.btnOn);
        btnOff = findViewById(R.id.btnOff);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnOn:
                txtEstado.setText("En servicio");
                txtEstado.setTextColor(getResources().getColor(R.color.colorGreen));
                btnOff.getBackground().setColorFilter(Color.parseColor("#D32F2F"), PorterDuff.Mode.SRC_OVER);
                btnOn.getBackground().setColorFilter(Color.parseColor("#E7E7E7"), PorterDuff.Mode.SRC_OVER);
                break;
            case R.id.btnOff:
                txtEstado.setText("Fuera de servicio");
                txtEstado.setTextColor(getResources().getColor(R.color.colorGreyDark));
                btnOff.getBackground().setColorFilter(Color.parseColor("#E7E7E7"), PorterDuff.Mode.SRC_OVER);
                btnOn.getBackground().setColorFilter(Color.parseColor("#32AC37"), PorterDuff.Mode.SRC_OVER);
                break;
        }
    }

}
