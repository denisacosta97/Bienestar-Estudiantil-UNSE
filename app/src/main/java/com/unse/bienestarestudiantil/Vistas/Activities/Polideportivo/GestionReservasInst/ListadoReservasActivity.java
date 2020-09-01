package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionReservasInst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.PiletaIngreso;
import com.unse.bienestarestudiantil.Modelos.Reserva;
import com.unse.bienestarestudiantil.Modelos.ReservaEspacio;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionIngreso.ListadoIngresosPiletaActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ListadoIngresosAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ReservaEspacioAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ListadoReservasActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnBuscar;
    EditText edtFecha;
    LinearLayout linlayListado, linlayNo;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ReservaEspacioAdapter adapter;
    ArrayList<ReservaEspacio> reservaEspacios;
    ProgressBar mProgressBar;
    DialogoProcesamiento dialog;
    ImageView imgIcono;
    boolean itsOk = false;
    String mes, dia, anio;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_reservas);
        
        loadView();

        loadData();
        
        loadListener();

        setToolbar();

    }

    private void loadListener() {
        btnBuscar.setOnClickListener(this);
        edtFecha.setOnClickListener(this);
        imgIcono.setOnClickListener(this);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), InfoReservaInstActivity.class);
                i.putExtra(Utils.RESERVA, reservaEspacios.get(position));
                startActivity(i);
            }
        });
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Torneo");
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    private void loadData() {
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mProgressBar.setVisibility(View.GONE);
    }

    private void loadView() {
        btnBuscar = findViewById(R.id.btnBuscar);
        edtFecha = findViewById(R.id.edtFecha);
        linlayListado = findViewById(R.id.linlayListado);
        linlayNo = findViewById(R.id.linlayNo);
        mProgressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);

    }

    private void selectFechaNac() {

        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                anio = String.valueOf(year);
                month = month + 1;
                if (month < 10) {
                    mes = "0" + month;
                } else
                    mes = String.valueOf(month);
                if (day < 10)
                    dia = "0" + day;
                else
                    dia = String.valueOf(day);
                final String selectedDate = year + "-" + mes + "-" + dia;
                edtFecha.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");

    }

    private boolean searchReserva() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBuscar:
                if(!edtFecha.getText().equals("")) {
                    loadInfo();
                }
                else
                    Toast.makeText(this, "Selecciona una fecha", Toast.LENGTH_SHORT).show();
                break;
            case R.id.edtFecha:
                selectFechaNac();
                break;
        }
    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&d=%s&m=%s&a=%s", Utils.URL_RESERVAS_ESPACIOS_FECHA, id, key, dia, mes, anio);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //mProgressBar.setVisibility(View.GONE);
                Utils.showCustomToast(ListadoReservasActivity.this, getApplicationContext(),
                        getString(R.string.servidorOff), R.drawable.ic_error);
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
                    Utils.showCustomToast(ListadoReservasActivity.this, getApplicationContext(),
                            getString(R.string.errorInternoAdmin), R.drawable.ic_error);
                    break;
                case 1:
                    //Exito
                    linlayNo.setVisibility(View.GONE);
                    loadInfo(jsonObject);
                    break;
                case 2:
//                    Utils.showCustomToast(GestionRecorridosActivity.this, getApplicationContext(),
//                            getString(R.string.noReservas), R.drawable.ic_error);
                    break;
                case 4:
                    Utils.showCustomToast(ListadoReservasActivity.this, getApplicationContext(),
                            getString(R.string.camposInvalidos), R.drawable.ic_error);
                    break;
                case 3:
                    Utils.showCustomToast(ListadoReservasActivity.this, getApplicationContext(),
                            getString(R.string.tokenInvalido), R.drawable.ic_error);
                    break;
                case 100:
                    //No autorizado
                    Utils.showCustomToast(ListadoReservasActivity.this, getApplicationContext(),
                            getString(R.string.tokenInexistente), R.drawable.ic_error);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showCustomToast(ListadoReservasActivity.this, getApplicationContext(),
                    getString(R.string.errorInternoAdmin), R.drawable.ic_error);
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                reservaEspacios = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    ReservaEspacio reservaEspacio = ReservaEspacio.mapper(o, 0);
                    reservaEspacios.add(reservaEspacio);
                }
                linlayNo.setVisibility(View.GONE);
                linlayListado.setVisibility(View.VISIBLE);

                Comparator<ReservaEspacio> comparable = new Comparator<ReservaEspacio>() {
                    @Override
                    public int compare(ReservaEspacio o1, ReservaEspacio o2) {
                        DateFormat format = new SimpleDateFormat("yyy-mm-dd");
                        Date date = null, date1 = null;;
                        try {
                            date = format.parse(o1.getFechaEv());
                            date1 = format.parse(o2.getFechaEv());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (date.compareTo(date1) > 0)
                            return 1;
                        else
                            return 0;
                    }
                };
                Collections.sort(reservaEspacios, comparable);
                adapter = new ReservaEspacioAdapter(reservaEspacios, getApplicationContext());
                mRecyclerView.setAdapter(adapter);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}