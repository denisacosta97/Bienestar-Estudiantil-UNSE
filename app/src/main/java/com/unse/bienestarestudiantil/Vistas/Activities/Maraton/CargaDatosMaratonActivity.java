package com.unse.bienestarestudiantil.Vistas.Activities.Maraton;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Categoria;
import com.unse.bienestarestudiantil.Modelos.Maraton;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.CategoriasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CargaDatosMaratonActivity extends AppCompatActivity
        implements View.OnClickListener {

    Button btnCargar, btnSiguiente, btnRegistrar, btnInscribir;
    EditText edtNombre, edtDNI, edtFechaNac, edtMail, edtTelefono,
            edtPais, edtProv, edtLocalidad, edtDistancia, edtCat, edtApellido;
    TextView txtDescripcion;
    ImageView imgIcono;
    RecyclerView recyclerTipoUsuario;
    CategoriasAdapter adapterCategorias;
    RecyclerView.LayoutManager mManager;
    ArrayList<Categoria> mList;
    ArrayList<Maraton> datos;
    DialogoProcesamiento dialog;
    int pos = 0;
    String[] rangoEdad = {"Menor de 18 años", "19 a 24", "25 a 29", "30 a 34", "35 a 39", "40 a 44", "45 a 49", "50 a 54", "55 a 59", "60 o más"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_datos_maraton);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadData();

        loadListener();

        setToolbar();

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
        btnCargar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        btnInscribir.setOnClickListener(this);
    }

    private void loadViews() {
        edtApellido = findViewById(R.id.edtApellido);
        btnInscribir = findViewById(R.id.btnAceptar);
        btnRegistrar = findViewById(R.id.btnRegister);
        imgIcono = findViewById(R.id.imgFlecha);
        btnCargar = findViewById(R.id.btnCargar);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        txtDescripcion = findViewById(R.id.txtInfo);
        edtDistancia = findViewById(R.id.edtDistancia);
        edtCat = findViewById(R.id.edtCategoria);
        edtFechaNac = findViewById(R.id.edtFecha);
        edtProv = findViewById(R.id.edtProvincia);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtLocalidad = findViewById(R.id.edtLocalidad);
        edtPais = findViewById(R.id.edtPais);
        edtNombre = findViewById(R.id.edtNombre);
        edtDNI = findViewById(R.id.edtDNI);
        edtMail = findViewById(R.id.edtEmail);
        recyclerTipoUsuario = findViewById(R.id.recycler);


    }

    private void loadData() {
        txtDescripcion.setText("Hay 0 registros");
        mList = new ArrayList<>();
        mList.add(new Categoria(1, "Estudiante"));
        mList.add(new Categoria(2, "Docente"));
        mList.add(new Categoria(3, "Nodocente"));
        mList.add(new Categoria(4, "Graduado"));
        mList.add(new Categoria(5, "Particular"));

        recyclerTipoUsuario.setHasFixedSize(true);
        mManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        recyclerTipoUsuario.setLayoutManager(mManager);
        adapterCategorias = new CategoriasAdapter(mList, getApplicationContext());
        recyclerTipoUsuario.setAdapter(adapterCategorias);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Carga de Datos");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnRegister:
                if (datos != null && datos.size() > 0)
                    sendServer(datos.get(pos));
                break;
            case R.id.btnAceptar:
                if (datos != null && datos.size() > 0)
                    inscribir(datos.get(pos));
                break;
            case R.id.btnCargar:
                openFile();
                break;
            case R.id.btnSiguiente:
                siguiente();
                break;
        }
    }

    public void sendServer(final Maraton data) {
        String URL = Utils.URL_USUARIO_INSERTAR;
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("idU", String.valueOf(data.getDNI()));
                param.put("nom", data.getNombre());
                param.put("ape", " ");
                param.put("fechan", data.getFechaNac());
                param.put("pais", data.getPais().toUpperCase());
                String p = data.getProvincia().toLowerCase();
                if (p.contains("sde") || p.contains("sgo") || p.contains("estero") || p.contains("santiago")
                        || p.contains("tiago")) {
                    param.put("prov", "5486 - SANTIAGO DEL ESTERO");
                } else {
                    param.put("prov", data.getPais());
                }
                param.put("local", data.getLocalidad());
                param.put("dom", "Sin datos");
                param.put("sex", "Sin datos");
                param.put("tipo", String.valueOf(data.getTipoUsuario() + 1));
                param.put("mail", data.getMail());
                param.put("tel", data.getTelefono());
                param.put("barr", "Sin datos");
                param.put("key", "jeje");
                if (data.getTipoUsuario() + 1 == 1) {
                    param.put("car", "Sin datos");
                    param.put("fac", "Sin datos");
                    param.put("anio", "2021");
                    param.put("leg", "Sin datos");
                    param.put("idReg", "0");

                } else if (data.getTipoUsuario() + 1 == 2) {
                    param.put("prof", "Sin datos");
                    param.put("fechain", "Sin datos");
                } else if (data.getTipoUsuario() + 1 == 4) {
                    param.put("prof", "Sin datos");
                    param.put("fechaeg", "Sin datos");
                }
                String tel = data.getTelefono();
                String contrasenia = String.format("%s%s%s", data.getDNI(),
                        tel.charAt(tel.length() - 1), tel.charAt(tel.length() - 2));
                param.put("pass", contrasenia);
                return param;
            }
        };
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
                    Utils.showToast(getApplicationContext(), getString(R.string.usuarioRegistrado));
                    break;
                case 2:
                    //Error 1
                    Utils.showToast(getApplicationContext(), getString(R.string.usuarioNoRegistro));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.usuarioExiste));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 5:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorCredencialToken));
                case 100:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void inscribir(final Maraton maraton) {
        final HashMap<String, String> map = new HashMap<>();
        String URL = Utils.URL_INSCRIPCION_MARATON;
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        final int id = preferenceManager.getValueInt(Utils.MY_ID);
        final String token = preferenceManager.getValueString(Utils.TOKEN);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuestaInscribirse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                map.put("key", token);
                map.put("idU", String.valueOf(id));
                map.put("iu", String.valueOf(maraton.getDNI()));
                map.put("ca", String.valueOf(maraton.getCategoria()));
                map.put("di", maraton.getCarrera());

                return map;
            }
        };
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void procesarRespuestaInscribirse(String response) {
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
                    Utils.showToast(getApplicationContext(), getString(R.string.inscripcionExitosa));
                    break;
                case 2:
                case 5:
                    Utils.showToast(getApplicationContext(), getString(R.string.inscripcionYaRegistrada));
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

    private void siguiente() {
        if (datos.size() != 0) {
            pos++;
            loadInfo(pos);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utils.REQUEST_FILE_CSV) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                String extension = "";
                if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
                    //If scheme is a content
                    final MimeTypeMap mime = MimeTypeMap.getSingleton();
                    extension = mime.getExtensionFromMimeType(getApplicationContext().getContentResolver().getType(uri));
                } else {
                    extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

                }
                if (extension.toLowerCase().equals("tsv")){
                    try {
                        InputStream inStream = getContentResolver().openInputStream(uri);
                        if (inStream != null) {
                            readData(inStream);
                            loadInfo(pos);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }else
                    Utils.showToast(getApplicationContext(), getString(R.string.noCSV));


            }
        }
    }

    private void loadInfo(int i) {
        if (i <= (datos.size() - 1)) {
            try {
                txtDescripcion.setText(String.format("Hay %s registros, mostrando %s de %s",
                        datos.size(), (i + 1), datos.size()));
                Maraton maraton = datos.get(pos);
                edtApellido.setText(" ");
                edtNombre.setText(maraton.getNombre());
                edtDNI.setText(String.valueOf(maraton.getDNI()));
                edtFechaNac.setText(maraton.getFechaNac());
                edtTelefono.setText(maraton.getTelefono());
                edtMail.setText(maraton.getMail());
                edtPais.setText(maraton.getPais());
                edtProv.setText(maraton.getProvincia());
                edtLocalidad.setText(maraton.getLocalidad());
                edtDistancia.setText(maraton.getCarrera());
                edtCat.setText(String.format("%s - %s", maraton.getCategoria(),
                        rangoEdad[maraton.getCategoria() - 1]));
                reset();
                try {
                    mList.get(maraton.getTipoUsuario()).setEstado(true);
                } catch (IndexOutOfBoundsException e) {
                    mList.get(4).setEstado(true);
                }
                adapterCategorias.notifyDataSetChanged();
            } catch (Exception e) {
                txtDescripcion.setText("ERROR EN CARGAR DATOS " + (i + 1));
            }

        } else {
            txtDescripcion.setText("ERROR EN DATOS");
        }

    }

    private void reset() {
        for (Categoria cat : mList) {
            cat.setEstado(false);
        }
    }

    private void openFile() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("*/*");
        Intent intent = Intent.createChooser(chooseFile, "Selecciona un CSV");
        startActivityForResult(intent, Utils.REQUEST_FILE_CSV);
    }

    private void readData(InputStream is) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        datos = new ArrayList<>();

        try {
            int i = 0;
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                if (i != 0) {
                    Maraton maraton;
                    try {
                        String[] tokens = line.split("\t");
                        maraton = new Maraton();
                       /* String[] nombre = tokens[1].split(" ");
                        String nom = tokens[1].substring(0,
                                tokens[1].lastIndexOf(" ") == -1 ?
                                        tokens[1].length() :
                                        tokens[1].lastIndexOf(" ")
                        ).trim();
                        if (nombre.length >= 2) {

                            String apellido = nombre[nombre.length - 1].trim();
                            maraton.setApellido(apellido);
                        } else {
                            maraton.setApellido(" ");
                        }*/
                        maraton.setNombre(tokens[1]);


                        maraton.setMail(tokens[2]);
                        maraton.setDNI(tokens[3]);
                        maraton.setTelefono(tokens[4]);
                        maraton.setPais(tokens[5]);
                        maraton.setProvincia(tokens[6]);
                        maraton.setLocalidad(tokens[7]);
                        String fecha = tokens[8];
                        fecha = fecha.replaceAll("\\.", "");
                        if (!fecha.contains("/") && !fecha.contains("-")) {
                            fecha = fecha.replaceAll(" ", "/");
                        }
                        fecha = fecha.replaceAll(" ", "");
                        if (fecha.contains("-")) {
                            fecha = fecha.replaceAll("-", "/");
                        }
                        String[] dias = fecha.split("/");
                        if (Integer.parseInt(dias[0]) < 10 && dias[0].length() == 1) {
                            dias[0] = "0" + dias[0];
                        }
                        if (Integer.parseInt(dias[1]) < 10 && dias[1].length() == 1) {
                            dias[1] = "0" + dias[1];
                        }
                        if (dias[2].length() == 2) {
                            if (Integer.parseInt(dias[2]) < 22) {
                                dias[2] = "20" + dias[2];
                            } else {
                                dias[2] = "19" + dias[2];
                            }
                        }
                        fecha = String.format("%s/%s/%s", dias[2], dias[1], dias[0]);
                        maraton.setFechaNac(fecha);
                        String categoria = tokens[9];
                        if (categoria.contains(",")) {
                            String[] cats = categoria.split(",");
                            for (int j = 0; j < cats.length; j++) {
                                int p = getPos(cats[j]);
                                cats[j] = String.valueOf(p);
                            }
                            Arrays.sort(cats);
                            maraton.setTipoUsuario(Integer.parseInt(cats[0]));

                        } else {
                            maraton.setTipoUsuario(getPos(categoria));
                        }
                        maraton.setCarrera(tokens[10].replace("KM", "").trim());
                        String edad = tokens[11];
                        maraton.setCategoria(getPosCategoria(edad));

                        datos.add(maraton);
                    } catch (Exception e) {
                        txtDescripcion.setText("ERROR AL CARGAR LOS DATOS");
                        break;
                    }


                }

                i++;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public int getPosCategoria(String cat) {
        int i = 0;
        for (String categoria : rangoEdad) {
            if (categoria.toLowerCase().trim().equals(cat.toLowerCase().trim()))
                return i + 1;
            else
                i++;
        }
        return i + 1;
    }

    public int getPos(String cat) {
        int i = 0;
        for (Categoria categoria : mList) {
            if (categoria.getNombre().toUpperCase().trim().equals(cat.toUpperCase().trim()))
                return i;
            else
                i++;
        }
        return 4;
    }
}
