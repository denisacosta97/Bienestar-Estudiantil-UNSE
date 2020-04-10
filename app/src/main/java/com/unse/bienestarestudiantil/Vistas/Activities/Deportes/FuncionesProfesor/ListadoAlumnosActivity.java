package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.FuncionesProfesor;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.UsuariosAdapter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListadoAlumnosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    UsuariosAdapter mAdapter;
    ArrayList<Usuario> mList;
    ImageView imgIcono, imgBuscador;
    TextInputLayout tilBuscador;
    EditText edtBuscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alumnos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();

        loadData();

        setToolbar();

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    private void loadData() {
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mList = new ArrayList<>();
        //mList.add(new Alumno("","","","2015","as2d","","FCEyT","29/09/2018","23/15", true));

        mAdapter = new UsuariosAdapter(mList, getApplicationContext(), Utils.TIPO_ESTUDIANTE);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Utils.changeColorDrawable(imgBuscador, getApplicationContext(), R.color.colorPrimary);

    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
        imgBuscador = findViewById(R.id.imgBuscar);
        tilBuscador = findViewById(R.id.til_buscar);
        edtBuscar = findViewById(R.id.edtBuscar);
    }

}
