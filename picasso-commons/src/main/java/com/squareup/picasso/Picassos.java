package com.squareup.picasso;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Picassos {

    private Picassos() {
    }

    @NonNull
    public static Cache getCache(@NonNull Context context) {
        return getCache(Picasso.with(context));
    }

    @NonNull
    public static Cache getCache(@NonNull Picasso picasso) {
        return picasso.cache;
    }
}