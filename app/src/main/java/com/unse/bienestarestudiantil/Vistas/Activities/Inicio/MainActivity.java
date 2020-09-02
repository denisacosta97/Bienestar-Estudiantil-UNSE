package com.unse.bienestarestudiantil.Vistas.Activities.Inicio;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unse.bienestarestudiantil.Databases.RolViewModel;
import com.unse.bienestarestudiantil.Databases.UsuarioViewModel;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.FileStorageManager;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Rol;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.AboutActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionSistemaActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Perfil.PerfilActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.TermsActivity;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.AccesoDenegadoFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.BecasFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.CiberFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.ComedorFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DeportesFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.InicioFragmento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.PoliFragment;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.TransporteFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    //SlidingLayout mSlidingLayout;
    PreferenceManager manager;
    DialogoProcesamiento dialog;
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
    Double lat, lon;
    public Boolean isReady = false, qrCiber = false;
    String pat = "", idR = "";

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
            case R.id.item_becas:
                fragmentoGenerico = new BecasFragment();
                break;
            case R.id.item_inicio:
                fragmentoGenerico = new InicioFragmento();
                break;
            case R.id.item_poli:
                fragmentoGenerico = new PoliFragment();
                break;
            case R.id.item_ciber:
                fragmentoGenerico = new CiberFragment();
                ((CiberFragment) fragmentoGenerico).setActivity(MainActivity.this);
                break;
            case R.id.item_deporte:
                fragmentoGenerico = new DeportesFragment();
                ((DeportesFragment) fragmentoGenerico).setContext(getApplicationContext());
                ((DeportesFragment) fragmentoGenerico).setFragmentManager(getSupportFragmentManager());
                break;

            case R.id.item_transporte:
                fragmentoGenerico = new TransporteFragment();
                ((TransporteFragment) fragmentoGenerico).setContext(getApplicationContext());
                ((TransporteFragment) fragmentoGenerico).setFragmentManager(getSupportFragmentManager());
                ((TransporteFragment) fragmentoGenerico).setActivity(MainActivity.this);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentIntegrator = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentIntegrator != null) {
            if (intentIntegrator.getContents() == null) {
                Utils.showCustomToast(MainActivity.this, getApplicationContext(), "Cancelaste", R.drawable.ic_error);

            } else {
                String contenido = intentIntegrator.getContents();
                if (qrCiber)
                    decordeQRCiber(contenido);
                else
                    decodeQR(contenido);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void decordeQRCiber(String contenido) {
        String code = "";
        Pattern pattern = Pattern.compile("MAQ-[0-9]+");
        Matcher matcher = pattern.matcher(contenido);
        if(matcher.find()){
            code = matcher.group();
        }
        registrarUso(code);
    }

    private void registrarUso(String code) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        //CAMBIAR URL EN UTILS
        String URL = String.format("%s?idU=%s&key=%s&co=%s", Utils.URL_REGISTRAR_INGRESO, id, key, code);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuestaActualizar(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showCustomToast(MainActivity.this, getApplicationContext(),
                        getString(R.string.servidorOff), R.drawable.ic_error);
                dialog.dismiss();
            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void decodeQR(String contenido) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9 ]+");
        Matcher matcher = pattern.matcher(contenido);
        if(matcher.find()){
            pat = matcher.group();
        }

        pattern = Pattern.compile("-[0-9]+");
        matcher = pattern.matcher(contenido);
        if(matcher.find()){
            idR = matcher.group();
            idR = idR.substring(1);
        }
        getLatLong();
        isReady = true;
    }

    private void getLatLong() {
        Criteria criteria = new Criteria();
        LocationManager locationManager = (LocationManager) Objects.requireNonNull(getApplicationContext()).getSystemService(LOCATION_SERVICE);
        //VERIFICAR 23/07
        String provider = locationManager != null ? locationManager.getBestProvider(criteria, true) : null;
        if (provider != null) {
            Location location = locationManager.getLastKnownLocation(provider);
            lat = location.getLatitude();
            lon = location.getLongitude();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isReady){
            isReady = false;
            changeEstado(pat, idR);
        }
    }

    private void changeEstado(String pat, String idR) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        Date date = new Date(System.currentTimeMillis());
        String fecha = Utils.getFechaName(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH)+1;
        int anio = calendar.get(Calendar.YEAR);
        String URL = String.format("%s?idU=%s&key=%s&iu=%s&pat=%s&fl=%s&la=%s&lo=%s&ir=%s&d=%s&m=%s&a=%s",
                Utils.URL_PASAJERO_SERVICIO, id, key, id, pat, fecha, lat, lon, idR, dia, mes, anio);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
        String URL = String.format("%s?idU=%s&key=%s&ir=%s&ie=%s&e=%s&d=%s", Utils.URL_PASAJERO_REGISTRAR_SERVICIO, id, key, idReserva, id, 3, dni);
        StringRequest request = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuestaActualizar(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showCustomToast(MainActivity.this, getApplicationContext(),
                        getString(R.string.servidorOff), R.drawable.ic_error);
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void procesarRespuestaActualizar(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showCustomToast(MainActivity.this, getApplicationContext(),
                            getString(R.string.errorInternoAdmin), R.drawable.ic_error);
                    break;
                case 1:
                    //Exito
                    Toast.makeText(this, "Listo prro", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Utils.showCustomToast(MainActivity.this, getApplicationContext(),
                            "", R.drawable.ic_error);
                    break;
                case 3:
                    Utils.showCustomToast(MainActivity.this, getApplicationContext(),
                            getString(R.string.tokenInvalido), R.drawable.ic_error);
                    break;
                case 4:
                    Utils.showCustomToast(MainActivity.this, getApplicationContext(),
                            getString(R.string.camposIncompletos), R.drawable.ic_error);
                    break;
                case 100:
                    //No autorizado
                    Utils.showCustomToast(MainActivity.this, getApplicationContext(),
                            getString(R.string.tokenInexistente), R.drawable.ic_error);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showCustomToast(MainActivity.this, getApplicationContext(),
                    getString(R.string.errorInternoAdmin), R.drawable.ic_error);

        }
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
