package com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Torneo;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.TorneosAdapter;

import java.util.ArrayList;

public class PerfilChoferActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgIcono;
    Usuario mUsuarios;
    TextView mNameDep, mNombreApe, mDni, mFechaNac, mDomicilio, mBarrio, mTel, mFechaIng;
    ImageView mPhotoUser;
    Button btnBaja;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerChofer;
    ArrayList<Torneo> mTorneos, mTorneosBaja;
    TorneosAdapter mTorneosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_chofer);

        if (getIntent().getParcelableExtra(Utils.USER_NAME) != null) {
            mUsuarios = getIntent().getParcelableExtra(Utils.USER_NAME);
        }

        if (mUsuarios != null) {
            loadViews();

            loadListener();

            loadData();
        } else {
            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelva a intentar");
            finish();
        }

        setToolbar();
    }

    private void loadData() {
        mNombreApe.setText(String.format("%s %s", mUsuarios.getNombre(), mUsuarios.getApellido()));
        mDni.setText(Integer.toString(mUsuarios.getIdUsuario()));
        mFechaNac.setText(String.format("%s", mUsuarios.getFechaNac()));
        mDomicilio.setText(mUsuarios.getDomicilio());
        mBarrio.setText(mUsuarios.getBarrio());
        mTel.setText(mUsuarios.getTelefono());
        //mFechaIng.setText(String.format("%s", DEALGÚNLADO.getFechaIngreso()));

        mPhotoUser.setImageResource(R.drawable.user);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de chofer");

    }


    private void loadListener() {
        imgIcono.setOnClickListener(this);

//        mTorneosAdapter = new TorneosAdapter(mTorneos, getApplicationContext());
//        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
//        mRecyclerTorneo.setNestedScrollingEnabled(true);
//        mRecyclerTorneo.setLayoutManager(mLayoutManager);
//        mRecyclerTorneo.setAdapter(mTorneosAdapter);
//
//        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerTorneo);
//        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerView parent, View view, int position, long id) {
//                Intent i = new Intent(getApplicationContext(), PerfilTorneoEditActivity.class);
//                i.putExtra(Utils.TORNEO, mTorneos.get(position));
//                startActivity(i);
//            }
//        });
    }


    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);

        mPhotoUser = findViewById(R.id.imgvUser);
        mNameDep = findViewById(R.id.txtNameProf);
        mNombreApe = findViewById(R.id.txtNameApe);
        mDni = findViewById(R.id.txtDni);
        mFechaNac = findViewById(R.id.txtFechaN);
        mDomicilio = findViewById(R.id.txtDomicilio);
        mBarrio = findViewById(R.id.txtBarrio);
        mTel = findViewById(R.id.txtTel);
        mFechaIng = findViewById(R.id.txtFechaIng);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

}
