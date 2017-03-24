package io.github.hendraanggrian.picassodemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.hendraanggrian.picasso.target.Targets;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar_target) Toolbar toolbar;
    @BindView(R.id.edittext_target) EditText editText;
    @BindView(R.id.button_target_clear) Button buttonClear;
    @BindView(R.id.button_target_go) Button buttonGo;
    @BindView(R.id.view_target) View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
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
                        .placeholder(R.mipmap.ic_launcher)
                        .into(Targets.background(Targets.SCALE_CENTER_INSIDE, view));
                break;
        }
    }
}