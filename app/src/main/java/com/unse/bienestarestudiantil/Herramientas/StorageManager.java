package com.unse.bienestarestudiantil.Herramientas;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    public String getUriFile() {
        return new ContextWrapper(context).getDir(getFolderName(), Context.MODE_PRIVATE).getAbsolutePath();
    }

    public void saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(getFolderName(), Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, getFileName());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
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

    public void delete(){
        File file = new File(getUriFile());
        file.delete();
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
}
