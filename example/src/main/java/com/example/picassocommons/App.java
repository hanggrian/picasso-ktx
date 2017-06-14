package com.example.picassocommons;

import android.app.Application;

import com.squareup.picasso.Picassos;

import butterknife.ButterKnife;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Picassos.setDebug(BuildConfig.DEBUG);
        ButterKnife.setDebug(BuildConfig.DEBUG);
    }
}