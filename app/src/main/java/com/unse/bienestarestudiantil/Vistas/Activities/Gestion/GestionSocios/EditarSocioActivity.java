package com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionSocios;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Interfaces.OnClickListenerAdapter;
import com.unse.bienestarestudiantil.Modelos.Credencial;
import com.unse.bienestarestudiantil.Modelos.Familiar;
import com.unse.bienestarestudiantil.Modelos.Socio;
import com.unse.bienestarestudiantil.Modelos.SuscripcionSocio;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.CredencialesAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.FamiliaAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.SuscripcionAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoActivarDesactivar;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoBuscaSuscripcion;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoFamiliar;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditarSocioActivity extends AppCompatActivity {

    RecyclerView mRecyclerViewFamilia, mRecyclerViewInscripciones, mRecyclerViewCredenciales;
    RecyclerView.LayoutManager mLayoutManagerFamilia, mLayoutManagerInscripciones, mLayoutManagerCredenciales;
    EditText edtFechaRegistro, edtTipo;
    ArrayList<Familiar> mFamiliars;
    ArrayList<SuscripcionSocio> mSuscripcionSocios;
    ArrayList<Credencial> mCredencialSocios;
    Button btnAceptar, btnAgregarFamiliar, btnAgregarCredencial, btnAgregarSuscripcion;
    TextView txtNombre, txtDNI;
    ImageView imgIcono;
    CircleImageView imgPerfil;
    CredencialesAdapter mCredencialesAdapter;
    FamiliaAdapter mFamiliaAdapter;
    SuscripcionAdapter mSuscripcionAdapter;
    Usuario mUsuario;
    Socio mSocio;

    int positionCred, positionFamiliar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_socio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        isAdmin();

        loadViews();

        loadData();

        setToolbar();

        loadListener();
    }

    private void loadListener() {
        btnAgregarFamiliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoFamiliar dialogoFamiliar = new DialogoFamiliar();
                dialogoFamiliar.setContext(getApplicationContext());
                dialogoFamiliar.setFragmentManager(getSupportFragmentManager());;
                dialogoFamiliar.setBasicFragment(getFragmentManager());
                dialogoFamiliar.setIdUsuario(mSocio.getIdUsuario());
                dialogoFamiliar.setOnClickListenerAdapter(new OnClickListenerAdapter() {
                    @Override
                    public void onClick(Object id) {
                        mFamiliars.add((Familiar) id);
                        //mFamiliars.get(positionFamiliar).setValidez(id);
                        mFamiliaAdapter.notifyDataSetChanged();
                        updateButton();
                    }
                });
                dialogoFamiliar.show(getSupportFragmentManager(), "dialogo_act_desc");
            }
        });
        ItemClickSupport itemClickSupportFamiliar = ItemClickSupport.addTo(mRecyclerViewFamilia);
        itemClickSupportFamiliar.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                positionFamiliar = position;
                Familiar familiar = mFamiliars.get(position);
                DialogoFamiliar dialogoFamiliar = new DialogoFamiliar();
                dialogoFamiliar.setContext(getApplicationContext());
                dialogoFamiliar.setFragmentManager(getSupportFragmentManager());
                dialogoFamiliar.setFamiliar(familiar);
                dialogoFamiliar.setBasicFragment(getFragmentManager());
                dialogoFamiliar.setIdUsuario(mSocio.getIdUsuario());
                dialogoFamiliar.setOnClickListenerAdapter(new OnClickListenerAdapter() {
                    @Override
                    public void onClick(Object id) {
                        mFamiliars.set(positionFamiliar, (Familiar) id);
                        mFamiliaAdapter.notifyDataSetChanged();
                        updateButton();
                    }
                });
                dialogoFamiliar.show(getSupportFragmentManager(), "dialogo_act_desc");
            }
        });
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerViewCredenciales);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                positionCred = position;
                Credencial credencial = mCredencialSocios.get(position);
                DialogoActivarDesactivar dialogoActivarDesactivar = new DialogoActivarDesactivar();
                dialogoActivarDesactivar.setContext(getApplicationContext());
                dialogoActivarDesactivar.setFragmentManager(getSupportFragmentManager());
                dialogoActivarDesactivar.setPosition(position);
                dialogoActivarDesactivar.setCredencial(credencial);
                dialogoActivarDesactivar.setOnClickListenerAdapter(new OnClickListenerAdapter() {
                    @Override
                    public void onClick(Object id) {
                        mCredencialSocios.get(positionCred).setValidez((Integer) id);
                        mCredencialesAdapter.notifyDataSetChanged();
                        updateButton();
                    }
                });
                dialogoActivarDesactivar.show(getSupportFragmentManager(), "dialogo_act_desc");
            }
        });

        btnAgregarSuscripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoBuscaSuscripcion dialogoBuscaSuscripcion = new DialogoBuscaSuscripcion();
                dialogoBuscaSuscripcion.setContext(getApplicationContext());
                dialogoBuscaSuscripcion.setFragmentManager(getSupportFragmentManager());
                dialogoBuscaSuscripcion.setIdSocio(mSocio.getIdUsuario());
                dialogoBuscaSuscripcion.setSuscripcionSocios(mSuscripcionSocios);
                dialogoBuscaSuscripcion.show(getSupportFragmentManager(), "dialog_buscar_an");
            }
        });
    }

    private void updateButton() {
        /*if (mCredencialSocios.size() > 0 && mCredencialSocios.get(0).getValidez() == 1){
            btnAgregarCredencial.setEnabled(false);
        }else
            btnAgregarCredencial.setEnabled(true);*/
    }

    private void isAdmin() {
        mFamiliars = new ArrayList<>();
        mCredencialSocios = new ArrayList<>();
        mSuscripcionSocios = new ArrayList<>();
        if (getIntent().getParcelableExtra(Utils.USER_INFO) != null) {
            mUsuario = getIntent().getParcelableExtra(Utils.USER_INFO);
            mSocio = (Socio) mUsuario;
        }
        if (getIntent().getSerializableExtra(Utils.LIST_HIJOS) != null) {
            mFamiliars = (ArrayList<Familiar>) getIntent().getSerializableExtra(Utils.LIST_HIJOS);
        }
        if (getIntent().getSerializableExtra(Utils.LIST_CRED) != null) {
            mCredencialSocios = (ArrayList<Credencial>) getIntent().getSerializableExtra(Utils.LIST_CRED);
        }
        if (getIntent().getSerializableExtra(Utils.LIST_SUSCROP) != null) {
            mSuscripcionSocios = (ArrayList<SuscripcionSocio>) getIntent().getSerializableExtra(Utils.LIST_SUSCROP);
        }
    }

    private void loadData() {
        mLayoutManagerFamilia = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewFamilia.setHasFixedSize(true);
        mRecyclerViewFamilia.setNestedScrollingEnabled(true);
        mRecyclerViewFamilia.setLayoutManager(mLayoutManagerFamilia);
        mFamiliaAdapter = new FamiliaAdapter(mFamiliars, getApplicationContext());
        mRecyclerViewFamilia.setAdapter(mFamiliaAdapter);

        mLayoutManagerCredenciales = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewCredenciales.setHasFixedSize(true);
        mRecyclerViewCredenciales.setNestedScrollingEnabled(true);
        mRecyclerViewCredenciales.setLayoutManager(mLayoutManagerCredenciales);
        mCredencialesAdapter = new CredencialesAdapter(mCredencialSocios, getApplicationContext(), true);
        mRecyclerViewCredenciales.setAdapter(mCredencialesAdapter);

        mLayoutManagerInscripciones = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewInscripciones.setHasFixedSize(true);
        mRecyclerViewInscripciones.setNestedScrollingEnabled(true);
        mRecyclerViewInscripciones.setLayoutManager(mLayoutManagerInscripciones);
        mSuscripcionAdapter = new SuscripcionAdapter(mSuscripcionSocios, getApplicationContext());
        mRecyclerViewInscripciones.setAdapter(mSuscripcionAdapter);

        String URL = String.format("%s%s.jpg", Utils.URL_USUARIO_IMAGE_LOAD, mSocio.getIdUsuario());
        Glide.with(imgPerfil.getContext()).load(URL)
                .apply(new RequestOptions().error(R.drawable.ic_user).placeholder(R.drawable.ic_user)).into(imgPerfil);

        edtFechaRegistro.setText(mSocio.getFechaRegistro());
        edtTipo.setText(Utils.getTipoUser(mSocio.getTipoUsuario()));
        txtDNI.setText(String.valueOf(mSocio.getIdUsuario()));
        txtNombre.setText(String.format("%s %s", mSocio.getNombre(), mSocio.getApellido()));
    }

    private void loadViews() {
        edtTipo = findViewById(R.id.edtTipo);
        edtFechaRegistro = findViewById(R.id.edtFechaRegistro);
        imgPerfil = findViewById(R.id.imgIcon);
        txtNombre = findViewById(R.id.txtNombre);
        txtDNI = findViewById(R.id.txtDni);
        mRecyclerViewFamilia = findViewById(R.id.recyclerHijos);
        mRecyclerViewCredenciales = findViewById(R.id.recyclerCredenciales);
        mRecyclerViewInscripciones = findViewById(R.id.recyclerInscripciones);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnAgregarCredencial = findViewById(R.id.btnAgregarCred);
        btnAgregarFamiliar = findViewById(R.id.btnAgregar);
        btnAgregarSuscripcion = findViewById(R.id.btnInscribir);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Info Socio");
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }
}
