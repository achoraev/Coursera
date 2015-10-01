package com.dailyselfie.selfie;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by angelr on 01-Oct-15.
 */
public class Selfie {
    private Uri uri;
    private Bitmap bitmap;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}