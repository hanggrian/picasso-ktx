package io.github.hendraanggrian.picassotransformationsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.hendraanggrian.picassotransformations.Transformations;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ImageView imageView;
    private CheckBox checkBox_CropSquare, checkBox_CropCircle, checkBox_CropRounded, checkBox_ColorOverlay, checkBox_Grayscale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        Transformations.setDebug(true);

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

        Picasso.with(this)
                .load(R.drawable.bg_test)
                .transform(transformations)
                .into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.item_info).setIcon(Transformations.overlay(this, R.color.colorAccent).toDrawable(this, android.R.drawable.ic_dialog_info));
        return true;
    }
}