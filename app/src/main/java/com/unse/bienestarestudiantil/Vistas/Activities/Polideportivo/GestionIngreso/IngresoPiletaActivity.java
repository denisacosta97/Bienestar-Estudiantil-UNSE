package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionIngreso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.ObservadorPrecio;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.PiletaAcompañante;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.AcompañanteAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.View.GONE;

public class IngresoPiletaActivity extends AppCompatActivity implements android.view.View.OnClickListener, ObservadorPrecio {

    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<PiletaAcompañante> mList;
    AcompañanteAdapter mAdapter;
    Button btnGuardar, btnDia, btnSemana, btnMes, btnAñadir;
    LinearLayout mLayout;
    EditText edtDNI;
    TextView txtTotal;
    Spinner mSpinnerCategorias;
    CheckBox checkFamilia;
    DialogoProcesamiento dialog;
    String[] categorias = {"Seleccione una opción...", "Alumno", "Profesor", "Nodocente", "Egresado",
            "Particular", "Afiliado", "Jubilado", "Otro"};

    int categoriaSelect = 0, tipo = 0;
    float total = 0f;
    float precioTotal = 0f;
    boolean isFamily = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_pileta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadData();

        loadListener();

        updatePrecio(0);

    }

    private void loadData() {
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, R.layout.style_spinner, categorias);
        dataAdapter2.setDropDownViewResource(R.layout.style_spinner);
        mSpinnerCategorias.setAdapter(dataAdapter2);

        mList = new ArrayList<>();

        mAdapter = new AcompañanteAdapter(mList, getApplicationContext(), this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        registerForContextMenu(mRecyclerView);

        checkFamilia.setVisibility(GONE);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            //position = ((AcompañanteAdapter)getAdapter()).getPosition();
            position = mAdapter.getPosition();
        } catch (Exception e) {
            Log.d("je", e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        mList.get(position).setCategoria(item.getItemId());
        mAdapter.update();
        return super.onContextItemSelected(item);
    }

    private void loadListener() {
        btnAñadir.setOnClickListener(this);
        btnDia.setOnClickListener(this);
        btnMes.setOnClickListener(this);
        btnSemana.setOnClickListener(this);
        checkFamilia.setOnClickListener(this);

        mSpinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                categoriaSelect = position;
                updateButtons(R.id.btnDia);
                tipo = 0;
                float precioTotal = calcularPrecio(categoriaSelect);
                updatePrecio(precioTotal);
                mAdapter.setCategoriaGral(categoriaSelect-1);
                mAdapter.update();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView parent, View view, int position, long id) {
                mList.remove(position);
                mAdapter.notifyDataSetChanged();
                updatePrecio(calcularPrecio(categoriaSelect));
                return false;
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

    }

    private void updatePrecio(float precioTotal) {
        float precio = 0f;
        for (PiletaAcompañante pileta : mList) {
            precio = precio + pileta.getPrecioTotal();
        }
        precio = precio + precioTotal;
        total = precio;
        txtTotal.setText(String.format("$ %s", precio));
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnDia = findViewById(R.id.btnDia);
        btnSemana = findViewById(R.id.btnSemana);
        btnMes = findViewById(R.id.btnMes);
        edtDNI = findViewById(R.id.edtDNI);
        txtTotal = findViewById(R.id.txtTotal);
        mSpinnerCategorias = findViewById(R.id.spineer);
        btnAñadir = findViewById(R.id.btnAñadir);
        mLayout = findViewById(R.id.layoutAcompañantes);
        checkFamilia = findViewById(R.id.checkFamiliar);
    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.imgFlecha).setVisibility(android.view.View.VISIBLE);
        findViewById(R.id.imgFlecha).setOnClickListener(this);
        ((TextView) findViewById(R.id.txtTitulo)).setText("Ingreso Nuevo");
    }

    private float calcularPrecio(int categ) {

        switch (categ) {
            case 6:
            case 0:
                //Afiliados
                precioTotal = 0;
                break;
            case 1: //Estudiante
            case 7: //Jubilado
                if (tipo == 0) {
                    precioTotal = 50;
                } else if (tipo == 1) {
                    precioTotal = 250;
                } else {
                    precioTotal = 1000;
                }
                break;
            case 5:
                //Particular
                if (tipo == 0) {
                    precioTotal = 200;
                } else if (tipo == 1) {
                    precioTotal = 1000;
                } else {
                    precioTotal = 3000;
                }
                break;
            default:
                //Docentes, Nodocentes y Egresados
                if (tipo == 0) {
                    precioTotal = 100;
                } else if (tipo == 1) {
                    precioTotal = 500;
                } else {
                    if (isFamily)
                        precioTotal = 2000;
                    else
                        precioTotal = 1000;
                }
                break;
        }
        return precioTotal;
    }

    @Override
    public void onClick(View v) {
        float precioTotal = 0;
        switch (v.getId()) {
            case R.id.checkFamiliar:
                if (checkFamilia.getVisibility() == android.view.View.VISIBLE){
                    if (isFamily){
                        checkFamilia.setChecked(false);
                        isFamily = false;
                    }else{
                        checkFamilia.setChecked(true);
                        isFamily = true;
                    }
                    updatePrecio(calcularPrecio(categoriaSelect));
                }
                break;
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnAñadir:
                mList.add(new PiletaAcompañante(0, 1, 0f));
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btnDia:
                tipo = 0;
                precioTotal = calcularPrecio(categoriaSelect);
                updatePrecio(precioTotal);
                updateButtons(v.getId());
                break;
            case R.id.btnSemana:
                tipo = 1;
                precioTotal = calcularPrecio(categoriaSelect);
                updatePrecio(precioTotal);
                updateButtons(v.getId());
                break;
            case R.id.btnMes:
                tipo = 2;
                precioTotal = calcularPrecio(categoriaSelect);
                updatePrecio(precioTotal);
                updateButtons(v.getId());
                break;
        }
    }

    public void updateButtons(int id) {
        switch (id) {
            case R.id.btnMes:
                btnMes.setBackgroundResource(R.drawable.button_border_select);
                btnDia.setBackgroundResource(R.drawable.button_border);
                btnSemana.setBackgroundResource(R.drawable.button_border);
                if (categoriaSelect == 2 || categoriaSelect ==3  || categoriaSelect ==6){
                    checkFamilia.setVisibility(android.view.View.VISIBLE);
                }else
                    checkFamilia.setVisibility(GONE);
                break;
            case R.id.btnDia:
                btnDia.setBackgroundResource(R.drawable.button_border_select);
                btnMes.setBackgroundResource(R.drawable.button_border);
                btnSemana.setBackgroundResource(R.drawable.button_border);
                checkFamilia.setVisibility(GONE);
                break;
            case R.id.btnSemana:
                btnSemana.setBackgroundResource(R.drawable.button_border_select);
                btnDia.setBackgroundResource(R.drawable.button_border);
                btnMes.setBackgroundResource(R.drawable.button_border);
                checkFamilia.setVisibility(GONE);
                break;
        }

        if (id != R.id.btnDia) {
            mLayout.setVisibility(GONE);
            assert  mList != null;
            mList.clear();
            mAdapter.notifyDataSetChanged();
            updatePrecio(calcularPrecio(categoriaSelect));
        } else
            mLayout.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void actualizarPrecio() {
        updatePrecio(calcularPrecio(categoriaSelect));
    }

    private void save() {
        Validador validador = new Validador(getApplicationContext());

        String dni = edtDNI.getText().toString().trim();

        if (validador.validarDNI(edtDNI)) {
            sendServer(dni, 1);

        } else
            Toast.makeText(getApplicationContext(), R.string.camposInvalidos, Toast.LENGTH_SHORT).show();
    }

    public void sendServer(String dni, int cant) {
        String datos = "";
        int cantid = cant;
        for (PiletaAcompañante piletaAcompanante : mList){
            datos = datos.concat(String.format("&apt[]=%s&acn[]=%s&act[]=%s", piletaAcompanante.getPrecioTotal(),
                    piletaAcompanante.getCantidad(), piletaAcompanante.getCategoria()));
            cantid = cantid + piletaAcompanante.getCantidad();
        }
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&iu=%s&ie=%s&ct=%s&cn=%s&p=%s&pt=%s%s",
                Utils.URL_INGRESO_PILE, key, idLocal, dni, idLocal, categoriaSelect, cantid, precioTotal, total, datos);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), R.string.servidorOff, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }


    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Toast.makeText(getApplicationContext(), R.string.errorInternoAdmin, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //Exito
                    Toast.makeText(getApplicationContext(), "Ingreso registrado", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(), R.string.camposInvalidos, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), R.string.tokenInvalido, Toast.LENGTH_SHORT).show();
                    break;
                case 100:
                    Toast.makeText(getApplicationContext(), R.string.tokenInexistente, Toast.LENGTH_SHORT).show();
                    //No autorizado
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.errorInternoAdmin, Toast.LENGTH_SHORT).show();
        }
    }

}