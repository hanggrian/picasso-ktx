package io.github.hendraanggrian.picassodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.hendraanggrian.picasso.target.Targets;
import io.github.hendraanggrian.picasso.transformation.Transformations;

public class TransformationActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar_transformation) Toolbar toolbar;
    @BindView(R.id.imageview_transformation) ImageView imageView;
    @BindView(R.id.checkbox_transformation_cropsquare) CheckBox checkBox_CropSquare;
    @BindView(R.id.checkbox_transformation_cropcircle) CheckBox checkBox_CropCircle;
    @BindView(R.id.checkbox_transformation_croprounded) CheckBox checkBox_CropRounded;
    @BindView(R.id.checkbox_transformation_coloroverlay) CheckBox checkBox_ColorOverlay;
    @BindView(R.id.checkbox_transformation_grayscale) CheckBox checkBox_Grayscale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transformation);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        for (CheckBox checkBox : Arrays.asList(checkBox_CropSquare, checkBox_CropCircle, checkBox_CropRounded, checkBox_ColorOverlay, checkBox_Grayscale))
            checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        List<Transformation> transformations = new ArrayList<>();
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
                .into(Targets.image(imageView));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }
}