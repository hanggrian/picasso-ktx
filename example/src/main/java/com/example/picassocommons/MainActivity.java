package com.example.picassocommons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hendraanggrian.support.utils.widget.Toasts;
import com.squareup.picasso.Picassos;

import butterknife.BindViews;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindViews({R.id.button_main1, R.id.button_main2, R.id.button_main3}) Button[] buttons;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (Button button : buttons)
            button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_main1:
                startActivity(new Intent(this, TransformationActivity.class));
                break;
            case R.id.button_main2:
                startActivity(new Intent(this, TargetActivity.class));
                break;
            case R.id.button_main3:
                Picassos.getCache(this).clear();
                Toasts.showShort(this, "Cache cleared");
                break;
        }
    }
}