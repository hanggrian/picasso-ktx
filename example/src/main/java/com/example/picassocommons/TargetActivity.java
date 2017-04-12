package com.example.picassocommons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hendraanggrian.picasso.commons.target.Targets;
import com.hendraanggrian.picasso.commons.target.callback.OnBitmapFailed;
import com.hendraanggrian.picasso.commons.target.callback.OnBitmapLoaded;
import com.hendraanggrian.picasso.commons.target.callback.OnPrepareLoad;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TargetActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar_target) Toolbar toolbar;
    @BindView(R.id.edittext_target) EditText editText;
    @BindView(R.id.button_target_clear) Button buttonClear;
    @BindView(R.id.button_target_go) Button buttonGo;
    @BindView(R.id.imageview_target) ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        buttonClear.setOnClickListener(this);
        buttonGo.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_target_clear:
                editText.setText("");
                break;
            case R.id.button_target_go:
                View focusedView = getCurrentFocus();
                if (focusedView != null)
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(focusedView.getWindowToken(), 0);

                Picasso.with(this)
                        .load(editText.getText().toString())
                        .into(Targets.image(imageView).placeholder(Targets.PLACEHOLDER_PROGRESS).callback(new OnBitmapLoaded() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                Toast.makeText(TargetActivity.this, "Image successfully loaded.", Toast.LENGTH_SHORT).show();
                            }
                        }, new OnBitmapFailed() {
                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                Toast.makeText(TargetActivity.this, "Image failed to load.", Toast.LENGTH_SHORT).show();
                            }
                        }, new OnPrepareLoad() {
                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                                Toast.makeText(TargetActivity.this, "Picasso started loading.", Toast.LENGTH_SHORT).show();
                            }
                        }));
                break;
        }
    }
}