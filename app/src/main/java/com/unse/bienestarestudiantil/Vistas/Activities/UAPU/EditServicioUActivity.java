package com.unse.bienestarestudiantil.Vistas.Activities.UAPU;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.ServiciosU;
import com.unse.bienestarestudiantil.Modelos.Torneo;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoDropTorneo;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class EditServicioUActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    ServiciosU mServiciosU;
    EditText edtName, edtNameDoc, edtDia, edtHorarios, edtDesc;
    ImageView imgIcono;
    Button btnBorrar, btnEditar;
    DialogoProcesamiento dialog;
    EditText[] campos;
    boolean isEdit = false;
    int mode = 0, TIPO_USER = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_servicio_u);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.TORNEO) != null) {
            mServiciosU = getIntent().getParcelableExtra(Utils.TORNEO);
        }

        if (mServiciosU != null) {
            loadViews();

            loadListener();

            loadData();

            editMode(0);
        } else {
            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelta a intentar");
            finish();
        }

        setToolbar();

    }

    private void loadData() {
        edtName.setText(mServiciosU.getTitulo());
        edtNameDoc.setText(mServiciosU.getTitulo());
        edtDia.setText(mServiciosU.getDias());
        edtHorarios.setText(mServiciosU.getHorario());
        edtDesc.setText(mServiciosU.getDescripcion());

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnBorrar.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
    }

    private void loadViews() {
        edtName = findViewById(R.id.edtName);
        edtNameDoc = findViewById(R.id.edtNameDoc);
        edtDia = findViewById(R.id.edtDia);
        edtHorarios = findViewById(R.id.edtHorarios);
        edtDesc = findViewById(R.id.edtDesc);

        btnBorrar = findViewById(R.id.btnBorrar);
        btnEditar = findViewById(R.id.btnEditar);
        imgIcono = findViewById(R.id.imgFlecha);

        campos = new EditText[]{edtName, edtNameDoc, edtDia, edtHorarios, edtDesc};
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Servicio");
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEditar:
                activateEditMode();
                break;
            case R.id.btnCancelar:

                break;
            case R.id.imgIcon:
                onBackPressed();
                break;
        }
    }


    private void editMode(int mode) {
        for (EditText e : campos) {
            if (mode == 0) {
                e.setEnabled(false);
                e.setBackgroundColor(getResources().getColor(R.color.transparente));
                e.removeTextChangedListener(null);
            } else {
                e.setEnabled(true);
                e.setBackground(getResources().getDrawable(R.drawable.edit_text_logreg));
                e.addTextChangedListener(this);
            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isEdit = true;
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void activateEditMode() {
        if (isEdit) {
            save();
            return;
        }
        if (mode == 0)
            mode = 1;
        else
            mode = 0;
        editMode(mode);
    }

    private void save() {
        String name = edtName.getText().toString().trim();
        String fechaIni = edtNameDoc.getText().toString().trim();
        String fechaFin = edtDia.getText().toString().trim();
        String desc = edtHorarios.getText().toString().trim();
        String lugar = edtDesc.getText().toString().trim();
        String fecha = Utils.getFechaNameWithinHour(new Date(System.currentTimeMillis()));
        Validador validador = new Validador(getBaseContext());
        /*if (!validador.noVacio(dni) && !validador.noVacio(cantP) && !validador.noVacio(precio)) {
            sendServer(dni, cantP, precio, desc);
        }*/
    }

    private void sendServer(String dni, String cantP, String precio, String desc) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&iu=%s&de=%s&ca=%s&pr=%s",
                Utils.URL_REGISTRAR_IMPR, key, id, dni, desc, cantP, precio);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta2(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.servidorOff), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Servicio modificado", Toast.LENGTH_SHORT).show();
                    //Exito
                    //loadInfo(jsonObject);
                    finish();
                    break;
                case 2:
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