package com.unse.bienestarestudiantil.Vistas.Activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Noticia;
import com.unse.bienestarestudiantil.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NoticiaLectorActivity extends AppCompatActivity implements View.OnClickListener {

    Noticia mNoticia;
    TextView etiqueta, titulo, cuerpo, fecha, hora;
    ImageView imgFlecha, imgFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_reader);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.NOTICIA) != null) {
            mNoticia = getIntent().getParcelableExtra(Utils.NOTICIA);
        }

        if (mNoticia != null) {

            setToolbar();

            loadViews();

            loadData();

            loadListener();

            Utils.changeColorDrawable(imgFlecha, getApplicationContext(), R.color.colorPrimary);

        } else {
            Utils.showToast(getApplicationContext(), "Error al abrir la noticia, vuelta a intentar");
            finish();
        }

    }

    private void setToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ((TextView) findViewById(R.id.txtTitulo)).setText("");
    }

    private void loadListener() {
        imgFlecha.setOnClickListener(this);
    }

    private void loadData() {
        etiqueta.setText(mNoticia.getNombreArea());
        titulo.setText(mNoticia.getTitulo());
        cuerpo.setText(mNoticia.getDescripcion());
        fecha.setText(Utils.getBirthday(Utils.getFechaDateWithHour(mNoticia.getFechaRegistro())));
        hora.setText("Hace: 5 Minutos");
        String URL = String.format(Utils.URL_IMAGE_NOTICIA, mNoticia.getImagen());
        Glide.with(imgFoto.getContext())
                .applyDefaultRequestOptions(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .load(URL)
                .into(imgFoto);
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
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }
}
