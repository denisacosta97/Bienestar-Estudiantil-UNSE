package com.unse.bienestarestudiantil.Vistas.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.unse.bienestarestudiantil.Databases.RolViewModel;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.Modelos.Rol;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Becas.GestionBecas.MainGestionBecasActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Ciber.GestionCiberActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes.GestionGeneralDeportesActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.EstadisticasActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionArchivos.GestionArchivosActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionNoticiasActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionRoles.GestionRolesActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionSocios.GestionSociosActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionUsuarios.GestionUsuariosActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Maraton.GestionMaratonActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionPolideportivoActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.PuntosConectividad.GestionPuntosConectividadActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporteActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionUAPUActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InicioFragmento extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mOpciones, mOpcionesFinal;
    ArrayList<String> ids;
    ImageView imgIcono;
    Context mContext;
    FragmentManager mFragmentManager;
    View view;

    public void setContext(Context context) {
        mContext = context;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    DialogoProcesamiento dialog;

    public InicioFragmento() {
        // Metodo necesario
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Crea la vista de Inicio
        view = inflater.inflate(R.layout.fragmento_inicio, container, false);

        loadViews();

        loadData();

        loadListener();

        return view;
    }

    private void loadData() {
        mOpciones = new ArrayList<>();
        mOpcionesFinal = new ArrayList<>();
        ids = new ArrayList<>();


        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 1000, "Gestión de Usuarios", R.drawable.ic_user, R.color.colorPrimary));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 1200, "Gestión de Socios", R.drawable.ic_config, R.color.colorPrimary));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 1, "Gestión de Estadisticas", R.drawable.ic_estadistica, R.color.colorGreyDark));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 900, "Gestión de Roles", R.drawable.ic_usuarios, R.color.colorGreyDark));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 1100, "Gestión de Archivos", R.drawable.ic_pdf, R.color.colorRed));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 100, "Gestión Deportes", R.drawable.ic_config, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 200, "Gestión Polideportivo", R.drawable.ic_config, R.color.colorOrange));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 300, "Gestión UAPU", R.drawable.ic_config, R.color.colorAccent));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 400, "Gestión Área Becas", R.drawable.ic_config, R.color.colorGreen));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 500, "Gestión Cyber", R.drawable.ic_config, R.color.colorGreen));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 600, "Gestión Transporte", R.drawable.ic_config, R.color.colorGreen));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 700, "Gestión Residencia", R.drawable.ic_config, R.color.colorGreen));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 800, "Gestión Comedor", R.drawable.ic_config, R.color.colorGreen));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 1300, "Gestión Noticias", R.drawable.ic_config, R.color.colorGreen));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 1400, "Puntos de Conectividad", R.drawable.ic_config, R.color.colorGreen));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 1500, "Gestión de Maratón Virtual", R.drawable.ic_config, R.color.colorGreen));

        mLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OpcionesAdapter(mOpcionesFinal, mContext, 1);
        mRecyclerView.setAdapter(mAdapter);

        filtrarOpciones();

        mAdapter.notifyDataSetChanged();
    }

    private void loadViews() {
        mRecyclerView = view.findViewById(R.id.recycler);
        imgIcono = view.findViewById(R.id.imgFlecha);
    }

    private void filtrarOpciones() {
        RolViewModel datos = new RolViewModel();
        ArrayList<Rol> roles = datos.getAll();
        for (Opciones e : mOpciones) {
            for (Rol rol : roles) {
                if (e.getId() == rol.getIdRol()) {
                    mOpcionesFinal.add(e);
                }
            }

        }

    }

    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                switch ((int) id) {
                    case 900:
                        startActivity(new Intent(mContext, GestionRolesActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, EstadisticasActivity.class));
                        break;
                    case 100:
                        startActivity(new Intent(mContext, GestionGeneralDeportesActivity.class));
                        break;
                    case 200:
                        startActivity(new Intent(mContext, GestionPolideportivoActivity.class));
                        break;
                    case 300:
                        startActivity(new Intent(mContext, GestionUAPUActivity.class));
                        break;
                    case 400:
                        startActivity(new Intent(mContext, MainGestionBecasActivity.class));
                        break;
                    case 500:
                        startActivity(new Intent(mContext, GestionCiberActivity.class));
                        break;
                    case 1000:
                        startActivity(new Intent(mContext, GestionUsuariosActivity.class));
                        break;
                    case 1100:
                        startActivity(new Intent(mContext, GestionArchivosActivity.class));
                        break;
                    case 1200:
                        startActivity(new Intent(mContext, GestionSociosActivity.class));
                        break;
                    case 600:
                        startActivity(new Intent(mContext, GestionTransporteActivity.class));
                        break;
                    case 1300:
                        startActivity(new Intent(mContext, GestionNoticiasActivity.class));
                        break;
                    case 1400:
                        startActivity(new Intent(mContext, GestionPuntosConectividadActivity.class));
                        break;
                    case 1500:
                        startActivity(new Intent(mContext, GestionMaratonActivity.class));
                        break;

                }
            }
        });

    }

}
