package com.unse.bienestarestudiantil.Vistas.Activities.PuntosConectividad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unse.bienestarestudiantil.Databases.RolViewModel;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.Modelos.Rol;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

public class GestionPuntosConectividadActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mOpciones;
    DialogoProcesamiento dialog;
    ImageView imgIcono;
    boolean reservar = false;
    String dniUser = "", idRes = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_puntos_conectividad);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();

        loadData();

        setToolbar();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Puntos de Conectividad");
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                switch ((int) id) {
                    case 14001:
                        startActivity(new Intent(getApplicationContext(), TurnosDiaPCActivity.class));
                        break;
                    case 14002:
                        startActivity(new Intent(getApplicationContext(), TurnosHistoricosPCActivity.class));
                        break;
                    case 14003:
                        scanQR();
                        break;
                    case 14004:
                        startActivity(new Intent(getApplicationContext(), EstadisticasPCActivity.class));
                        break;
                }
                //Utils.showToast(getApplicationContext(), "Item: "+mOpciones.get(position).getTitulo());
            }
        });
        imgIcono.setOnClickListener(this);

    }

    private void loadData() {
        mOpciones = new ArrayList<>();
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL, 14001, "Turnos del día", R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL, 14002, "Turnos históricos", R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL, 14003, "Escanear QR", R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL, 14004, "Estadisticas", R.drawable.ic_item_arrow, R.color.colorFCEyT));

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OpcionesAdapter(filtrarOpciones(mOpciones), getApplicationContext(), 1);
        mRecyclerView.setAdapter(mAdapter);

    }

    private ArrayList<Opciones> filtrarOpciones(ArrayList<Opciones> opciones) {
        ArrayList<Opciones> finals = new ArrayList<>();
        RolViewModel datos = new RolViewModel();
        ArrayList<Rol> roles = datos.getAll();
        for (Opciones e : opciones) {
            for (Rol rol : roles) {
                if (e.getId() == rol.getIdRol()) {
                    finals.add(e);
                }

            }

        }

        return finals;
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    public void scanQR() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("Escanear QR");
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
        try {
            if (!dni.equals("") && !idReserva.equals("")) {
                dniUser = String.valueOf(dni);
                idRes = String.valueOf(idReserva);
                reservar = true;
            } else {
                Utils.showToast(getApplicationContext(), getString(R.string.qrNoData));
            }
        } catch (NumberFormatException e) {
            Utils.showToast(getApplicationContext(), getString(R.string.qrNoData));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (reservar) {
            if (!dniUser.equals("") && !idRes.equals("")) {
                changeEstado(idRes, dniUser);
                reservar = false;
            }
        }

    }

    private void changeEstado(String idReserva, String dni) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&it=%s&ie=%s&est=%s&iu=%s", Utils.URL_PC_ESCANEAR, id, key,
                idReserva, id, 4, dni);
        StringRequest request = new StringRequest(POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuestaActualizar(response);
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

    private void procesarRespuestaActualizar(String response) {
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
                    dialogo(true);
                    break;
                case 2:
                    dialogo(false);
                    Utils.showToast(getApplicationContext(), getString(R.string.noData));
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
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));

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
                                                                                                valor == 14 ? R.string.reservaHoyNoRetirada : R.string.error))
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
                .setIcono(estado ? R.drawable.ic_chek : R.drawable.ic_advertencia)
                .setTipo(DialogoGeneral.TIPO_ACEPTAR);
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getSupportFragmentManager(), "dialog_ad");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

}