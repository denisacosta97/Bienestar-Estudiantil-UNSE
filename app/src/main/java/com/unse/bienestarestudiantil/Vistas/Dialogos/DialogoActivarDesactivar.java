package com.unse.bienestarestudiantil.Vistas.Dialogos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickListenerAdapter;
import com.unse.bienestarestudiantil.Modelos.Credencial;
import com.unse.bienestarestudiantil.Modelos.CredencialSocio;
import com.unse.bienestarestudiantil.Modelos.Regularidad;
import com.unse.bienestarestudiantil.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DialogoActivarDesactivar extends DialogFragment {

    View view;
    Button btnOk;
    TextView txtTitulo, txtFecha, txtEstado, txtTituloPrin;
    SwitchCompat switchEstado;
    LinearLayout latEstado;
    Context mContext;
    DialogoProcesamiento dialog;
    FragmentManager mFragmentManager;
    Regularidad mRegularidad;
    Credencial mCredencial;
    int position, idUsuario;
    boolean isActive = false;
    OnClickListenerAdapter mOnClickListenerAdapter;

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

    public OnClickListenerAdapter getOnClickListenerAdapter() {
        return mOnClickListenerAdapter;
    }

    public void setOnClickListenerAdapter(OnClickListenerAdapter onClickListenerAdapter) {
        mOnClickListenerAdapter = onClickListenerAdapter;
    }


    public Credencial getCredencial() {
        return mCredencial;
    }

    public void setCredencial(Credencial credencial) {
        mCredencial = credencial;
    }

    public Regularidad getRegularidad() {
        return mRegularidad;
    }

    public void setRegularidad(Regularidad regularidad) {
        mRegularidad = regularidad;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogo_estado_switch, container, false);
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
                if (mOnClickListenerAdapter != null) {
                    mOnClickListenerAdapter.onClick(isActive ? 1 : 0);
                    dismiss();
                }
            }
        });
        switchEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiar();
            }
        });
        latEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiar();
            }
        });
    }

    private void cambiar() {
        isActive = !isActive;
        PreferenceManager manager = new PreferenceManager(getContextDialog());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = mRegularidad != null ? String.format("%s?id=%s&key=%s&idU=%s&est=%s&idReg=%s", Utils.URL_REGULARIDAD_CAMBIAR,
                id, key, getIdUsuario(), isActive ? 1 : 0, mRegularidad.getIdRegularidad())
                : !(mCredencial instanceof CredencialSocio) ? String.format("%s?id=%s&key=%s&idC=%s&est=%s", Utils.URL_CREDENCIAL_CAMBIAR, id, key, mCredencial.getId(), isActive ? 1 : 0)
                : String.format("%s?id=%s&key=%s&idC=%s&est=%s",Utils.URL_CREDENCIAL_SOCIO_CAMBIAR, id, key, ((CredencialSocio)mCredencial).getIdCredencialSocio(), isActive ? 1 : 0);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                isActive = !isActive;
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
                    isActive = !isActive;
                    break;
                case 1:
                    //Exito
                    Utils.showToast(getContextDialog(), mRegularidad != null ? getString(R.string.regularidadActualizada) :
                            getString(R.string.credencialActualizada));
                    updateReg(isActive);
                    break;
                case 2:
                    Utils.showToast(getContextDialog(), getString(R.string.regularidadNoCambiada));
                    isActive = !isActive;
                    break;
                case 3:
                    Utils.showToast(getContextDialog(), getString(R.string.tokenInvalido));
                    isActive = !isActive;
                    break;
                case 4:
                    Utils.showToast(getContextDialog(), getString(R.string.camposInvalidos));
                    isActive = !isActive;
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getContextDialog(), getString(R.string.tokenInexistente));
                    isActive = !isActive;
                    break;
            }

        } catch (JSONException e) {
            isActive = !isActive;
            e.printStackTrace();
            Utils.showToast(getContextDialog(), getString(R.string.errorInternoAdmin));
        }
    }

    private void updateReg(boolean reg) {
        if (reg) {
            txtEstado.setText("ACTIVADO");
            switchEstado.setChecked(true);
            Utils.changeColor(latEstado.getBackground(), getContextDialog(), R.color.colorGreen);
        } else {
            txtEstado.setText("DESACTIVADO");
            switchEstado.setChecked(false);
            Utils.changeColor(latEstado.getBackground(), getContextDialog(), R.color.colorPrimaryDark);
        }
    }


    private void loadData() {
        txtTituloPrin.setText(mRegularidad != null ? "Info Regularidad" : "Info Credencial");
        isActive = mRegularidad != null ? mRegularidad.getValidez() == 1 : mCredencial.getValidez() == 1;
        txtTitulo.setText(mRegularidad != null ?
                String.format("Regularidad #%s - %s", mRegularidad.getIdRegularidad(), mRegularidad.getAnio())
                : mCredencial.getTitulo());
        updateReg(isActive);
        txtFecha.setText(Utils.getFechaFormat(mRegularidad != null ?
                mRegularidad.getFechaOtorg()
                : mCredencial.getFecha()));


    }

    private void loadViews() {
        txtTituloPrin = view.findViewById(R.id.txtTitulo2);
        txtEstado = view.findViewById(R.id.txtDescripcion);
        txtFecha = view.findViewById(R.id.txtFecha);
        txtTitulo = view.findViewById(R.id.txtTitulo);
        switchEstado = view.findViewById(R.id.switchCred);
        latEstado = view.findViewById(R.id.latSwitch);
        btnOk = view.findViewById(R.id.btnAceptar);
    }
}