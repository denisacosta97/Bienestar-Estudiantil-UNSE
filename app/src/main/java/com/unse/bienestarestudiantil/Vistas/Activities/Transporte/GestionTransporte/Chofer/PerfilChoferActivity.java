package com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.Chofer;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.Chofer;
import com.unse.bienestarestudiantil.Modelos.TemporadaArea;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.TemporadaAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilChoferActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgIcono;
    CircleImageView imgUser;
    Chofer mChofer;
    TextView txtNombre, txtDNI, txtFechaNac, txtFechaIng, txtFechaMod, txtServicios;
    Button btnBaja, btnEditar;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerTemporadas;
    ArrayList<TemporadaArea> mTemporadas;
    TemporadaAdapter mTemporadaAdapter;
    DialogoProcesamiento dialog;
    int validez = 0, mode = 0;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_chofer);

        if (getIntent().getParcelableExtra(Utils.USER_NAME) != null) {
            mChofer = getIntent().getParcelableExtra(Utils.USER_NAME);
        }

        if (mChofer != null) {
            setToolbar();

            loadViews();

            loadListener();

            loadData();
        } else {
            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelva a intentar");
            finish();
        }

    }

    private void loadData() {
        txtDNI.setText(String.valueOf(mChofer.getIdUsuario()));
        txtNombre.setText(String.format("%s %s", mChofer.getNombre(), mChofer.getApellido()));
        txtFechaNac.setText(mChofer.getFechaNac());
        txtFechaIng.setText(mChofer.getFechaRegistro());
        txtFechaMod.setText(mChofer.getFechaModificacion());
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Integer cant : mChofer.getCantidades()) {
            stringBuilder.append(String.format("Servicio: %s\nCantidad: %s", mChofer.getRecorridos()[i], cant));
            stringBuilder.append("\n");
            i++;

        }
        validez = mChofer.getValidez();
        changeButton();
        txtServicios.setText(stringBuilder.toString());
        Utils.loadPicture(imgUser, mChofer.getIdUsuario()).into(imgUser);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerTemporadas.setLayoutManager(mLayoutManager);
        mRecyclerTemporadas.setNestedScrollingEnabled(true);
        mRecyclerTemporadas.setHasFixedSize(true);
        mTemporadas = mChofer.getTemporadas();
        mTemporadaAdapter = new TemporadaAdapter(mTemporadas, getApplicationContext());
        mRecyclerTemporadas.setAdapter(mTemporadaAdapter);

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Info Chofer");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
        btnBaja.setOnClickListener(this);
        txtFechaIng.setOnClickListener(this);
    }


    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);
        imgUser = findViewById(R.id.imgUser);
        mRecyclerTemporadas = findViewById(R.id.recycler);
        txtDNI = findViewById(R.id.txtDni);
        txtNombre = findViewById(R.id.txtNameApe);
        txtFechaNac = findViewById(R.id.txtFechaN);
        txtFechaIng = findViewById(R.id.txtFechaIng);
        txtServicios = findViewById(R.id.txtServicios);
        btnBaja = findViewById(R.id.btnAltaBaja);
        btnEditar = findViewById(R.id.btnEditar);
        txtFechaMod = findViewById(R.id.txtFechaMod);

    }

    private void baja(int val) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&idC=%s&val=%s", Utils.URL_ELIMINAR_CHOFER, key,
                idLocal, mChofer.getIdUsuario(), val);
        StringRequest requestImage = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuestaEliminar(response);

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

    private void procesarRespuestaEliminar(String response) {
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
                    String texto = null;
                    if (validez == 0)
                        texto = getString(R.string.usuarioDeshabilitado);
                    else
                        texto = getString(R.string.usuarioHabilitado);
                    Utils.showToast(getApplicationContext(), texto);
                    changeButton();
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.usuarioNoExiste));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
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

    private void altaBajaUser() {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(R.string.advertencia))
                .setDescripcion(String.format(getString(R.string.usuarioEliminar), validez == 0 ? "alta" : "baja",
                        mChofer.getNombre(),
                        mChofer.getApellido()))
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {
                        if (validez == 0)
                            validez = 1;
                        else
                            validez = 0;
                        baja(validez);
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

    private void elegirFecha() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String mes, dia;
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
                if (!txtFechaIng.getText().toString().equals(selectedDate))
                    isEditMode = true;
                txtFechaIng.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");

    }

    private void changeButton() {
        if (validez == 0)
            btnBaja.setText(getString(R.string.btnAlta));
        else btnBaja.setText(getString(R.string.btnBaja));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnAltaBaja:
                altaBajaUser();
                break;
            case R.id.txtFechaIng:
                elegirFecha();
                break;
            case R.id.btnEditar:
                openEditMode();
                break;
        }
    }

    private void openEditMode() {
        if (isEditMode) {
            save();
            return;
        }
        if (mode == 0)
            mode = 1;
        else
            mode = 0;
        editMode(mode);
    }

    private void editMode(int mode) {
        if (mode != 0) {
            txtFechaIng.setOnClickListener(this);
            btnEditar.setText("GUARDAR");
        } else {
            txtFechaIng.setOnClickListener(null);
            btnEditar.setText("EDITAR");
        }

        if (mode == 0) {
            //txtFechaIng.setEnabled(false);
            txtFechaIng.setBackgroundColor(getResources().getColor(R.color.transparente));
        } else {
            //txtFechaIng.setEnabled(true);
            txtFechaIng.setBackground(getResources().getDrawable(R.drawable.edit_text_logreg));
        }

    }

    private void save() {
        String fecha = txtFechaIng.getText().toString();
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&idC=%s&fi=%s&val=%s", Utils.URL_ACTUALIZAR_CHOFER, key,
                idLocal, mChofer.getIdUsuario(), fecha, validez);
        StringRequest requestImage = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuestaChoferInfo(response);

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

    private void procesarRespuestaChoferInfo(String response) {
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
                    Utils.showToast(getApplicationContext(), getString(R.string.choferActualizado));
                    isEditMode = false;
                    mode = 0;
                    editMode(mode);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.choferErrorActualizar));
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

}
