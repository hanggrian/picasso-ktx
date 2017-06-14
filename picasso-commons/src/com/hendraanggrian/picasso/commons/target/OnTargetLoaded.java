package com.hendraanggrian.picasso.commons.target;

import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public interface OnTargetLoaded {

    /**
     * @see com.squareup.picasso.Target#onBitmapLoaded(Bitmap, Picasso.LoadedFrom)
     */
    void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from);
}