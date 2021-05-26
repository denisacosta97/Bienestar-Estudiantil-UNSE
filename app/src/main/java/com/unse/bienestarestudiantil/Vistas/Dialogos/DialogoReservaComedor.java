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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.Reserva;
import com.unse.bienestarestudiantil.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DialogoReservaComedor extends DialogFragment {

    View view;
    Button btnOk, btnBuscar;
    EditText edtDNI;
    Context mContext;
    DialogoProcesamiento dialog;
    FragmentManager mFragmentManager;
    LinearLayout latDatos;
    TextView txtDNI, txtNombre, txtFacultad, txtCarrera;
    int dni = 0, idMenu = 0;

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
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
        view = inflater.inflate(R.layout.dialog_busca_usuario_comedor, container, false);
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
                if (dni != 0) {
                    reservar(dni);
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
                    buscar(edtDNI.getText().toString());
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

    private void reservar(int dni) {
        PreferenceManager manager = new PreferenceManager(getContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&i=%s&im=%s&t=%s&ie=%s",
                Utils.URL_RESERVA_INSERTAR_ALUMNO, id, key, dni,
                getIdMenu(), 4, id);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuestaReserva(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                dialogReserva(false, null);
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getFragmentManagerDialog(), "dialog_process");
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    private void dialogReserva(boolean b, Reserva reserva) {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getContext())
                .setTitulo(getString(b ? R.string.reservada : R.string.salioMal))
                .setDescripcion(b ? String.format(getString(R.string.reservaAdminExito), String.valueOf(reserva.getIdReserva()))
                        : getString(R.string.reservaError))
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {
                        dismiss();
                    }

                    @Override
                    public void no() {

                    }
                })
                .setIcono(b ? R.drawable.ic_exito : R.drawable.ic_advertencia)
                .setTipo(DialogoGeneral.TIPO_ACEPTAR);
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getFragmentManagerDialog(), "dialog_ad");
    }

    private void procesarRespuestaReserva(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getContext(), getString(R.string.errorInternoAdmin));
                    dialogReserva(false, null);
                    break;
                case 1:
                    loadInfoReserva(jsonObject);
                    break;
                case 2:
                    Utils.showToast(getContext(), getString(R.string.reservaNoExiste));
                    dialogReserva(false, null);
                    break;
                case 3:
                    Utils.showToast(getContext(), getString(R.string.tokenInvalido));
                    dialogReserva(false, null);
                    break;
                case 4:
                    dialogReserva(false, null);
                    break;
                case 5:
                    yaExisteDialogo(5);
                    break;
                case 6:
                    yaExisteDialogo(6);
                    break;
                case 7:
                    yaExisteDialogo(7);
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getContext(), getString(R.string.tokenInexistente));
                    dialogReserva(false, null);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getContext(), getString(R.string.errorInternoAdmin));
        }

    }

    private void loadInfoReserva(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje") && jsonObject.has("dato")) {

                Reserva reserva = Reserva.mapper(jsonObject.getJSONObject("dato"), Reserva.COMPLETE);

                dialogReserva(true, reserva);
            } else {
                dialogReserva(false, null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            dialogReserva(false, null);
        }
    }

    private void yaExisteDialogo(int valor) {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getContext())
                .setTitulo(getString(valor == 5 ? R.string.yaReservo : valor == 6 ? R.string.error : R.string.error))
                .setDescripcion(getString(valor == 5 ? R.string.usuarioYaReservo : valor == 6 ?
                        R.string.menuPermite : R.string.menuCerrado))
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {

                    }

                    @Override
                    public void no() {

                    }
                })
                .setIcono(valor == 5 ? R.drawable.ic_advertencia : R.drawable.ic_error)
                .setTipo(DialogoGeneral.TIPO_ACEPTAR);
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getFragmentManagerDialog(), "dialog_ad");
    }

    private void buscar(String dni) {
        PreferenceManager manager = new PreferenceManager(getContextDialog());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?id=%s&key=%s&idU=%s", Utils.URL_USUARIO_BY_ID_REDUCE_COMEDOR,
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
                String carrera = datos.has("carrera") ? datos.getString("carrera") : "";
                String facultad = datos.has("facultad") ? datos.getString("facultad") : "";

                latDatos.setVisibility(View.VISIBLE);
                txtCarrera.setText(carrera);
                txtFacultad.setText(facultad);
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
        txtFacultad = view.findViewById(R.id.txtFacultad);
        txtCarrera = view.findViewById(R.id.txtCarrera);
        btnBuscar = view.findViewById(R.id.btnBuscar);
    }

}
