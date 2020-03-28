package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isseiaoki.simplecropview.CropImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.CompletableSource;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class CropImageActivity extends AppCompatActivity implements View.OnClickListener {

    CropImageView mCropImageView;
    ImageView imgRotar, btnBack;
    TextView txtCancelar, txtOk;
    Uri uri;
    static String nameDir;

    private CompositeDisposable mDisposable = new CompositeDisposable();
    private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.JPEG;
    private RectF mFrameRect = null;
    private Uri mSourceUri = null;
    private DialogoProcesamiento mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.URI_IMAGE) != null) {
            uri = getIntent().getParcelableExtra(Utils.URI_IMAGE);
        }
        if (uri != null) {
            loadViews();

            setToolbar();

            loadData();

            loadListener();
        } else {
            Utils.showToast(getApplicationContext(), "Error al procesar imagen");
        }


    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("");
        DrawableCompat.setTint(btnBack.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
    }

    private void loadListener() {
        btnBack.setOnClickListener(this);
        txtOk.setOnClickListener(this);
        txtCancelar.setOnClickListener(this);
        imgRotar.setOnClickListener(this);
    }

    private void loadData() {
        mDisposable.add(loadImage(uri));

    }

    private Disposable loadImage(final Uri uri) {
        mSourceUri = uri;
        return new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .filter(new Predicate<Boolean>() {
                    @Override
                    public boolean test(@io.reactivex.annotations.NonNull Boolean granted)
                            throws Exception {
                        return granted;
                    }
                })
                .flatMapCompletable(new Function<Boolean, CompletableSource>() {
                    @Override
                    public CompletableSource apply(@io.reactivex.annotations.NonNull Boolean aBoolean)
                            throws Exception {
                        return mCropImageView.load(uri)
                                .useThumbnail(true)
                                .initialFrameRect(mFrameRect)
                                .executeAsCompletable();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
    }

    private Disposable cropImage() {
        return mCropImageView.crop(mSourceUri)
                .executeAsSingle()
                .flatMap(new Function<Bitmap, SingleSource<Uri>>() {
                    @Override
                    public SingleSource<Uri> apply(@io.reactivex.annotations.NonNull Bitmap bitmap)
                            throws Exception {
                        return mCropImageView.save(bitmap)
                                .compressFormat(mCompressFormat)
                                .executeAsSingle(getUriSave());
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Disposable disposable)
                            throws Exception {
                        showProgress();
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        dismissProgress();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Uri>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Uri uri) throws Exception {
                        Intent intent = new Intent();
                        intent.putExtra(Utils.URI_IMAGE, uri);
                        intent.putExtra("name", nameDir);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable)
                            throws Exception {
                    }
                });
    }

    private void dismissProgress() {
        mDialog.dismiss();
    }

    private void showProgress() {
        mDialog = new DialogoProcesamiento();
        mDialog.setCancelable(false);
        mDialog.show(getSupportFragmentManager(), "dialog_procs");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }

    private void loadViews() {
        mCropImageView = findViewById(R.id.imgIcon);
        txtCancelar = findViewById(R.id.txtCancelar);
        txtOk = findViewById(R.id.txtOk);
        imgRotar = findViewById(R.id.imgRotar);
        btnBack = findViewById(R.id.imgFlecha);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgRotar:
                mCropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
                break;
            case R.id.txtOk:
                mDisposable.add(cropImage());
                break;
            case R.id.txtCancelar:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.imgFlecha:
                setResult(RESULT_CANCELED);
                onBackPressed();
                break;

        }

    }

    public Uri getUriSave() {
        return createNewUri(getApplicationContext(), Bitmap.CompressFormat.JPEG);
    }

    public static String getDirPath() {
        String dirPath = "";
        File imageDir = null;
        File extStorageDir = Environment.getExternalStorageDirectory();
        if (extStorageDir.canWrite()) {
            imageDir = new File(extStorageDir.getPath() + "/BIENESTAR ESTUDIANTIL");
        }
        if (imageDir != null) {
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }
            if (imageDir.canWrite()) {
                dirPath = imageDir.getPath();
            }
        }
        return dirPath;
    }

    public static Uri createNewUri(Context context, Bitmap.CompressFormat format) {
        long currentTimeMillis = System.currentTimeMillis();
        Date today = new Date(currentTimeMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String title = dateFormat.format(today);
        String dirPath = getDirPath();
        String fileName = "pic" + title + "." + getMimeType(format);
        String path = dirPath + "/" + fileName;
        File file = new File(path);
        nameDir = path;
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/" + getMimeType(format));
        values.put(MediaStore.Images.Media.DATA, path);
        long time = currentTimeMillis / 1000;

        values.put(MediaStore.MediaColumns.DATE_ADDED, time);
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, time);
        if (file.exists()) {
            values.put(MediaStore.Images.Media.SIZE, file.length());
        }
        ContentResolver resolver = context.getContentResolver();
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Utils.showLog("Data foto", "SaveUri = " + uri);
        return uri;
    }

    public static String getMimeType(Bitmap.CompressFormat format) {
        switch (format) {
            case JPEG:
                return "jpg";
            case PNG:
                return "png";
        }
        return "png";
    }

}
