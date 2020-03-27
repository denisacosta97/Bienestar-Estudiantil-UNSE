package com.unse.bienestarestudiantil.Vistas.Activities.Inicio;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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

import com.unse.bienestarestudiantil.Databases.AlumnosRepo;
import com.unse.bienestarestudiantil.Databases.BDGestor;
import com.unse.bienestarestudiantil.Databases.DBManager;
import com.unse.bienestarestudiantil.Databases.UsuariosRepo;
import com.unse.bienestarestudiantil.Herramientas.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.AboutActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Becas.GestionBecas.MainGestionBecasActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.RegistroDeporteActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionSistemaActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Perfil.PerfilActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionPolideportivoActivity;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.AccesoDenegadoFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.BecasFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.ComedorFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DeportesFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.InicioFragmento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.PoliFragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    PreferenceManager manager;
    Toolbar mToolbar;
    Fragment mFragment;
    int itemSelecionado = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        comprobarNavigationView();

        setToolbar();

        createBD();

        loadData();

        checkUser();

    }

    private void createBD() {
        BDGestor gestor = new BDGestor(getApplicationContext());
        DBManager.initializeInstance(gestor);

    }

    private void loadData() {
        manager = new PreferenceManager(getApplicationContext());
    }

    private void loadViews() {
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.toolbar);
    }

    private void comprobarNavigationView() {
        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
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
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
        navigationView.setCheckedItem(R.id.item_inicio);
    }

    private void checkUser() {
        navigationView.removeHeaderView(navigationView.getHeaderView(0));
        View hView = navigationView.inflateHeaderView(R.layout.cabecera_drawer);
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        boolean isLogin = preferenceManager.getValue(Utils.IS_LOGIN);

        ImageView img = hView.findViewById(R.id.imgUserPerfil);
        ImageView imgBienestar = hView.findViewById(R.id.logoBienestar);
        TextView nameUser = hView.findViewById(R.id.txtNombreUser);
        TextView carrera = hView.findViewById(R.id.txtCarrera);
        if (isLogin) {
            int dni = preferenceManager.getValueInt(Utils.MY_ID);
            Usuario usuario = new UsuariosRepo(getApplicationContext()).get(dni);
            Alumno alumno = new AlumnosRepo(getApplicationContext()).get(dni);
            imgBienestar.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);
            img.setImageResource(R.drawable.user);
            nameUser.setText(String.format("%s %s", usuario.getNombre(), usuario.getApellido()));
            carrera.setText(alumno.getCarrera());
        } else {
            img.setVisibility(View.GONE);
            imgBienestar.setVisibility(View.VISIBLE);
            imgBienestar.setImageResource(R.drawable.ic_logo_bienestar_01);
            nameUser.setText("BIENESTAR ESTUDIANTIL");
            carrera.setText("Secretaría de Bienestar Estudiantil");
        }

    }

    /*
    REVISAAAAARRRRR
     */
    private void updateMenu() {
        Menu menu = navigationView.getMenu();
        int range = manager.getValueInt(Utils.TYPE_RANGE);
        if (range == 0) {
            MenuItem men = menu.findItem(R.id.item_perfil);
            men.setVisible(false);
            men = menu.findItem(R.id.profe_profile);
            men.setVisible(false);
            men = menu.findItem(R.id.item_config);
           // men.setVisible(false);
        }
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
            case R.id.item_comedor:
                fragmentoGenerico = new ComedorFragment();
                break;
            case R.id.item_perfil:
                startActivity(new Intent(MainActivity.this, PerfilActivity.class));
                break;
            case R.id.profe_profile:
                startActivity(new Intent(MainActivity.this, RegistroDeporteActivity.class));
                break;
            case R.id.item_config:
                startActivity(new Intent(this, GestionSistemaActivity.class));
                break;
            case R.id.item_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }


        if (!(fragmentoGenerico instanceof InicioFragmento)) {
            boolean isLogin = manager.getValue(Utils.IS_LOGIN);
            if (!isLogin) {
                fragmentoGenerico = new AccesoDenegadoFragment();
            }
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
        ((TextView) findViewById(R.id.txtTitulo)).setText(itemDrawer.getTitle());
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        findViewById(R.id.imgFlecha).setVisibility(View.GONE);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("Bienestar Estudiantíl");
            mToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        int range = manager.getValueInt(Utils.TYPE_RANGE);
        if (range == 0) {
            MenuItem menuItem = menu.findItem(R.id.item_admin);
            menuItem.setVisible(false);
        }
        //updateMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
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
                        case R.id.item_becas:
                            if (true) { //Si tiene los permisos necesarios
                                startActivity(new Intent(getApplicationContext(), MainGestionBecasActivity.class));
                            }
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(Gravity.LEFT);
        else if (!(mFragment instanceof InicioFragmento)) {
            seleccionarItem(navigationView.getMenu().getItem(0));
            navigationView.setCheckedItem(R.id.item_inicio);
        } else
            super.onBackPressed();
    }

}
