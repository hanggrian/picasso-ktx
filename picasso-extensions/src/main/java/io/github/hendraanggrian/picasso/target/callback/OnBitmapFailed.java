package io.github.hendraanggrian.picasso.target.callback;

import android.graphics.drawable.Drawable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public interface OnBitmapFailed {

    void onBitmapFailed(Drawable errorDrawable);
}