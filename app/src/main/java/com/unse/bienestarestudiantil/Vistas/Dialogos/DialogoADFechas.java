package com.unse.bienestarestudiantil.Vistas.Dialogos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickListenerAdapter;
import com.unse.bienestarestudiantil.Modelos.FechasInhabilitadas;
import com.unse.bienestarestudiantil.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DialogoADFechas extends DialogFragment {

    View view;
    Button btnOk;
    TextView txtTitulo, txtFecha, txtEstado, txtTituloPrin, txtDescTit;
    SwitchCompat switchEstado;
    LinearLayout latEstado;
    Context mContext;
    DialogoProcesamiento dialog;
    FragmentManager mFragmentManager;
    FechasInhabilitadas mFechas;

    int position, idUsuario;
    boolean isActive = false;
    OnClickListenerAdapter mOnClickListenerAdapter;

    public FechasInhabilitadas getFecha() {
        return mFechas;
    }

    public void setFecha(FechasInhabilitadas fecha) {
        mFechas = fecha;
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

    public OnClickListenerAdapter getOnClickListenerAdapter() {
        return mOnClickListenerAdapter;
    }

    public void setOnClickListenerAdapter(OnClickListenerAdapter onClickListenerAdapter) {
        mOnClickListenerAdapter = onClickListenerAdapter;
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

                dismiss();

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
        String URL = String.format("%s?idU=%s&key=%s&di=%s&me=%s&an=%s&val=%s", Utils.URL_CAMBIAR_FECHA,
                id, key, mFechas.getDia(), mFechas.getMes(), mFechas.getAnio(), isActive ? 1 : 0);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
                    Utils.showToast(getContextDialog(), getString(R.string.fechaModif));
                    updateReg(isActive);
                    break;
                case 2:
                    Utils.showToast(getContextDialog(), getString(R.string.fechaNoModif));
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
        txtTituloPrin.setText("Modificar fecha");
        isActive = mFechas.getVal() == 1;
        txtTitulo.setText(String.format("Fecha: %s", mFechas.getDia() + mFechas.getMes() + mFechas.getAnio()));
        updateReg(isActive);
        txtDescTit.setText("Descripción:");
        txtFecha.setText(mFechas.getDesc());

    }

    private void loadViews() {
        txtTituloPrin = view.findViewById(R.id.txtTitulo2);
        txtEstado = view.findViewById(R.id.txtDescripcion);
        txtFecha = view.findViewById(R.id.txtFecha);
        txtTitulo = view.findViewById(R.id.txtTitulo);
        switchEstado = view.findViewById(R.id.switchCred);
        latEstado = view.findViewById(R.id.latSwitch);
        btnOk = view.findViewById(R.id.btnAceptar);
        txtDescTit = view.findViewById(R.id.txtDescripcionTit);
    }

}
