package com.unse.bienestarestudiantil.Vistas.Activities.Transporte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Paradas;
import com.unse.bienestarestudiantil.Modelos.Punto;
import com.unse.bienestarestudiantil.Modelos.Recorrido;
import com.unse.bienestarestudiantil.Modelos.Servicio;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.GestionRecorridosActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.PerfilRecorridoActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.RecorridoAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class RecorridoRealActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap map;
    private ArrayList<Marker> listMarker;
    private Punto mPuntos;
    private Servicio mServicio;
    private SupportMapFragment mapFragment;
    DialogoProcesamiento dialog;
    ImageView imgIcono;
    TextView txtServicio, txtInicio, txtChofer, txtPatente, txtHoraAct;
    TimerTask mTimerTask;
    Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorrido_real);

        if (getIntent().getParcelableExtra(Utils.PUNTO) != null) {
            mPuntos = getIntent().getParcelableExtra(Utils.PUNTO);
        }

        if (getIntent().getParcelableExtra(Utils.SERVICIO) != null) {
            mServicio = getIntent().getParcelableExtra(Utils.SERVICIO);
        }

        loadViews();

        loadData();

        loadListener();

        setToolbar();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText(mServicio.getDescripcio());
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    private void loadData() {
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        txtServicio.setText(mServicio.getDescripcio());
        txtInicio.setText(Utils.getHora(Utils.getFechaDateWithHour(mServicio.getFechaInicio())));
        String name = mServicio.getNombre() + " " + mServicio.getApellido();
        txtChofer.setText(name);
        txtPatente.setText(mServicio.getPatente());
        txtHoraAct.setText(Utils.getHora(Utils.getFechaDateWithHour(mPuntos.getFechaRecepcion())));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                getLastPoint(mServicio.getIdServicio());
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTimerTask,0, Utils.UPDATE_INTERVAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mTimer != null){
            mTimer.cancel();
        }
    }

    private void getLastPoint(int idServicio) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&is=%s", Utils.URL_ULTIMO_SERVICIO, id, key, idServicio);
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
                Utils.showCustomToast(RecorridoRealActivity.this, getApplicationContext(),
                        getString(R.string.servidorOff), R.drawable.ic_error);
                dialog.dismiss();

            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showCustomToast(RecorridoRealActivity.this, getApplicationContext(),
                            getString(R.string.errorInternoAdmin), R.drawable.ic_error);
                    break;
                case 1:
                    //Exito
                    loadInfo(jsonObject);
                    break;
                case 2:
//                    Utils.showCustomToast(GestionRecorridosActivity.this, getApplicationContext(),
//                            getString(R.string.noReservas), R.drawable.ic_error);
                    break;
                case 4:
                    Utils.showCustomToast(RecorridoRealActivity.this, getApplicationContext(),
                            getString(R.string.camposInvalidos), R.drawable.ic_error);
                    break;
                case 3:
                    Utils.showCustomToast(RecorridoRealActivity.this, getApplicationContext(),
                            getString(R.string.tokenInvalido), R.drawable.ic_error);
                    break;
                case 100:
                    //No autorizado
                    Utils.showCustomToast(RecorridoRealActivity.this, getApplicationContext(),
                            getString(R.string.tokenInexistente), R.drawable.ic_error);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showCustomToast(RecorridoRealActivity.this, getApplicationContext(),
                    getString(R.string.errorInternoAdmin), R.drawable.ic_error);
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                if (jsonObject.get("mensaje") instanceof Boolean){
                    Utils.showCustomToast(RecorridoRealActivity.this, getApplicationContext(),
                            getString(R.string.errorLocation), R.drawable.ic_error);
                } else {
                    JSONObject punto = jsonObject.getJSONObject("mensaje");
                    Punto punto1 = Punto.mapper(punto, Punto.COMPLETE);
                    loadPoint(punto1);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadPoint(Punto punto1) {
        int height = 140;
        int width = 140;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_parada);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        LatLng latLng = new LatLng(punto1.getLatitud(), punto1.getLongitud());
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("Aquí estoy").
                        icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        map.addMarker(markerOptions);
        txtHoraAct.setText(Utils.getHora(Utils.getFechaDateWithHour(punto1.getFechaRecepcion())));
    }


    private void loadListener() {
        imgIcono.setOnClickListener(this);
    }

    private void loadViews() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        imgIcono = findViewById(R.id.imgFlecha);

        txtServicio = findViewById(R.id.txtServicios);
        txtInicio = findViewById(R.id.txtInicio);
        txtChofer = findViewById(R.id.txtChofer);
        txtPatente = findViewById(R.id.txtPatente);
        txtHoraAct = findViewById(R.id.txtHoraAct);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 20) {
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_night_mode));
        } else if (timeOfDay < 8) {
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_night_mode));
        }

        Criteria criteria = new Criteria();
        LocationManager locationManager = (LocationManager) Objects.requireNonNull(getApplicationContext()).getSystemService(LOCATION_SERVICE);
        if (locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            map.setMyLocationEnabled(true);
        }
        String provider = locationManager != null ? locationManager.getBestProvider(criteria, true) : null;
        LatLng sde = null;
        if (provider != null) {
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                sde = new LatLng(location.getLatitude(), location.getLongitude());

            } else {
                sde = new LatLng(-27.791156, -64.250532);
            }
        } else {
            sde = new LatLng(-27.791156, -64.250532);
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sde, 15));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }

    }
}