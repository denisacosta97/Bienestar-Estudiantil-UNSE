package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Reserva;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarReservasActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    Calendar calendar;
    CalendarView calendarView;
//    ArrayList<ReservasFirebase> mReservas;
    ArrayList<Reserva> listRes;
    String [] fechaHora;
    TextView txtMensaje;
    ProgressBar progres;

    boolean download = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_asistencia);

        setToolbar();

        loadView();

        loadData();

        loadListener();

        //queryToFirebase();

    }

    private void loadListener() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String msg = i2 + "/" + (i1 + 1) + "/" + i;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                //getFechasOcupadas(msg);

                if(download){
                    Intent h = new Intent(getApplicationContext(), SelectInstActivity.class);
//                    h.putExtra("listOcupados", listRes);
//                    h.putExtra("fecha", msg);
                    startActivity(h);

                }else{
                    Utils.showToast(getApplicationContext(), "Espere y vuelva a intentar");
                }

            }
        });
    }

    private void loadView() {
        calendarView = findViewById(R.id.calendarView);
        progres = findViewById(R.id.progres);
        txtMensaje = findViewById(R.id.txtDesc);
    }

    private void loadData() {
        //mReservas = new ArrayList<>();
        //listRes = new ArrayList<>();
        calendar = Calendar.getInstance();

        updateView(2);

        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        calendar.set(Calendar.YEAR, 2012);

        calendar.add(Calendar.YEAR, 1);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

    }

    private void updateView(int b) {
        switch (b){
            case 0:
            progres.setVisibility(View.VISIBLE);
            txtMensaje.setVisibility(View.VISIBLE);
            calendarView.setVisibility(View.GONE);
            txtMensaje.setText("CARGANDO RESERVAS");
            break;
            case 1:
            progres.setVisibility(View.VISIBLE);
            txtMensaje.setVisibility(View.VISIBLE);
                calendarView.setVisibility(View.GONE);
            txtMensaje.setText("ERROR: VUELVA A INGRESAR A LAS RESERVAS");
            break;
            case 2:
                progres.setVisibility(View.GONE);
                calendarView.setVisibility(View.VISIBLE);
                txtMensaje.setVisibility(View.GONE);
                break;

        }
    }

//    private void getFechasOcupadas(String fechaSeleccionada) {
//        listRes = new ArrayList<>();
//        for(ReservasFirebase rShort : mReservas){
//            String fechaRes = rShort.getFechaReserva();
//            fechaHora = fechaRes.split(" ");
//            if (Arrays.asList(fechaHora).contains(fechaSeleccionada)){
//                Toast.makeText(this, ""+ fechaSeleccionada, Toast.LENGTH_SHORT).show();
//                listRes.add(rShort);
//            }
//
//        }
//    }


//    private void queryToFirebase() {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("reservas").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                if(!queryDocumentSnapshots.isEmpty()){
//                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//
//                    if(list.size() != 0) {
//                        for (DocumentSnapshot d : list) {
//                            ReservasFirebase reserva = d.toObject(ReservasFirebase.class);
//                            reserva.setId(d.getId());
//                            if(!reserva.getEstado().equals("cancelado")) {
//                                mReservas.add(reserva);
//                            }
//                        }
//                    }
//                    else
//                        Utils.showToast(getApplicationContext(), "Sin reservas actuales");
//                    updateView(2);
//                    filter(mReservas);
//
//                }
//                download = true;
//            }
//
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                updateView(1);
//                Utils.showToast(getApplicationContext(),"Error: ¡Revisa tu conexion a internet!");
//            }
//        });
//
//    }

//    private void filter(ArrayList<ReservasFirebase> reservas) {
//        ArrayList<ReservasFirebase> actualizar = new ArrayList<>();
//        for (ReservasFirebase re : reservas){
//            if(re.getEstado().equals("pendiente")){
//
//                List<String> fecha = Arrays.asList(re.getFecha());
//                Date reserva = Utils.getFechaDate(fecha.get(0));
//
//                Date evento = Utils.getFechaDate(re.getFechaReserva());
//               /* Calendar calendar = new GregorianCalendar();
//                calendar.setTime(evento);
//                calendar.add(Calendar.DAY_OF_MONTH, -2);
//                evento = calendar.getTime();
//                //reserva = calendar.getTime();*/
//
//                Date hoy = new Date(System.currentTimeMillis());
//                int diasHoy = reserva.compareTo(evento), diasEvento = evento.compareTo(hoy);
//                Long dias = Utils.computeDiff(hoy, evento).get(TimeUnit.DAYS);
//                if(dias != null){
//                    if(dias <= 0){
//                        re.setEstado("cancelado");
//                        actualizar.add(re);
//                    }
//                }
//            }
//
//        }
//        for(ReservasFirebase fe : actualizar){
//            sendDataUpdate(fe);
//        }
//        /*
//        if(re.getEstado().equals("pendiente")){
//                List<String> fecha = Arrays.asList(re.getFecha());
//                Date reserva = Utils.getFechaDate(fecha.get(0));
//                //Calendar calendar = new GregorianCalendar();
//                //calendar.setTime(reserva);
//                //calendar.add(Calendar.DAY_OF_MONTH, 7);
//                //reserva = calendar.getTime();
//                Date evento = Utils.getFechaDate(re.getFechaReserva());
//                Date hoy = new Date(System.currentTimeMillis());
//                int diasHoy = reserva.compareTo(evento), diasEvento = evento.compareTo(hoy);
//                Long dias = Utils.computeDiff(reserva, hoy).get(TimeUnit.DAYS);
//                if(dias != null){
//                    if(dias >= 7){
//                        re.setEstado("cancelado");
//                        actualizar.add(re);
//                    }
//                }
//                dias = Utils.computeDiff(hoy, evento).get(TimeUnit.DAYS);
//                if(dias != null){
//                    if(dias < 0){
//                        re.setEstado("cancelado");
//                        actualizar.add(re);
//                    }
//                }
//            }
//         */
//    }

//    private void sendDataUpdate(ReservasFirebase reservasFirebase) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        String id = reservasFirebase.getId();
//        db.collection("reservas").document(id).set(reservasFirebase).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                //Utils.showToast(getApplicationContext(), "¡Error al enviar datos!");
//            }
//        }).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                //Utils.showToast(getApplicationContext(), "¡Registro subido!");
//            }
//        });
//    }


    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.imgFlecha).setVisibility(View.VISIBLE);
        findViewById(R.id.imgFlecha).setOnClickListener(this);
        ((TextView)findViewById(R.id.txtTitulo)).setText("Calendario de reservas");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

}
