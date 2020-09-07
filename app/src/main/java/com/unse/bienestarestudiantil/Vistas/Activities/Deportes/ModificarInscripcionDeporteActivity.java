package com.unse.bienestarestudiantil.Vistas.Activities.Deportes;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickListenerAdapter;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.Credencial;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.Modelos.Estado;
import com.unse.bienestarestudiantil.Modelos.Inscripcion;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.CredencialesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoActivarDesactivar;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoListaEstados;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class ModificarInscripcionDeporteActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    EditText edtDni, edtNombre, edtApellido, edtEdad, edtBarrio, edtLocalidad, edtProvincia,
            edtPais, edtDomicilio, edtMail, edtAnioIngreso, edtMaterias, edtFace, edtInsta, edtPeso,
            edtAltura, edtTelefono, edtLugar, edtObj, edtCuales, edtLegajo, edtCarrera, edtFacultad,
            edtIMC, edtIMCEstado;
    EditText[] campos, noEditables;
    CheckBox[] noCheck;
    FloatingActionButton fabEditar;
    CheckBox chIsWsp, echSiActividad, chNoActividad, chBaja, chMedia, chAlta;
    LinearLayout linearActividades, linearAdmin, linearCredencial, latIMC, latIMCEstado;
    TextView txtTitulo, txtDeporte, edtFechaNac, txtFechaIns, txtFechaModi;
    ImageView btnBack, imgIcono;
    Button btnBajaAlta, btnEstado, btnCarnet, btnPDF;
    ArrayList<Credencial> mList;
    CredencialesAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    Deporte deporte;

    DialogoProcesamiento dialog;

    boolean isWsp = false, isActividad = false, isEdit = false, isAdmin = false, isEstadoEdit = false;
    int intensidad = 1, idInscrip = -1, idTem = 0, idPre = 0, tipoU = 0,
            idU = 0, disp = 0, estado = 0, modeUI = 0, val = 0, positionReg = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_inscripcion_deporte);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        isAdmin();

        if (idInscrip != -1) {
            loadViews();

            setToolbar();

            loadData();

            loadListener();

            editMode(0);

        } else {
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
            finish();
        }


    }

    public void isAdmin() {
        if (getIntent().getIntExtra(Utils.CREDENCIAL, -1) != -1) {
            idInscrip = getIntent().getIntExtra(Utils.CREDENCIAL, -1);
        }
        if (getIntent().getBooleanExtra(Utils.IS_ADMIN_MODE, false)) {
            isAdmin = getIntent().getBooleanExtra(Utils.IS_ADMIN_MODE, false);
        }
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("");
        Utils.changeColorDrawable(btnBack, getApplicationContext(), R.color.colorPrimaryDark);
    }

    private void loadData() {
        if (isAdmin) {
            latIMCEstado.setVisibility(View.VISIBLE);
            latIMC.setVisibility(View.VISIBLE);
            linearAdmin.setVisibility(View.VISIBLE);
            linearCredencial.setVisibility(View.VISIBLE);

        } else {
            latIMCEstado.setVisibility(View.GONE);
            latIMC.setVisibility(View.GONE);
            linearAdmin.setVisibility(View.GONE);
            linearCredencial.setVisibility(View.GONE);
        }
        mList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        loadInfo();

        updateButtonCarnet();

    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?id=%s&key=%s&idI=%s", Utils.URL_INSCRIPCION_PARTICULAR, id, key, idInscrip);
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
                    if (isEstadoEdit) {
                        Utils.showToast(getApplicationContext(), getString(R.string.estadoActualizado));
                        isEstadoEdit = false;
                    } else {
                        Utils.showToast(getApplicationContext(), getString(R.string.actualizado));
                        isEdit = false;
                        edit();
                    }
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.actualizadoError));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    isEdit = false;
                    edit();
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 6:
                    Utils.showToast(getApplicationContext(), getString(R.string.noExiste));
                    break;
                case 5:
                    Utils.showToast(getApplicationContext(), getString(R.string.noActualizableForm));
                    isEdit = false;
                    edit();

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
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setDescripcion(getString(R.string.inscripcionNoCarga))
                .setIcono(R.drawable.ic_error)
                .setTipo(DialogoGeneral.TIPO_SI_NO)
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {
                        loadInfo();
                    }

                    @Override
                    public void no() {
                        finish();
                    }
                })
                .setTipo(DialogoGeneral.TIPO_SI_NO);
        final DialogoGeneral mensaje = builder.build();
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
                    showDialogs();
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

            int anio = 0, idDeporte;
            String carrera, facultad, legajo, nombreD;
            Inscripcion inscripcion = Inscripcion.mapper(o, Inscripcion.COMPLETE);
            Usuario usuario = Usuario.mapper(o, Usuario.MEDIUM);
            if (inscripcion != null && usuario != null) {
                idTem = inscripcion.getIdTemporada();
                tipoU = usuario.getTipoUsuario();
                idPre = inscripcion.getIdPregunta();
                idU = inscripcion.getIdUsuario();
                disp = inscripcion.getDisponible();
                estado = inscripcion.getIdEstado();
                val = inscripcion.getValidez();
                idDeporte = Integer.parseInt(o.getString("idDeporte"));
                nombreD = o.getString("nombreD");
                if (jsonObject.has("dato")) {
                    JSONObject object = jsonObject.getJSONObject("dato");
                    anio = Integer.parseInt(object.getString("anio"));
                    carrera = object.getString("carrera");
                    facultad = object.getString("facultad");
                    legajo = object.getString("legajo");
                } else {
                    carrera = "N/A";
                    facultad = "N/A";
                    legajo = "N/A";
                    anio = 0;
                }
                edtIMC.setText(Utils.obtainIMC(inscripcion.getPeso(), inscripcion.getAltura()));
                edtIMCEstado.setText(Utils.obtainEstado(edtIMC.getText().toString().trim()).toUpperCase());
                edtIMCEstado.setTextColor(getApplicationContext().getResources().getColor(
                        Utils.getColorIMC(edtIMC.getText().toString().trim())));
                txtFechaIns.setText(Utils.getFechaFormat(inscripcion.getFechaRegistro()));
                txtFechaModi.setText(Utils.getFechaFormat(inscripcion.getFechaModificacion()));
                edtEdad.setText(String.valueOf(Utils.getEdad(Utils.getFechaDate(usuario.getFechaNac()))));
                edtDni.setText(String.valueOf(usuario.getIdUsuario()));
                edtNombre.setText(usuario.getNombre());
                edtApellido.setText(usuario.getApellido());
                edtFechaNac.setText(usuario.getFechaNac());
                edtBarrio.setText(usuario.getBarrio());
                edtLocalidad.setText(usuario.getLocalidad());
                edtProvincia.setText(usuario.getProvincia());
                edtPais.setText(usuario.getPais());
                edtDomicilio.setText(usuario.getDomicilio());
                edtMail.setText(usuario.getMail());
                edtAnioIngreso.setText(String.valueOf(anio));
                edtFace.setText(inscripcion.getFacebook());
                edtInsta.setText(inscripcion.getInstagram());
                edtPeso.setText(inscripcion.getPeso());
                edtAltura.setText(inscripcion.getAltura());
                edtTelefono.setText(usuario.getTelefono());
                edtLugar.setText(inscripcion.getLugar());
                edtObj.setText(inscripcion.getObjetivo());
                edtCuales.setText(inscripcion.getCuales());
                edtMaterias.setText(String.valueOf(inscripcion.getCantMaterias()));
                txtDeporte.setText(nombreD);
                txtTitulo.setText(String.format("Inscripción #%s", inscripcion.getIdInscripcion()));
                edtCarrera.setText(carrera);
                edtFacultad.setText(facultad);
                edtLegajo.setText(legajo);
                if (inscripcion.getWsp() == 1) {
                    chIsWsp.setChecked(true);
                    isWsp = true;
                } else {
                    isWsp = false;
                    chIsWsp.setChecked(false);

                }

                updateButton(val);

                if (inscripcion.getIntensidad() == -1) {
                    linearActividades.setVisibility(View.GONE);
                    chNoActividad.setChecked(true);
                    echSiActividad.setChecked(false);
                    isActividad = false;
                } else {
                    linearActividades.setVisibility(View.VISIBLE);
                    echSiActividad.setChecked(true);
                    chNoActividad.setChecked(false);
                    isActividad = true;
                    switch (inscripcion.getIntensidad()) {
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
                Glide.with(imgIcono.getContext()).load(deporte.getIconDeporte()).into(imgIcono);

                if (jsonObject.has("cred")) {

                    JSONArray credencial = jsonObject.getJSONArray("cred");

                    if (credencial.length() > 0) {
                        btnCarnet.setEnabled(false);

                        for (int i = 0; i < credencial.length(); i++) {
                            JSONObject object = credencial.getJSONObject(i);
                            String idCredencial = object.getString("idCredencial");
                            String idInscripcion = object.getString("idInscripcion");
                            String validez = object.getString("validez");
                            String fecha = object.getString("fechaCreacion");
                            String aneo = object.getString("anio");

                            String titulo = String.format("%s #%s/%s",
                                    "CREDENCIAL", idCredencial, aneo.substring(2));

                            Credencial credencial1 = new Credencial(Integer.parseInt(idCredencial),
                                    Integer.parseInt(validez), Integer.parseInt(aneo), titulo, fecha);

                            mList.add(credencial1);
                        }
                    } else {
                        btnCarnet.setEnabled(true);
                    }


                }
                mAdapter = new CredencialesAdapter(mList, getApplicationContext(), true);
                ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
                itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position, long id) {
                        positionReg = position;
                        Credencial credencial = mList.get(position);
                        DialogoActivarDesactivar dialogoActivarDesactivar = new DialogoActivarDesactivar();
                        dialogoActivarDesactivar.setContext(getApplicationContext());
                        dialogoActivarDesactivar.setFragmentManager(getSupportFragmentManager());
                        dialogoActivarDesactivar.setPosition(position);
                        dialogoActivarDesactivar.setCredencial(credencial);
                        dialogoActivarDesactivar.setOnClickListenerAdapter(new OnClickListenerAdapter() {
                            @Override
                            public void onClick(Object id) {
                                mList.get(positionReg).setValidez((Integer) id);
                                mAdapter.notifyDataSetChanged();
                                updateButtonCarnet();
                            }
                        });
                        dialogoActivarDesactivar.show(getSupportFragmentManager(), "dialogo_act_desc");
                    }
                });
                /*mAdapter.setOnClickListenerAdapter(new OnClickListenerAdapter() {
                    @Override
                    public void onClick(int id) {
                        mList.get(id).setValidez(mList.get(id).getValidez() == 1 ? 0 : 1);
                        mAdapter.notifyDataSetChanged();
                    }
                });*/
                mRecyclerView.setAdapter(mAdapter);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            showDialogs();
        }

    }

    private void updateButtonCarnet() {
        if (mList != null && mList.size() > 0 && mList.get(0).getValidez() == 1) {
            btnCarnet.setEnabled(false);
        } else {
            btnCarnet.setEnabled(true);
        }
    }

    private void updateButton(int val) {
        if (val == 0)
            btnBajaAlta.setText("HABILITAR INSCRIPCION");
        else btnBajaAlta.setText("DESHABILITAR INSCRIPCION");
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
        btnEstado.setOnClickListener(this);
        btnPDF.setOnClickListener(this);
        btnCarnet.setOnClickListener(this);
        btnBajaAlta.setOnClickListener(this);
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Utils.showToast(getApplicationContext(), mList.get(position).getTitulo());
            }
        });

    }

    private void loadViews() {
        edtIMC = findViewById(R.id.edtIMC);
        edtIMCEstado = findViewById(R.id.edtIMCEstado);
        latIMC = findViewById(R.id.latIMC);
        latIMCEstado = findViewById(R.id.latIMCEstado);
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
        txtFechaIns = findViewById(R.id.edtFechaRegistro);
        txtFechaModi = findViewById(R.id.edtFechaMod);
        mRecyclerView = findViewById(R.id.recycler);
        linearCredencial = findViewById(R.id.latCredencial);

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
        btnBajaAlta = findViewById(R.id.btnAltaBaja);
        btnCarnet = findViewById(R.id.btnCarnet);
        btnPDF = findViewById(R.id.btnPDF);
        btnEstado = findViewById(R.id.btnEstado);
        linearAdmin = findViewById(R.id.latAdmin);

        noCheck = new CheckBox[]{echSiActividad, chNoActividad, chMedia, chAlta, chBaja, chIsWsp};

        linearActividades = findViewById(R.id.disablePreg);
        txtDeporte = findViewById(R.id.txtDeporte);
        txtTitulo = findViewById(R.id.txtTituloI);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAltaBaja:
                if (val == 0)
                    val = 1;
                else val = 0;
                bajaAlta();
                break;
            case R.id.btnEstado:
                openDialogEstado();
                break;
            case R.id.btnPDF:
                break;
            case R.id.btnCarnet:
                generarCredencial();
                break;
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
                if (!isActividad) {
                    isEdit = true;
                    chNoActividad.setChecked(false);
                    isActividad = true;
                    linearActividades.setVisibility(View.VISIBLE);
                    chBaja.setChecked(true);
                    intensidad = 1;
                }
                echSiActividad.setChecked(true);
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

    private void generarCredencial() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String fecha = Utils.getFechaName(new Date(System.currentTimeMillis()));
        String URL = String.format("%s?id=%s&key=%s&idI=%s&fe=%s",
                Utils.URL_INSCRIPCION_CARNET, id, key, idInscrip, fecha);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuestaCarnet(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void procesarRespuestaCarnet(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    Utils.showToast(getApplicationContext(), getString(R.string.credencialCreada));
                    btnCarnet.setEnabled(false);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorCrearCredencial));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 100:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void bajaAlta() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?id=%s&key=%s&idI=%s&val=%s",
                Utils.URL_INSCRIPCION_PARTICULAR_ELIMIAR, id, key, idInscrip, val);
        StringRequest request = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuestaAltaBaja(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }

    private void procesarRespuestaAltaBaja(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    String mensaje = "";
                    if (val == 0)
                        mensaje = "Inscripción deshabilitada";
                    else mensaje = "Inscripción habilitada";
                    updateButton(val);
                    Utils.showToast(getApplicationContext(), mensaje);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.noData));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 100:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void openDialogEstado() {
        final DialogoListaEstados dialogoListaEstados = new DialogoListaEstados();
        dialogoListaEstados.setContextEstado(getApplicationContext());
        final ArrayList<Estado> list = new ArrayList<>();
        list.add(new Estado(1, "ACEPTADO", false));
        list.add(new Estado(2, "RECHAZADO", false));
        list.add(new Estado(3, "EN PROCESO", false));
        list.get(estado - 1).setSelect(true);
        dialogoListaEstados.setListener(new OnClickListenerAdapter() {
            @Override
            public void onClick(Object id) {
                dialogoListaEstados.dismiss();
                if (estado == (Integer) id + 1)
                    return;
                isEstadoEdit = true;
                estado = (Integer) id + 1;
                if (estado == 1)
                    disp = 0;
                else disp = 1;
                update();
            }
        });
        dialogoListaEstados.setList(list);
        dialogoListaEstados.setId(estado - 1);
        dialogoListaEstados.show(getSupportFragmentManager(), "dialog_estado");
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

        if (!validador.validarNombresEdt(edtObj) && validador.validarNumero(edtMaterias)
                && validador.validarNumeroDecimal(edtAltura)
                && validador.validarNumeroDecimal(edtPeso)) {

            if (ins.equals(""))
                ins = "N/A";
            if (face.equals(""))
                face = "N/A";

            String datos = "?id=%s&idT=%s&idI=%s&cm=%s&fa=%s&ins=%s&isw=%s&obj=%s&disp=%s&idP=%s&cual=%s&inte=%s&lug=%s&pes=%s&alt=%s&fm=%s&est=%s";

            if (isActividad) {

                if (!validador.validarNombresEdt(edtCuales, edtLugar) && !(cual.equals("N/A") || lugar.equals("N/A"))) {

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


        }

    }

    private void sendServer(String datos) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        String URL = String.format("%s%s&key=%s&ad=%s", Utils.URL_INSCRIPCION_ACTUALIZAR, datos, key, isAdmin ? 1 : 2);
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

        if (mode != 0) {
            btnPDF.setEnabled(false);
            btnEstado.setEnabled(false);
            btnBajaAlta.setEnabled(false);
        } else {
            btnPDF.setEnabled(true);
            btnEstado.setEnabled(true);
            btnBajaAlta.setEnabled(true);
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

    /*@Override
    public void onBackPressed() {
        if (isEdit)
            Utils.showToast(getApplicationContext(), getString(R.string.modoEdicionOn));
        super.onBackPressed();
    }*/

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
