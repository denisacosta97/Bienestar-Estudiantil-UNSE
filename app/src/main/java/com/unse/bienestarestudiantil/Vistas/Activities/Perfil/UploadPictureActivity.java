package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.unse.bienestarestudiantil.Herramientas.BlurTransformation;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.FileStorageManager;
import com.unse.bienestarestudiantil.Herramientas.UploadManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleyMultipartRequest;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class UploadPictureActivity extends AppCompatActivity implements View.OnClickListener {

    CircleImageView imgUser;
    ImageView imgBack;
    FloatingActionButton fabPic;
    Button btnSaltar, btnAceptar;
    DialogoProcesamiento dialog;

    boolean isAdminMode = false,
            isReadyForLoad = false;
    int idUser = 0;

    Bitmap mBitmapFileSelect;
    Uri uriFileSelect;
    String nameFileSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_picture);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        isAdmin();

        loadViews();

        loadData();

        loadListener();

    }

    private void loadListener() {
        fabPic.setOnClickListener(this);
        imgUser.setOnClickListener(this);
        btnSaltar.setOnClickListener(this);
        btnAceptar.setOnClickListener(this);
    }

    private void loadData() {
        btnAceptar.setEnabled(false);
    }


    private void loadViews() {
        btnSaltar = findViewById(R.id.btnSaltar);
        btnAceptar = findViewById(R.id.btnAceptar);
        imgUser = findViewById(R.id.imgUserRegister);
        fabPic = findViewById(R.id.fabPic);
        imgBack = findViewById(R.id.imgBack);
    }

    private void isAdmin() {
        if (getIntent().getBooleanExtra(Utils.IS_ADMIN_MODE, false)) {
            isAdminMode = getIntent().getBooleanExtra(Utils.IS_ADMIN_MODE, false);
        }
        if (getIntent().getIntExtra(Utils.MY_ID, -1) != -1) {
            idUser = getIntent().getIntExtra(Utils.MY_ID, -1);
        } else {
            idUser = 123456789;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgUserRegister:
            case R.id.fabPic:
                openGallery();
                break;
            case R.id.btnSaltar:
                saltar();
                break;
            case R.id.btnAceptar:
                openNext();
                break;
        }

    }

    private void openNext() {
        if (!isAdminMode) {
            Utils.showToast(getApplicationContext(), getString(R.string.iniciarSesionConf));
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case Utils.PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri uri = null;
                    if (data.getData() != null) {
                        uri = data.getData();
                        Intent intent = new Intent(getApplicationContext(), CropImageActivity.class);
                        intent.putExtra(Utils.URI_IMAGE, uri);
                        startActivityForResult(intent, Utils.EDIT_IMAGE);

                    } else {
                        Utils.showToast(getApplicationContext(), getString(R.string.imagenErrorLoad));
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Utils.showToast(getApplicationContext(), getString(R.string.noSelectImagen));
                }
                break;
            case Utils.EDIT_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri uri = null;
                    String name = null;
                    if (data.getParcelableExtra(Utils.URI_IMAGE) != null) {
                        uri = data.getParcelableExtra(Utils.URI_IMAGE);
                    }
                    if (data.getStringExtra(Utils.NAME_GENERAL) != null) {
                        name = data.getStringExtra(Utils.NAME_GENERAL);
                    }
                    if (uri != null && name != null) {
                        loadDataPicture(uri, name);
                    } else {
                        Utils.showToast(getApplicationContext(), getString(R.string.imagenErrorCrop));
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Utils.showToast(getApplicationContext(), getString(R.string.imagenErrorCrop));
                } else if (resultCode == 2) {
                    Utils.showToast(getApplicationContext(), getString(R.string.cambioNoGuardado));
                }
                break;
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (isReadyForLoad) {
            uploadImage(mBitmapFileSelect);
            isReadyForLoad = false;
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
                    Glide.with(imgUser.getContext()).load(bitmap).into(imgUser);
                    Glide.with(imgBack.getContext()).load(bitmap)
                            .apply(RequestOptions.bitmapTransform(new BlurTransformation(this))
                                    .centerCrop())
                            .into(imgBack);
                    if (!isAdminMode)
                        FileStorageManager.saveBitmap(getApplicationContext(),
                                Utils.FOLDER,
                                String.format(Utils.PROFILE_PIC, idUser),
                                bitmap,
                                false);
                    Utils.showToast(getApplicationContext(), getString(R.string.fotoPerfilExito));
                    btnAceptar.setEnabled(true);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.fotoPerfilError));
                    btnAceptar.setEnabled(false);
                    break;
            }
            FileStorageManager.deleteFileFromUri(getApplicationContext(), uriFileSelect);

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
            btnAceptar.setEnabled(false);
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

    private void openGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Seleccionar imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, Utils.PICK_IMAGE);
    }

    private void saltar() {
        Utils.showToast(getApplicationContext(), getString(R.string.fotoPerfilRecordatorio));
        if (!isAdminMode) {
            Utils.showToast(getApplicationContext(), getString(R.string.iniciarSesionConf));
            finish();
        } else {
            finish();
        }

    }
}
