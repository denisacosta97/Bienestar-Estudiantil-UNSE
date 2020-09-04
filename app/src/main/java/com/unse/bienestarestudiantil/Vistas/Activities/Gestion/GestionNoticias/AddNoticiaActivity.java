package com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionNoticias;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.UploadManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleyMultipartRequest;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.Noticia;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import static com.unse.bienestarestudiantil.Herramientas.Utils.GET_FROM_GALLERY;

public class AddNoticiaActivity extends AppCompatActivity implements View.OnClickListener,
        TextWatcher {


    Button btnGuardar, btnAltaBaja, btnNotificacion;
    EditText edtTitulo, edtSubTitulo, edtDesc;
    AppCompatImageView imgvAddImg;
    Spinner mSpinnerCategorias;
    DialogoProcesamiento dialog;
    String[] categorias = {"Seleccione una opción...", "Área Comedor", "Área Deportes", "Área Transporte"
            , "Área Becas", "Área Residencia", "Área Cyber Estudiantil", "Área UPA", "Área Polideportivo", "Área General"};
    Bitmap mBitmapFileSelect;
    String nameFileSelect, titulo, subtitulo, cuerpo;
    int categoriaSelect = 0, validez = 0;
    boolean isPic = false, isEdit = false;
    Noticia mNoticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_noticia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.NOTICIA_INFO) != null) {
            mNoticia = getIntent().getParcelableExtra(Utils.NOTICIA_INFO);
        }

        setToolbar();

        loadViews();

        loadData();

        loadListener();

    }

    private void loadData() {
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, R.layout.style_spinner, categorias);
        dataAdapter2.setDropDownViewResource(R.layout.style_spinner);
        mSpinnerCategorias.setAdapter(dataAdapter2);
        if (mNoticia == null) {
            btnNotificacion.setVisibility(View.GONE);
            btnAltaBaja.setVisibility(View.GONE);
        } else {
            validez = mNoticia.getValidez();
            String URL = String.format(Utils.URL_IMAGE_NOTICIA, mNoticia.getImagen());
            Glide.with(imgvAddImg.getContext())
                    .applyDefaultRequestOptions(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .load(URL)
                    .into(imgvAddImg);
            btnNotificacion.setVisibility(View.VISIBLE);
            btnAltaBaja.setVisibility(View.VISIBLE);
            mSpinnerCategorias.setSelection(mNoticia.getIdArea());
            edtTitulo.setText(mNoticia.getTitulo());
            edtSubTitulo.setText(mNoticia.getSubtitlo());
            edtDesc.setText(mNoticia.getDescripcion());
            changeButton();
        }
    }

    private void loadListener() {
        imgvAddImg.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        btnAltaBaja.setOnClickListener(this);
        btnNotificacion.setOnClickListener(this);
        edtDesc.addTextChangedListener(this);
        edtSubTitulo.addTextChangedListener(this);
        edtTitulo.addTextChangedListener(this);
        mSpinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                categoriaSelect = position;
                if (mNoticia != null) {
                    if (mNoticia.getIdArea() != categoriaSelect) {
                        isEdit = true;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadViews() {
        btnAltaBaja = findViewById(R.id.btnAltaBaja);
        btnNotificacion = findViewById(R.id.btnNotificacion);
        btnGuardar = findViewById(R.id.btnGuardar);
        mSpinnerCategorias = findViewById(R.id.spineer);
        imgvAddImg = findViewById(R.id.imgImage);
        edtDesc = findViewById(R.id.edtDesc);
        edtTitulo = findViewById(R.id.edtTitulo);
        edtSubTitulo = findViewById(R.id.edtSubTitulo);
    }

    private void setToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.imgFlecha).setVisibility(android.view.View.VISIBLE);
        findViewById(R.id.imgFlecha).setOnClickListener(this);
        ((TextView) findViewById(R.id.txtTitulo)).setText(mNoticia == null ? "Nueva noticia" : "Editar Noticia");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.imgImage:
                openGallery();
                break;
            case R.id.btnGuardar:
                save();
                break;
            case R.id.btnNotificacion:
                enviarNotificacion();
                break;
            case R.id.btnAltaBaja:
                altaBaja();
                break;
        }
    }

    private void altaBaja() {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(R.string.advertencia))
                .setDescripcion(String.format(getString(R.string.noticiaEliminar), validez == 1 ? "baja" : "alta"))
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

    private void baja(int val) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&in=%s&val=%s", Utils.URL_ELIMINAR_NOTICIA, key,
                idLocal, mNoticia.getIdNoticia(), val);
        StringRequest requestImage = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
                        texto = getString(R.string.noticiaDeshabilitado);
                    else
                        texto = getString(R.string.noticiaHabilitado);
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

    private void changeButton() {
        if (validez == 0)
            btnAltaBaja.setText(getString(R.string.btnHabilitar));
        else btnAltaBaja.setText(getString(R.string.btnDeshabilitar));
    }

    private void enviarNotificacion() {
    }

    private void openGallery() {
        startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                GET_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();
                try {
                    mBitmapFileSelect = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    if (mBitmapFileSelect != null) {
                        Glide.with(imgvAddImg.getContext())
                                .applyDefaultRequestOptions(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE))
                                .load(mBitmapFileSelect).into(imgvAddImg);
                    }
                    if (mNoticia != null)
                        isEdit = true; nameFileSelect = "cambio";
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Utils.showToast(getApplicationContext(), getString(R.string.noSelectImagen));
            }

        }
    }

    private void uploadImage(final Bitmap bitmap) {
        VolleyMultipartRequest.DataPart dataPart = new VolleyMultipartRequest.DataPart(
                 nameFileSelect,
                Utils.getFileDataFromDrawable(bitmap)
        );
        VolleyMultipartRequest volleyMultipartRequest = new UploadManager.Builder(getApplicationContext())
                .setMetodo(Request.Method.POST)
                .setURL(Utils.URL_NOTICIA_IMAGE)
                .setOkListener(new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        procesarRespuestaImagen(new String(response.data));
                    }
                })
                .setErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Utils.showToast(getApplicationContext(), getString(R.string.imagenErrorLoad));
                    }
                })
                .setDato(dataPart)
                .setTipoDato(UploadManager.IMAGE)
                .setParams(null)
                .build();
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(volleyMultipartRequest);
    }

    private void procesarRespuestaImagen(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case 1:
                    //Exito
                    isPic = true;
                    sendServer(titulo, subtitulo, cuerpo);
                    break;
                case 2:
                    isPic = false;
                    Utils.showToast(getApplicationContext(), getString(R.string.errorImagenLoad));
                    break;
            }
            ;

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void save() {
        Validador validador = new Validador(getApplicationContext());
        String tit = edtTitulo.getText().toString().trim();
        String stit = edtSubTitulo.getText().toString().trim();
        String cuerp = edtDesc.getText().toString().trim();

        if (validador.validarTexto(edtTitulo) && validador.validarTexto(edtSubTitulo)
                && validador.validarTexto(edtDesc)) {

            if (isEdit) {
                titulo = tit;
                subtitulo = stit;
                cuerpo = cuerp;
                if (nameFileSelect == null) {
                    nameFileSelect = mNoticia.getImagen();
                    sendServer(titulo, subtitulo, cuerpo);
                } else {
                    nameFileSelect = mNoticia.getImagen();
                    uploadImage(mBitmapFileSelect);
                }
            } else if (categoriaSelect == 0) {
                Utils.showToast(getApplicationContext(), getString(R.string.categoriaElegir));
            } else if (mBitmapFileSelect == null) {
                Utils.showToast(getApplicationContext(), getString(R.string.noSelectImagen));
            } else if (!isPic) {
                Date date = new Date(System.currentTimeMillis());
                String fecha = Utils.getFechaOrderOnly(date) + "_" + Utils.getHoraWithSeconds(date);
                fecha = fecha.replaceAll("/", "_");
                nameFileSelect = String.format("noticia_%s.jpg", fecha);
                titulo = tit;
                subtitulo = stit;
                cuerpo = cuerp;
                uploadImage(mBitmapFileSelect);
            } else {
                sendServer(tit, stit, cuerp);
            }

        } else {
            Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
        }
    }

    public void sendServer(String tit, String stit, String cuerpo) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = "";
        if (mNoticia == null)
            URL = String.format("%s?key=%s&idU=%s&t=%s&st=%s&d=%s&i=%s&iu=%s&ia=%s",
                    Utils.URL_AGREGAR_NOTICIA, key, idLocal, tit, stit, cuerpo,
                    nameFileSelect, idLocal, categoriaSelect);
        else URL = String.format("%s?key=%s&idU=%s&t=%s&st=%s&d=%s&i=%s&iu=%s&ia=%s&in=%s",
                Utils.URL_ACTUALIZAR_NOTICIA, key, idLocal, tit, stit, cuerpo,
                nameFileSelect, idLocal, categoriaSelect, mNoticia.getIdNoticia());
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
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
                    Utils.showToast(getApplicationContext(), getString(isEdit ? R.string.noticiaActualizada :
                            R.string.noticiaAgregada));
                    isEdit = false;
                    finish();
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(isEdit ? R.string.noticiaActualizarError :
                            R.string.noticiaError));
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mNoticia != null)
            isEdit = true;

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}