package com.unse.bienestarestudiantil.Vistas.Dialogos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickUser;
import com.unse.bienestarestudiantil.Modelos.IngresoCiber;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class DialogoBuscarUsuario extends DialogFragment {

    View view;
    Button btnOk, btnBuscar;
    EditText edtDNI;
    Context mContext;
    DialogoProcesamiento dialog;
    FragmentManager mFragmentManager;
    LinearLayout latDatos;
    TextView txtDNI, txtNombre, txtCuerpo;
    int dni = 0;
    ArrayList<Usuario> mList;
    OnClickUser mOnClickUser;
    boolean isNoValid = false;

    public void setNoValid(boolean noValid) {
        isNoValid = noValid;
    }

    public DialogoBuscarUsuario(Context context, FragmentManager fragmentManager, ArrayList<Usuario> list, OnClickUser onClickUser) {
        mContext = context;
        mFragmentManager = fragmentManager;
        mList = list;
        mOnClickUser = onClickUser;
    }

    public ArrayList<Usuario> getList() {
        return mList;
    }

    public void setList(ArrayList<Usuario> list) {
        mList = list;
    }

    public Context getContextDialog() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
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
        view = inflater.inflate(R.layout.dialog_buscar_usuario, container, false);
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
                if (isNoValid)
                    dni = Integer.parseInt(edtDNI.getText().toString());
                if (dni != 0 || isNoValid) {
                    if (mOnClickUser != null) {
                        dismiss();
                        mOnClickUser.onUserSelected(dni);

                    }
                } else {
                    Utils.showToast(getContextDialog(), getContextDialog().getString(R.string.primeroBuscar));
                }
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtDNI.getText().toString().equals("")) {
                    latDatos.setVisibility(View.GONE);
                    if (!isAdd(edtDNI.getText().toString()))
                        buscar(edtDNI.getText().toString());
                    else Utils.showToast(getContextDialog(), getString(R.string.usuarioYaAgregado));
                } else Utils.showToast(getContextDialog(), getString(R.string.campoVacio));
            }
        });
        edtDNI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dni = 0;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean isAdd(String dni) {
        if (mList != null){
            for (Usuario usuario : mList) {
                if (dni.equals(String.valueOf(usuario.getIdUsuario())))
                    return true;
            }
            return false;
        }

        return false;
    }

    private void buscar(String dni) {
        PreferenceManager manager = new PreferenceManager(getContextDialog());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&id=%s", Utils.URL_USUARIO_BY_ID_REDUCE,
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

                JSONObject datos = jsonObject.getJSONObject("mensaje");
                String dniNumber = datos.getString("idusuario");
                String nombre = datos.getString("nombre");
                String apellido = datos.getString("apellido");

                latDatos.setVisibility(View.VISIBLE);
                txtCuerpo.setText("");
                txtDNI.setText(dniNumber);
                txtNombre.setText(String.format("%s %s", nombre, apellido));

                dni = Integer.parseInt(dniNumber);

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private void loadData() {
        latDatos.setVisibility(View.GONE);
    }

    private void loadViews() {
        edtDNI = view.findViewById(R.id.edtBuscar);
        btnOk = view.findViewById(R.id.btnAceptar);
        latDatos = view.findViewById(R.id.latDatos);
        txtDNI = view.findViewById(R.id.txtDni);
        txtNombre = view.findViewById(R.id.txtNombreUser);
        txtCuerpo = view.findViewById(R.id.txtCuerpo);
        btnBuscar = view.findViewById(R.id.btnBuscar);
    }

}

