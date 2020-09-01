package com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionNoticias;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.FileStorageManager;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.BlurTransformation;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.UploadManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleyMultipartRequest;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.PiletaAcompañante;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.AcompañanteAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static android.view.View.GONE;

public class AddNoticiaActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    Button btnGuardar;
    EditText edtTitulo, edtSubTitulo, edtDesc;
    ImageButton imgvAddImg;
    Spinner mSpinnerCategorias;
    DialogoProcesamiento dialog;
    public static final int GET_FROM_GALLERY = 3;
    String[] categorias = {"Seleccione una opción...", "Área Becas", "Ciber Estudiantil",
            "Comedor Universitario", "Área Deportes", "Residencia Universitaria",
            "Transporte Universitario", "UPA", "General"};
    Bitmap mBitmapFileSelect;
    Uri uriFileSelect;
    String nameFileSelect;
    boolean isAdminMode = false,
            isReadyForLoad = false;
    int idUser = 0;
    int categoriaSelect = 0, tipo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_noticia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadData();

        loadListener();

    }

    private void loadData() {
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, R.layout.style_spinner, categorias);
        dataAdapter2.setDropDownViewResource(R.layout.style_spinner);
        mSpinnerCategorias.setAdapter(dataAdapter2);
    }

    private void loadListener() {
        imgvAddImg.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);

        mSpinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                categoriaSelect = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadViews() {
        btnGuardar = findViewById(R.id.btnGuardar);
        mSpinnerCategorias = findViewById(R.id.spineer);
        imgvAddImg = findViewById(R.id.imgvAddImg);
        edtDesc = findViewById(R.id.edtDesc);
        edtTitulo = findViewById(R.id.edtTitulo);
        edtSubTitulo = findViewById(R.id.edtSubTitulo);
    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.imgFlecha).setVisibility(android.view.View.VISIBLE);
        findViewById(R.id.imgFlecha).setOnClickListener(this);
        ((TextView) findViewById(R.id.txtTitulo)).setText("Nueva noticia");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.imgvAddImg:
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                        GET_FROM_GALLERY);
                break;
            case R.id.btnGuardar:
                //save();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(final Bitmap bitmap) {
        VolleyMultipartRequest.DataPart dataPart = new VolleyMultipartRequest.DataPart(
                String.format(Utils.PROFILE_PIC, idUser),
                Utils.getFileDataFromDrawable(bitmap)
        );
        VolleyMultipartRequest volleyMultipartRequest = new UploadManager.Builder(getApplicationContext())
                .setMetodo(Request.Method.POST)
                .setURL(Utils.URL_USUARIO_IMAGE)
                .setOkListener(new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        procesarRespuestaImagen(new String(response.data), bitmap);
                    }
                })
                .setErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Utils.showToast(getApplicationContext(), getString(R.string.imagenErrorLoad));
                        FileStorageManager.deleteFileFromUri(getApplicationContext(), uriFileSelect);
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

    private void procesarRespuestaImagen(String response, Bitmap bitmap) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case 1:
                    //Exito
                   /* Glide.with(imgUser.getContext()).load(bitmap).into(imgUser);
                    Glide.with(imgBack.getContext()).load(bitmap)
                            .apply(RequestOptions.bitmapTransform(new BlurTransformation(this))
                                    .centerCrop())
                            .into(imgBack);*/
                    if (!isAdminMode)
                        FileStorageManager.saveBitmap(getApplicationContext(),
                                Utils.FOLDER,
                                String.format(Utils.PROFILE_PIC, idUser),
                                bitmap,
                                false);
                    Utils.showToast(getApplicationContext(), getString(R.string.fotoPerfilExito));
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.fotoPerfilError));
                    break;
            }
            FileStorageManager.deleteFileFromUri(getApplicationContext(), uriFileSelect);

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
            FileStorageManager.deleteFileFromUri(getApplicationContext(), uriFileSelect);
        }
    }


    private void loadDataPicture(final Uri uri, final String nameFile) {
        FileStorageManager.resizeBitmapAndFile(nameFile);
        mBitmapFileSelect = BitmapFactory.decodeFile(new File(nameFile).getPath());
        uriFileSelect = uri;
        nameFileSelect = nameFile;
        isReadyForLoad = true;
    }

    private void save() {
        Validador validador = new Validador(getApplicationContext());

        String tit = edtTitulo.getText().toString().trim();
        String stit = edtSubTitulo.getText().toString().trim();
        String cuerpo = edtDesc.getText().toString().trim();

        if (!validador.noVacio(tit) && !validador.noVacio(stit) && !validador.noVacio(cuerpo)) {
            sendServer(tit, stit, cuerpo);

        } else
            Toast.makeText(getApplicationContext(), R.string.camposInvalidos, Toast.LENGTH_SHORT).show();
    }

    public void sendServer(String tit, String stit, String cuerpo) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&iu=%s", Utils.URL_ADD_NOTICIA, key, idLocal);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), R.string.servidorOff, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), R.string.errorInternoAdmin, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //Exito
                    Toast.makeText(getApplicationContext(), "Ingreso registrado", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(), R.string.camposInvalidos, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), R.string.tokenInvalido, Toast.LENGTH_SHORT).show();
                    break;
                case 100:
                    Toast.makeText(getApplicationContext(), R.string.tokenInexistente, Toast.LENGTH_SHORT).show();
                    //No autorizado
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.errorInternoAdmin, Toast.LENGTH_SHORT).show();
        }
    }

}