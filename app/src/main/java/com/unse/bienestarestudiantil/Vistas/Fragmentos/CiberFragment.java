package com.unse.bienestarestudiantil.Vistas.Fragmentos;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.integration.android.IntentIntegrator;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Inicio.MainActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.RecorridoActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.RecorridoAdapter;

import java.util.ArrayList;

public class CiberFragment extends Fragment implements View.OnClickListener  {

    View view;
    CardView cardScanner;
    Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ciber, container, false);

        loadViews();

        loadData();

        return view;
    }

    private void loadData() {
        cardScanner.setOnClickListener(this);
    }

    private void loadViews() {
        cardScanner = view.findViewById(R.id.cardScanner);
    }

    public void scanQR() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(mActivity);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("Scan");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();
        ((MainActivity)mActivity).qrCiber = true;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cardScanner){
            scanQR();
        }
    }
}