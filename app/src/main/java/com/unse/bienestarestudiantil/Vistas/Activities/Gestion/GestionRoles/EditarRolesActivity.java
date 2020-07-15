package com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionRoles;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.unse.bienestarestudiantil.Databases.RolViewModel;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.TreeView.AndroidTreeView;
import com.unse.bienestarestudiantil.Herramientas.TreeView.TreeNode;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Rol;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.TreeAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditarRolesActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout latDatos;
    CircleImageView mImageView;
    TextView txtNombre, txtDni;
    Button btnAceptar;

    Usuario mUsuario;
    ArrayList<String> roles;
    ArrayList<Rol> mRols;
    HashMap<String, Integer> mapRoles;

    AndroidTreeView mAndroidTreeView;
    DialogoProcesamiento dialog;
    RolViewModel mRolViewModel;

    boolean isAdmin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_roles);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        isAdmin();

        loadViews();

        loadData();

        loadListener();
    }

    private void loadListener() {
        btnAceptar.setOnClickListener(this);
    }

    private void loadData() {
        mRolViewModel = new RolViewModel(getApplicationContext());
        mapRoles = new HashMap<>();
        for (String s : roles) {
            mapRoles.put(s, Integer.parseInt(s));
        }
        if (mUsuario != null) {
            txtNombre.setText(String.format("%s %s", mUsuario.getNombre(), mUsuario.getApellido()));
            txtDni.setText(String.valueOf(mUsuario.getIdUsuario()));
            String URL = String.format("%s%s.jpg", Utils.URL_USUARIO_IMAGE_LOAD, mUsuario.getIdUsuario());
            Glide.with(mImageView.getContext()).load(URL)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user))
                    .into(mImageView);
        }
        loadRoles();
    }

    public int getMargin(int level) {
        return level * 25;
    }

    private void loadRoles() {
        //Root
        TreeNode root = TreeNode.root();
        for (Rol rol : mRols) {
            if ((rol.getIdRol() != Utils.LIST_PERMISOS[0] ||
                    rol.getIdRol() != Utils.LIST_PERMISOS[1]) &&
                    (mRolViewModel.getByPermission(Utils.LIST_PERMISOS[0]) != null)
            ) {
                if (rol.getIdRolPadre() == 0) {
                    TreeAdapter.IconTreeItem nodo = new TreeAdapter.IconTreeItem(
                            R.drawable.ic_keyboard_arrow, rol.getDescripcion());
                    TreeNode node = new TreeNode(nodo).setViewHolder(new TreeAdapter(
                            getApplicationContext(), mapRoles.containsKey(String.valueOf(rol.getIdRol())),
                            TreeAdapter.PARENT, getMargin(0), 0));
                    node.setRol(rol);
                    root.addChild(node);
                } else {
                    boolean isFound = foundFather(root, rol);
                }
            }

        }

        mAndroidTreeView = new AndroidTreeView(getApplicationContext(), root);
        View view = mAndroidTreeView.getView();
        latDatos.addView(view);

    }

    private boolean foundFather(TreeNode root, Rol rol) {
        boolean isFound = false;
        if (root.getRol() != null && root.getRol().getIdRol() == rol.getIdRolPadre()) {
            TreeAdapter.IconTreeItem nodo = new TreeAdapter.IconTreeItem(R.drawable.ic_keyboard_arrow,
                    rol.getDescripcion());
            TreeNode node = new TreeNode(nodo).setViewHolder(new TreeAdapter(getApplicationContext(),
                    mapRoles.containsKey(String.valueOf(rol.getIdRol())), TreeAdapter.CHILD,
                    getMargin(root.getLevel()), root.getLevel()));
            node.setRol(rol);
            root.addChild(node);
            isFound = true;
        } else {
            for (TreeNode node : root.getChildren()) {
                isFound = foundFather(node, rol);
                if (isFound)
                    break;

            }

        }
        return isFound;
    }

    private void loadViews() {
        latDatos = findViewById(R.id.latDatos);
        mImageView = findViewById(R.id.imgIcon);
        txtNombre = findViewById(R.id.txtNombre);
        txtDni = findViewById(R.id.txtDni);
        btnAceptar = findViewById(R.id.btnAceptar);
    }

    private void isAdmin() {
        roles = new ArrayList<>();
        mUsuario = new Usuario();
        mRols = new ArrayList<>();
        if (getIntent().getBooleanExtra(Utils.IS_ADMIN_MODE, false)) {
            isAdmin = getIntent().getBooleanExtra(Utils.IS_ADMIN_MODE, false);
        }
        if (getIntent().getSerializableExtra(Utils.ROLES_USER) != null) {
            roles = getIntent().getStringArrayListExtra(Utils.ROLES_USER);
        }
        if (getIntent().getParcelableExtra(Utils.USER_INFO) != null) {
            mUsuario = getIntent().getParcelableExtra(Utils.USER_INFO);
        }
        if (getIntent().getSerializableExtra(Utils.ROLES) != null) {
            mRols = (ArrayList<Rol>) getIntent().getSerializableExtra(Utils.ROLES);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAceptar:
                sendPermissons();
                break;
        }
    }

    private void sendPermissons() {
        roles.clear();
        //if (mapRoles.containsKey(String.valueOf(Utils.LIST_PERMISOS[0])))
          //  roles.add(String.valueOf(Utils.LIST_PERMISOS[0]));
        //if (mapRoles.containsKey(String.valueOf(Utils.LIST_PERMISOS[1])))
          //  roles.add(String.valueOf(Utils.LIST_PERMISOS[1]));
        visit(mAndroidTreeView.getRoot());
        String rolesURL = "";
        for (String s : roles) {
            rolesURL = rolesURL.concat("&rol[]=" + s);
        }
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String fecha = Utils.getFechaName(new Date(System.currentTimeMillis()));
        String URL = String.format("%s?id=%s&key=%s&idU=%s%s&fecha=%s", Utils.URL_ROLES_INSERTAR,
                id, key, mUsuario.getIdUsuario(), rolesURL, fecha);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    Utils.showToast(getApplicationContext(), getString(R.string.permisosActualizados));
                    finish();
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.permisosAlgunosNo));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void visit(TreeNode root) {
        if (root.getViewHolder() instanceof TreeAdapter) {
            boolean isCheck = ((TreeAdapter) root.getViewHolder()).getChecked();
            if (isCheck)
                roles.add(String.valueOf(root.getRol().getIdRol()));
            for (TreeNode node : root.getChildren()) {
                visit(node);
            }

        }else{
            for (TreeNode node : root.getChildren()) {
                visit(node);
            }
        }
    }
}
