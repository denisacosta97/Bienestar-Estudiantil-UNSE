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
import com.unse.bienestarestudiantil.Databases.RolViewModel;
import com.unse.bienestarestudiantil.Databases.UsuarioViewModel;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.FileStorageManager;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.StorageManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Rol;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.AboutActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionSistemaActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Perfil.PerfilActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionPolideportivoActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.TermsActivity;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.AccesoDenegadoFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.BecasFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.ComedorFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DeportesFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.InicioFragmento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.PoliFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.TransporteFragment;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    //SlidingLayout mSlidingLayout;
    PreferenceManager manager;
    UsuarioViewModel mUsuarioViewModel;
    RolViewModel mRolViewModel;
    Toolbar mToolbar;
    View headerView;
    Fragment mFragment;
    int itemSelecionado = -1, idUser = 0;
    ImageView imgPerfil, imgBienestar;
    TextView txtNombre;
    HashMap<String, Integer> ids;
    //ItemDrawerAdapter mAdapter;
    //ArrayList<ItemDrawer> mItemDrawers;
    //RecyclerView.LayoutManager mLayoutManager;
    //RecyclerView mRecyclerView;
    //LinearLayout mLayout;
    //boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        setToolbar();

        loadData();

        //openDrawer();

        loadListener();

        checkUser();

        comprobarNavigationView();

    }

    private void loadListener() {
       /* ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                resetItem();
                mItemDrawers.get(position).setSelect(true);
                processClick(position, (int) id);
                mAdapter.notifyDataSetChanged();
                openDrawer();
            }
        });*/
    }

   /* private void resetItem() {
        for (ItemDrawer itemDrawer : mItemDrawers) {
            itemDrawer.setSelect(false);
        }
    }

    private void processClick(int position, int id) {
        seleccionarItem(id, position);
    }*/


    private void loadData() {
        mFragment = new Fragment();
        ids = new HashMap<>();
        ids.put(getString(R.string.itemPerfil), R.id.item_perfil);
        ids.put(getString(R.string.itemSistema), R.id.item_sistema);
        ids.put(getString(R.string.itemNosotros), R.id.item_about);
        ids.put(getString(R.string.itemCondiciones), R.id.item_terminos);
        ids.put(getString(R.string.itemContacto), R.id.item_contactos);
        mRolViewModel = new RolViewModel(getApplicationContext());
        manager = new PreferenceManager(getApplicationContext());
        mUsuarioViewModel = new UsuarioViewModel(getApplicationContext());
      /*  mItemDrawers = new ArrayList<>();
        mItemDrawers.add(new ItemDrawer(R.id.item_inicio, "Inicio", R.drawable.ic_home, true, ItemDrawer.TIPO_OPCION));
        mItemDrawers.add(new ItemDrawer(R.id.item_poli, "Polideportivo", R.drawable.ic_poli, false, ItemDrawer.TIPO_OPCION));
        mItemDrawers.add(new ItemDrawer(R.id.item_deporte, "Deportes", R.drawable.ic_deportes, false, ItemDrawer.TIPO_OPCION));
        //mItemDrawers.add(new ItemDrawer(4, "UPA", R.drawable.ic_upa, true, ItemDrawer.TIPO_OPCION));
        //mItemDrawers.add(new ItemDrawer(5, "Área Becas", R.drawable.ic_becas, true, ItemDrawer.TIPO_OPCION));
        //mItemDrawers.add(new ItemDrawer(6, "Ciber", R.drawable.ic_ciber, true, ItemDrawer.TIPO_OPCION));
        //mItemDrawers.add(new ItemDrawer(7, "Transporte", R.drawable.ic_transporte, true, ItemDrawer.TIPO_OPCION));
        //mItemDrawers.add(new ItemDrawer(8, "Residencia", R.drawable.ic_residencia, true, ItemDrawer.TIPO_OPCION));
        //mItemDrawers.add(new ItemDrawer(9, "Comedor", R.drawable.ic_menu_comedor, true, ItemDrawer.TIPO_OPCION));
        mItemDrawers.add(new ItemDrawer(R.id.item_sistema, "Gestión del Sistema", R.drawable.ic_settings, false, ItemDrawer.TIPO_OPCION));
        mItemDrawers.add(new ItemDrawer(R.id.item_perfil, "Perfil", R.drawable.ic_user, false, ItemDrawer.TIPO_OPCION));
        mItemDrawers.add(new ItemDrawer(R.id.item_about, "Sobre nosotros", R.drawable.ic_b_bienestar, false, ItemDrawer.TIPO_OPCION));
        mItemDrawers.add(new ItemDrawer(R.id.item_contactos, "Contactos", R.drawable.ic_informacion, false, ItemDrawer.TIPO_OPCION));
        mItemDrawers.add(new ItemDrawer(R.id.item_terminos, "Términos y condiciones", R.drawable.ic_terms, false, ItemDrawer.TIPO_OPCION));
        mAdapter = new ItemDrawerAdapter(mItemDrawers, getApplicationContext());
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);*/
    }

    private void loadViews() {
        //mRecyclerView = findViewById(R.id.recycler);
        //mLayout = findViewById(R.id.drawer);
        //mSlidingLayout = findViewById(R.id.drawer_layout);
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
        //seleccionarItem(R.id.item_inicio, 0);
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
            Glide.with(imgPerfil.getContext()).load(bitmap)/*.listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    StorageManager manager = new StorageManager.Builder(getApplicationContext())
                            .setFolder("BIENESTAR").setNameFile("foto.jpg").build();
                       manager.saveBitmap(true, ((BitmapDrawable) resource).getBitmap());
                    Bitmap bitmap1 = manager.loadBitmap(true);
                    if (bitmap1 != null)
                        return false;
                    return false;
                }
            })*/.into(imgPerfil);
        } else {
            String URL = String.format("%s%s.jpg", Utils.URL_USUARIO_IMAGE_LOAD, idUser);
            Glide.with(imgPerfil.getContext()).load(URL)
                    .apply(new RequestOptions().error(R.drawable.ic_user)
                            .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_user))
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
            txtNombre.setVisibility(View.VISIBLE);
            loadProfilePicture();
            txtNombre.setText(String.format("%s %s", usuario.getNombre(), usuario.getApellido()));
        } else {
            imgPerfil.setVisibility(View.GONE);
            imgBienestar.setVisibility(View.VISIBLE);
            txtNombre.setVisibility(View.VISIBLE);
            imgBienestar.setImageResource(R.drawable.ic_logo_bienestar_01);
            txtNombre.setText(getText(R.string.app_name_shor).toString().toUpperCase());
        }
        updateMenu();

    }

    private void updateMenu() {
        Menu menu = navigationView.getMenu();
        MenuItem item = menu.findItem(R.id.item_sistema);
        Rol rol = mRolViewModel.getByPermission(10);
        if (rol == null) {
            item.setVisible(false);
        }
        /*int position = 0, i = 0;
        for (ItemDrawer itemDrawer : mItemDrawers) {
            if (itemDrawer.getId() == R.id.item_sistema) {
                position = i;
                break;
            }
            i++;
        }
        if (rol == null) {
            mItemDrawers.remove(position);
            mAdapter.notifyDataSetChanged();
        }*/
    }


    private void seleccionarItem(MenuItem itemDrawer/*int itemDrawer, int position*/) {
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
            case R.id.item_transporte:
                fragmentoGenerico = new TransporteFragment();
                ((TransporteFragment) fragmentoGenerico).setContext(getApplicationContext());
                ((TransporteFragment) fragmentoGenerico).setFragmentManager(getSupportFragmentManager());
                break;
            case R.id.item_comedor:
                fragmentoGenerico = new ComedorFragment();
                break;
            case R.id.item_perfil:
                startActivity(new Intent(MainActivity.this, PerfilActivity.class));
                break;
            case R.id.item_sistema:
                startActivity(new Intent(this, GestionSistemaActivity.class));
                break;
            case R.id.item_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.item_terminos:
                startActivity(new Intent(this, TermsActivity.class));
                break;
        }


        if (!(fragmentoGenerico instanceof InicioFragmento)) {
            boolean isLogin = manager.getValue(Utils.IS_LOGIN);
            if (!isLogin) {
                fragmentoGenerico = new AccesoDenegadoFragment();
            }
        }

        if (mFragment != null && fragmentoGenerico != null && !(mFragment.getClass().equals(fragmentoGenerico.getClass()))) {
            if (fragmentoGenerico != null) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.contenedor_principal, fragmentoGenerico)
                        .commit();
            }
        }

        mFragment = fragmentoGenerico;
        // itemSelecionado = itemDrawer.getItemId();

        if (!ids.containsKey(itemDrawer.getTitle()))
            ((TextView) findViewById(R.id.txtTitulo)).setText(itemDrawer.getTitle());

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

                drawerLayout.openDrawer(Gravity.START);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*public void toggleMenu() {
        mSlidingLayout.toggleMenu();

    }*/

    /*private void openDrawer() {
        toggleMenu();
        if (!isOpen){
            final int margin = 0;
            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLayout.getLayoutParams();
                    params.setMarginStart(margin);
                    mLayout.setLayoutParams(params);
                }
            };
            animation.setDuration(500);
            animation.start();
            isOpen = true;
        }else{
            final int margin = -280;
            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLayout.getLayoutParams();
                    params.setMarginStart(margin);
                    mLayout.setLayoutParams(params);
                }
            };
            animation.setDuration(500);
            animation.start();
            isOpen = false;
        }
    }*/

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(Gravity.LEFT);
        else if (!(mFragment instanceof InicioFragmento)) {
            seleccionarItem(navigationView.getMenu().getItem(0));
            navigationView.setCheckedItem(R.id.item_inicio);
        } else
            super.onBackPressed();
        /*if (mSlidingLayout.isMenuShown()) {
            mSlidingLayout.toggleMenu();
        } else {
            super.onBackPressed();
        }

        if (isOpen)
            openDrawer();
        else if (!(mFragment instanceof InicioFragmento)) {
            seleccionarItem(R.id.item_inicio, 0);
            resetItem();
            mItemDrawers.get(0).setSelect(true);
            mAdapter.notifyDataSetChanged();
            //navigationView.setCheckedItem(R.id.item_inicio);
        } else
            super.onBackPressed();*/
    }

}
