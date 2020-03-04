package com.unse.bienestarestudiantil.Vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.Result;
import com.unse.bienestarestudiantil.Herramientas.Utils;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void handleResult(Result rawResult) {
       // AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Resultado  del scanner: ");
        //builder.setMessage("Resultado "+rawResult.getText()+"\n"+"Formato "+rawResult.getBarcodeFormat());

        ArrayList<String> resultados = new ArrayList<>();
        Pattern regex = Pattern.compile("@([A-Z0-9\\/ ])*");
        Matcher matcher = regex.matcher(rawResult.getText());
        while (matcher.find()){
            resultados.add(matcher.group(0).substring(1));
        }

        mScannerView.resumeCameraPreview(this);

        Intent result = new Intent();
        result.putExtra(Utils.BARCODE, resultados);
        setResult(RESULT_OK, result);
        finish();

    }
}
