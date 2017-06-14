package com.hendraanggrian.picasso.commons.target;

import android.graphics.drawable.Drawable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public interface OnTargetPrepare {

    /**
     * @see com.squareup.picasso.Target#onPrepareLoad(Drawable)
     */
    void onPrepareLoad(Drawable placeHolderDrawable);
}