package com.unse.bienestarestudiantil.Vistas.Dialogos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Estado;
import com.unse.bienestarestudiantil.Modelos.Rol;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionRoles.EditarRolesActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.EstadoAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.UsuariosAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.helpers.Util;

import java.util.ArrayList;
import java.util.Date;

public class DialogoBuscaUsuario extends DialogFragment {

    View view;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    UsuariosAdapter mUsuariosAdapter;
    ArrayList<Usuario> mUsuarios;
    ArrayList<Usuario> mUsuariosLista;
    Button btnOk, btnBuscar;
    EditText edtDNI;
    Context mContext;
    DialogoProcesamiento dialog;
    FragmentManager mFragmentManager;
    ArrayList<Rol> mRoles;

    public ArrayList<Rol> getRoles() {
        return mRoles;
    }

    public void setRoles(ArrayList<Rol> roles) {
        mRoles = roles;
    }

    public Context getContextDialog() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public ArrayList<Usuario> getUsuariosLista() {
        return mUsuariosLista;
    }

    public void setUsuariosLista(ArrayList<Usuario> usuariosLista) {
        mUsuariosLista = usuariosLista;
    }

    public FragmentManager getFragmentManagerDialog() {
        return mFragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_busca_usuario, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loadViews();

        loadData();

        loadListener();

        return view;
    }

    private void loadListener() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (mUsuarios.size() > 0){
                        boolean isIgual = false;
                        for (Usuario usuario : mUsuariosLista){
                            if (usuario.getIdUsuario() == mUsuarios.get(0).getIdUsuario()){
                                isIgual = true;
                                break;
                            }
                        }
                        if (!isIgual) {
                            dismiss();
                            Intent intent = new Intent(getContextDialog(), EditarRolesActivity.class);
                            intent.putExtra(Utils.USER_INFO, mUsuarios.get(0));
                            intent.putExtra(Utils.ROLES_USER, new ArrayList<String>());
                            intent.putExtra(Utils.ROLES, mRoles);
                            intent.putExtra(Utils.IS_ADMIN_MODE, true);
                            startActivity(intent);
                        }else Utils.showToast(getContextDialog(), getString(R.string.usuarioPermisosOn));

                    }else Utils.showToast(getContextDialog(), getString(R.string.primeroBuscar));
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtDNI.getText().toString().equals(""))
                    buscar(edtDNI.getText().toString());
                else Utils.showToast(getContextDialog(), getString(R.string.campoVacio));
            }
        });
    }

    private void buscar(String dni) {
        PreferenceManager manager = new PreferenceManager(getContextDialog());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?id=%s&key=%s&idU=%s", Utils.URL_USUARIO_BY_ID,
                id, key, dni);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getContextDialog(), getString(R.string.servidorOff));
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getFragmentManagerDialog(), "dialog_process");
        VolleySingleton.getInstance(getContextDialog()).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getContextDialog(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                   loadInfo(jsonObject);
                    break;
                case 2:
                    Utils.showToast(getContextDialog(), getString(R.string.usuarioNoExiste));
                    break;
                case 3:
                    Utils.showToast(getContextDialog(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getContextDialog(), getString(R.string.camposInvalidos));
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getContextDialog(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getContextDialog(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                Usuario usuario = Usuario.mapper(jsonObject.getJSONObject("mensaje"), Usuario.BASIC);
                mUsuarios.clear();
                mUsuarios.add(usuario);
                mUsuariosAdapter = new UsuariosAdapter(mUsuarios, getContextDialog(), Utils.TIPO_USUARIO);
                mRecyclerView.setAdapter(mUsuariosAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        mUsuariosAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        mUsuarios = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContextDialog(), LinearLayoutManager.VERTICAL, false);
        mUsuariosAdapter = new UsuariosAdapter(mUsuarios, getContextDialog(), Utils.TIPO_USUARIO);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void loadViews() {
        edtDNI = view.findViewById(R.id.edtBuscar);
        btnOk = view.findViewById(R.id.btnAceptar);
        mRecyclerView = view.findViewById(R.id.recycler);
        btnBuscar = view.findViewById(R.id.btnBuscar);
    }

}
