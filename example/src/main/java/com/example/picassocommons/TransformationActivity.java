package com.example.picassocommons;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.hendraanggrian.picasso.commons.target.Targets;
import com.hendraanggrian.picasso.commons.transformation.Transformations;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class TransformationActivity extends BaseActivity {

    @BindView(R.id.toolbar_transformation) Toolbar toolbar;
    @BindView(R.id.imageview_transformation) ImageView imageView;
    @BindView(R.id.checkbox_transformation_cropsquare) CheckBox checkBox_CropSquare;
    @BindView(R.id.checkbox_transformation_cropcircle) CheckBox checkBox_CropCircle;
    @BindView(R.id.checkbox_transformation_croprounded) CheckBox checkBox_CropRounded;
    @BindView(R.id.checkbox_transformation_coloroverlay) CheckBox checkBox_ColorOverlay;
    @BindView(R.id.checkbox_transformation_grayscale) CheckBox checkBox_Grayscale;

    @Override
    protected int getContentView() {
        return R.layout.activity_transformation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        for (CheckBox checkBox : Arrays.asList(checkBox_CropSquare, checkBox_CropCircle, checkBox_CropRounded, checkBox_ColorOverlay, checkBox_Grayscale)) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
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
                        .into(Targets.placeholder(imageView));
            });
        }
    }
}