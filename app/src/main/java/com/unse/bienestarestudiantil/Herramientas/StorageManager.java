package com.unse.bienestarestudiantil.Herramientas;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageManager {

    Context context;
    String folderName, fileName;

    public StorageManager(Context context) {
        this.context = context;

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
        if (!ext)
            return new ContextWrapper(context).getDir(getFolderName(), Context.MODE_PRIVATE).getAbsolutePath();
        else return Environment.getExternalStorageDirectory().toString();
    }


    public void saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(getFolderName(), Context.MODE_PRIVATE);
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

    public Bitmap loadImageFromStorage(String path) {
        Bitmap b = null;
        try {
            File f = new File(path, getFileName());
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;

    }

    public void saveToExternalStorage(Bitmap bitmapImage, boolean ext) {
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

    public Bitmap loadImageFromStorageExternal(String path) {
        Bitmap b = null;
        try {
            File f = new File(path + getFileName());
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return b;

    }

    public void delete(boolean ext) {
        File file = new File(getUriFile(ext));
        file.delete();
    }


}
