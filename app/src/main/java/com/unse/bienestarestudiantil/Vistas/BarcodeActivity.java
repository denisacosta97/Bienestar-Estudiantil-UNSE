package com.unse.bienestarestudiantil.Vistas;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado  del scanner: ");
        builder.setMessage("Resultado "+rawResult.getText()+"\n"+"Formato "+rawResult.getBarcodeFormat());

        ArrayList<String> resultados = new ArrayList<>();
        Pattern regex = Pattern.compile("@([A-Z0-9\\/ ])*");
        Matcher matcher = regex.matcher(rawResult.getText());
        while (matcher.find()){
            resultados.add(matcher.group(0));
        }

        

//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }
}
