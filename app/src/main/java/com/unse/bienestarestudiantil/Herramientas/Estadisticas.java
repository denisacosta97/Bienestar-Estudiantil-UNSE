package com.unse.bienestarestudiantil.Herramientas;

import android.graphics.Color;
import android.graphics.Typeface;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Estadisticas {

    public static void initPie(PieChart mPie) {
        mPie.invalidate();
        mPie.clear();
        mPie.setUsePercentValues(true);
        mPie.getDescription().setEnabled(false);
        mPie.setExtraOffsets(5, 10, 5, 5);
        mPie.getLegend().setEnabled(false);
        mPie.setDragDecelerationFrictionCoef(0.99f);
        mPie.setDrawHoleEnabled(true);
        mPie.setHoleColor(Color.WHITE);
        mPie.setTransparentCircleRadius(61f);
        mPie.setEntryLabelTextSize(0);
        mPie.setEntryLabelColor(Color.BLACK);
    }

    public static void endPie(PieChart mPie, ArrayList<PieEntry> entradasInfo, int[] colors) {
        final DecimalFormat mFormat = new DecimalFormat("###,###,##0.0");

        PieDataSet dataSetDatos = new PieDataSet(entradasInfo, "");
        dataSetDatos.setSliceSpace(3f);
        dataSetDatos.setSelectionShift(5f);
        dataSetDatos.setColors(colors);
        PieData pieDataDatos = new PieData(dataSetDatos);
        pieDataDatos.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return mFormat.format(v) + " %";
            }
        });
        pieDataDatos.setValueTextSize(16f);
        pieDataDatos.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        pieDataDatos.setValueTextColor(Color.BLACK);
        mPie.setData(pieDataDatos);
    }

    public static void initBar(BarChart mBar) {
        mBar.invalidate();
        mBar.clear();
        mBar.getDescription().setEnabled(false);
        mBar.getLegend().setEnabled(false);

        XAxis xAxis2 = mBar.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setTextSize(12f);
        //Habilita los labels
        xAxis2.setDrawAxisLine(true);
        xAxis2.setDrawGridLines(false);

        YAxis leftAxis, rightAxis;
        leftAxis = mBar.getAxisLeft();
        rightAxis = mBar.getAxisRight();

        leftAxis.setTextSize(12f);
        leftAxis.setAxisMinimum(0);
        rightAxis.setAxisMinimum(0);

        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);
    }

    public static void endBar(BarChart mBar, ArrayList<BarEntry> entries, int[] colors) {
        BarDataSet barDataSet = new BarDataSet(entries, "");
        barDataSet.setColors(colors);
        barDataSet.setValueTextSize(13);
        barDataSet.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        barDataSet.setValueTextColor(Color.rgb(155, 155, 155));
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f); // set custom bar width

        mBar.setData(barData);
        mBar.setFitBars(true);
        mBar.invalidate();
        mBar.setScaleEnabled(true);
        mBar.setDoubleTapToZoomEnabled(false);
        mBar.setBackgroundColor(Color.rgb(255, 255, 255));

        mBar.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";

            }
        });
    }

    public static void initLine(LineChart mLine) {
        mLine.getDescription().setEnabled(false);
        mLine.setExtraOffsets(5, 10, 5, 5);
        mLine.getLegend().setEnabled(true);
        mLine.setDragDecelerationFrictionCoef(0.99f);
    }

    public static void endLine(LineChart mLineRetiro, ArrayList<Entry> entries, float max) {
        YAxis leftAxis = mLineRetiro.getAxisLeft();
        leftAxis.setTextSize(12f);
        leftAxis.setAxisMinimum(-1);
        leftAxis.setAxisMaximum(max + 1);

        LineDataSet lineDataSet = new LineDataSet(entries, "");
        lineDataSet.setColor(Utils.getColors()[0]);
        //lineDataSet.setDrawCircles(false);
        lineDataSet.setCircleRadius(5);
        lineDataSet.setCircleColor(Color.BLACK);
        lineDataSet.setValueTextSize(0);
        lineDataSet.setLineWidth(2);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData lineData = new LineData(dataSets);
        mLineRetiro.setData(lineData);
        mLineRetiro.invalidate();
    }
}
