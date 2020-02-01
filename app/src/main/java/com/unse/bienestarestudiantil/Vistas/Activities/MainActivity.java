package com.unse.bienestarestudiantil.Vistas.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionSistemaActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionPolideportivoActivity;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.BecasFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DeportesFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.InicioFragmento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.PoliFragment;
import com.unse.bienestarestudiantil.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private ActivityMainBinding mBinding;
    Toolbar mToolbar;
    Fragment mFragment;
    int itemSelecionado = -1;
    boolean loginOk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //Utils.setFont(getApplicationContext(), (ViewGroup) findViewById(android.R.id.content), Utils.MONSERRAT);

        comprobarNavigationView();

        NavigationView navigationView = findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        navigationView.removeHeaderView(navigationView.getHeaderView(0));
        View hView = navigationView.inflateHeaderView(R.layout.cabecera_drawer);
        if(loginOk){
            ImageView imgbien = hView.findViewById(R.id.logoBienestar);
            ImageView imgvw = hView.findViewById(R.id.imgUserPerfil);
            TextView nameUser = hView.findViewById(R.id.txtNombreUser);
            TextView carrera = hView.findViewById(R.id.txtCarrera);
            imgbien.setVisibility(View.GONE);
            imgvw.setVisibility(View.VISIBLE);
            imgvw.setImageResource(R.drawable.user);
            nameUser.setText("Cristian Ledesma");
            carrera.setText("Lic. en Sistemas de Información");
        }
        else {
            ImageView img = hView.findViewById(R.id.imgUserPerfil);
            ImageView imgvw = hView.findViewById(R.id.logoBienestar);
            TextView nameUser = hView.findViewById(R.id.txtNombreUser);
            TextView carrera = hView.findViewById(R.id.txtCarrera);
            img.setVisibility(View.GONE);
            imgvw.setVisibility(View.VISIBLE);
            imgvw.setImageResource(R.drawable.ic_logo_bienestar_01);
            nameUser.setText("BIENESTAR ESTUDIANTIL");
            carrera.setText("Secretaría de Bienestar Estudiantil");
        }

        setToolbar();

        Utils.createPDF(getApplicationContext());

        String x = "Denis";
        String msj = Utils.crypt(Utils.getStringValue(x,29,9,1997));
        x = "Deniss";
        String msj2 = Utils.crypt(Utils.getStringValue(x,29,9,1997));
        if (msj.equals(msj2)){
            Utils.showToast(getApplicationContext(),"Iguales");
        }
    }


    private void comprobarNavigationView() {
        if (mBinding.navView != null) {
            prepararDrawer(mBinding.navView);
            // Seleccionar item por defecto
            seleccionarItem(mBinding.navView.getMenu().getItem(0));
        }
    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        mBinding.drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new InicioFragmento();
                break;
            case R.id.item_poli:
                fragmentoGenerico = new PoliFragment();
                break;
            case R.id.item_deporte:
                fragmentoGenerico = new DeportesFragment();
                break;
            case R.id.item_becas:
                fragmentoGenerico = new BecasFragment();
                break;
            case R.id.item_perfil:
                startActivity(new Intent(MainActivity.this, PerfilActivity.class));
                break;
            case R.id.profe_profile:
                startActivity(new Intent(MainActivity.this, PerfilProfesorActivity.class));
                break;
            case R.id.item_config:
                startActivity(new Intent(this, GestionSistemaActivity.class));
                break;

        }

        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

        mFragment = fragmentoGenerico;

        itemSelecionado = itemDrawer.getItemId();

        // Setear título actual
        mBinding.contenedor.toolbarLay.txtTitulo.setText(itemDrawer.getTitle());
        //setTitle(itemDrawer.getTitle());
    }

    private void setToolbar() {
        setSupportActionBar(mBinding.contenedor.toolbarLay.toolbar);
        mBinding.contenedor.toolbarLay.imgFlecha.setVisibility(View.GONE);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("Bienestar Estudiantíl");
            mBinding.contenedor.toolbarLay.toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Utils.setFont(getApplicationContext(), (ViewGroup) findViewById(android.R.id.content), Utils.MONSERRAT);
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mBinding.drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.item_admin:
                if (itemSelecionado != -1) {
                    switch (itemSelecionado) {
                        case R.id.item_poli:
                            if (true) { //Si tiene los permisos necesarios
                                startActivity(new Intent(getApplicationContext(), GestionPolideportivoActivity.class));
                            }
                            break;
                        case R.id.item_deporte:
                            break;
                        default:
                            Utils.showToast(getApplicationContext(), "No se definió una zona administrativa aún");
                            break;
                        //Resto de ls secciones
                    }
                } else {
                    Utils.showToast(getApplicationContext(), "No tiene los permisos necesarios para administrar dicha sección");
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START))
            mBinding.drawerLayout.closeDrawer(Gravity.LEFT);
        else if (!(mFragment instanceof InicioFragmento)) {
            seleccionarItem(mBinding.navView.getMenu().getItem(0));
            mBinding.navView.setCheckedItem(0);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
