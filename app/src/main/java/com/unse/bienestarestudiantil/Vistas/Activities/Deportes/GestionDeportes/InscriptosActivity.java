package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes;

import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.AlumnosAdapter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InscriptosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    AlumnosAdapter mAdapter;
    ArrayList<Alumno> mList;
    ImageView imgIcono, imgBuscador;
    TextInputLayout tilBuscador;
    EditText edtBuscar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscriptos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadDataRecycler();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Inscriptos");

    }

    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Utils.showToast(getApplicationContext(), "Item: " + mList.get(position).getNombre());
            }
        });
        imgIcono.setOnClickListener(this);

        edtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilBuscador.setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {
                buscar(s.toString());
            }
        });

        imgIcono.setOnClickListener(this);

    }

    private void buscar(String txt) {
        Pattern pattern = Pattern.compile("[0-9]+(\\/|-)[0-9]*");
        Matcher matcher = pattern.matcher(txt);
        if (matcher.find()){
            mAdapter.filtrar(txt, Utils.LIST_LEGAJO);
            return;
        }
        pattern = Pattern.compile("([0-9]+){5,8}");
        matcher = pattern.matcher(txt);
        if (matcher.find()){
            if (txt.length() > 4){
                mAdapter.filtrar(txt, Utils.LIST_DNI);
                return;
            }else{
                mAdapter.filtrar(txt, Utils.LIST_LEGAJO);
                return;
            }
        }
        pattern = Pattern.compile("[a-zA-Z_ ]+");
        matcher = pattern.matcher(txt);
        if (matcher.find()){
            mAdapter.filtrar(txt, Utils.LIST_NOMBRE);
            return;
        } else {
            mAdapter.filtrar(txt, Utils.LIST_RESET);
            return;
        }
    }

    private void loadDataRecycler() {
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mList = new ArrayList<>();
        //mList.add(new Alumno("ELEESEI","ESATAS","207/15","2015","as2d","","FCEyT","29/09/2018","23/15", true));

        mAdapter = new AlumnosAdapter(mList, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Utils.changeColorDrawable(imgBuscador, getApplicationContext(), R.color.colorPrimary);

    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
        fab = findViewById(R.id.fab);
        imgBuscador = findViewById(R.id.imgBuscar);
        tilBuscador = findViewById(R.id.til_buscar);
        edtBuscar = findViewById(R.id.edtBuscar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.fab:

                break;
        }
    }


}
