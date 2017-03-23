package io.github.hendraanggrian.picassotransformationsdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.hendraanggrian.picasso.target.ProgressTarget;
import io.github.hendraanggrian.picasso.transformation.Transformations;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ImageView imageView;
    private CheckBox checkBox_CropSquare, checkBox_CropCircle, checkBox_CropRounded, checkBox_ColorOverlay, checkBox_Grayscale;

    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        imageView = (ImageView) findViewById(R.id.imageview);
        checkBox_CropSquare = (CheckBox) findViewById(R.id.checkbox_cropsquare);
        checkBox_CropCircle = (CheckBox) findViewById(R.id.checkbox_cropcircle);
        checkBox_CropRounded = (CheckBox) findViewById(R.id.checkbox_croprounded);
        checkBox_ColorOverlay = (CheckBox) findViewById(R.id.checkbox_colorOverlay);
        checkBox_Grayscale = (CheckBox) findViewById(R.id.checkbox_grayscale);

        for (CheckBox checkBox : Arrays.asList(checkBox_CropSquare, checkBox_CropCircle, checkBox_CropRounded, checkBox_ColorOverlay, checkBox_Grayscale))
            checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final List<Transformation> transformations = new ArrayList<>();
        if (checkBox_CropSquare.isChecked())
            transformations.add(Transformations.square());
        if (checkBox_CropCircle.isChecked())
            transformations.add(Transformations.circle());
        if (checkBox_CropRounded.isChecked())
            transformations.add(Transformations.rounded(25, 10, true));
        if (checkBox_ColorOverlay.isChecked())
            transformations.add(Transformations.overlay(this, R.color.colorAccent, 150));
        if (checkBox_Grayscale.isChecked())
            transformations.add(Transformations.grayscale());

        (imageUrl != null
                ? Picasso.with(this).load(imageUrl)
                : Picasso.with(this).load(R.drawable.bg_test))
                .transform(transformations)
                .into(new ProgressTarget(imageView));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item)
            new MaterialDialog.Builder(this)
                    .title("Load url")
                    .input("image url", "", false, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            imageUrl = input.toString();
                            Picasso.with(MainActivity.this)
                                    .load(input.toString())
                                    .into(new ProgressTarget(imageView));
                        }
                    })
                    .show();
        return true;
    }
}