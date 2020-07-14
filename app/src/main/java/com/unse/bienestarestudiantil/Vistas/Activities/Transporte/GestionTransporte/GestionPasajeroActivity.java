package com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.UsuariosAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GestionPasajeroActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerUsuarios;
    ArrayList<Usuario> mUsuarios;
    ArrayList<Profesor> mProfesors;
    ArrayList<Deporte> mDeportes;
    UsuariosAdapter mUsuariosAdapter;
    ImageView imgIcono;
    DialogoProcesamiento dialog;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_pasajero);

        setToolbar();

        loadViews();

        loadListener();

        loadDataRecycler();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de profesores");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscar(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadDataRecycler() {
        mProfesors = new ArrayList<>();
        mUsuarios = new ArrayList<>();
        mDeportes = new ArrayList<>();

        mUsuariosAdapter = new UsuariosAdapter(mUsuarios, getApplicationContext(),  Utils.TIPO_ESTUDIANTE);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mRecyclerUsuarios.setNestedScrollingEnabled(true);
        mRecyclerUsuarios.setLayoutManager(mLayoutManager);
        mRecyclerUsuarios.setAdapter(mUsuariosAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerUsuarios);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), PerfilPasajeroActivity.class);
                i.putExtra(Utils.USER_NAME, mUsuarios.get(position));
                startActivity(i);
            }
        });

    }

    private void loadViews() {
        mRecyclerUsuarios = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
        mEditText = findViewById(R.id.edtBuscar);
    }

    private void buscar(String txt) {
        Pattern pattern = Pattern.compile(Utils.PATRON_NUMEROS);
        Matcher matcher = pattern.matcher(txt);
        if (matcher.find()) {
            mUsuariosAdapter.filtrar(txt, Utils.LIST_DNI);
            return;
        }
        pattern = Pattern.compile(Utils.PATRON_NOMBRES);
        matcher = pattern.matcher(txt);
        if (matcher.find()) {
            mUsuariosAdapter.filtrar(txt, Utils.LIST_NOMBRE);
        } else {
            mUsuariosAdapter.filtrar(txt, Utils.LIST_RESET);
        }
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
