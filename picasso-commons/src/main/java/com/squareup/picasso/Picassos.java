package com.squareup.picasso;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Picassos {

    public static final String TAG = "Picassos";
    private static boolean DEBUG;

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

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    public static boolean isDebug() {
        return DEBUG;
    }
}