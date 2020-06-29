package com.unse.bienestarestudiantil.Herramientas.PDF;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.unse.bienestarestudiantil.Databases.UsuarioViewModel;
import com.unse.bienestarestudiantil.Herramientas.Credencial.CustomPictureButton;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.FileStorageManager;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Archivo;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Map;

public class LoadInfoPDF extends AsyncTask<String, Integer, String> {

    private Context mContext;
    private String directory_path;
    private File origen, destino;
    private Archivo mArchivo;
    private PdfDocument mPdfDocument;
    private boolean isYes = true;
    private YesNoDialogListener mListener;
    private DialogoProcesamiento mDialogoProcesamiento;
    private FragmentManager mFragmentManager;

    public LoadInfoPDF(Context context, Archivo archivo, YesNoDialogListener listener, FragmentManager manager) {
        this.mContext = context;
        this.mArchivo = archivo;
        this.directory_path = Utils.getDirectoryPath(true, context);
        this.mListener = listener;
        this.mFragmentManager = manager;
    }

    @Override
    protected void onPreExecute() {
        mDialogoProcesamiento = new DialogoProcesamiento();
        mDialogoProcesamiento.show(mFragmentManager, "dialog_proces");
        origen = new File(directory_path + mArchivo.getNombreArchivo());
        destino = new File(directory_path + "old_" + mArchivo.getNombreArchivo());
        try {
            FileInputStream inputStream = new FileInputStream(origen);
            FileOutputStream outputStream = new FileOutputStream(destino);
            FileChannel inChanel = inputStream.getChannel();
            FileChannel outChanel = outputStream.getChannel();
            inChanel.transferTo(0, inChanel.size(), outChanel);
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            isYes = false;
        } catch (IOException e) {
            e.printStackTrace();
            isYes = false;
        }
        origen = new File(directory_path + "old_" + mArchivo.getNombreArchivo());
        destino = new File(directory_path + mArchivo.getNombreArchivo());
        try {
            mPdfDocument = new PdfDocument(new PdfReader(origen), new PdfWriter(destino));
        } catch (IOException e) {
            e.printStackTrace();
            isYes = false;
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        if (mPdfDocument != null) {
            PdfAcroForm form = PdfAcroForm.getAcroForm(mPdfDocument, true);

            if (form.getFormFields().size() > 0) {


                completeValues(form, form.getFormFields());


            } else {
                return "EMP";
            }


            return "OK";
        } else {
            return null;
        }

    }

    private void completeValues(PdfAcroForm form, Map<String, PdfFormField> formFields) {
        int id = new PreferenceManager(mContext).getValueInt(Utils.MY_ID);
        //Info user
        UsuarioViewModel usuarioViewModel = new UsuarioViewModel(mContext);
        Usuario usuario = usuarioViewModel.getById(id);
        if (usuario != null) {
            if (formFields.containsKey("dni"))
                formFields.get("dni").setValue(String.valueOf(usuario.getIdUsuario()));
            if (formFields.containsKey("nombreApe"))
                formFields.get("nombreApe").setValue(String.format("%s %s", usuario.getNombre(), usuario.getApellido()));
            if (formFields.containsKey("nombre"))
                formFields.get("nombre").setValue(usuario.getNombre());
            if (formFields.containsKey("apellido"))
                formFields.get("apellido").setValue(usuario.getApellido());
            if (formFields.containsKey("fechaNac"))
                formFields.get("fechaNac").setValue(usuario.getFechaNac());
            if (formFields.containsKey("pais"))
                formFields.get("pais").setValue(usuario.getPais());
            if (formFields.containsKey("foto")) {

                Bitmap bitmap = FileStorageManager.getBitmap(mContext, Utils.FOLDER,
                        String.format(Utils.PROFILE_PIC, id), false);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
               /* Image signatura;
                signatura = new Image(ImageDataFactory.create(stream.toByteArray()));
                signatura.setAutoScale(true);
                signatura.setFixedPosition(300,200);*/

                //PdfButtonFormField ad = (PdfButtonFormField) form.copyField("foto");
                Document document = new Document(mPdfDocument);


                //PdfFormField ad = form.copyField("foto");

                CustomPictureButton ad = new CustomPictureButton((PdfButtonFormField) formFields.get("foto"));
                ad.setImage(ImageDataFactory.create(stream.toByteArray()));
                formFields.remove("foto");
                document.add(new Paragraph().add(ad));





            }

        }

    }

    @Override
    protected void onPostExecute(String s) {
        mDialogoProcesamiento.dismiss();
        if (origen != null)
            origen.delete();
        if (mPdfDocument != null)
            mPdfDocument.close();
        if (s != null)
            if (isYes && (s.equals("OK") || s.equals("EMP")))
                mListener.yes();
            else
                mListener.no();
        else
            mListener.no();

    }
}
