package com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionArchivos;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUriExposedException;
import android.os.StrictMode;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.unse.bienestarestudiantil.Databases.UsuarioViewModel;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.PDF.DownloadPDF;
import com.unse.bienestarestudiantil.Herramientas.UploadManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleyMultipartRequest;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.Archivo;
import com.unse.bienestarestudiantil.Modelos.Area;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.RequiresApi;

public class NuevoArchivoActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtNombre;
    TextView txtNombre, txtTamano, txtTipo, txtDisponibilidad;
    Button btnGuardar, btnVerArchivo;
    Spinner mSpinnerArea;
    ArrayList<Area> mList;
    ArrayAdapter<String> areasAdapter;
    int positionArea = -1;
    boolean isBig = false, isUpdate = true;
    LinearLayout latElegir;
    Uri uriFile = null;
    String nameArchivo = null;
    DialogoProcesamiento dialog;
    Usuario mUsuario;
    UsuarioViewModel mUsuarioViewModel;
    LinearLayout latSwitch;
    SwitchCompat mSwitchCompat;
    Archivo mArchivo;
    ImageView imgIcono;
    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_archivo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        isEdit();

        loadViews();

        setToolbar();

        loadData();

        loadListener();

      StrictMode.setVmPolicy(builder.build());
      builder.detectFileUriExposure();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText(getString(R.string.tituloArchivosNuevo));
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    private void isEdit() {
        if (getIntent().getSerializableExtra(Utils.ARCHIVO_NAME) != null) {
            mArchivo = (Archivo) getIntent().getSerializableExtra(Utils.ARCHIVO_NAME);
        }
    }

    private void loadListener() {
        btnVerArchivo.setOnClickListener(this);
        imgIcono.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        latElegir.setOnClickListener(this);
        if (mArchivo != null) {
            latSwitch.setOnClickListener(this);
            mSwitchCompat.setOnClickListener(this);
        }else mSwitchCompat.setEnabled(false);
    }

    private void updateReg(boolean reg) {
        if (reg) {
            txtDisponibilidad.setText("DISPONIBLE");
            mSwitchCompat.setChecked(true);
            Utils.changeColor(latSwitch.getBackground(), getApplicationContext(), R.color.colorGreen);
            if (mArchivo != null) mArchivo.setValidez(1);
        } else {
            txtDisponibilidad.setText("NO DISPONIBLE");
            mSwitchCompat.setChecked(false);
            Utils.changeColor(latSwitch.getBackground(), getApplicationContext(), R.color.colorPrimaryDark);
            if (mArchivo != null) mArchivo.setValidez(0);
        }
    }

    public void setArea(int idArea) {
        int pos = -1;
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getIdArea() == idArea) {
                pos = i;
                break;
            }
        }
        if (pos == -1) {
            mSpinnerArea.setSelection(0);
        } else {
            positionArea = pos;
            mSpinnerArea.setSelection(positionArea);
        }

    }

    private void loadData() {
        mUsuarioViewModel = new UsuarioViewModel(getApplicationContext());
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        int id = preferenceManager.getValueInt(Utils.MY_ID);
        mUsuario = mUsuarioViewModel.getById(id);
        txtTipo.setText("");
        txtTamano.setText("");
        txtNombre.setText("");
        mList = new ArrayList<>();
        if (getIntent().getSerializableExtra(Utils.LIST_REGULARIDAD) != null) {
            mList = (ArrayList<Area>) getIntent().getSerializableExtra(Utils.LIST_REGULARIDAD);
        }
        List<String> list = new ArrayList<>();
        for (Area area : mList) {
            list.add(area.getDescripcion());
        }
        areasAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, list);
        mSpinnerArea.setAdapter(areasAdapter);
        mSpinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionArea = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (mArchivo != null) {
            btnVerArchivo.setVisibility(View.VISIBLE);
            findViewById(R.id.viewSpace).setVisibility(View.VISIBLE);
            edtNombre.setText(mArchivo.getNombre());
            updateReg(mArchivo.getValidez() == 1);
            setArea(mArchivo.getIdArea());
            int index = mArchivo.getNombreArchivo().lastIndexOf('.');
            String ext = mArchivo.getNombreArchivo().substring(index + 1);
            txtNombre.setText(mArchivo.getNombreArchivo().substring(0, index != -1 ? index : mArchivo.getNombreArchivo().length()));
            txtTipo.setText(ext.toUpperCase());
            txtTamano.setText("NO DISPONIBLE");
            nameArchivo = mArchivo.getNombreArchivo();

        } else {
            btnVerArchivo.setVisibility(View.GONE);
            findViewById(R.id.viewSpace).setVisibility(View.GONE);
            updateReg(true);
            latSwitch.setAlpha(0.5f);
            latSwitch.setClickable(false);
        }
    }

    private void loadViews() {
        btnVerArchivo = findViewById(R.id.btnVer);
        latElegir = findViewById(R.id.latDatos);
        edtNombre = findViewById(R.id.edtNombre);
        txtNombre = findViewById(R.id.txtNombre);
        txtTamano = findViewById(R.id.txtDescripcion);
        txtTipo = findViewById(R.id.txtEstado);
        btnGuardar = findViewById(R.id.btnAceptar);
        mSpinnerArea = findViewById(R.id.spinner1);
        txtDisponibilidad = findViewById(R.id.txtDisponibilidad);
        latSwitch = findViewById(R.id.latSwitch);
        mSwitchCompat = findViewById(R.id.switchCred);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVer:
                downloadFile();
                break;
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.latSwitch:
            case R.id.switchCred:
                updateReg(mArchivo.getValidez() != 1);
                break;
            case R.id.btnAceptar:
                guardar();
                break;
            case R.id.latDatos:
                openFiles();
                break;
        }
    }

    private void downloadFile() {
        DownloadPDF downloadPDF = new DownloadPDF(getApplicationContext(), mArchivo.getNombreArchivo(),
                getSupportFragmentManager(), new YesNoDialogListener() {
            @Override
            public void yes() {
                openFile(mArchivo);
            }

            @Override
            public void no() {
                Utils.showToast(getApplicationContext(), getString(R.string.archivoError));
            }
        }, true);
        String URL = String.format("%s%s", Utils.URL_ARCHIVOS, mArchivo.getNombreArchivo());
        downloadPDF.execute(URL);
    }

    public void openFile(Archivo archivo){
        File file = new File(Utils.getDirectoryPath(true, getApplicationContext()) + archivo.getNombreArchivo());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (file.exists()) {
            Uri uri = null;
            String path = file.getPath();
            String absolite = file.getAbsolutePath();
            Utils.showLog("jeje", path+absolite);
            String extension = mArchivo.getNombreArchivo().substring(archivo.getNombreArchivo().lastIndexOf('.')+1);
            switch (extension.toUpperCase()){
                case "PNG":
                case "JPG":
                case "JPEG":
                    uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext()
                    .getPackageName() + ".provider", file);
                    intent.setDataAndType(uri, "image/*");
                    break;
                case "PDF":
                    Uri apkURI = FileProvider.getUriForFile(
                            getApplicationContext(),
                            getApplicationContext()
                                    .getPackageName() + ".provider", file);
                    intent.setDataAndType(apkURI, "application/pdf");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    break;
                case "DOC":
                case "DOCX":
                    uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext()
                            .getPackageName() + ".provider", file);
                    intent.setDataAndType(uri, "application/msword");
                    break;
                case "PPT":
                    uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext()
                            .getPackageName() + ".provider", file);
                    intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                    break;
                case "ZIP":
                    uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext()
                            .getPackageName() + ".provider", file);
                    intent.setDataAndType(uri, "application/zip");
                    break;
                case "RAR":
                    uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext()
                            .getPackageName() + ".provider", file);
                    intent.setDataAndType(uri, "application/x-rar-compressed");
                    break;
                case "XLS":
                    uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext()
                            .getPackageName() + ".provider", file);
                    intent.setDataAndType(uri, "application/vnd.ms-excel");
                    break;
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Utils.showToast(getApplicationContext(), String.format(getString(R.string.archivoNoAbrir), extension.toUpperCase()));
            }
        } else
            Utils.showToast(getApplicationContext(), getString(R.string.archivoEliminado));

    }
    private void openFiles() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, Utils.SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Utils.SELECT_FILE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        loadInfo(uri);
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Utils.showToast(getApplicationContext(), getString(R.string.archivoSeleecionError));
                }
                break;
        }
    }

    private void loadInfo(Uri uri) {
        isUpdate = false;
        isBig = false;
        uriFile = uri;
        String nombreArchivo = null;
        Long size = null;
        String extension = null;
        if (uri.getScheme() != null && uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                nombreArchivo = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                size = cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE));
                cursor.close();
            }
        }
        if (nombreArchivo == null) {
            nombreArchivo = uri.getPath();
            int cut = nombreArchivo.lastIndexOf('/');
            if (cut != -1) {
                nombreArchivo = nombreArchivo.substring(cut + 1);
            }
        }
        int index = nombreArchivo.lastIndexOf('.');
        extension = nombreArchivo.substring(index + 1);
        nameArchivo = nombreArchivo;
        txtNombre.setText(nombreArchivo.substring(0, index != -1 ? index : nombreArchivo.length()));
        txtTipo.setText(extension.toUpperCase());
        txtTamano.setText(Utils.getSizeFile(size));
        if (size >= (2 * (1024 * 1024))) {
            txtTamano.setTextColor(getResources().getColor(R.color.colorRed));
            txtTamano.setText(txtTamano.getText() + "\n¡ARCHIVO DEMASIADO GRANDE!");
            isBig = true;
        }else{
            txtTamano.setTextColor(getResources().getColor(R.color.colorTextDefault));
        }

    }


    private void guardar() {
        if (uriFile != null || isUpdate) {
            if (!edtNombre.getText().toString().equals("")) {
                if (!isBig) {
                    send();
                } else Utils.showToast(getApplicationContext(), getString(R.string.archivoGrande));

            } else {
                edtNombre.setError(getString(R.string.campoVacio));
            }
        } else Utils.showToast(getApplicationContext(), getString(R.string.archivoNoSeleccionado));
    }

    private void send() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        VolleyMultipartRequest.DataPart dataPart = null;
        if (uriFile != null)
            dataPart = new VolleyMultipartRequest.DataPart(
                    nameArchivo,
                    Utils.getFileDataFromUri(getApplicationContext(), uriFile)
            );
        HashMap<String, String> param = new HashMap<>();
        param.put("nn", edtNombre.getText().toString().trim());
        param.put("na", nameArchivo);
        param.put("aa", String.valueOf(mList.get(positionArea).getIdArea()));
        param.put("ff", Utils.getFechaName(new Date(System.currentTimeMillis())));
        param.put("idU", String.valueOf(mUsuario.getIdUsuario()));
        param.put("id", String.valueOf(mUsuario.getIdUsuario()));
        param.put("key", manager.getValueString(Utils.TOKEN));
        if (isUpdate) {
            param.put("idA", String.valueOf(mArchivo.getId()));
            param.put("est", String.valueOf(mArchivo.getValidez()));
            if (uriFile == null) {
                param.put("up", String.valueOf(100));
            }
        } else {
            if (mArchivo != null) {
                param.put("idA", String.valueOf(mArchivo.getId()));
                param.put("est", String.valueOf(mArchivo.getValidez()));
            }
        }
        VolleyMultipartRequest volleyMultipartRequest = new UploadManager.Builder(getApplicationContext())
                .setMetodo(Request.Method.POST)
                .setURL(Utils.URL_UPLOAD_FILE)
                .setOkListener(new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        procesarRespuesta(new String(response.data));
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
                .setTipoDato(UploadManager.FILE)
                .setParams(param)
                .build();
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(volleyMultipartRequest);
    }

    private void procesarRespuesta(String s) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(s);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case 1:
                    //Exito
                    if (isUpdate)
                        Utils.showToast(getApplicationContext(), getString(R.string.archivoActualizado));
                    else
                        Utils.showToast(getApplicationContext(), getString(R.string.archivoSubido));
                    finish();
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.archivoError));
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }
}
