package com.unse.bienestarestudiantil.Vistas.Activities.Comedor;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.unse.bienestarestudiantil.Herramientas.Estadisticas;
import com.unse.bienestarestudiantil.Herramientas.GeneratePDFTask;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Datos;
import com.unse.bienestarestudiantil.Modelos.Menu;
import com.unse.bienestarestudiantil.Modelos.Reserva;
import com.unse.bienestarestudiantil.Modelos.ReservaEspecial;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.EstadisticasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class ResumenPDFComedorActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgIcono;
    TextView txtMes, txtTotalFAYA, txtTotalFCE, txtTotalFCF, txtTotalFCM, txtTotalFHS, txtTotalReservas,
            txtTotalPorciones, txtTotalEspeciales, txtPorcionesEspeciales;
    Button btnPdf;
    BarChart barMes;
    PieChart pieFacultad;
    LinearLayout latDatos;
    ArrayList<Menu> menus;
    ArrayList<Usuario> alumnos;
    ArrayList<Reserva> reservas;
    ArrayList<ReservaEspecial> reservasE;
    ProgressBar mProgressBar;
    String mes;
    HashMap<String, Menu> menusHash;
    HashMap<String, Integer[]> reservasAlumnosDias;
    int numberMes = 0, days = 0, porciones, cantidadPorciones = 0, cantidadReservasEspeciales = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_comedor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadIntent();

        loadViews();

        loadListener();

        loadData();

        setToolbar();

    }

    private void loadIntent() {
        if (getIntent().getSerializableExtra(Utils.ID_MENU) != null) {
            menus = (ArrayList<Menu>) getIntent().getSerializableExtra(Utils.ID_MENU);
        }
        if (getIntent().getSerializableExtra(Utils.ALUMNO_NAME) != null) {
            alumnos = (ArrayList<Usuario>) getIntent().getSerializableExtra(Utils.ALUMNO_NAME);
        }
        if (getIntent().getSerializableExtra(Utils.RESERVA) != null) {
            reservas = (ArrayList<Reserva>) getIntent().getSerializableExtra(Utils.RESERVA);
        }

        if (getIntent().getSerializableExtra(Utils.RESERVA_ESPECIALES) != null) {
            reservasE = (ArrayList<ReservaEspecial>) getIntent().getSerializableExtra(Utils.RESERVA_ESPECIALES);
        }

        if (getIntent().getStringExtra(Utils.MESNAME) != null) {
            mes = getIntent().getStringExtra(Utils.MESNAME);
        }
        if (getIntent().getIntExtra(Utils.NUMERO_MES, -1) != -1) {
            numberMes = getIntent().getIntExtra(Utils.NUMERO_MES, -1);
        }
    }

    private void loadData() {
        Calendar calendar = new GregorianCalendar(2020, numberMes, 1);
        days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        reservasAlumnosDias = new HashMap<>();
        txtMes.setText(mes.toUpperCase());
        loadEstadisticas();
        txtPorcionesEspeciales.setText(String.valueOf(cantidadReservasEspeciales));
        txtTotalReservas.setText(String.valueOf(reservas.size()));
        latDatos.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    private void loadEstadisticas() {
        loadMenus();
        loadEstadisticasReservas();
        loadEstadisticasFacultad();
    }

    private void loadMenus() {
        menusHash = new HashMap<>();
        for (Menu menu : menus) {
            menusHash.put(String.valueOf(menu.getIdMenu()), menu);
        }
    }

    private void loadEstadisticasFacultad() {
        int[] facultades = new int[6];
        for (Usuario usuario : alumnos) {
            Alumno alumno = (Alumno) usuario;
            switch (alumno.getFacultad()) {
                case "FAyA":
                    facultades[0] = facultades[0] + 1;
                    break;
                case "FCEyT":
                    facultades[1] = facultades[1] + 1;
                    break;
                case "FCF":
                    facultades[2] = facultades[2] + 1;
                    break;
                case "FCM":
                    facultades[3] = facultades[3] + 1;
                    break;
                case "FHCSyS":
                    facultades[4] = facultades[4] + 1;
                    break;
            }

        }
        facultades[5] = reservasE.size();

        txtTotalFAYA.setText(String.valueOf(facultades[0]));
        txtTotalFCE.setText(String.valueOf(facultades[1]));
        txtTotalFCF.setText(String.valueOf(facultades[2]));
        txtTotalFCM.setText(String.valueOf(facultades[3]));
        txtTotalFHS.setText(String.valueOf(facultades[4]));
        txtTotalEspeciales.setText(String.valueOf(reservasE.size()));

        //Alumnos por Facultad
        pieFacultad.setUsePercentValues(true);
        pieFacultad.getDescription().setEnabled(false);
        pieFacultad.setExtraOffsets(5, 10, 5, 5);
        pieFacultad.getLegend().setEnabled(true);
        pieFacultad.setDragDecelerationFrictionCoef(0.99f);
        pieFacultad.setDrawHoleEnabled(true);
        pieFacultad.setHoleColor(Color.WHITE);
        pieFacultad.setTransparentCircleRadius(61f);
        //mPieChart.setEntryLabelColor(Color.BLACK);
        pieFacultad.setEntryLabelTextSize(0); //Nuevo
        ArrayList<PieEntry> yEntry = new ArrayList<>();
        int i = 0;
        for (Integer integer : facultades) {
            yEntry.add(new PieEntry(integer, i != 5 ? Utils.facultad[i] : "Otros"));
            i++;
        }
        PieDataSet dataSet = new PieDataSet(yEntry, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(new int[]{R.color.colorRed, R.color.colorFCEyT,
                R.color.colorGreen, R.color.colorLightBlue, R.color.colorYellow, R.color.colorGreyDark}, getApplicationContext());
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(17f);
        pieData.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        pieData.setValueTextColor(Color.BLACK);
        pieFacultad.setData(pieData);
    }

    private void loadEstadisticasReservas() {
        //Reservas Ultimos 7 dias
        Estadisticas.initBar(barMes);
        //Reservas Ultimos 7 dias
        final ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> entryLabels = new ArrayList<String>();
        YAxis leftAxis = barMes.getAxisLeft();

        leftAxis.setTextSize(12f);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);


        int cantidadRes = 0, cantidadReti = 0, cantidadCancelado = 0, cantidadNoRetirados = 0;
        porciones = 0;
        for (Reserva reserva : reservas) {

            Menu menu = menusHash.get(String.valueOf(reserva.getIdMenu()));
            if (menu != null) {
                int dia = menu.getDia();
                if (reservasAlumnosDias.containsKey(String.valueOf(reserva.getIdUsuario()))) {
                    reservasAlumnosDias.get(String.valueOf(reserva.getIdUsuario()))[dia - 1] = 1;
                } else {
                    Integer[] reser = new Integer[days];
                    Arrays.fill(reser, 0);
                    reser[dia - 1] = 1;
                    reservasAlumnosDias.put(String.valueOf(reserva.getIdUsuario()), reser);
                }
            }
            if (reserva.getDescripcion().equals("RESERVADO")) {
                cantidadRes++;
            } else if (reserva.getDescripcion().equals("CANCELADO")) {
                cantidadCancelado++;
            } else if (reserva.getDescripcion().equals("RETIRADO")) {
                if (menu != null)
                    porciones = porciones + menu.getPorcion();
                cantidadReti++;
            } else if (reserva.getDescripcion().equals("NO RETIRADO")) {

                cantidadNoRetirados++;
            }
        }

        for (ReservaEspecial reservaEspecial : reservasE) {

            Menu menu = menusHash.get(String.valueOf(reservaEspecial.getIdMenu()));
            if (menu != null) {
                int dia = menu.getDia();
                if (reservasAlumnosDias.containsKey(String.valueOf(reservaEspecial.getIdUsuario()))) {
                    reservasAlumnosDias.get(String.valueOf(reservaEspecial.getIdUsuario()))[dia - 1] = 1;
                } else {
                    Integer[] reser = new Integer[days];
                    Arrays.fill(reser, 0);
                    reser[dia - 1] = 1;
                    reservasAlumnosDias.put(String.valueOf(reservaEspecial.getIdUsuario()), reser);
                }
                cantidadReservasEspeciales = cantidadReservasEspeciales + reservaEspecial.getCantidad();
                cantidadPorciones = cantidadPorciones + (reservaEspecial.getPorcion() * reservaEspecial.getCantidad());
            }
        }
        txtTotalPorciones.setText(String.format("%s + %s (Esp.) = %s", porciones, cantidadPorciones,
                String.valueOf(porciones + cantidadPorciones)));
        entries.add(new BarEntry(1, reservas.size()));
        entryLabels.add("Total");
        entries.add(new BarEntry(2, cantidadReti));
        entryLabels.add("Retiros");
        entries.add(new BarEntry(3, cantidadRes));
        entryLabels.add("Reservas");
        entries.add(new BarEntry(4, cantidadCancelado));
        entryLabels.add("Cancelos");
        entries.add(new BarEntry(5, cantidadNoRetirados));
        entryLabels.add("No Retirados");

        ArrayList<Datos> datos = new ArrayList<>();
        for (BarEntry e : entries) {
            datos.add(new Datos(entryLabels.get(((int) e.getX()) - 1), e.getY()));
        }

        RecyclerView linearLayout = findViewById(R.id.latDatos2);
        int[] colors = new int[]{ColorTemplate.rgb("#03C5DA"), ColorTemplate.rgb("#32AC37"),
                ColorTemplate.rgb("#E64A19"), ColorTemplate.rgb("#D32F2F"), ColorTemplate.rgb("#C2185B")};
        EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 2);
        linearLayout.setLayoutManager(manager);
        linearLayout.setNestedScrollingEnabled(true);
        linearLayout.setAdapter(adapter);

        Estadisticas.endBar(barMes, entries, colors);

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnPdf.setOnClickListener(this);

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Informe");
    }

    private void loadViews() {
        txtPorcionesEspeciales = findViewById(R.id.txtTotalPorcionesEspeciales);
        txtTotalEspeciales = findViewById(R.id.txtTotalEspeciales);
        txtTotalPorciones = findViewById(R.id.txtTotalPorciones);
        txtTotalReservas = findViewById(R.id.txtTotalReservas);
        txtTotalFAYA = findViewById(R.id.txtTotalFaya);
        txtTotalFCE = findViewById(R.id.txtTotalFCeT);
        txtTotalFCF = findViewById(R.id.txtTotalFCF);
        txtTotalFCM = findViewById(R.id.txtTotalFCM);
        txtTotalFHS = findViewById(R.id.txtTotalFHS);
        mProgressBar = findViewById(R.id.progress_bar);
        latDatos = findViewById(R.id.latDatos);
        imgIcono = findViewById(R.id.imgFlecha);
        txtMes = findViewById(R.id.txtMes);
        btnPdf = findViewById(R.id.btnPdf);
        barMes = findViewById(R.id.barMeses);
        pieFacultad = findViewById(R.id.pieFacultad);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnPdf:
                checkPermiso();
                break;

        }
    }

    private void checkPermiso() {
        if (Utils.isPermissionGranted(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (Utils.isPermissionGranted(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                generatePDF();
            } else {
                showPermission();
            }

        } else {
            showPermission();
        }
    }

    private void showPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private void generatePDF() {
        Bitmap graficoBarra = Utils.getBitmapFromView(this, findViewById(R.id.cardReservas));
        Bitmap graficoTorta = Utils.getBitmapFromView(this, findViewById(R.id.cardPorFacultad));
        DialogoProcesamiento dialogoProcesamiento = new DialogoProcesamiento();
        new GeneratePDFTask(2, alumnos, mes, graficoBarra, graficoTorta, days,
                reservasAlumnosDias, dialogoProcesamiento, getApplicationContext()
                , new int[]{porciones, cantidadPorciones, cantidadReservasEspeciales, reservas.size()}).execute();
        dialogoProcesamiento.show(getSupportFragmentManager(), "jeje");

    }
}
