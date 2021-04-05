package com.unse.bienestarestudiantil.Vistas.Dialogos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.widget.SwitchCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickListenerAdapter;
import com.unse.bienestarestudiantil.Modelos.Familiar;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;

public class DialogoFamiliar extends DialogFragment {

    View view;
    EditText edtNombre, edtApellido, edtDNI;
    TextView txtTitulo, txtFecha, txtFechaNac, txtEstado;
    Spinner mSpinner;
    Button btnAceptar, btnAct;
    SwitchCompat switchEstado;
    LinearLayout latEstado;
    Context mContext;
    DialogoProcesamiento dialog;
    Familiar mFamiliar;
    String[] vinculos = new String[]{"Hijo/a", "Esposo/a", "Madre", "Padre", "Otro"};
    FragmentManager mFragmentManager;
    android.app.FragmentManager mBasicFragment;
    OnClickListenerAdapter mOnClickListenerAdapter;

    int idUsuario, positionVin = 0;

    boolean isActive = true, update = false, edit = false;

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

    public Familiar getFamiliar() {
        return mFamiliar;
    }

    public void setFamiliar(Familiar familiar) {
        mFamiliar = familiar;
    }

    public void setOnClickListenerAdapter(OnClickListenerAdapter onClickListenerAdapter) {
        mOnClickListenerAdapter = onClickListenerAdapter;
    }

    public android.app.FragmentManager getBasicFragment() {
        return mBasicFragment;
    }

    public void setBasicFragment(android.app.FragmentManager basicFragment) {
        mBasicFragment = basicFragment;
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
        view = inflater.inflate(R.layout.dialogo_familiar, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loadViews();

        loadData();

        loadListener();

        return view;
    }

    private void loadListener() {
        TextWatcher mWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mFamiliar != null) {
                    edit = true;
                    btnAceptar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != positionVin) {
                    edit = true;
                    btnAceptar.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtDNI.addTextChangedListener(mWatcher);
        edtApellido.addTextChangedListener(mWatcher);
        edtNombre.addTextChangedListener(mWatcher);
        btnAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFamiliar != null)
                    checkCampos();
            }
        });
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFamiliar == null)
                    checkCampos();
                else if (mOnClickListenerAdapter != null) {
                    loadDataFam();
                    mOnClickListenerAdapter.onClick(mFamiliar);
                    dismiss();
                }

            }
        });
        txtFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTime();
            }
        });
        switchEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiar(null, null, null, null, null);
            }
        });
        latEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiar(null, null, null, null, null);
            }
        });
    }

    public void loadDataFam() {
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

    }


    private void openTime() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String mes, dia;
                month = month + 1;
                if (month < 10) {
                    mes = "0" + month;
                } else
                    mes = String.valueOf(month);
                if (day < 10)
                    dia = "0" + day;
                else
                    dia = String.valueOf(day);
                final String selectedDate = year + "-" + mes + "-" + dia;
                txtFechaNac.setText(selectedDate);
            }
        });
        newFragment.show(getBasicFragment(), "datePicker");
    }

    private void loadData() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, vinculos);
        mSpinner.setAdapter(arrayAdapter);
        if (mFamiliar != null) {
            isActive = mFamiliar.getValidez() == 1;
            txtTitulo.setText("Info Familiar");
            edtNombre.setText(mFamiliar.getNombre());
            edtApellido.setText(mFamiliar.getApellido());
            edtDNI.setText(String.valueOf(mFamiliar.getDni()));
            txtFechaNac.setText(mFamiliar.getFechaNac());
            txtFecha.setText(Utils.getFechaFormat(mFamiliar.getFechaRegistro()));
            int pos = Arrays.asList(vinculos).indexOf(mFamiliar.getRelacion());
            positionVin = pos;
            mSpinner.setSelection(pos);
            btnAct.setEnabled(true);
        } else {
            btnAct.setEnabled(false);
            isActive = true;
            txtTitulo.setText("Agregar Familiar");
            latEstado.setEnabled(false);
            switchEstado.setEnabled(false);
            mSpinner.setSelection(0);
        }

        updateReg(isActive);
    }

    private void loadViews() {
        btnAct = view.findViewById(R.id.btnAct);
        edtApellido = view.findViewById(R.id.edtApellido);
        edtDNI = view.findViewById(R.id.edtDNI);
        edtNombre = view.findViewById(R.id.edtNombre);
        mSpinner = view.findViewById(R.id.spinner1);
        btnAceptar = view.findViewById(R.id.btnAceptar);
        txtEstado = view.findViewById(R.id.txtDescripcion);
        txtFecha = view.findViewById(R.id.txtFecha);
        txtTitulo = view.findViewById(R.id.txtTitulo);
        txtFechaNac = view.findViewById(R.id.txtFechaNac);
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

    private void cambiar(String nombre, String apellido, String dni, String relacion, String fechaNac) {
        String fechaR = Utils.getFechaName(new Date(System.currentTimeMillis()));
        if (nombre == null) isActive = !isActive;
        else update = true;
        PreferenceManager manager = new PreferenceManager(getContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = mFamiliar != null ? String.format("%s?id=%s&key=%s&idU=%s&idH=%s&nn=%s&aa=%s&re=%s&fn=%s&fr=%s&est=%s", Utils.URL_SOCIO_FAMILIAR_ACTUALIZAR,
                id, key, getIdUsuario(), dni, nombre, apellido, relacion, fechaNac, fechaR, isActive ? 1 : 0) :
                String.format("%s?id=%s&key=%s&idU=%s&idH=%s&nn=%s&aa=%s&re=%s&fn=%s&fr=%s", Utils.URL_SOCIO_FAMILIAR_AGREGAR,
                        id, key, getIdUsuario(), dni, nombre, apellido, relacion, fechaNac, fechaR);
        StringRequest request = new StringRequest(mFamiliar != null ? Request.Method.POST : Request.Method.PUT, URL, new Response.Listener<String>() {
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
                    Utils.showToast(getContext(), mFamiliar != null ? getString(R.string.familiarCambiabo) :
                            getString(R.string.familiarAgregado));
                    if (mFamiliar != null) {
                        updateReg(isActive);
                        edit = false;
                        btnAceptar.setEnabled(true);
                    } else {
                        loadDataFam();
                        mOnClickListenerAdapter.onClick(mFamiliar);
                        dismiss();
                    }
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
