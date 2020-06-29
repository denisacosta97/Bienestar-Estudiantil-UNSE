package com.unse.bienestarestudiantil.Herramientas.Almacenamiento;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.unse.bienestarestudiantil.Herramientas.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
Creas el objeto
StorageManager manager = new StorageManager.Builder(getApplicationContext()) //Necesita el contexto
                            .setFolder("BIENESTAR") //Indicas el nombre de la carpeta donde deseas almacenarlo
                            .setNameFile("foto.jpg") //Indicas el nombre del archivo, mayormente guardalo en JPG
                            .build(); //Y ahi se construye el objeto
  //Para guardar la foto se necesita en forma de Bitmap
  //True o false indicará si TRUE (Lo guardo en el almacenamiento externo) y FALSE en el interno
 manager.saveBitmap(true, resource.getBitmap()); //Aqui lo guarda en el almacenamiento externo
 //Luego para obtenerlo, en este caso como ya tiene el nombre del archivo y carpeta guardado 
 (en la variable manager) directamente haces load e indicar si true o false. En este caso como lo guardamos
 en el externo tiene que ser
 Bitmap bitmap1 = manager.loadBitmap(true)

 IGUALMENTE PUEDES REVISAR EL ARCHIVO EN CASO QUE ELIJAS EXTERNO VAS A VER LA CARPETA CREADA EN TU SD


 */
public class StorageManager {

    private Context mContext;
    private String folder;
    private String file;

    public StorageManager() {

    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public static class Builder {

        private StorageManager mManager;

        public Builder(Context context) {
            mManager = new StorageManager();
            mManager.setContext(context);
        }

        public Builder setFolder(String folder) {
            mManager.setFolder(folder);
            return this;
        }

        public Builder setNameFile(String file) {
            mManager.setFile(file);
            return this;
        }

        public StorageManager build() {
            return mManager;
        }


    }

    public Bitmap loadBitmap(boolean isExt){
        if (isExt)
            return loadExternalStorage();
        else return loadInternalStorage();

    }

    public static int deleteFileFromUri(Context context, Uri uri) {
        if (uri != null)
            return context.getContentResolver().delete(uri, null, null);
        return -1;
    }

    public static void resizeBitmap(String name) {
        File file = new File(name);
        Bitmap in = BitmapFactory.decodeFile(file.getPath());
        Bitmap out = Utils.resize(in, 600, 600);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            out.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
            fOut.flush();
            fOut.close();
            in.recycle();
            out.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap loadInternalStorage() {
        Bitmap b = null;
        try {
            File f = new File(getUriFile(false), getFile());
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;

    }

    private Bitmap loadExternalStorage() {
        Bitmap b = null;
        try {
            File f = new File(String.format("%s/%s/%s",getUriFile(true), getFolder(),getFile()));
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;

    }

    public void saveBitmap(boolean isExt, Bitmap bitmap) {
        if (isExt)
            saveExternalStorage(bitmap);
        else
            saveInternalStorage(bitmap);
    }

    public String getUriFile(boolean ext) {
        if (!ext)
            return new ContextWrapper(mContext).getDir(getFolder(), Context.MODE_PRIVATE).getAbsolutePath();
        else return Environment.getExternalStorageDirectory().toString();
    }

    private void saveExternalStorage(Bitmap bitmap) {
        String path = String.format("%s/%s/", getUriFile(true), getFolder());
        File folder = new File(path);
        File mypath = new File(path + getFile());
        if (!folder.exists())
            folder.mkdirs();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveInternalStorage(Bitmap bitmap) {
        ContextWrapper cw = new ContextWrapper(mContext);
        String folder = getFolder();
        File directory = cw.getDir(folder, Context.MODE_PRIVATE);
        File mypath = new File(directory, getFile());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
