package com.hendraanggrian.picasso.commons.target;

import android.graphics.drawable.Drawable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public interface OnTargetFailed {

    /**
     * @see com.squareup.picasso.Target#onBitmapFailed(Drawable)
     */
    void onBitmapFailed(Drawable errorDrawable);
}