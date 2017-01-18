package io.github.hendraanggrian.picassotransformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

/**
 * Made to test whether it's a good idea to keep references of Transformation in {@link java.util.WeakHashMap}.
 * <p>
 * Although the result are not consistent, it is expected that {@link CachingTest#withCaching()} performs
 * twice as fast as {@link CachingTest#withoutCaching()}.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
@RunWith(AndroidJUnit4.class)
public class CachingTest {

    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    private static final int COUNT = 1000;

    private CountDownLatch latch;
    private int currentCount;

    @Test
    public void withoutCaching() throws Throwable {
        currentCount = 0;
        latch = new CountDownLatch(COUNT);
        uiThreadTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Runnable runnable = this;
                Picasso.with(getContext())
                        .load(android.R.drawable.alert_dark_frame)
                        .transform(new CropCircleTransformation())
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                latch.countDown();
                                currentCount++;
                                if (currentCount < COUNT)
                                    try {
                                        uiThreadTestRule.runOnUiThread(runnable);
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
                        });
            }
        });
        latch.await();
    }

    @Test
    public void withCaching() throws Throwable {
        currentCount = 0;
        latch = new CountDownLatch(COUNT);
        uiThreadTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Runnable runnable = this;
                Picasso.with(getContext())
                        .load(android.R.drawable.alert_dark_frame)
                        .transform(Transformations.cropCircle())
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                latch.countDown();
                                currentCount++;
                                if (currentCount < COUNT)
                                    try {
                                        uiThreadTestRule.runOnUiThread(runnable);
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
                        });
            }
        });
        latch.await();
    }

    public Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }
}