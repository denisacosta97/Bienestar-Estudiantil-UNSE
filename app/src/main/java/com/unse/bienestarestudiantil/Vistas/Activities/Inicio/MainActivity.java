package com.unse.bienestarestudiantil.Vistas.Activities.Inicio;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.unse.bienestarestudiantil.Databases.UsuarioViewModel;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.FileStorageManager;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.AboutActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionSistemaActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Perfil.PerfilActivity;
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
    UsuarioViewModel mUsuarioViewModel;
    Toolbar mToolbar;
    View headerView;
    Fragment mFragment;
    int itemSelecionado = -1, idUser = 0;
    ImageView imgPerfil, imgBienestar;
    TextView txtNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        comprobarNavigationView();

        setToolbar();

        loadData();

        checkUser();

    }


    private void loadData() {
        manager = new PreferenceManager(getApplicationContext());
        mUsuarioViewModel = new UsuarioViewModel(getApplicationContext());
    }

    private void loadViews() {
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.toolbar);
        navigationView.removeHeaderView(navigationView.getHeaderView(0));
        headerView = navigationView.inflateHeaderView(R.layout.cabecera_drawer);
        imgPerfil = headerView.findViewById(R.id.imgUserPerfil);
        imgBienestar = headerView.findViewById(R.id.logoBienestar);
        txtNombre = headerView.findViewById(R.id.txtNombreUser);
    }

    private void comprobarNavigationView() {
        if (navigationView != null) {
            prepararDrawer(navigationView);
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

    private void loadProfilePicture() {
        Bitmap bitmap = FileStorageManager.getBitmap(getApplicationContext(), Utils.FOLDER, String.format(Utils.PROFILE_PIC, idUser),
                false);
        if (bitmap != null) {
            Glide.with(imgPerfil.getContext()).load(bitmap).into(imgPerfil);
        } else {
            String URL = String.format("%s%s.jpg", Utils.URL_USUARIO_IMAGE_LOAD, idUser);
            Glide.with(imgPerfil.getContext()).load(URL)
                    .apply(new RequestOptions().error(R.drawable.ic_user)
                            .diskCacheStrategy(DiskCacheStrategy.DATA).placeholder(R.drawable.ic_user))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            FileStorageManager.saveBitmap(getApplicationContext(), Utils.FOLDER,
                                    String.format(Utils.PROFILE_PIC,
                                            idUser),
                                    ((BitmapDrawable) resource).getBitmap(), false);
                            return false;
                        }
                    }).into(imgPerfil);

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadProfilePicture();
    }

    private void checkUser() {
        boolean isLogin = manager.getValue(Utils.IS_LOGIN);
        if (isLogin) {
            idUser = manager.getValueInt(Utils.MY_ID);
            Usuario usuario = mUsuarioViewModel.getById(idUser);
            imgBienestar.setVisibility(View.GONE);
            imgPerfil.setVisibility(View.VISIBLE);
            loadProfilePicture();
            txtNombre.setText(String.format("%s %s", usuario.getNombre(), usuario.getApellido()));
        } else {
            imgPerfil.setVisibility(View.GONE);
            imgBienestar.setVisibility(View.VISIBLE);
            imgBienestar.setImageResource(R.drawable.ic_logo_bienestar_01);
            txtNombre.setText(getText(R.string.app_name_shor).toString().toUpperCase());
        }

    }

    /*
    REVISAAAR
     */
    private void updateMenu() {
        Menu menu = navigationView.getMenu();
        int range = manager.getValueInt(Utils.TYPE_RANGE);
        if (range == 0) {
            MenuItem men = menu.findItem(R.id.item_perfil);
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
                ((DeportesFragment) fragmentoGenerico).setContext(getApplicationContext());
                ((DeportesFragment) fragmentoGenerico).setFragmentManager(getSupportFragmentManager());
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

        if (itemDrawer.getItemId() != R.id.item_perfil ||
                itemDrawer.getItemId() != R.id.item_config
                || itemDrawer.getItemId() != R.id.item_about
                || itemDrawer.getItemId() != R.id.item_terminos
                || itemDrawer.getItemId() != R.id.item_contactos) {
            ((TextView) findViewById(R.id.txtTitulo)).setText(itemDrawer.getTitle());
        }
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        findViewById(R.id.imgFlecha).setVisibility(View.GONE);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("Bienestar Estudiantíl");
            mToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
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
