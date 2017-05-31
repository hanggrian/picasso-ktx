package com.example.picassocommons;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hendraanggrian.picasso.commons.target.Targets;
import com.hendraanggrian.support.utils.view.InputMethods;
import com.hendraanggrian.support.utils.widget.Toasts;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class TargetActivity extends BaseActivity {

    @BindView(R.id.toolbar_target) Toolbar toolbar;
    @BindView(R.id.edittext_target) EditText editText;
    @BindView(R.id.button_target_clear) Button buttonClear;
    @BindView(R.id.button_target_go) Button buttonGo;
    @BindView(R.id.imageview_target) ImageView imageView;

    @Override
    protected int getContentView() {
        return R.layout.activity_target;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        buttonClear.setOnClickListener(v -> editText.setText(""));
        buttonGo.setOnClickListener(v -> {
            InputMethods.hideSoftInput(this);
            Picasso.with(this)
                    .load(editText.getText().toString())
                    .into(Targets.placeholder(imageView).callback(
                            (bitmap, from) -> Toasts.showShort(this, "Loaded."),
                            errorDrawable -> Toasts.showShort(this, "Failed."),
                            placeHolderDrawable -> Toasts.showShort(this, "Prepare...")));
        });
    }
}