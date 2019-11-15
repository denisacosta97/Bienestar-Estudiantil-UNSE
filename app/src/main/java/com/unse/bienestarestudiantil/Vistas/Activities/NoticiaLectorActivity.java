package com.unse.bienestarestudiantil.Vistas.Activities;

import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Noticia;
import com.unse.bienestarestudiantil.R;

public class NoticiaLectorActivity extends AppCompatActivity implements View.OnClickListener {

    Noticia mNoticia;
    TextView etiqueta, titulo, cuerpo, fecha, hora;
    ImageView imgFlecha, imgFoto;

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
            loadListener();
            Utils.changeColorDrawable(imgFlecha,getApplicationContext(),R.color.colorPrimary);


        } else {
            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelta a intentar");
            finish();
        }




    }

    private void loadListener() {
        imgFlecha.setOnClickListener(this);
    }

    private void loadData() {
        etiqueta.setText(mNoticia.getEtiqueta());
        titulo.setText(mNoticia.getTitulo());
        cuerpo.setText(mNoticia.getCuerpo());
        fecha.setText(mNoticia.getFechahora());
        hora.setText("Hace: 5 Minutos");
        Glide.with(imgFoto.getContext()).load(mNoticia.getUrlImagen()).into(imgFoto);

    }


    private void loadViews() {
        etiqueta = findViewById(R.id.txtEtiqueta);
        titulo = findViewById(R.id.txtTitulo2);
        cuerpo = findViewById(R.id.txtCuerpo);
        fecha = findViewById(R.id.txtFecha);
        hora = findViewById(R.id.txtHoraPublicado);
        imgFlecha = findViewById(R.id.imgFlecha);
        imgFoto = findViewById(R.id.imgPortada);

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
