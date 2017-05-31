package com.example.picassocommons;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.hendraanggrian.support.utils.widget.Toasts;
import com.squareup.picasso.Picassos;

import butterknife.BindView;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class MainActivity extends BaseActivity {

    @BindView(R.id.button_main1) Button button1;
    @BindView(R.id.button_main2) Button button2;
    @BindView(R.id.button_main3) Button button3;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button1.setOnClickListener(v -> startActivity(new Intent(this, TransformationActivity.class)));
        button2.setOnClickListener(v -> startActivity(new Intent(this, TargetActivity.class)));
        button3.setOnClickListener(v -> {
            Picassos.getCache(this).clear();
            Toasts.showShort(this, "Cache cleared");
        });
    }
}