package com.unse.bienestarestudiantil.Herramientas;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;

import java.util.regex.Pattern;

public class Utils {

    //Constantes para las BD
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String FLOAT_TYPE = "float";
    public static final String NULL_TYPE = "not null";
    public static final String AUTO_INCREMENT = "primary key autoincrement";
    //Constantes de preferencias
    public static final String IS_FIRST_TIME_LAUNCH = "is_first";
    public static final String UPDATE = "0";
    public static final int PERMISSION_ALL = 1010;

    public static final int NOTICIA_NORMAL = 3030;
    public static final int NOTICIA_BUTTON_WEB = 3131;
    public static final int NOTICIA_BUTTON_TIENDA = 3132;
    public static final int NOTICIA_BUTTON_APP = 3133;

    public static final int TIPO_COMEDOR = 1;
    public static final int TIPO_DEPORTE = 2;
    public static final int TIPO_TRANSPORTE  = 3;
    public static final int TIPO_BECA = 4;
    public static final int TIPO_RESIDENCIA = 5;
    public static final int TIPO_CYBER = 6;
    public static final int TIPO_UPA = 7;


    public static void showToast(Context c, String msj) {
        Toast.makeText(c, msj, Toast.LENGTH_SHORT).show();
    }

    public static void showLog(String title, String msj){
        Log.e(title,msj);
    }

//    public static void showCustomToast(Activity activity, Context context, String text, int icon){
//        LayoutInflater inflater = activity.getLayoutInflater();
//        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.toast_layout));
//
//        FontChangeUtil fontChanger = new FontChangeUtil(context.getAssets(), "product_sans_regular.ttf");
//        fontChanger.replaceFonts((ViewGroup) layout);
//
//        ImageView image = layout.findViewById(R.id.image);
//        image.setImageResource(icon);
//        TextView text2 = layout.findViewById(R.id.text);
//        text2.setText(text);
//
//        Toast toast = new Toast(context);
//        toast.setGravity(Gravity.BOTTOM, 0, 30);
//        toast.setDuration(Toast.LENGTH_LONG + 4);
//        toast.setView(layout);
//        toast.show();
//    }


    public static boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static boolean validarTelefono(String tel) {
        Pattern pattern = Patterns.PHONE;
        return pattern.matcher(tel).matches();
    }

    //Metodo para saber si un permiso esta autorizado o no
    public static boolean isPermissionGranted(Context ctx, String permision) {
        int permission = ActivityCompat.checkSelfPermission(
                ctx,
                permision);
        return permission == PackageManager.PERMISSION_GRANTED;

    }


    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public static void setFont(Context context, Activity view, String font){
        FontChangeUtil fontChanger = new FontChangeUtil(context.getAssets(), font);
        fontChanger.replaceFonts((ViewGroup)view.findViewById(android.R.id.content));
    }

}

