package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionIngreso;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class DialogIngresoPolideportivo extends DialogFragment {

    View view;
    Button btnGuardar;
    EditText edtDni, edtCant;
    Context mContext;
    FragmentManager mFragmentManager;
    DialogoProcesamiento dialog;
    Spinner spinnerCat;
    String[] categorias = {"Seleccione una opción...", "Alumno", "Profesor", "Nodocente", "Egresado",
            "Particular", "Afiliado", "Jubilado", "Otro"};

    int categoriaSelect = 0;

    public DialogIngresoPolideportivo(Context mContext, FragmentManager fragmentManager) {
        this.mContext = mContext;
        mFragmentManager = fragmentManager;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_ingreso_polideportivo, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Esto es lo nuevoooooooo, evita los bordes cuadrados
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loadViews();

        loadData();

        loadListener();

        return view;
    }

    private void loadData() {
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), R.layout.style_spinner, categorias);
        dataAdapter2.setDropDownViewResource(R.layout.style_spinner);
        spinnerCat.setAdapter(dataAdapter2);
    }

    private void loadListener() {
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriaSelect = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadViews() {
        btnGuardar = view.findViewById(R.id.btnGuardar);
        edtDni = view.findViewById(R.id.edtDniImput);
        edtCant = view.findViewById(R.id.edtCant);
        spinnerCat = view.findViewById(R.id.spineer);
    }

    private void save() {
        Validador validador = new Validador(mContext);

        String dni = edtDni.getText().toString().trim();
        String cant = edtCant.getText().toString().trim();

        if (validador.validarDNI(edtDni) && !validador.noVacio(cant)) {
            sendServer(dni, cant);

        } else
            Toast.makeText(mContext, R.string.camposInvalidos, Toast.LENGTH_SHORT).show();
    }

    public void sendServer(String dni, String cant) {
        PreferenceManager manager = new PreferenceManager(mContext);
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&iu=%s&ie=%s&ct=%s&cn=%s", Utils.URL_INGRESO_POLI, key, idLocal, dni, idLocal, categoriaSelect, cant);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(mContext, R.string.servidorOff, Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(mFragmentManager, "dialog_process");
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
    }


    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Toast.makeText(mContext, R.string.errorInternoAdmin, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //Exito
                    Toast.makeText(mContext, "Ingreso registrado", Toast.LENGTH_SHORT).show();
                    dismiss();
                    break;
                case 4:
                    Toast.makeText(mContext, R.string.camposInvalidos, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(mContext, R.string.tokenInvalido, Toast.LENGTH_SHORT).show();
                    break;
                case 100:
                    Toast.makeText(mContext, R.string.tokenInexistente, Toast.LENGTH_SHORT).show();
                    //No autorizado
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, R.string.errorInternoAdmin, Toast.LENGTH_SHORT).show();
        }
    }

}
