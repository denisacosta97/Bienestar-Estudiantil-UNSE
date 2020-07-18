package com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.Chofer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unse.bienestarestudiantil.BuildConfig;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.Punto;
import com.unse.bienestarestudiantil.Modelos.Servicio;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS;

public class NuevoServicioActivity extends AppCompatActivity implements
        View.OnClickListener, OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        ResultCallback<Status> {


    private static final String TAG = "jejee";
    ImageView imgIcono;
    TextView txtEstado, txtPatente, txtServicio, txtHoraInicio, txtHoraFin, txtTime, txtPosition;
    Button btnOn, btnOff;
    GoogleMap mGoogleMap;
    Servicio mServicio;
    Handler mHandler;
    Runnable mRunnable;
    Punto mPunto;
    DialogoProcesamiento dialog;
    boolean isService = true;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private Location mLastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getParcelableExtra(Utils.SERVICIO) != null) {
            mServicio = getIntent().getParcelableExtra(Utils.SERVICIO);
        }
        if (getIntent().getParcelableExtra(Utils.PUNTO) != null) {
            mPunto = getIntent().getParcelableExtra(Utils.PUNTO);
        }
        if (mServicio != null) {

            setContentView(R.layout.activity_mi_servicio);

            setToolbar();

            loadViews();

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null)
                mapFragment.getMapAsync(this);

            loadData();

            loadListener();

            buildGoogleApiClient();
            // Crear configuración de peticiones
            createLocationRequest();

            // Crear opciones de peticiones
            buildLocationSettingsRequest();

            // Verificar ajustes de ubicación actuales
            checkLocationSettings();
        } else {
            Utils.showToast(getApplicationContext(), getString(R.string.servicioError));
            finish();
        }
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText(
                Utils.getBirthday(
                        Utils.getFechaDateWithHour(mServicio.getFechaInicio())));

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnOn.setOnClickListener(this);
        btnOff.setOnClickListener(this);

    }

    private void loadData() {
        if (mServicio.getEstado() == 2) {
            updateButton(true);
        }
        txtPatente.setText(mServicio.getPatente());
        txtHoraInicio.setText(Utils.getHora(Utils.getFechaDateWithHour(mServicio.getFechaInicio())));
        if (mServicio.getFechaFin().equals("null")) {
            txtHoraFin.setText("??:??");
        }
        loadChronometer();
    }

    private void loadChronometer() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                Date horaInicio = Utils.getFechaDateWithHour(mServicio.getFechaInicio());
                Date horaActual = new Date(System.currentTimeMillis());
                if (!horaInicio.after(horaActual)) {
                    long diff = horaActual.getTime() - horaInicio.getTime();
                    long horas = diff / (60 * 60 * 1000) % 24;
                    long minutes = diff / (60 * 1000) % 60;
                    long segundos = diff / 1000 % 60;
                    String tiempo = String.format("%02d:%02d:%02d", horas, minutes, segundos);
                    txtTime.setText(tiempo);
                } else {
                    txtTime.setText("ERROR DE TIEMPO");
                }
                mHandler.postDelayed(this, 1000);
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isService) {
            mHandler = new Handler();
            mHandler.post(mRunnable);
        }
    }

    private void loadViews() {
        txtPosition = findViewById(R.id.txtPosition);
        imgIcono = findViewById(R.id.imgFlecha);
        txtHoraFin = findViewById(R.id.txtFin);
        txtHoraInicio = findViewById(R.id.txtInicio);
        txtServicio = findViewById(R.id.txtServicios);
        txtPatente = findViewById(R.id.txtPatente);
        txtTime = findViewById(R.id.txtTime);
        txtEstado = findViewById(R.id.txtEstado);
        btnOn = findViewById(R.id.btnOn);
        btnOff = findViewById(R.id.btnOff);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnOn:
                updateButton(true);
                break;
            case R.id.btnOff:
                openDialogAdvertencia();
                break;
        }
    }

    private void openDialogAdvertencia() {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(R.string.advertencia))
                .setDescripcion(getString(R.string.servicioFinalizar))
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {
                        finalizarServicio();
                    }

                    @Override
                    public void no() {

                    }
                })
                .setIcono(R.drawable.ic_advertencia)
                .setTipo(DialogoGeneral.TIPO_SI_NO);
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getSupportFragmentManager(), "dialog_ad");
    }

    private void finalizarServicio() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&is=%s", Utils.URL_FINALIZAR_SERVICIO, key,
                idLocal, mServicio.getIdServicio());
        StringRequest requestImage = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
                dialog.dismiss();
            }
        });
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(requestImage);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    updateButton(false);
                    if (mHandler != null) {
                        mHandler.removeCallbacks(mRunnable);
                    }
                    btnOff.setEnabled(false);
                    btnOn.getBackground().setColorFilter(Color.parseColor("#E7E7E7"), PorterDuff.Mode.SRC_OVER);

                    btnOn.setEnabled(false);
                    stopLocationUpdates();
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.serviciosNoHay));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    public void updateButton(boolean b) {
        isService = b;
        txtEstado.setText(b ? "EN SERVICIO" : "FINALIZADO");
        txtEstado.setTextColor(getResources().getColor(b ? R.color.colorGreen : R.color.colorGreyDark));
        btnOff.getBackground().setColorFilter(Color.parseColor(b ? "#D32F2F" : "#E7E7E7"), PorterDuff.Mode.SRC_OVER);
        btnOn.getBackground().setColorFilter(Color.parseColor(b ? "#E7E7E7" : "#32AC37"), PorterDuff.Mode.SRC_OVER);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        loadPunto();

    }

    private void loadPunto() {
        //mGoogleMap.clear();
        if (mPunto != null) {
            LatLng latLng = new LatLng(mPunto.getLatitud(), mPunto.getLongitud());
            mGoogleMap.addMarker(new MarkerOptions().position(latLng));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            txtPosition.setText(String.format("Última actualización: %s",
                    Utils.getFechaOrder(
                            Utils.getFechaDateWithHour(mPunto.getFechaRecepcion()))));

        } else {
            LatLng latLng = new LatLng(-27.8013843811806, -64.25174295902252);
            mGoogleMap.addMarker(new MarkerOptions().position(latLng));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            txtPosition.setText("Última actualización: NO FECHA");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Utils.REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Utils.showToast(getApplicationContext(),
                                "El usuario permitió el cambio de ajustes de ubicación.");
                        processLastLocation();
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Utils.showToast(getApplicationContext(),
                                "El usuario no permitió el cambio de ajustes de ubicación");
                        break;
                }
                break;
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest()
                .setInterval(Utils.UPDATE_INTERVAL)
                //.setFastestInterval(UPDATE_FASTEST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest)
                .setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();
    }

    private void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient, mLocationSettingsRequest
                );

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                Status status = result.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        if (isLocationPermissionGranted())
                            startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(
                                    NuevoServicioActivity.this,
                                    Utils.REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Sin operaciones
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;

                }
            }
        });
    }


    private void updateLocationUI() {
        if (mPunto == null)
            mPunto = new Punto();
        String fecha = Utils.getFechaName(new Date(System.currentTimeMillis()));
        mPunto.setFechaRecepcion(fecha);
        mPunto.setLatitud(mLastLocation.getLatitude());
        mPunto.setLongitud(mLastLocation.getLongitude());
        loadPunto();
    }


    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi
                .removeLocationUpdates(mGoogleApiClient, this);
    }


    private void getLastLocation() {
        if (isLocationPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
    }

    private void processLastLocation() {
        getLastLocation();
        if (mLastLocation != null) {
            updateLocationUI();
        }
    }

    private void startLocationUpdates() {
        if (isLocationPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        } else {
            manageDeniedPermission();
        }
    }

    private void manageDeniedPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            //Abrir configuracion
            Intent intent = new Intent();
            intent.setAction(ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
            intent.setData(uri);
            startActivityForResult(intent, Utils.REQUEST_GROUP_PERMISSIONS_LOCATION);
            Utils.showToast(getApplicationContext(), "Por favor, autoriza el permiso");

        } else {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Utils.REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Utils.REQUEST_GROUP_PERMISSIONS_LOCATION:
                switch (grantResults[0]) {
                    case PackageManager.PERMISSION_GRANTED:
                        processLastLocation();
                        startLocationUpdates();
                        break;
                    case PackageManager.PERMISSION_DENIED:
                        stopLocationUpdates();
                        Utils.showToast(getApplicationContext(), "Por favor, concede el permiso");
                        break;
                }
                break;
        }
    }

    private boolean isLocationPermissionGranted() {
        int permission = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        // Obtenemos la última ubicación al ser la primera vez
        processLastLocation();
        // Iniciamos las actualizaciones de ubicación
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, String.format("Nueva ubicación: (%s, %s)",
                location.getLatitude(), location.getLongitude()));
        mLastLocation = location;
        updateLocationUI();
    }


    @Override
    public void onResult(@NonNull Status status) {
    }


}
