package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.unse.bienestarestudiantil.Databases.AlumnoViewModel;
import com.unse.bienestarestudiantil.Databases.EgresadoViewModel;
import com.unse.bienestarestudiantil.Databases.ProfesorViewModel;
import com.unse.bienestarestudiantil.Databases.UsuarioViewModel;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.FileStorageManager;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Egresado;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Inicio.LoginActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView btnBack;
    TextView txtNombre, txtDescripcion, txtFacultad;
    CircleImageView imgPerfil;

    ArrayList<Opciones> mList, mListActividades;
    OpcionesAdapter mAdapter, mAdapterActividades;
    RecyclerView mRecyclerViewFunciones, mRecyclerActividades;
    RecyclerView.LayoutManager mLayoutManager, mLayoutActividades;

    UsuarioViewModel mUsuarioViewModel;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        setToolbar();

        loadData();

        loadListener();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("");
        DrawableCompat.setTint(btnBack.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
    }

    private void loadData() {

        mUsuarioViewModel = new UsuarioViewModel(getApplicationContext());

        loadInfo();

        mList = new ArrayList<>();
        mList.add(new Opciones(LinearLayout.VERTICAL, true, 1, "Mis Datos", R.drawable.ic_data, R.color.colorWhite, R.color.colorTextDefault, 12));
        //mList.add(new Opciones(LinearLayout.VERTICAL, true, 3, "Credenciales", R.drawable.ic_credencial, R.color.colorWhite, R.color.colorTextDefault, 12));
        //mList.add(new Opciones(LinearLayout.VERTICAL, true, 4, "Gestión de Archivos", R.drawable.ic_pdf, R.color.colorWhite, R.color.colorTextDefault, 12));
        //mList.add(new Opciones(LinearLayout.VERTICAL, false, 5, "¿Mensajes?", R.drawable.ic_msg, R.color.colorWhite, R.color.colorTextDefault, 12));
        //mList.add(new Opciones(LinearLayout.VERTICAL, true, 7, "Inscripciones", R.drawable.ic_inscipcion, R.color.colorWhite, R.color.colorTextDefault, 12));
        mList.add(new Opciones(LinearLayout.VERTICAL, true, 2, "Configuraciones", R.drawable.ic_settings, R.color.colorWhite, R.color.colorTextDefault, 12));
        mList.add(new Opciones(LinearLayout.VERTICAL, true, 6, "Cerrar Sesión", R.drawable.ic_cerrar_e, R.color.colorWhite, R.color.colorTextDefault, 12));

        mAdapter = new OpcionesAdapter(mList, getApplicationContext(),1);

        mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);

        mRecyclerViewFunciones.setLayoutManager(mLayoutManager);
        mRecyclerViewFunciones.setHasFixedSize(true);
        mRecyclerViewFunciones.setAdapter(mAdapter);

        mListActividades = new ArrayList<>();
      //  mListActividades.add(new Opciones(LinearLayout.VERTICAL, false, 1, "Reservas", R.drawable.ic_reservas, R.color.colorWhite, R.color.colorTextDefault, 12));
       // mListActividades.add(new Opciones(LinearLayout.VERTICAL, false, 2, "Historia Clínica", R.drawable.ic_historia_clinica, R.color.colorWhite, R.color.colorTextDefault, 12));
        //mListActividades.add(new Opciones(LinearLayout.VERTICAL, false, 3, "Historial de Turnos", R.drawable.ic_turnos, R.color.colorWhite, R.color.colorTextDefault, 12));


        mAdapterActividades = new OpcionesAdapter(mListActividades, getApplicationContext(),1);
        mLayoutActividades = new GridLayoutManager(getApplicationContext(), 3);

        mRecyclerActividades.setLayoutManager(mLayoutActividades);
        mRecyclerActividades.setHasFixedSize(true);
        mRecyclerActividades.setAdapter(mAdapterActividades);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadInfo();
    }


    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        int idLocal = manager.getValueInt(Utils.MY_ID);
        usuario = mUsuarioViewModel.getById(idLocal);
        
        if (txtNombre != null) {
            txtNombre.setText("NOMBRE");
            txtDescripcion.setText("CARRERA");
            txtFacultad.setText("RANGO");
            loadProfilePicture();
            if (usuario != null) {
                txtNombre.setText(String.format("%s %s", usuario.getNombre(), usuario.getApellido()));
                if (usuario.getTipoUsuario() != 5) {
                    if (usuario.getTipoUsuario() == 1) {
                        Alumno alumno = null;
                        AlumnoViewModel alumnoViewModel = new AlumnoViewModel(getApplicationContext());
                        alumno = alumnoViewModel.getById(idLocal);
                        if (alumno != null) {
                            txtDescripcion.setText(alumno.getCarrera());
                            txtFacultad.setText(alumno.getFacultad());
                        }
                    } else if (usuario.getTipoUsuario() == 4) {
                        Egresado egresado = null;
                        EgresadoViewModel egresadoViewModel = new EgresadoViewModel(getApplicationContext());
                        egresado = egresadoViewModel.getById(idLocal);
                        if (egresado != null) {
                            txtDescripcion.setText(egresado.getProfesion());
                            txtFacultad.setText(View.GONE);
                        }
                    } else if (usuario.getTipoUsuario() == 2) {
                        Profesor profesor = null;
                        ProfesorViewModel profesorViewModel = new ProfesorViewModel(getApplicationContext());
                        profesor = profesorViewModel.getById(idLocal);
                        if (profesor != null) {
                            txtDescripcion.setText(profesor.getProfesion());
                            txtFacultad.setText("PROFESOR");
                        }
                    } else if (usuario.getTipoUsuario() == 3) {
                        txtDescripcion.setVisibility(View.GONE);
                        txtFacultad.setText("NODOCENTE");
                    }
                } else {
                    txtDescripcion.setVisibility(View.GONE);
                    txtFacultad.setText("PARTICULAR");
                }
            }
        }

    }

    private void loadProfilePicture() {
        Bitmap bitmap = FileStorageManager.getBitmap(getApplicationContext(), Utils.FOLDER, String.format(Utils.PROFILE_PIC, usuario.getIdUsuario()), false);
        if (bitmap != null) {
            Glide.with(imgPerfil.getContext()).load(bitmap).into(imgPerfil);
        } else {
            String URL = String.format("%s%s.jpg", Utils.URL_USUARIO_IMAGE_LOAD, usuario.getIdUsuario());
            Glide.with(imgPerfil.getContext()).load(URL)
                    .apply(new RequestOptions().error(R.drawable.ic_user)
                            .diskCacheStrategy(DiskCacheStrategy.DATA).placeholder(R.drawable.ic_user))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            FileStorageManager.saveBitmap(getApplicationContext(), Utils.FOLDER,
                                    String.format(Utils.PROFILE_PIC,
                                            usuario.getIdUsuario()),
                                    ((BitmapDrawable)resource).getBitmap(), false);
                            return false;
                        }
                    }).into(imgPerfil);

        }
    }


    private void loadListener() {
        btnBack.setOnClickListener(this);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerViewFunciones);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                procesarClick(parent, view, position, (int) id);
            }
        });

        ItemClickSupport itemClickSupport2 = ItemClickSupport.addTo(mRecyclerActividades);
        itemClickSupport2.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                 procesarClick2(parent, view, position, (int) id);
            }
        });
    }

    private void procesarClick2(RecyclerView parent, View view, int position, int id) {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setIcono(R.drawable.ic_repair)
                .setDescripcion( "¡Ésta sección se encuentra en desarrollo!")
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {

                    }

                    @Override
                    public void no() {

                    }
                })
                .setTipo(DialogoGeneral.TIPO_ACEPTAR);
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.setCancelable(false);
        dialogoGeneral.show(getSupportFragmentManager(), "dialog_proces");
        switch (id) {
            case 1:
                //startActivity(new Intent(getApplicationContext(), CredencialActivity.class));
                break;
        }
    }

    private void procesarClick(RecyclerView parent, View view, int position, int id) {
        switch (id) {
            case 1:
                startActivity(new Intent(getApplicationContext(), InfoUsuarioActivity.class));
                break;
            case 6:
                logout();
                break;
            case 7:
                startActivity(new Intent(getApplicationContext(), InscripcionesActivity.class));
                break;
            case 2:
                startActivity(new Intent(getApplicationContext(), ConfiguracionesActivities.class));
                break;
            case 3:
                startActivity(new Intent(getApplicationContext(), TiposCredencialesActivity.class));
                break;
            case 4:
                startActivity(new Intent(getApplicationContext(), ArchivosActivity.class));
                break;

        }
    }

    private void logout() {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(R.string.advertencia))
                .setDescripcion("¿Seguro que desea cerrar sesión?")
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {
                        Utils.resetData(getApplicationContext());
                        PreferenceManager manager = new PreferenceManager(getApplicationContext());
                        manager.setValue(Utils.IS_LOGIN, false);
                        finishAffinity();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void no() {
                    }
                })
                .setTipo(DialogoGeneral.TIPO_SI_NO);
        final DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getSupportFragmentManager(), "dialog_yesno");

    }

    private void loadViews() {
        btnBack = findViewById(R.id.imgFlecha);
        mRecyclerViewFunciones = findViewById(R.id.recycler);
        imgPerfil = findViewById(R.id.imgUserPerfil);
        txtNombre = findViewById(R.id.txtNombre);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtFacultad = findViewById(R.id.txtFacultad);

        mRecyclerActividades = findViewById(R.id.recyclerActividades);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }
}
