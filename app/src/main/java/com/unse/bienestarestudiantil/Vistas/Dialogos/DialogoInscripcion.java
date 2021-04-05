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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickListenerAdapter;
import com.unse.bienestarestudiantil.Modelos.SuscripcionSocio;
import com.unse.bienestarestudiantil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class DialogoInscripcion extends DialogFragment {

    View view;
    TextView txtTitulo, txtFecha, txtAnio, txtEstado;
    Spinner mSpinner;
    Button btnAceptar;
    SwitchCompat switchEstado;
    LinearLayout latEstado;
    Context mContext;
    DialogoProcesamiento dialog;
    SuscripcionSocio mSuscripcionSocio;
    String[] estados = new String[]{"ACEPTADO", "EN PROCESO", "RECHAZADO"};
    FragmentManager mFragmentManager;
    OnClickListenerAdapter mOnClickListenerAdapter;

    int idUsuario, positionVin = 0;

    boolean isActive = true, update = false, edit = false;

    public SuscripcionSocio getSuscripcionSocio() {
        return mSuscripcionSocio;
    }

    public void setSuscripcionSocio(SuscripcionSocio suscripcionSocio) {
        mSuscripcionSocio = suscripcionSocio;
    }

    @Nullable
    @Override
    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public OnClickListenerAdapter getOnClickListenerAdapter() {
        return mOnClickListenerAdapter;
    }


    public void setOnClickListenerAdapter(OnClickListenerAdapter onClickListenerAdapter) {
        mOnClickListenerAdapter = onClickListenerAdapter;
    }

    public FragmentManager getFragmentManagerCustom() {
        return mFragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_inscripcion, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loadViews();

        loadData();

        loadListener();

        return view;
    }

    private void loadListener() {
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != positionVin) {
                    edit = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit)
                    dismiss();
                else if (mOnClickListenerAdapter != null) {
                    //loadDataFam();
                    cambiar(2);
                    //  mOnClickListenerAdapter.onClick(mFamiliar);
                    dismiss();
                }


            }
        });
        switchEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiar(1);
            }
        });
        latEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiar(1);
            }
        });
    }

   /* public void loadDataFam() {
        if (mFamiliar == null) mFamiliar = new Familiar(0, null, null, null, null, null, 0);
        mFamiliar.setValidez(isActive ? 1 : 0);
        mFamiliar.setNombre(edtNombre.getText().toString().trim());
        mFamiliar.setApellido(edtApellido.getText().toString().trim());
        mFamiliar.setDni(Integer.parseInt(edtDNI.getText().toString().trim()));
        mFamiliar.setFechaNac(txtFechaNac.getText().toString().trim());
        mFamiliar.setRelacion(vinculos[mSpinner.getSelectedItemPosition()]);
    }

    private void checkCampos() {
        String nom = edtNombre.getText().toString().trim();
        String apell = edtApellido.getText().toString().trim();
        String dni = edtDNI.getText().toString().trim();
        String fecha = txtFechaNac.getText().toString().trim();
        Validador validador = new Validador(getContext());
        if (validador.validarDNI(edtDNI) && !validador.validarNombresEdt(edtNombre, edtApellido) && !fecha.equals("")) {


            cambiar(nom, apell, dni, vinculos[mSpinner.getSelectedItemPosition()], fecha);
        }

    }*/


    private void loadData() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, estados);
        mSpinner.setAdapter(arrayAdapter);
        positionVin = mSuscripcionSocio.getEstado() - 1;
        mSpinner.setSelection(positionVin);
        isActive = mSuscripcionSocio.getValidez() == 1;
        txtTitulo.setText(String.format("Inscripción #%s/%s", mSuscripcionSocio.getIdIdentificacion(), String.valueOf(
                mSuscripcionSocio.getAnio()).substring(2, 3)));
        txtAnio.setText(String.valueOf(mSuscripcionSocio.getAnio()));
        txtFecha.setText(Utils.getFechaFormat(mSuscripcionSocio.getFechaRegistro()));
        updateReg(isActive);
    }

    private void loadViews() {
        mSpinner = view.findViewById(R.id.spinner1);
        btnAceptar = view.findViewById(R.id.btnAceptar);
        txtEstado = view.findViewById(R.id.txtDescripcion);
        txtFecha = view.findViewById(R.id.txtFecha);
        txtTitulo = view.findViewById(R.id.txtTitulo);
        txtAnio = view.findViewById(R.id.txtAnio);
        switchEstado = view.findViewById(R.id.switchCred);
        latEstado = view.findViewById(R.id.latSwitch);
    }

    private void updateReg(boolean reg) {
        if (reg) {
            txtEstado.setText("ACTIVADO");
            switchEstado.setChecked(true);
            Utils.changeColor(latEstado.getBackground(), getContext(), R.color.colorGreen);
        } else {
            txtEstado.setText("DESACTIVADO");
            switchEstado.setChecked(false);
            Utils.changeColor(latEstado.getBackground(), getContext(), R.color.colorPrimaryDark);
        }
    }

    private void cambiar(int i) {
        String fechaR = Utils.getFechaName(new Date(System.currentTimeMillis()));
        if (i == 1) isActive = !isActive;
        else update = true;
        PreferenceManager manager = new PreferenceManager(getContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
      /*  String URL = i == 1 ? String.format("%s?id=%s&key=%s&idI=%s&est=%s", Utils.URL_SOCIO_SUSCRIPCION_CAMBIAR,
                id, key, mSuscripcionSocio.getIdIdentificacion(), isActive ? 1 : 0) :
                String.format("%s?id=%s&key=%s&idU=%s&idH=%s&nn=%s&aa=%s&re=%s&fn=%s&fr=%s", Utils.URL_SOCIO_FAMILIAR_AGREGAR,
                        id, key, getIdUsuario(), dni, nombre, apellido, relacion, fechaNac, fechaR);*/
        StringRequest request = new StringRequest(fechaR != null ? Request.Method.POST : Request.Method.PUT, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                isActive = !isActive;
                Utils.showToast(getContext(), getString(R.string.servidorOff));
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getContext(), getString(R.string.errorInternoAdmin));
                    isActive = !isActive;
                    break;
                case 1:
                    //Exito
                    /*Utils.showToast(getContext(), mFamiliar != null ? getString(R.string.familiarCambiabo) :
                            getString(R.string.familiarAgregado));
                    if (mFamiliar != null) {
                        updateReg(isActive);
                        edit = false;
                        btnAceptar.setEnabled(true);
                    } else {
                        loadDataFam();
                        mOnClickListenerAdapter.onClick(mFamiliar);
                        dismiss();
                    }*/
                    break;
                case 2:
                    Utils.showToast(getContext(), getString(R.string.regularidadNoCambiada));
                    isActive = !isActive;
                    break;
                case 3:
                    Utils.showToast(getContext(), getString(R.string.tokenInvalido));
                    isActive = !isActive;
                    break;
                case 4:
                    Utils.showToast(getContext(), getString(R.string.camposInvalidos));
                    isActive = !isActive;
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getContext(), getString(R.string.tokenInexistente));
                    isActive = !isActive;
                    break;
            }

        } catch (JSONException e) {
            isActive = !isActive;
            e.printStackTrace();
            Utils.showToast(getContext(), getString(R.string.errorInternoAdmin));
        }
    }

}
