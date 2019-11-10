package com.unse.bienestarestudiantil.Vistas.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Noticia;
import com.unse.bienestarestudiantil.R;

public class NewsReader extends AppCompatActivity {

    Noticia mNoticia;
    TextView etiqueta, titulo, cuerpo, fecha, hora;
    ImageView portada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_reader);

        Utils.setFont(getApplicationContext(), (ViewGroup) findViewById(android.R.id.content), Utils.MONSERRAT);

        if (getIntent().getParcelableExtra(Utils.NOTICIA) != null) {
            mNoticia = getIntent().getParcelableExtra(Utils.NOTICIA);
        }

        if (mNoticia != null) {
            loadViews();
            loadData();

        } else {
            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelta a intentar");
            finish();
        }

    }

    private void loadData() {
        etiqueta.setText(mNoticia.getEtiqueta());
        titulo.setText(mNoticia.getTitulo());
        cuerpo.setText(mNoticia.getCuerpo());
        fecha.setText(mNoticia.getFechahora());
        hora.setText("Hace: 5 Minutos");

    }


    private void loadViews() {
        etiqueta = findViewById(R.id.txtEtiqueta);
        titulo = findViewById(R.id.txtTitulo);
        cuerpo = findViewById(R.id.txtCuerpo);
        fecha = findViewById(R.id.txtFecha);
        hora = findViewById(R.id.txtHoraPublicado);

    }

}
