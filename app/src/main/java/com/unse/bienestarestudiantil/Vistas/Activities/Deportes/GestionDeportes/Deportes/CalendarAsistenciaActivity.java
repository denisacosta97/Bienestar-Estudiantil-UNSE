package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes.Deportes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Asistencia;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarAsistenciaActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    Calendar calendar;
    CalendarView calendarView;
    ArrayList<Asistencia> mAsistencias;
    Deporte mDeportes;
    ArrayList<Usuario> mUsuarios;
    TextView txtMensaje;
    ProgressBar progres;
    DialogoProcesamiento dialog;

    boolean download = true;

    List<EventDay> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_reservas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.DEPORTE_NAME) != null) {
            mDeportes = getIntent().getParcelableExtra(Utils.DEPORTE_NAME);
        }

        setToolbar();

        loadView();

        loadData();

        loadListener();

        getAll();
    }

    private void loadListener() {
        mAsistencias = new ArrayList<>();
        mUsuarios = new ArrayList<>();
        events = new ArrayList<>();

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                Intent i = new Intent(getApplicationContext(), ListAsistenciaDeportesActivity.class);
                i.putExtra(Utils.ASISTENCIA, mAsistencias);
                i.putExtra(Utils.USER_INFO, mUsuarios);
                i.putExtra("fecha", clickedDayCalendar);
                startActivity(i);
                Toast.makeText(CalendarAsistenciaActivity.this, "Funca", Toast.LENGTH_SHORT).show();
            }
        });

        /*calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String msg = i2 + "/" + (i1 + 1) + "/" + i;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                //getFechasAsistencia(msg);

                if(download){
                    Intent h = new Intent(getApplicationContext(), ListAsistenciaDeportesActivity.class);
//                    h.putExtra("listOcupados", listRes);
//                    h.putExtra("fecha", msg);
                    startActivity(h);

                }else{
                    Utils.showToast(getApplicationContext(), "Espere y vuelva a intentar");
                }

            }
        });*/
    }

    private void loadView() {
        calendarView = findViewById(R.id.calendarView);
        progres = findViewById(R.id.progres);
        txtMensaje = findViewById(R.id.txtDesc);
    }

    private void loadData() {
        updateView(0);
        calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());

        try {
            calendarView.setDate(date);
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

    }

    private void updateView(int b) {
        switch (b){
            case 0:
                progres.setVisibility(View.VISIBLE);
                txtMensaje.setVisibility(View.VISIBLE);
                calendarView.setVisibility(View.GONE);
                txtMensaje.setText("CARGANDO");
                break;
            case 1:
                progres.setVisibility(View.VISIBLE);
                txtMensaje.setVisibility(View.VISIBLE);
                calendarView.setVisibility(View.GONE);
                txtMensaje.setText("ERROR: VUELVA A INGRESAR.");
                break;
            case 2:
                progres.setVisibility(View.GONE);
                calendarView.setVisibility(View.VISIBLE);
                txtMensaje.setVisibility(View.GONE);
                break;

        }
    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.imgFlecha).setVisibility(View.VISIBLE);
        findViewById(R.id.imgFlecha).setOnClickListener(this);
        ((TextView)findViewById(R.id.txtTitulo)).setText("Asistencia");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

    /*private void getFechasAsistencia(String fechaSeleccionada) {
        listRes = new ArrayList<>();
        for(ReservasFirebase rShort : mReservas){
            String fechaRes = rShort.getFechaReserva();
            fechaHora = fechaRes.split(" ");
            if (Arrays.asList(fechaHora).contains(fechaSeleccionada)){
                Toast.makeText(this, ""+ fechaSeleccionada, Toast.LENGTH_SHORT).show();
                listRes.add(rShort);
            }

        }
    }*/

    private void getAll() {
        String key = new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN);
        String URL = String.format("%s?key=%s", Utils.URL_ASISTENCIA, key);

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getApplicationContext(), "Error de conexión o servidor fuera de rango");
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case 1:
                    //Exito
                    JSONArray jsonArray = jsonObject.getJSONArray("mensaje");
                    load(jsonArray);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), "La contraseña actual ingresada es inválida");
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), "No se puede procesar la tarea solicitada");
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), "No está autorizado para realizar ésta operación");
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), "Error desconocido, contacta al Administrador");
        }
    }

    private void load(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject j = jsonArray.getJSONObject(i);
            int idDep = j.getInt("idDeporte");

            if(idDep == mDeportes.getIdDep()) {
                Asistencia asistencia = new Asistencia();
                asistencia.setId(j.getInt("idAsistencia"));
                asistencia.setIdAlumno(j.getInt("idAlumno"));
                asistencia.setIdDeporte(j.getInt("idDeporte"));
                asistencia.setFechaFalta(j.getString("fechaFalta"));
                asistencia.setAsistencia(j.getString("asistencia"));
                mAsistencias.add(asistencia);

                String fecha = j.getString("fechaFalta");
                setEventsCalendar(fecha);

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(j.getInt("idUsuario"));
                usuario.setNombre(j.getString("nombre"));
                usuario.setApellido(j.getString("apellido"));
                mUsuarios.add(usuario);
            }

        }

        calendarView.setEvents(events);
        updateView(2);
    }

    private void setEventsCalendar(String fecha) {
        //DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        //Date parsedDate = format.parse(fecha);

        Calendar calendar = Calendar.getInstance();
        String[] values = fecha.split("-");
        calendar.set(Integer.parseInt(values[2]), Integer.parseInt(values[1]), Integer.parseInt(values[0]));

        events.add(new EventDay(calendar, R.drawable.circle_calendar));

    }

}
