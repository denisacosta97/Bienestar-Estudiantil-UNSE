package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.unse.bienestarestudiantil.Vistas.Dialogos.YesNoDialogListener;

import java.util.ArrayList;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView btnBack;
    TextView txtNombre, txtDescripcion, txtFacultad;

    ArrayList<Opciones> mList;
    OpcionesAdapter mAdapter;
    RecyclerView mRecyclerViewFunciones;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadData();

        loadListener();
    }

    private void loadData() {

        loadInfo();

        mList = new ArrayList<>();
        mList.add(new Opciones(1, "Mis Datos", R.drawable.ic_becas, R.color.colorWhite, R.color.colorTextDefault, 12));
        mList.add(new Opciones(3, "Credenciales", R.drawable.ic_deportes, R.color.colorWhite, R.color.colorTextDefault, 12));
        mList.add(new Opciones(4, "Gestión de Archivos", R.drawable.ic_pdf, R.color.colorWhite, R.color.colorTextDefault, 12));
        mList.add(new Opciones(5, "¿Mensajes?", R.drawable.ic_becas, R.color.colorWhite, R.color.colorTextDefault, 12));
        mList.add(new Opciones(6, "Cerrar Sesión", R.drawable.ic_apagar, R.color.colorWhite, R.color.colorTextDefault, 12));
        mList.add(new Opciones(2, "Configuraciones", R.drawable.ic_becas, R.color.colorWhite, R.color.colorTextDefault, 12));


        mList.add(new Opciones(1, "Inscripciones", R.drawable.ic_deportes, R.color.colorWhite, R.color.colorTextDefault, 12));
        mAdapter = new OpcionesAdapter(mList, getApplicationContext());

        mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);

        mRecyclerViewFunciones.setLayoutManager(mLayoutManager);
        mRecyclerViewFunciones.setHasFixedSize(true);
        mRecyclerViewFunciones.setAdapter(mAdapter);

    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        int id = manager.getValueInt(Utils.MY_ID);
        Usuario usuario = new UsuariosRepo(getApplicationContext()).get(id);
        txtNombre.setText("NOMBRE");
        txtDescripcion.setText("CARRERA");
        txtFacultad.setText("RANGO");
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
    }

    private void procesarClick(RecyclerView parent, View view, int position, int id) {
        switch (id) {
            case 1:
                startActivity(new Intent(getApplicationContext(), InfoUsuarioActivity.class));
                break;
            case 6:
                logout();
                break;
            case 2:
                startActivity(new Intent(getApplicationContext(), ConfiguracionesActivities.class));
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
        btnBack = findViewById(R.id.btnBack);
        mRecyclerViewFunciones = findViewById(R.id.recycler);

        txtNombre = findViewById(R.id.txtNombre);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtFacultad = findViewById(R.id.txtFacultad);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }
}
