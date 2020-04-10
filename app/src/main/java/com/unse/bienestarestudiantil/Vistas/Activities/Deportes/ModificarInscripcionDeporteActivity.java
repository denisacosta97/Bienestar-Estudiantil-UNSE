package com.unse.bienestarestudiantil.Vistas.Activities.Deportes;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogYesNoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class ModificarInscripcionDeporteActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    EditText edtDni, edtNombre, edtApellido, edtEdad, edtBarrio, edtLocalidad, edtProvincia,
            edtPais, edtDomicilio, edtMail, edtAnioIngreso, edtMaterias, edtFace, edtInsta, edtPeso,
            edtAltura, edtTelefono, edtLugar, edtObj, edtCuales, edtLegajo, edtCarrera, edtFacultad;
    EditText[] campos, noEditables;
    CheckBox[] noCheck;
    FloatingActionButton fabEditar;
    CheckBox chIsWsp, echSiActividad, chNoActividad, chBaja, chMedia, chAlta;
    LinearLayout linearActividades;
    TextView txtTitulo, txtDeporte, edtFechaNac;
    ImageView btnBack, imgIcono;

    DialogoProcesamiento dialog;

    boolean isWsp = false, isActividad = false, isEdit = false;
    int intensidad = 1, idInscrip = -1, idTem, idPre, tipoU, idU, disp, estado, modeUI = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_inscripcion_deporte);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getIntExtra(Utils.CREDENCIAL, -1) != -1) {
            idInscrip = getIntent().getIntExtra(Utils.CREDENCIAL, -1);
        }

        if (idInscrip != -1) {
            loadViews();

            setToolbar();

            loadData();

            loadListener();

            editMode(0);
        } else {
            Utils.showToast(getApplicationContext(), "Error, intente nuevamente");
            finish();
        }


    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("");
        Utils.changeColorDrawable(btnBack, getApplicationContext(), R.color.colorPrimaryDark);
    }

    private void loadData() {
        loadInfo();

    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?id=%s&key=%s&idI=%s", Utils.URL_INSCRIPCIONES_PARTICULAR_DEPORTE, id, key, idInscrip);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
                showDialogs();


            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void procesarRespuestaUpdate(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    Utils.showToast(getApplicationContext(), getString(R.string.actualizado));
                    isEdit = false;
                    edit();
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.actualizadoError));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 6:
                    Utils.showToast(getApplicationContext(), getString(R.string.noExiste));
                    break;
                case 5:
                    Utils.showToast(getApplicationContext(), getString(R.string.noActualizableForm));
                    break;
                case 100:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
            showDialogs();
        }
    }

    private void showDialogs() {
        final DialogYesNoGeneral mensaje = new DialogYesNoGeneral();
        mensaje.loadData(null, "Error al obtener la inscripcion. ¿Desea volver a intentarlo?", new YesNoDialogListener() {
            @Override
            public void yes() {
                mensaje.dismiss();
                loadInfo();
            }

            @Override
            public void no() {
                mensaje.dismiss();
                finish();
            }
        }, getApplicationContext());
        mensaje.loadIcon(R.drawable.ic_error);
        mensaje.setCancelable(false);
        mensaje.show(getSupportFragmentManager(), "dialog_error");
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
                    loadInfo(jsonObject);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.noData));
                    showDialogs();
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    showDialogs();
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    showDialogs();
                    break;
                case 100:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    showDialogs();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
            showDialogs();
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            JSONArray array = jsonObject.getJSONArray("mensaje");
            JSONObject o = array.getJSONObject(0);

            int idInscripcion, idDeporte, disponible, est, idUsuario, idTemporada, wsp, inten, tipoUsuario, cantMaterias, idPreguntas, anio = 0;
            String facebook, instagram, objetivo, altura, peso,
                    nombre, nombreD, apellido, fechaNac, pais, provincia, localidad, domicilio,
                    barrio, telefono, mail, cuales, carrera = "", legajo = "", facultad = "", lugar;


            idInscripcion = Integer.parseInt(o.getString("idInscripcion"));
            idUsuario = Integer.parseInt(o.getString("idUsuario"));
            idTemporada = Integer.parseInt(o.getString("idTemporada"));
            wsp = Integer.parseInt(o.getString("isWhatsapp"));
            inten = Integer.parseInt(o.getString("intensidad"));
            tipoUsuario = Integer.parseInt(o.getString("tipoUsuario"));
            cantMaterias = Integer.parseInt(o.getString("cantMaterias"));
            idPreguntas = Integer.parseInt(o.getString("idPregunta"));
            idDeporte = Integer.parseInt(o.getString("idDeporte"));
            disponible = Integer.parseInt(o.getString("disponible"));
            est = Integer.parseInt(o.getString("estado"));


            idTem = idTemporada;
            tipoU = tipoUsuario;
            idPre = idPreguntas;
            idU = idUsuario;
            disp = disponible;
            estado = est;

            facebook = o.getString("facebook");
            instagram = o.getString("instagram");
            telefono = o.getString("telefono");
            objetivo = o.getString("objetivo");
            altura = o.getString("altura");
            peso = o.getString("peso");
            nombre = o.getString("nombre");
            apellido = o.getString("apellido");
            fechaNac = o.getString("fechaNac");
            pais = o.getString("pais");
            provincia = o.getString("provincia");
            localidad = o.getString("localidad");
            domicilio = o.getString("domicilio");
            barrio = o.getString("barrio");
            mail = o.getString("mail");
            cuales = o.getString("cuales");
            lugar = o.getString("lugar");
            nombreD = o.getString("nombreD");

            if (jsonObject.get("dato") != null) {
                JSONObject object = jsonObject.getJSONObject("dato");
                anio = Integer.parseInt(object.getString("anio"));
                carrera = object.getString("carrera");
                facultad = object.getString("facultad");
                legajo = object.getString("legajo");
            } else {
                carrera = "N/A";
                facultad = "N/A";
                legajo = "N/A";
            }

            edtEdad.setText(String.valueOf(Utils.getEdad(Utils.getFechaDate(fechaNac))));
            edtDni.setText(String.valueOf(idUsuario));
            edtNombre.setText(nombre);
            edtApellido.setText(apellido);
            edtFechaNac.setText(fechaNac);
            edtBarrio.setText(barrio);
            edtLocalidad.setText(localidad);
            edtProvincia.setText(provincia);
            edtPais.setText(pais);
            edtDomicilio.setText(domicilio);
            edtMail.setText(mail);
            edtAnioIngreso.setText(String.valueOf(anio));
            edtFace.setText(facebook);
            edtInsta.setText(instagram);
            edtPeso.setText(peso);
            edtAltura.setText(altura);
            edtTelefono.setText(telefono);
            edtLugar.setText(lugar);
            edtObj.setText(objetivo);
            edtCuales.setText(cuales);
            edtMaterias.setText(String.valueOf(cantMaterias));
            txtDeporte.setText(nombreD);
            txtTitulo.setText(String.format("Inscripción #%s", idInscripcion));
            edtCarrera.setText(carrera);
            edtFacultad.setText(facultad);
            edtLegajo.setText(legajo);

            if (wsp == 1) {
                chIsWsp.setChecked(true);
                isWsp = true;
            } else {
                isWsp = false;
                chIsWsp.setChecked(false);

            }

            if (inten == -1) {
                linearActividades.setVisibility(View.GONE);
                chNoActividad.setChecked(true);
                echSiActividad.setChecked(false);
                isActividad = false;
            } else {
                linearActividades.setVisibility(View.VISIBLE);
                echSiActividad.setChecked(true);
                chNoActividad.setChecked(false);
                isActividad = true;
                switch (inten) {
                    case 1:
                        chMedia.setChecked(false);
                        chAlta.setChecked(false);
                        chBaja.setChecked(true);
                        intensidad = 1;
                        break;
                    case 2:
                        chMedia.setChecked(false);
                        chAlta.setChecked(true);
                        chBaja.setChecked(false);
                        intensidad = 2;
                        break;
                    case 3:
                        chMedia.setChecked(true);
                        chAlta.setChecked(false);
                        chBaja.setChecked(false);
                        intensidad = 3;
                        break;
                }


            }
            Glide.with(imgIcono.getContext()).load(Utils.getIconDeporte(idDeporte)).into(imgIcono);

        } catch (JSONException e) {
            e.printStackTrace();
            showDialogs();
        }

    }

    private void loadListener() {
        fabEditar.setOnClickListener(this);
        chIsWsp.setOnClickListener(this);
        chNoActividad.setOnClickListener(this);
        echSiActividad.setOnClickListener(this);
        chBaja.setOnClickListener(this);
        chMedia.setOnClickListener(this);
        chAlta.setOnClickListener(this);
        btnBack.setOnClickListener(this);

    }

    private void loadViews() {
        imgIcono = findViewById(R.id.imgIcon);
        edtDni = findViewById(R.id.edtDNI);
        edtNombre = findViewById(R.id.edtNombre);
        edtApellido = findViewById(R.id.edtApellido);
        edtEdad = findViewById(R.id.edtxEdad);
        edtFechaNac = findViewById(R.id.edtFecha);
        edtBarrio = findViewById(R.id.edtBarrio);
        edtLocalidad = findViewById(R.id.edtLocalidad);
        edtProvincia = findViewById(R.id.edtProvincia);
        edtPais = findViewById(R.id.edtPais);
        edtDomicilio = findViewById(R.id.edtDomicilio);
        edtMail = findViewById(R.id.edtMail);
        edtAnioIngreso = findViewById(R.id.edtAnioIngrAlu);
        edtFace = findViewById(R.id.edtFacebook);
        edtInsta = findViewById(R.id.edtxInst);
        edtPeso = findViewById(R.id.edxtPeso);
        edtAltura = findViewById(R.id.edtxAltura);
        edtTelefono = findViewById(R.id.edtCelu);
        edtLugar = findViewById(R.id.edtxPreg3);
        edtObj = findViewById(R.id.edtObjetivo);
        edtCuales = findViewById(R.id.edtxPreg2);
        edtMaterias = findViewById(R.id.edtxCantMat);
        edtCarrera = findViewById(R.id.edtCarrera);
        edtLegajo = findViewById(R.id.edtLegajo);
        edtFacultad = findViewById(R.id.edtxFacultad);

        campos = new EditText[]{edtMaterias, edtFace, edtInsta, edtPeso, edtAltura,
                edtLugar, edtObj, edtCuales};

        noEditables = new EditText[]{edtDni, edtNombre, edtApellido, edtEdad, edtBarrio, edtLocalidad, edtProvincia,
                edtPais, edtDomicilio, edtMail, edtAnioIngreso, edtTelefono, edtLegajo, edtCarrera, edtFacultad};


        btnBack = findViewById(R.id.imgFlecha);
        echSiActividad = findViewById(R.id.chbxSi);
        chNoActividad = findViewById(R.id.chbxNo);
        chBaja = findViewById(R.id.chbxBaja);
        chAlta = findViewById(R.id.chbxContin);
        chMedia = findViewById(R.id.chbxMedia);
        fabEditar = findViewById(R.id.fab);
        chIsWsp = findViewById(R.id.chbxWhats);

        noCheck = new CheckBox[]{echSiActividad, chNoActividad, chMedia, chAlta, chBaja, chIsWsp};

        linearActividades = findViewById(R.id.disablePreg);
        txtDeporte = findViewById(R.id.txtDeporte);
        txtTitulo = findViewById(R.id.txtTituloI);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                edit();
                break;
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.chbxBaja:
                isEdit = true;
                chMedia.setChecked(false);
                chAlta.setChecked(false);
                chBaja.setChecked(true);
                intensidad = 1;
                break;
            case R.id.chbxContin:
                isEdit = true;
                chMedia.setChecked(false);
                chAlta.setChecked(true);
                chBaja.setChecked(false);
                intensidad = 2;
                break;
            case R.id.chbxMedia:
                isEdit = true;
                chMedia.setChecked(true);
                chAlta.setChecked(false);
                chBaja.setChecked(false);
                intensidad = 3;
                break;
            case R.id.chbxNo:
                isEdit = true;
                chNoActividad.setChecked(true);
                echSiActividad.setChecked(false);
                isActividad = false;
                linearActividades.setVisibility(View.GONE);
                break;
            case R.id.chbxSi:
                isEdit = true;
                echSiActividad.setChecked(true);
                chNoActividad.setChecked(false);
                isActividad = true;
                linearActividades.setVisibility(View.VISIBLE);
                break;
            case R.id.chbxWhats:
                isEdit = true;
                if (isWsp) {
                    isWsp = false;
                    chIsWsp.setChecked(isWsp);
                } else {
                    isWsp = true;
                    chIsWsp.setChecked(isWsp);
                }
                break;
        }
    }

    private void edit() {
        if (isEdit) {
            update();
            return;
        }
        if (modeUI == 0)
            modeUI = 1;
        else
            modeUI = 0;
        editMode(modeUI);
    }

    private void update() {
        Validador validador = new Validador(getApplicationContext());
        //is wsp, dips, idP, intens, idI, idTempor
        String cantM = edtMaterias.getText().toString().trim();
        String face = edtFace.getText().toString().trim();
        String ins = edtInsta.getText().toString().trim();
        String obj = edtObj.getText().toString().trim();
        String cual = edtCuales.getText().toString().trim();
        String lugar = edtLugar.getText().toString().trim();
        String peso = edtPeso.getText().toString().trim();
        String altura = edtAltura.getText().toString().trim();
        String fechaM = Utils.getFechaName(new Date(System.currentTimeMillis()));

        if (!validador.noVacio(cantM, obj, peso, altura, fechaM)) {

            if (validador.validarNumero(cantM)) {

                if (validador.validadNumeroDecimal(peso) && validador.validadPeso(peso)
                        && validador.validadNumeroDecimal(altura) && validador.validadAltura(altura)) {

                    if (validador.lengthMores(face, ins)) {


                        if (ins.equals(""))
                            ins = "N/A";
                        if (face.equals(""))
                            face = "N/A";

                        String datos = "?id=%s&idT=%s&idI=%s&cm=%s&fa=%s&ins=%s&isw=%s&obj=%s&disp=%s&idP=%s&cual=%s&inte=%s&lug=%s&pes=%s&alt=%s&fm=%s&est=%s";

                        if (isActividad) {

                            if (validador.lengthMores(cual, lugar) && !(cual.equals("N/A") || lugar.equals("N/A"))) {

                                if (intensidad != -1)

                                    //Send
                                    sendServer(String.format(datos, idU, idTem, idInscrip, cantM, face, ins, isWsp ? 1 : 2, obj, disp, idPre, cual, intensidad,
                                            lugar, peso, altura, fechaM, estado));
                                else
                                    Utils.showToast(getApplicationContext(), getString(R.string.elegirIntensidad));

                            } else
                                Utils.showToast(getApplicationContext(), getString(R.string.noActividadCompleta));

                        } else {
                            sendServer(String.format(datos, idU, idTem, idInscrip, cantM, face, ins, isWsp ? 1 : 2, obj, disp, idPre, "N/A", -1,
                                    "N/A", peso, altura, fechaM, estado));
                        }

                    } else
                        Utils.showToast(getApplicationContext(), getString(R.string.redesSociales));

                } else
                    Utils.showToast(getApplicationContext(), getString(R.string.noAlturaPeso));

            } else
                Utils.showToast(getApplicationContext(), String.format(getString(R.string.noNumero), String.valueOf(cantM)));

        } else {
            Utils.showToast(getApplicationContext(), getString(R.string.camposVacios));
        }


    }

    private void sendServer(String datos) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        String URL = String.format("%s%s&key=%s", Utils.URL_INSCRIPCION_ACTUALIZAR, datos, key);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuestaUpdate(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
                showDialogs();


            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }

    private void editMode(int mode) {
        if (mode != 0) {
            fabEditar.setImageResource(R.drawable.ic_save);
        } else {
            fabEditar.setImageResource(R.drawable.ic_edit_);
        }

        for (EditText e2 : noEditables) {
            e2.setEnabled(false);
        }

        for (CheckBox c : noCheck) {
            if (mode == 0)
                c.setEnabled(false);
            else c.setEnabled(true);
        }

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
}
