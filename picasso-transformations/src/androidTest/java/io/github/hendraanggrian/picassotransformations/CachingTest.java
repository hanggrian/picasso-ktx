package io.github.hendraanggrian.picassotransformations;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import io.github.hendraanggrian.picassotransformations.color.ColorGrayscaleTransformer;
import io.github.hendraanggrian.picassotransformations.color.ColorOverlayTransformer;
import io.github.hendraanggrian.picassotransformations.crop.CropCircleTransformer;
import io.github.hendraanggrian.picassotransformations.crop.CropRoundedTransformer;
import io.github.hendraanggrian.picassotransformations.crop.CropSquareTransformer;

/**
 * Made to test whether it's a good idea to keep references of Transformation in {@link java.util.WeakHashMap}.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
@RunWith(AndroidJUnit4.class)
public final class CachingTest extends BaseTest {

    private static final int COUNT = 1000;

    private final Random random = new Random();

    @Test
    public void withoutCaching() throws Throwable {
        initLatch(COUNT);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Runnable runnable = this;
                Picasso.with(getContext())
                        .load(android.R.drawable.alert_dark_frame)
                        .transform(new CropCircleTransformer())
                        .into(new TargetTest() {
                            @Override
                            void onBitmapLoaded(Bitmap bitmap) throws Throwable {
                                countdown();
                                if (counting())
                                    runOnUiThread(runnable);
                            }
                        });
            }
        });
        await();
    }

    @Test
    public void withCaching() throws Throwable {
        Transformations.clearCache();

        initLatch(COUNT);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Runnable runnable = this;
                Picasso.with(getContext())
                        .load(android.R.drawable.alert_dark_frame)
                        .transform(Transformations.circle())
                        .into(new TargetTest() {
                            @Override
                            void onBitmapLoaded(Bitmap bitmap) throws Throwable {
                                countdown();
                                if (counting())
                                    runOnUiThread(runnable);
                            }
                        });
            }
        });
        await();
    }

    @Test
    public void randomizedWithoutCaching() throws Throwable {
        initLatch(COUNT);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Runnable runnable = this;
                Picasso.with(getContext())
                        .load(android.R.drawable.alert_dark_frame)
                        .transform(randomize(false))
                        .into(new TargetTest() {
                            @Override
                            void onBitmapLoaded(Bitmap bitmap) throws Throwable {
                                countdown();
                                if (counting())
                                    runOnUiThread(runnable);
                            }
                        });
            }
        });
        await();
    }

    @Test
    public void randomizedWithCaching() throws Throwable {
        Transformations.clearCache();

        initLatch(COUNT);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Runnable runnable = this;
                Picasso.with(getContext())
                        .load(android.R.drawable.alert_dark_frame)
                        .transform(randomize(true))
                        .into(new TargetTest() {
                            @Override
                            void onBitmapLoaded(Bitmap bitmap) throws Throwable {
                                countdown();
                                if (counting())
                                    runOnUiThread(runnable);
                            }
                        });
            }
        });
        await();
    }

    @NonNull
    public Transformation randomize(boolean withCaching) {
        switch (random.nextInt(5 - 1 + 1) + 1) {
            case 1:
                return withCaching
                        ? Transformations.square()
                        : new CropSquareTransformer();
            case 2:
                return withCaching
                        ? Transformations.circle()
                        : new CropCircleTransformer();
            case 3:
                return withCaching
                        ? Transformations.rounded(25, 25)
                        : new CropRoundedTransformer(25, 25);
            case 4:
                return withCaching
                        ? Transformations.overlay(Color.RED)
                        : new ColorOverlayTransformer(Color.RED);
            case 5:
                return withCaching
                        ? Transformations.grayscale()
                        : new ColorGrayscaleTransformer();
            default:
                throw new RuntimeException("random error!");
        }
    }

    abstract class TargetTest implements Target {

        abstract void onBitmapLoaded(Bitmap bitmap) throws Throwable;

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            try {
                onBitmapLoaded(bitmap);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    }
}