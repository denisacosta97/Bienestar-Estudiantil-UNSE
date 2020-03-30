package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.unse.bienestarestudiantil.Databases.AlumnosRepo;
import com.unse.bienestarestudiantil.Databases.EgresadosRepo;
import com.unse.bienestarestudiantil.Databases.ProfesorRepo;
import com.unse.bienestarestudiantil.Databases.UsuariosRepo;
import com.unse.bienestarestudiantil.Herramientas.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Egresado;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Inicio.LoginActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogYesNoGeneral;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;

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

        loadInfo();

        mList = new ArrayList<>();
        mList.add(new Opciones(LinearLayout.VERTICAL, true, 1, "Mis Datos", R.drawable.ic_data, R.color.colorWhite, R.color.colorTextDefault, 12));
        mList.add(new Opciones(LinearLayout.VERTICAL, true, 3, "Credenciales", R.drawable.ic_credencial, R.color.colorWhite, R.color.colorTextDefault, 12));
        mList.add(new Opciones(LinearLayout.VERTICAL, true, 4, "Gestión de Archivos", R.drawable.ic_pdf, R.color.colorWhite, R.color.colorTextDefault, 12));
        mList.add(new Opciones(LinearLayout.VERTICAL, false, 5, "¿Mensajes?", R.drawable.ic_msg, R.color.colorWhite, R.color.colorTextDefault, 12));
        mList.add(new Opciones(LinearLayout.VERTICAL, true, 7, "Inscripciones", R.drawable.ic_inscipcion, R.color.colorWhite, R.color.colorTextDefault, 12));
        mList.add(new Opciones(LinearLayout.VERTICAL, true, 2, "Configuraciones", R.drawable.ic_settings, R.color.colorWhite, R.color.colorTextDefault, 12));
        mList.add(new Opciones(LinearLayout.VERTICAL, true, 6, "Cerrar Sesión", R.drawable.ic_cerrar_e, R.color.colorWhite, R.color.colorTextDefault, 12));

        mAdapter = new OpcionesAdapter(mList, getApplicationContext());

        mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);

        mRecyclerViewFunciones.setLayoutManager(mLayoutManager);
        mRecyclerViewFunciones.setHasFixedSize(true);
        mRecyclerViewFunciones.setAdapter(mAdapter);

        mListActividades = new ArrayList<>();
        mListActividades.add(new Opciones(LinearLayout.VERTICAL, true, 1, "Reservas", R.drawable.ic_reservas, R.color.colorWhite, R.color.colorTextDefault, 12));
        mListActividades.add(new Opciones(LinearLayout.VERTICAL, true, 2, "Historia Clínica", R.drawable.ic_historia_clinica, R.color.colorWhite, R.color.colorTextDefault, 12));
        mListActividades.add(new Opciones(LinearLayout.VERTICAL, true, 3, "Historial de Turnos", R.drawable.ic_turnos, R.color.colorWhite, R.color.colorTextDefault, 12));


        mAdapterActividades = new OpcionesAdapter(mListActividades, getApplicationContext());
        mLayoutActividades = new GridLayoutManager(getApplicationContext(), 3);

        mRecyclerActividades.setLayoutManager(mLayoutActividades);
        mRecyclerActividades.setHasFixedSize(true);
        mRecyclerActividades.setAdapter(mAdapterActividades);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInfo();
    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        int id = manager.getValueInt(Utils.MY_ID);
        Usuario usuario = new UsuariosRepo(getApplicationContext()).get(id);
        if (txtNombre != null) {
            txtNombre.setText("NOMBRE");
            txtDescripcion.setText("CARRERA");
            txtFacultad.setText("RANGO");
            String URL = String.format("%s%s.jpg", Utils.URL_USUARIO_IMAGE_LOAD, id);
            Glide.with(imgPerfil.getContext()).load(URL).apply(new RequestOptions().error(R.drawable.ic_user).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_user)).into(imgPerfil);

            if (usuario != null) {
                txtNombre.setText(String.format("%s %s", usuario.getNombre(), usuario.getApellido()));
                if (usuario.getTipoUsuario() != 5) {
                    if (usuario.getTipoUsuario() == 1) {
                        Alumno alumno = new AlumnosRepo(getApplicationContext()).get(id);
                        if (alumno != null) {
                            txtDescripcion.setText(alumno.getCarrera());
                            txtFacultad.setText(alumno.getFacultad());
                        }
                    } else if (usuario.getTipoUsuario() == 4) {
                        Egresado egresado = new EgresadosRepo(getApplicationContext()).get(id);
                        if (egresado != null) {
                            txtDescripcion.setText(egresado.getProfesion());
                            txtFacultad.setText(View.GONE);
                        }
                    } else if (usuario.getTipoUsuario() == 2) {
                        Profesor profesor = new ProfesorRepo(getApplicationContext()).get(id);
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


    private void loadListener() {
        btnBack.setOnClickListener(this);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerViewFunciones);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Utils.showToast(getApplicationContext(), "Tocaste: " + mList.get(position).getTitulo());
                procesarClick(parent, view, position, (int) id);
            }
        });

        ItemClickSupport itemClickSupport2 = ItemClickSupport.addTo(mRecyclerActividades);
        itemClickSupport2.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Utils.showToast(getApplicationContext(), "Tocaste: " + mListActividades.get(position).getTitulo());
                procesarClick2(parent, view, position, (int) id);
            }
        });
    }

    private void procesarClick2(RecyclerView parent, View view, int position, int id) {
        switch (id){
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
                startActivity(new Intent(getApplicationContext(), GestionArchivosActivity.class));
                break;

        }
    }

    private void logout() {
        final DialogYesNoGeneral dialog = new DialogYesNoGeneral();
        YesNoDialogListener listener = new YesNoDialogListener() {
            @Override
            public void yes() {
                dialog.dismiss();
                finishAffinity();
                PreferenceManager manager = new PreferenceManager(getApplicationContext());
                manager.setValue(Utils.IS_LOGIN, false);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void no() {
                dialog.dismiss();
            }
        };

        dialog.loadData("¡Advertencia!", "¿Seguro que desea cerrar sesión?", listener, getApplicationContext());
        dialog.show(getSupportFragmentManager(), "dialog_yesno");
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
