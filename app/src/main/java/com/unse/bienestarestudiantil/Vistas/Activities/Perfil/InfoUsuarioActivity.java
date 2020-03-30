package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.unse.bienestarestudiantil.Databases.AlumnosRepo;
import com.unse.bienestarestudiantil.Databases.EgresadosRepo;
import com.unse.bienestarestudiantil.Databases.ProfesorRepo;
import com.unse.bienestarestudiantil.Databases.UsuariosRepo;
import com.unse.bienestarestudiantil.Herramientas.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.StorageManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Egresado;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.VISIBLE;
import static com.unse.bienestarestudiantil.Herramientas.Utils.facultad;
import static com.unse.bienestarestudiantil.Herramientas.Utils.faya;
import static com.unse.bienestarestudiantil.Herramientas.Utils.fceyt;
import static com.unse.bienestarestudiantil.Herramientas.Utils.fcf;
import static com.unse.bienestarestudiantil.Herramientas.Utils.fcm;
import static com.unse.bienestarestudiantil.Herramientas.Utils.fhcys;

public class InfoUsuarioActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    ImageView btnBack;
    CircleImageView imgUser;
    LinearLayout latGeneral, latAlumno, latProfesor, latEgresado;
    FloatingActionButton fabEditar, fabPic;
    EditText edtNombre, edtApellido, edtDNI, edtSexo, edtMail, edtProfesionProf, edtAnioIngresoProf,
            edtProfesionEgre, edtAnioEgresoEgre, edtAnioIngresoAlu, edtLegajoAlu, edtDomicilio,
            edtProvincia, edtTelefono, edtPais, edtLocalidad, edtBarrio;
    TextView txtFechaNac;
    Spinner spinnerFacultad, spinnerCarrera;

    DialogoProcesamiento dialog;

    Usuario mUsuario = null;
    Object tipo = null;
    EditText[] campos;
    FragmentManager manager = null;

    Bitmap mBitmapFile;
    Uri uriFile;
    String nameFile;

    boolean isEdit = false, isReady = false;

    int fac = 0, carr = 0, mode = 0, TIPO_USER = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_usuario);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        setToolbar();

        loadData();

        loadListener();

        editMode(0);

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("");
        DrawableCompat.setTint(btnBack.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
    }

    private void loadListener() {
        txtFechaNac.setOnClickListener(this);
        fabEditar.setOnClickListener(this);
        fabPic.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        spinnerFacultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                spinnerFacultad.setSelection(position);
                switch (position) {
                    case 0:
                        //FAyA
                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, faya);
                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCarrera.setAdapter(dataAdapter2);
                        break;
                    case 1:
                        //FCEyT
                        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fceyt);
                        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCarrera.setAdapter(dataAdapter3);
                        break;
                    case 2:
                        //FCF
                        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fcf);
                        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCarrera.setAdapter(dataAdapter4);
                        break;
                    case 3:
                        //FCM
                        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fcm);
                        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCarrera.setAdapter(dataAdapter5);
                        break;
                    case 4:
                        //FHyCS
                        ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fhcys);
                        dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCarrera.setAdapter(dataAdapter6);
                        break;
                }
                if (fac != position && mode == 1)
                    isEdit = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
        spinnerCarrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mode == 0)
                    spinnerCarrera.setSelection(carr);
                if (carr != position && mode == 1)
                    isEdit = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerFacultad.setSelection(fac);

    }

    private void showDateDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                String mes, dia;
                if (month < 10) {
                    mes = "0" + month;
                } else
                    mes = String.valueOf(month);
                if (day < 10)
                    dia = "0" + day;
                else
                    dia = String.valueOf(day);
                final String selectedDate = year + "-" + mes + "-" + dia;
                if (!txtFechaNac.getText().toString().equals(selectedDate))
                    isEdit = true;
                txtFechaNac.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");

    }

    private void loadViews() {


        edtNombre = findViewById(R.id.edtNombre);
        edtApellido = findViewById(R.id.edtApellido);
        edtDNI = findViewById(R.id.edtDNI);
        edtSexo = findViewById(R.id.edtSexo);
        edtMail = findViewById(R.id.edtMail);
        edtProfesionProf = findViewById(R.id.edtProfesion1);
        edtAnioIngresoProf = findViewById(R.id.edtAnioIngProf);
        edtAnioIngresoAlu = findViewById(R.id.edtAnioIngrAlu);
        edtProfesionEgre = findViewById(R.id.edtProfesionEgre);
        edtAnioEgresoEgre = findViewById(R.id.edtAnioEgresoEgre);
        btnBack = findViewById(R.id.imgFlecha);
        spinnerCarrera = findViewById(R.id.spinner2);
        spinnerFacultad = findViewById(R.id.spinner1);
        edtProvincia = findViewById(R.id.edtProvincia);
        edtTelefono = findViewById(R.id.edtCelu);
        edtLocalidad = findViewById(R.id.edtLocalidad);
        edtDomicilio = findViewById(R.id.edtDomicilio);
        edtLegajoAlu = findViewById(R.id.edtLegajo);
        edtPais = findViewById(R.id.edtPais);
        edtBarrio = findViewById(R.id.edtBarrio);
        txtFechaNac = findViewById(R.id.edtFecha);
        fabEditar = findViewById(R.id.fab);
        fabPic = findViewById(R.id.fabPic);

        campos = new EditText[]{edtNombre, edtApellido, edtSexo, edtMail, edtProfesionProf,
                edtAnioIngresoProf, edtAnioIngresoAlu, edtProfesionEgre, edtAnioEgresoEgre, edtProvincia,
                edtPais, edtTelefono, edtLocalidad, edtDomicilio, edtLegajoAlu, edtBarrio};


        latGeneral = findViewById(R.id.layRango);
        latProfesor = findViewById(R.id.layProfesor);
        latEgresado = findViewById(R.id.layEgresado);
        latAlumno = findViewById(R.id.layAlumno);

        imgUser = findViewById(R.id.imgUserPerfil);
    }

    private void loadData() {
        manager = getSupportFragmentManager();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, facultad);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFacultad.setAdapter(dataAdapter);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, faya);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarrera.setAdapter(dataAdapter2);

        loadInfo();
    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        int id = manager.getValueInt(Utils.MY_ID);
        Usuario usuario = new UsuariosRepo(getApplicationContext()).get(id);
        if (usuario != null) {
            loadLayout(usuario.getTipoUsuario());
            edtDNI.setText(String.valueOf(id));
            edtDNI.setEnabled(false);
            edtNombre.setText(usuario.getNombre());
            edtApellido.setText(usuario.getApellido());
            edtSexo.setText(usuario.getSexo());
            txtFechaNac.setText(Utils.getFechaNameWithinHour(usuario.getFechaNac()));
            edtDomicilio.setText(usuario.getDomicilio());
            edtBarrio.setText(usuario.getBarrio());
            edtLocalidad.setText(usuario.getLocalidad());
            edtProvincia.setText(usuario.getProvincia());
            edtPais.setText(usuario.getPais());
            edtMail.setText(usuario.getMail());
            edtTelefono.setText(usuario.getTelefono());

            String URL = String.format("%s%s.jpg", Utils.URL_USUARIO_IMAGE_LOAD, id);
            Glide.with(imgUser.getContext()).load(URL).apply(new RequestOptions().error(R.drawable.ic_user).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_user)).into(imgUser);


            TIPO_USER = usuario.getTipoUsuario();

            if (usuario.getTipoUsuario() != 5) {
                if (usuario.getTipoUsuario() == 1) {
                    Alumno alumno = new AlumnosRepo(getApplicationContext()).get(id);
                    if (alumno != null) {
                        edtLegajoAlu.setText(alumno.getLegajo());
                        edtAnioIngresoAlu.setText(alumno.getAnio());
                        loadCarrera(alumno);

                    }
                } else if (usuario.getTipoUsuario() == 4) {
                    Egresado egresado = new EgresadosRepo(getApplicationContext()).get(id);
                    if (egresado != null) {
                        edtProfesionEgre.setText(egresado.getProfesion());
                        edtAnioEgresoEgre.setText(egresado.getFechaEgreso());
                    }
                } else if (usuario.getTipoUsuario() == 2) {
                    Profesor profesor = new ProfesorRepo(getApplicationContext()).get(id);
                    if (profesor != null) {
                        edtProfesionProf.setText(profesor.getProfesion());
                        edtAnioIngresoProf.setText(profesor.getFechaIngreso());
                    }
                }
            }
        }
    }

    private void loadCarrera(Alumno alumno) {
        List<String> facultad = Arrays.asList(Utils.facultad);
        List<String> faya = Arrays.asList(Utils.faya);
        List<String> fceyt = Arrays.asList(Utils.fceyt);
        List<String> fcf = Arrays.asList(Utils.fcf);
        List<String> fcm = Arrays.asList(Utils.fcm);
        List<String> fhcys = Arrays.asList(Utils.fhcys);
        int index = facultad.indexOf(alumno.getFacultad());
        fac = index;
        int index2 = -1;
        spinnerFacultad.setSelection(Math.max(index, 0));
        if (index != -1)
            switch (index) {
                case 0:
                    index2 = faya.indexOf(alumno.getCarrera());
                    //spinnerCarrera.setSelection(Math.max(index2, 0));
                    break;
                case 1:
                    index2 = fceyt.indexOf(alumno.getCarrera());
                    //spinnerCarrera.setSelection(Math.max(index2, 0));
                    break;
                case 2:
                    index2 = fcf.indexOf(alumno.getCarrera());
                    //spinnerCarrera.setSelection(Math.max(index2, 0));
                    break;
                case 3:
                    index2 = fcm.indexOf(alumno.getCarrera());
                    // spinnerCarrera.setSelection(Math.max(index2, 0));
                    break;
                case 4:
                    index2 = fhcys.indexOf(alumno.getCarrera());
                    // spinnerCarrera.setSelection(Math.max(index2, 0));
                    break;

            }
        carr = index2;
    }

    private void loadLayout(int tipoUsuario) {
        switch (tipoUsuario) {
            case 1:
                latGeneral.setVisibility(VISIBLE);
                latAlumno.setVisibility(VISIBLE);
                latEgresado.setVisibility(View.GONE);
                latProfesor.setVisibility(View.GONE);
                break;
            case 2:
                latGeneral.setVisibility(VISIBLE);
                latAlumno.setVisibility(View.GONE);
                latEgresado.setVisibility(View.GONE);
                latProfesor.setVisibility(VISIBLE);
                break;
            case 3:
            case 5:
                latGeneral.setVisibility(View.GONE);
                latAlumno.setVisibility(View.GONE);
                latEgresado.setVisibility(View.GONE);
                latProfesor.setVisibility(View.GONE);
                break;
            case 4:
                latGeneral.setVisibility(VISIBLE);
                latAlumno.setVisibility(View.GONE);
                latEgresado.setVisibility(VISIBLE);
                latProfesor.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabPic:
                openGallery();
                break;
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.edtFecha:
                showDateDialog();
                break;
            case R.id.fab:
                ObjectAnimator.ofFloat(fabEditar, "rotation", 0f, 360f).setDuration(600).start();
                activateEditMode();
                break;
        }
    }

    private void openGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Seleccionar imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, Utils.PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case Utils.PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri uri = null;
                    if (data.getData() != null) {
                        uri = data.getData();
                    }
                    Intent intent = new Intent(getApplicationContext(), CropImageActivity.class);
                    intent.putExtra(Utils.URI_IMAGE, uri);
                    startActivityForResult(intent, Utils.EDIT_IMAGE);
                } else if (resultCode == RESULT_CANCELED) {
                    Utils.showToast(getApplicationContext(), "No se guardaron cambios");
                }
                break;
            case Utils.EDIT_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri bitmap = null;
                    String name = "";
                    if (data.getParcelableExtra(Utils.URI_IMAGE) != null) {
                        bitmap = data.getParcelableExtra(Utils.URI_IMAGE);
                    }
                    if (data.getStringExtra("name") != null) {
                        name = data.getStringExtra("name");
                    }
                    if (bitmap != null) {
                        loadPic(bitmap, name);
                    }
                } else if (resultCode == 2) {
                    Utils.showToast(getApplicationContext(), "No se puedo recortar la imagen, intente nuevamente");
                } else if (resultCode == RESULT_CANCELED) {
                    Utils.showToast(getApplicationContext(), "No se guardaron cambios");
                }
                break;
        }

    }

    private void loadPic(final Uri uri, final String name) {
        // Get the file instance
        Utils.resizeBitmapAndFile(name);
        File file = new File(name);
        Bitmap in = BitmapFactory.decodeFile(file.getPath());
        mBitmapFile = in;
        uriFile = uri;
        nameFile = name;
        isReady = true;

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (isReady) {
            updatePicture(mBitmapFile, nameFile);
            isReady = false;
        }
    }


    private void updatePicture(final Bitmap mBitmap, final String name) {
        final int id = new PreferenceManager(getApplicationContext()).getValueInt(Utils.MY_ID);
        StringRequest requestImage = new StringRequest(Request.Method.POST, Utils.URL_USUARIO_IMAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuestaImagen(response, mBitmap, name);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.showToast(getApplicationContext(), "Error de comunicación con el servidor");
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parm = new HashMap<>();
                parm.put("id", String.valueOf(id));
                String info = Utils.convertImage(mBitmap);
                parm.put("img", info);
                return parm;
            }
        };
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(manager, "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(requestImage);
    }

    private void procesarRespuestaImagen(String response, Bitmap bitmap, String name) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case 1:
                    //Exito
                    Glide.with(imgUser.getContext()).load(bitmap).into(imgUser);
                    Utils.saveBitmap(getApplicationContext(), Utils.FOLDER, "pic.jpg", bitmap, false);
                    Utils.showToast(getApplicationContext(), "Foto actualizado!");
                    File file = new File(nameFile);
                    if (file.exists())
                        file.delete();
                    break;
                case 2:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), "No se pudo actualizar su foto de perfil");
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), "Error desconocido, contacta al Administrador");
        }
    }

    @Override
    public void onBackPressed() {
        if (isEdit) {
            Utils.showToast(getApplicationContext(), "Primero debe guardar los cambios");
        } else
            super.onBackPressed();
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

    private void editMode(int mode) {
        if (mode != 0) {
            fabEditar.setImageResource(R.drawable.ic_save);
            txtFechaNac.setOnClickListener(this);
            spinnerFacultad.setEnabled(true);
            spinnerCarrera.setEnabled(true);
            fabPic.setVisibility(VISIBLE);
        } else {
            fabEditar.setImageResource(R.drawable.ic_edit_);
            txtFechaNac.setOnClickListener(null);
            spinnerFacultad.setEnabled(false);
            spinnerCarrera.setEnabled(false);
            fabPic.setVisibility(View.INVISIBLE);
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

    private void save() {
        Validador validador = new Validador();

        String fecha = txtFechaNac.getText().toString().trim();
        String nombre = edtNombre.getText().toString().trim();
        String apellido = edtApellido.getText().toString().trim();
        String dni = edtDNI.getText().toString().trim();
        String sexo = edtSexo.getText().toString().trim();
        String mail = edtMail.getText().toString().trim();
        String profesion = edtProfesionProf.getText().toString().trim();
        String anioIngreso = edtAnioIngresoProf.getText().toString().trim();
        String faculta = facultad[spinnerFacultad.getSelectedItemPosition()].trim();
        String carrera = getCarrera(spinnerFacultad.getSelectedItemPosition()).trim();
        String anioIngreso2 = edtAnioIngresoAlu.getText().toString().trim();
        String profesion2 = edtProfesionEgre.getText().toString().trim();
        String anioEgreso = edtAnioEgresoEgre.getText().toString().trim();
        String domicilio = edtDomicilio.getText().toString().trim();
        String telefono = edtTelefono.getText().toString().trim();
        String pais = edtPais.getText().toString().trim();
        String provincia = edtProvincia.getText().toString().trim();
        String localidad = edtLocalidad.getText().toString().trim();
        String legajo = edtLegajoAlu.getText().toString().trim();
        String barrio = edtBarrio.getText().toString().trim();

        String token = new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN);

        UsuariosRepo usuariosRepo = new UsuariosRepo(getApplicationContext());
        int id = new PreferenceManager(getApplicationContext()).getValueInt(Utils.MY_ID);
        Usuario usuario = usuariosRepo.get(id);
        int tipoUsuario = usuario.getTipoUsuario();

        usuario = new Usuario(id, tipoUsuario, nombre, apellido, pais,
                provincia, localidad, domicilio, barrio, telefono, sexo, mail, "", usuario.getFoto(), Utils.getFechaDate(fecha), usuario.getFechaRegistro());
        mUsuario = usuario;

        //Comprobacion que no sean vacios
        if (!validador.noVacio(fecha, nombre, apellido, dni, sexo, mail
                , domicilio, telefono, pais, provincia, localidad, barrio)) {

            //Comprobacion del tipo d edatos
            if (validador.validarNombre(nombre) && validador.validarNombre(apellido) && validador.validarDNI(dni)
                    && validador.validarMail(mail) && sexo.length() == 1 && validador.validarNumero(telefono) &&
                    !validador.validarNombres(pais, provincia, localidad)) {

                //Comprobacion de tamaños
                if (validador.lengthMore(domicilio) && validador.lengthMore(pais) && validador.lengthMore(provincia) && validador.lengthMore(localidad)
                        && validador.lengthMore(barrio)) {

                    if (TIPO_USER == 1) {
                        if (!validador.noVacio(faculta, carrera, anioIngreso2, legajo)) {
                            if (validador.isLegajo(legajo)) {
                                sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad,
                                        domicilio, barrio, telefono, sexo, token, mail, 1, carrera, faculta, anioIngreso2
                                        , legajo, "SN", null, null));
                                tipo = new Alumno(usuario, carrera, faculta, legajo, anioIngreso2, id, "", 0);
                            } else
                                Utils.showToast(getApplicationContext(), "Número de legajo inválido");

                        } else {
                            Utils.showToast(getApplicationContext(), "Por favor, complete los campos");
                        }
                    } else if (TIPO_USER == 2) {
                        if (!validador.noVacio(profesion, anioIngreso)) {
                            if (validador.validarNumero(anioIngreso)) {
                                sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad,
                                        domicilio, barrio, telefono, sexo, token, mail,
                                        2, null, null, anioIngreso, null, "SN",
                                        profesion, null));
                                tipo = new Profesor(usuario, profesion, "", id, anioIngreso);
                            } else
                                Utils.showToast(getApplicationContext(), "El año de ingreso no es válido");
                        } else {
                            Utils.showToast(getApplicationContext(), "Por favor, complete los campos");
                        }
                    } else if (TIPO_USER == 4) {
                        if (!validador.noVacio(profesion2, anioEgreso)) {
                            if (validador.validarNumero(anioEgreso)) {
                                sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad, domicilio, barrio,
                                        telefono, sexo, token, mail, 4,
                                        null, null, null, null, "SN",
                                        profesion2, anioEgreso));
                                tipo = new Egresado(usuario, profesion2, "", id, anioEgreso);
                            } else
                                Utils.showToast(getApplicationContext(), "El año de ingreso no es válido");

                        } else {
                            Utils.showToast(getApplicationContext(), "Por favor, complete los campos");
                        }
                    } else {
                        //NoDocente y Particular
                        sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad, domicilio,
                                barrio, telefono, sexo, token, mail, TIPO_USER,
                                null, null, null, null, "SN", null, null));
                    }


                } else {
                    Utils.showToast(getApplicationContext(), "Hay campos con información inválida");
                }


            } else {
                Utils.showToast(getApplicationContext(), "Hay campos invalidos, corrijalos");
            }

        } else {
            Utils.showToast(getApplicationContext(), "Por favor, complete todos los campos");

        }


    }

    public String processString(String dni, String nombre, String apellido, String
            fecha, String pais, String provincia, String localidad, String domicilio,
                                String barrio, String telefono, String sexo, String key, String mail,
                                int tipo, String carrera, String facultad, String anioIng, String legajo, String
                                        contrasenia, String profesion, String anioEgreso) {
        String resp = "";
        if (tipo == 1) {
            resp = String.format(Utils.dataAlumno, dni, nombre, apellido, fecha, pais, provincia, localidad, domicilio, sexo, key, carrera, facultad,
                    anioIng, legajo, contrasenia, Utils.getFechaNameWithinHour(new Date(System.currentTimeMillis())),
                    "1", mail, telefono, barrio, Utils.getFechaName(new Date(System.currentTimeMillis())));

        } else if (tipo == 2) {
            resp = String.format(Utils.dataProfesor, dni, nombre, apellido, fecha, pais, provincia,
                    localidad, domicilio, sexo, key, contrasenia, Utils.getFechaNameWithinHour(new Date(System.currentTimeMillis())), tipo, mail, telefono,
                    profesion, anioIng, barrio, Utils.getFechaName(new Date(System.currentTimeMillis())));

        } else if (tipo == 4) {
            resp = String.format(Utils.dataEgresado, dni, nombre, apellido, fecha, pais, provincia,
                    localidad, domicilio, sexo, key, contrasenia, Utils.getFechaName(new Date(System.currentTimeMillis())),
                    tipo, mail, telefono, profesion, anioEgreso, barrio, Utils.getFechaName(new Date(System.currentTimeMillis())));
        } else {
            resp = String.format(Utils.dataPartiNoDoc, dni, nombre, apellido, fecha, pais, provincia, localidad,
                    domicilio, sexo, key, contrasenia, Utils.getFechaNameWithinHour(new Date(System.currentTimeMillis())),
                    tipo, mail, telefono, barrio, Utils.getFechaName(new Date(System.currentTimeMillis())));
        }
        return resp;
    }

    public void sendServer(String data) {


        String URL = Utils.URL_USUARIO_ACTUALIZAR + data;
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getApplicationContext(), "Error de conexión o servidor fuera de rango");
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private String getCarrera(int selectedItemPosition) {
        switch (selectedItemPosition) {
            case 0:
                return faya[spinnerCarrera.getSelectedItemPosition()];
            case 1:
                return fceyt[spinnerCarrera.getSelectedItemPosition()];
            case 2:
                return fcf[spinnerCarrera.getSelectedItemPosition()];
            case 3:
                return fcm[spinnerCarrera.getSelectedItemPosition()];
            case 4:
                return fhcys[spinnerCarrera.getSelectedItemPosition()];
        }
        return "";
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case 1:
                    //Exito
                    Utils.showToast(getApplicationContext(), "Registro actualizado!");
                    isEdit = false;
                    activateEditMode();
                    updateInBD(mUsuario, tipo);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), "Error interno al intentar actualizar, intente nuevamente");
                    break;
                case 4:
                    //Ya existe
                    Utils.showToast(getApplicationContext(), "El usuario ingresado no existe");
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), "No se puede procesar la tarea solicitada");
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), "No está autorizado para realizar ésta operación");
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), "Error desconocido, contacta al Administrador");
        }
    }

    private void updateInBD(Usuario usuario, Object tipo) {
        UsuariosRepo usuariosRepo = new UsuariosRepo(getApplicationContext());
        usuariosRepo.update(usuario);
        if (tipo instanceof Alumno) {
            AlumnosRepo alumnosRepo = new AlumnosRepo(getApplicationContext());
            alumnosRepo.update((Alumno) tipo);
        } else if (tipo instanceof Profesor) {
            ProfesorRepo profesorRepo = new ProfesorRepo(getApplicationContext());
            profesorRepo.update((Profesor) tipo);
        } else if (tipo instanceof Egresado) {
            EgresadosRepo egresadosRepo = new EgresadosRepo(getApplicationContext());
            egresadosRepo.update((Egresado) tipo);
        }
        loadInfo();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isEdit = true;
        s.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
