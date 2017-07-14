package com.example.picassocommons;

import android.app.Application;

import com.squareup.picasso.Picassos;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class App2 extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Picassos.INSTANCE.setDebug(true);
    }
}