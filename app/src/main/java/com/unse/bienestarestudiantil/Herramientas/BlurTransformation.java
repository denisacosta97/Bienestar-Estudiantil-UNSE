package com.unse.bienestarestudiantil.Herramientas;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Script;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

import io.reactivex.internal.operators.completable.CompletableNever;

public class BlurTransformation extends BitmapTransformation {

    private RenderScript rs;

    public BlurTransformation(Context context){
        rs = RenderScript.create(context);
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true);
        Allocation input = Allocation.createFromBitmap(rs,
                bitmap, Allocation.MipmapControl.MIPMAP_FULL,
                Allocation.USAGE_SHARED);
        Allocation output = Allocation.createTyped(rs, input.getType());

        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setInput(input);
        script.setRadius(20);
        script.forEach(output);
        output.copyTo(bitmap);
        toTransform.recycle();
        return bitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {


    }
}
