package com.unse.bienestarestudiantil.Vistas.Activities.Comedor;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.Menu;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoReservaComedor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.helpers.Util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiariaComedorActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgIcono;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mOpciones;
    ProgressBar mProgressBar;
    LinearLayout latAdmin;
    TextView txtMenu, txtTerminarDia, txtRestringirReservas;
    CardView cardInicio, cardNo, cardScanear,
            cardTerminar, cardRestringir, cardNuevaReserva, cardReservaEspecial;
    AppCompatImageView imgAlerta;
    DialogoProcesamiento dialog;
    Menu mMenu;
    PreferenceManager mPreferenciasManager;
    int idRes = 0, dniUser = 0;
    boolean reservar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_diaria_comedor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadData();
    }

    private void loadData() {
        mPreferenciasManager = new PreferenceManager(getApplicationContext());
        cardInicio.setVisibility(View.GONE);
        cardNo.setVisibility(View.GONE);
        latAdmin.setVisibility(View.VISIBLE);
        loadInfo();
    }

    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);
        imgAlerta = findViewById(R.id.imgAlerta);
        cardNuevaReserva = findViewById(R.id.cardReservarAlumno);
        cardReservaEspecial = findViewById(R.id.cardEspecial);
        cardRestringir = findViewById(R.id.cardCortar);
        txtRestringirReservas = findViewById(R.id.txtCortar);
        txtTerminarDia = findViewById(R.id.txtTerminar);
        txtMenu = findViewById(R.id.txtTextoMenu);
        latAdmin = findViewById(R.id.latAdmin);
        cardScanear = findViewById(R.id.cardScan);
        cardTerminar = findViewById(R.id.cardTerminar);
        cardNo = findViewById(R.id.cardNo);
        mProgressBar = findViewById(R.id.progress_bar);
        cardInicio = findViewById(R.id.card_one);
    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        cardScanear.setOnClickListener(this);
        cardTerminar.setOnClickListener(this);
        cardRestringir.setOnClickListener(this);
        cardReservaEspecial.setOnClickListener(this);
        cardNuevaReserva.setOnClickListener(this);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión Diaria");
    }

    private void loadInfo() {
        mProgressBar.setVisibility(View.VISIBLE);
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&d=%s", Utils.URL_MENU_HOY, id, key, 1);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                mProgressBar.setVisibility(View.GONE);
                cardNo.setVisibility(View.VISIBLE);
                Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
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
            mProgressBar.setVisibility(View.GONE);
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    cardNo.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    //Exito
                    loadInfo(jsonObject);
                    break;
                case 2:
                    cardNo.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    cardNo.setVisibility(View.VISIBLE);
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    cardNo.setVisibility(View.VISIBLE);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            cardNo.setVisibility(View.VISIBLE);
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");
                JSONObject object = jsonArray.getJSONObject(0);
                mMenu  = Menu.mapper(object, Menu.COMPLETE);

                loadMenu(mMenu);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadMenu(Menu menu) {
        if (menu != null) {
            loadInfoCardView(menu);
            if (menu.getValidez() == 0) {
                txtMenu.setText(getString(R.string.noMenuDisponible));
                Glide.with(imgAlerta.getContext()).load(R.drawable.ic_vacio_white).into(imgAlerta);
            } else {
                txtMenu.setText(getString(R.string.noMenuAceptaReserva));
                Glide.with(imgAlerta.getContext()).load(R.drawable.ic_no_reserva_more).into(imgAlerta);
            }

        } else {
            cardNo.setVisibility(View.VISIBLE);
        }

    }

    private void loadInfoCardView(Menu menu) {
        TextView txtFecha = findViewById(R.id.txtFecha);
        TextView txtPlato = findViewById(R.id.txtPlato);
        TextView txtPostre = findViewById(R.id.txtPostre);

        mPreferenciasManager.setValue(Utils.ID_MENU, menu.getIdMenu());

        txtFecha.setText(Utils.getDate(menu.getDia(), menu.getMes(), menu.getAnio()));
        String[] comida = menu.getComidas();
        String food = String.format("%s - %s", comida[0], comida[1]);
        txtPlato.setText(food);
        txtPostre.setText(comida[2]);

        cardInicio.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.cardScan:
                scanQR();
                break;
            case R.id.cardTerminar:
                terminarDialogo();
                break;
            case R.id.cardCortar:
                restringirDialogo();
                break;
            case R.id.cardEspecial:
                reservaEspecial();
                break;
            case R.id.cardReservarAlumno:
                reservaAlumno();
                break;
        }
    }

    private void reservaAlumno() {
        DialogoReservaComedor dialogoBuscaUsuario = new DialogoReservaComedor();
        dialogoBuscaUsuario.setContext(getApplicationContext());
        dialogoBuscaUsuario.setFragmentManager(getSupportFragmentManager());
        dialogoBuscaUsuario.setIdMenu(mMenu.getIdMenu());
        dialogoBuscaUsuario.show(getSupportFragmentManager(), "dialogo_buscar");
    }

    private void reservaEspecial() {
        Intent intent = new Intent(getApplicationContext(), NuevaReservaEspecialComedorActivity.class);
        intent.putExtra(Utils.ID_MENU, mMenu != null ? mMenu.getIdMenu() : -1);
        startActivity(intent);
    }

    private void restringirDialogo() {
        boolean b = mMenu.getDisponible() == 1;
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(R.string.advertencia))
                .setDescripcion(getString(b ? R.string.restringirMenu : R.string.rehabilitarReservaMenu))
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {
                        restringir();
                    }

                    @Override
                    public void no() {

                    }
                })
                .setIcono(R.drawable.ic_advertencia)
                .setTipo(DialogoGeneral.TIPO_ACEPTAR);
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getSupportFragmentManager(), "dialog_ad");
    }

    private void restringir() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&idM=%s&val=%s", Utils.URL_MENU_RESTRINGIR,
                id, key, mMenu.getIdMenu(), mMenu.getDisponible() == 1 ? 0 : 1);
        StringRequest request = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuestaRestringir(response);


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

    private void procesarRespuestaRestringir(String response) {
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
                    String mensaje = jsonObject.getString("mensaje");
                    if (mensaje.contains("permite")) {
                        mMenu.setDisponible(1);
                    } else if (mensaje.contains("restringido")) {
                        mMenu.setDisponible(0);
                    }
                    updateButton(mMenu.getValidez() == 1, mMenu.getDisponible() == 1);
                    break;
                case 2:
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
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

    private void terminarDialogo() {
        boolean b = mMenu.getValidez() == 1;
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(R.string.advertencia))
                .setDescripcion(getString(b ? R.string.finalizarMenu : R.string.rehabilitarMenu))
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {
                        terminar();
                    }

                    @Override
                    public void no() {

                    }
                })
                .setIcono(R.drawable.ic_advertencia)
                .setTipo(DialogoGeneral.TIPO_ACEPTAR);
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getSupportFragmentManager(), "dialog_ad");
    }


    private void terminar() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&idM=%s&val=%s", Utils.URL_MENU_TERMINAR,
                id, key, mMenu.getIdMenu(), mMenu.getValidez() == 1 ? 0 : 1);
        StringRequest request = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuestaTerminar(response);


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

    private void procesarRespuestaTerminar(String response) {
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
                    String mensaje = jsonObject.getString("mensaje");
                    if (mensaje.contains("habilitado")) {
                        //updateButton(true);
                        mMenu.setValidez(1);
                    } else if (mensaje.contains("terminado")) {
                        //updateButton(false);
                        mMenu.setValidez(0);
                    }
                    updateButton(mMenu.getValidez() == 1, mMenu.getDisponible() == 1);
                    break;
                case 2:
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
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

    private void updateButton(boolean terminar, boolean restringir) {
        txtTerminarDia.setText(terminar ? "FINALIZAR DIA" : "HABILITAR DIA");
        txtRestringirReservas.setText(restringir ? "RESTRINGIR RESERVAS" : "PERMITIR RESERVAS");
        cardInicio.setEnabled(terminar);
        if (restringir) {
            cardInicio.setAlpha(1);
        } else {
            cardInicio.setAlpha(0.5f);
        }
    }

    public void scanQR() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("Scan");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentIntegrator = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentIntegrator != null) {
            if (intentIntegrator.getContents() == null) {
                Utils.showToast(getApplicationContext(), getString(R.string.cancelaste));

            } else {
                String contenido = intentIntegrator.getContents();
                decodeQR(contenido);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void decodeQR(String contenido) {
        Pattern pattern = Pattern.compile("#[0-9A-Z]+-");
        Matcher matcher = pattern.matcher(contenido);
        String dni = "";
        if (matcher.find()) {
            dni = matcher.group().replace("#", "").replace("-", "");
        }
        pattern = Pattern.compile("-[0-9]+#");
        matcher = pattern.matcher(contenido);
        String idReserva = "";
        if (matcher.find()) {
            idReserva = matcher.group().replace("#", "").replace("-", "");
        }
        String[] dniDecode = new String[dni.length()];
        for (int i = 0; i < dniDecode.length; i++) {
            dniDecode[i] = String.valueOf(Utils.decode(dni.charAt(i)));
        }
        StringBuilder dniModif = new StringBuilder();
        for (int i = 0; i < dniDecode.length; i++) {
            dniModif.append(dniDecode[i]);
        }
        dni = dniModif.toString();
        try {
            if (!dni.equals("") && !idReserva.equals("")) {
                idRes = Integer.parseInt(idReserva);
                dniUser = Integer.parseInt(dni);
                reservar = true;
            } else {
                Utils.showToast(getApplicationContext(),
                        getString(R.string.qrNoData));
            }
        } catch (NumberFormatException e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (reservar) {
            if (dniUser != 0 && idRes != 0) {
                changeEstado(String.valueOf(idRes), String.valueOf(dniUser));
                reservar = false;
            }
        }

    }

    private void changeEstado(String idReserva, String dni) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&ir=%s&ie=%s&e=%s&d=%s", Utils.URL_RESERVA_ACTUALIZAR,
                id, key, idReserva, id, 3, dni);
        StringRequest request = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuestaActualizar(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getApplicationContext(),
                        getString(R.string.servidorOff));
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void procesarRespuestaActualizar(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    dialogo(true);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.error));
                    dialogo(false);
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.camposIncompletos));
                    break;
                case 5:
                    dialogoCustom(5);
                    break;
                case 6:
                    dialogoCustom(6);
                    break;
                case 7:
                    dialogoCustom(7);
                    break;
                case 8:
                    dialogoCustom(8);
                    break;
                case 9:
                    dialogoCustom(9);
                    break;
                case 10:
                    dialogoCustom(10);
                    break;
                case 11:
                    dialogoCustom(11);
                    break;
                case 12:
                    dialogoCustom(12);
                    break;
                case 13:
                    dialogoCustom(13);
                    break;
                case 14:
                    dialogoCustom(14);
                    break;
                case 15:
                    dialogoCustom(15);
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(),
                    getString(R.string.errorInternoAdmin));

        }
    }

    private void dialogoCustom(int valor) {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(
                        valor == 5 ? R.string.yaRetiro :
                                valor == 6 ? R.string.reservaNoExiste :
                                        (valor == 7 || valor == 8 || valor == 9 || valor == 10 || valor == 11) ? R.string.noMenuActual :
                                                valor == 12 ? R.string.cambioUsuario :
                                                        (valor == 13 || valor == 14) ? R.string.reservaYaComprobada :
                                                                valor == 15 ? R.string.error :
                                                                        R.string.error))
                .setDescripcion(getString(
                        valor == 5 ? R.string.reservaRetirada :
                                valor == 6 ? R.string.reservaNoExisteDesc :
                                        valor == 7 ? R.string.reservaAnteriorNoRetirada :
                                                valor == 8 ? R.string.reservaAnteriorCancelada :
                                                        valor == 9 ? R.string.reservaAnteriorRetirada :
                                                                valor == 10 ? R.string.reservaAnteriorYaNoRetirada :
                                                                        valor == 11 ? R.string.reservaNoExisteDesc :
                                                                                valor == 12 ? R.string.reservaNoDNI :
                                                                                        valor == 13 ? R.string.reservaHoyCancelada :
                                                                                                valor == 14 ? R.string.reservaHoyNoRetirada :
                                                                                                        valor == 15 ? R.string.menuCerrado :
                                                                                                                R.string.menuCerrado))
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {

                    }

                    @Override
                    public void no() {

                    }
                })
                .setIcono((valor == 5 || valor == 6 || valor == 12 || valor == 13 || valor == 14 || valor == 15) ? R.drawable.ic_error :
                        (valor == 7 || valor == 8 || valor == 9 || valor == 10 | valor == 11) ? R.drawable.ic_advertencia :
                                R.drawable.ic_error)
                .setTipo(DialogoGeneral.TIPO_ACEPTAR);
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getSupportFragmentManager(), "dialog_ad");
    }

    private void dialogo(boolean estado) {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(estado ? R.string.confirmado : R.string.error))
                .setDescripcion(getString(estado ? R.string.confirmadoReserva : R.string.confirmadoReservaError))
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {

                    }

                    @Override
                    public void no() {

                    }
                })
                .setIcono(estado ? R.drawable.ic_exito : R.drawable.ic_advertencia)
                .setTipo(DialogoGeneral.TIPO_ACEPTAR);
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getSupportFragmentManager(), "dialog_ad");
    }
}
