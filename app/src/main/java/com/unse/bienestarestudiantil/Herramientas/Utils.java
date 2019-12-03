package com.unse.bienestarestudiantil.Herramientas;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfTextAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.unse.bienestarestudiantil.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
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
    public static final int TIPO_TRANSPORTE = 3;
    public static final int TIPO_BECA = 4;
    public static final int TIPO_RESIDENCIA = 5;
    public static final int TIPO_CYBER = 6;
    public static final int TIPO_UPA = 7;

    public static final int TIPO_CANCHA = 1010;
    public static final int TIPO_QUINCHO = 1011;

    public static final String MONSERRAT = "Montserrat-Regular.ttf";
    public static final String MONTSERRAT_BOLD = "Montserrat-Black.ttf";
    public static final String DEPORTE_NAME = "dato_deporte";
    public static final String BARCODE = "dato_barcode";
    public static final String TORNEO = "dato_torneo";
    public static final String NOTICIA = "dato_noticias";
    public static final String RESERVA = "dato_reserva";
    public static final String DATA_RESERVA = "dato_reserva";
    public static final String ALUMNO_NAME = "dato_alumno";
    public static final int LIST_RESET = 0;
    public static final int LIST_LEGAJO = 1;
    public static final int LIST_DNI = 2;
    public static final int LIST_NOMBRE = 3;

    public static final int GET_FROM_GALLERY = 1011;
    public static final int GET_FROM_DNI = 1010;


    public static final String URL = "http://192.168.1.27/bienestar/usuario/insertar.php";
    public static final String URL_IMAGE = "http://192.168.1.27/bienestar/uploadImage.php";

    public static void changeColorDrawable(ImageView view, Context context, int color){
        DrawableCompat.setTint(
                DrawableCompat.wrap(view.getDrawable()),
                ContextCompat.getColor(context, color));
    }

    public static String getStringCamel(String string){
        Pattern pattern = Pattern.compile("[A-Za-z]+");
        Matcher matcher = pattern.matcher(string);
        StringBuilder resp = new StringBuilder();
        while (matcher.find()){
            if(matcher.group(0).length() > 1)
                resp.append(matcher.group(0).charAt(0)).append(matcher.group(0).substring(1).toLowerCase()).append(" ");
            else
                resp.append(matcher.group(0));
        }
        return resp.toString();
    }


    public static void showToast(Context c, String msj) {
        Toast.makeText(c, msj, Toast.LENGTH_SHORT).show();
    }

    public static void showLog(String title, String msj) {
        Log.e(title, msj);
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

    public static String getAppName(Context context, ComponentName name) {
        try {
            ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(
                    name, PackageManager.GET_META_DATA);
            return activityInfo.loadLabel(context.getPackageManager()).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void setFont(Context context, ViewGroup view, String font) {
        FontChangeUtil fontChanger = new FontChangeUtil(context.getAssets(), font);
        fontChanger.replaceFonts(view);
    }


    private static void crearArchivoProvisorio(InputStream inputStream, File file) {

        try  {
            FileOutputStream outputStream = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static String getStringValue(Object... arg){
        StringBuilder  msj = new StringBuilder();
        for (Object o:arg){
            msj.append(o.toString());
        }
        return msj.toString();
    }

    public static String crypt(String text)  {

        MessageDigest crypt = null;
        try {
            crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(text.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new BigInteger(1, crypt.digest()).toString(16);
    }

    public static String generateToken(String... data){
        String sha = "";
        if(data.length == 3){
            sha = data[0]+data[1]+data[2];
            return crypt(sha);
        }else{
            return sha;
        }

    }


    public static void createPDF(Context context) {
        File file = null;
        Document document = null;
        AssetManager assetManager = context.getAssets();
        InputStream in = null;
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/BIENESTAR/";
        try {
            File directorio = new File(directory_path);
            if (!directorio.exists())
                directorio.mkdirs();
            in = assetManager.open("ficha_deportes_label.pdf");
            file = new File(directory_path, "deportesProvisorio.pdf");
            crearArchivoProvisorio(in, file);

            File src = new File(directory_path, "deportesProvisorio.pdf");
            File des = new File(directory_path, "ficha_deportes.pdf");
            if (!src.exists()) {
                try {
                    InputStream is = context.getAssets().open("ficha_deportes_label.pdf");
                    byte[] buffer = new byte[1024];
                    is.read(buffer);
                    is.close();
                    FileOutputStream fos = new FileOutputStream(src);
                    fos.write(buffer);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            PdfDocument pdfDocument = new PdfDocument(new PdfReader(src), new PdfWriter(des));
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument, true);
            form.getField("nombre").setValue("Denis Lionel Acosta");
            pdfDocument.close();
            src.delete();
            //document = new Document(pdfDocument);
            //document.close();
            /*document.add(getText("SYSTOCK", 15, true));
            document.add(getText("Sistema de Gestión de Mercadería, Facturación y Gestión de Clientes", 12, true));
            document.add(getText("----------------------------------------------------------------------------------------------------------------------", 11, true));
            document.add(getText("Datos del Pedido", 12, false));
            document.close();*/

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Paragraph getText(String text, float size, boolean center) {
        return center ? new Paragraph(text).setFontSize(size).setTextAlignment(TextAlignment.CENTER)
                : new Paragraph(text).setFontSize(size);
    }

    public static String getNameFile(String directory_path, int codPedido, String fecha) {
        return directory_path + "REPORTE_" + codPedido + "_" +
                getFechaName(Utils.getFechaDate(fecha)) + ".pdf";
    }

    private static Cell createCell(String text, boolean center) {
        return center ? new Cell().setPadding(0.8f)
                .add(new Paragraph(text)
                        .setMultipliedLeading(1))
                : new Cell().setPadding(0.8f)
                .add(new Paragraph(text)
                        .setMultipliedLeading(1)).setTextAlignment(TextAlignment.CENTER);
    }

    private static Cell createCell(String text, PdfFont font) {
        return new Cell().setPadding(0.8f)
                .add(new Paragraph(text).setFont(font)
                        .setMultipliedLeading(1));
    }

    public static Date getFechaDate(String fecha) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaI = null;
        try {
            fechaI = simpleDateFormat.parse(fecha);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return fechaI;

    }

    public static String getFechaName(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String value = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
                + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" +
                cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);

        return value;


    }

}

