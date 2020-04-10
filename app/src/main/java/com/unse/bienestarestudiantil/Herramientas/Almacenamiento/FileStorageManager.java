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

public class FileStorageManager {

    Context context;
    String folderName, fileName;

    public FileStorageManager(Context context) {
        this.context = context;

    }

    public static void deleteAll(int id) {

    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUriFile(boolean ext) {
        String di = getFolderName().substring(0, getFolderName().length() - 1);
        if (!ext)
            return new ContextWrapper(context).getDir(di, Context.MODE_PRIVATE).getAbsolutePath();
        else return Environment.getExternalStorageDirectory().toString();
    }


    private void saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(context);
        String di = getFolderName().substring(0, getFolderName().length() - 1);
        File directory = cw.getDir(di, Context.MODE_PRIVATE);
        File mypath = new File(directory, getFileName());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap loadImageFromStorage(String path) {
        Bitmap b = null;
        try {
            File f = new File(path, getFileName());
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;

    }

    private void saveToExternalStorage(Bitmap bitmapImage, boolean ext) {
        String path = getUriFile(ext) + "/" + getFolderName();
        File folder = new File(path);
        File mypath = new File(path + getFileName());

        if (!folder.exists())
            folder.mkdirs();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap loadImageFromStorageExternal(String path) {
        Bitmap b = null;
        try {
            File f = new File(path + getFileName());
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return b;

    }

    public static void saveBitmap(Context applicationContext, String folder, String file, Bitmap bitmap, boolean ext) {
        FileStorageManager fileStorageManager = new FileStorageManager(applicationContext);
        fileStorageManager.setFileName(file);
        fileStorageManager.setFolderName(folder);
        if (!ext)
            fileStorageManager.saveToInternalStorage(bitmap);
        else
            fileStorageManager.saveToExternalStorage(bitmap, ext);
    }

    public static Bitmap getBitmap(Context applicationContext, String folder, String file, boolean ext) {
        FileStorageManager fileStorageManager = new FileStorageManager(applicationContext);
        fileStorageManager.setFileName(file);
        fileStorageManager.setFolderName(folder);
        if (!ext)
            return fileStorageManager.loadImageFromStorage(fileStorageManager.getUriFile(ext));
        else
            return fileStorageManager.loadImageFromStorageExternal(fileStorageManager.getUriFile(ext));
    }

    public static int deleteFileFromUri(Context context, Uri uri) {
        if (uri != null)
            return context.getContentResolver().delete(uri, null, null);
        return -1;
    }

    public static void resizeBitmapAndFile(String name) {
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



}
