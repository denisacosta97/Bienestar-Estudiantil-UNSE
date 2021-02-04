package com.unse.bienestarestudiantil.Vistas.Activities.Ciber.GestionCiber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickUser;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionIngreso.IngresosHistoricosActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoBuscarUsuario;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GestionIngresosCiberActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mOpciones;
    ImageView imgIcono;
    FloatingActionButton fabAgregar;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_ingresos_ciber);

        setToolbar();

        loadViews();

        loadListener();

        loadData();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de ingresos");
    }


    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                switch ((int) id) {
                    case 102:
                        startActivity(new Intent(getApplicationContext(), IngresosCiberDiaActivity.class));
                        break;
                    case 103:
                        startActivity(new Intent(getApplicationContext(), IngresosCiberHistoricosActivity.class));
                        break;
                }
                Utils.showToast(getApplicationContext(), "Item: " + mOpciones.get(position).getTitulo());
            }
        });
        imgIcono.setOnClickListener(this);
        fabAgregar.setOnClickListener(this);

    }

    private void loadData() {
        mOpciones = new ArrayList<>();
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 102, "Ingresos del día", R.drawable.ic_impresion, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 103, "Ingresos históricos", R.drawable.ic_estadisticas_color, R.color.colorFCEyT));

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OpcionesAdapter(mOpciones, getApplicationContext(), 1);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
        fabAgregar = findViewById(R.id.fabAdd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.fabAdd:
                DialogoBuscarUsuario nuevoIngresoDialog = new DialogoBuscarUsuario(getApplicationContext(),
                        getSupportFragmentManager(), null, new OnClickUser() {
                    @Override
                    public void onUserSelected(int idUsuario) {
                        sendServer(String.valueOf(idUsuario));
                    }
                });
                nuevoIngresoDialog.setNoValid(true);
                nuevoIngresoDialog.show(getSupportFragmentManager(),"dialog_nuevoing");
                break;
        }
    }


    private void sendServer(String dni) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&iu=%s", Utils.URL_REGISTRAR_INGRESO, key, id, dni);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta2(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void procesarRespuesta2(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Toast.makeText(getApplicationContext(), getString(R.string.errorInternoAdmin), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "Usuario agregado", Toast.LENGTH_SHORT).show();
                    //Exito
                    //loadInfo(jsonObject);
                    break;
                case 2:
                case 5:
                    Toast.makeText(getApplicationContext(), "Error interno", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(), getString(R.string.camposInvalidos), Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), getString(R.string.tokenInvalido), Toast.LENGTH_SHORT).show();
                    break;
                case 100:
                    //No autorizado
                    Toast.makeText(getApplicationContext(), getString(R.string.tokenInexistente), Toast.LENGTH_SHORT).show();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.errorInternoAdmin), Toast.LENGTH_SHORT).show();
        }
    }

}