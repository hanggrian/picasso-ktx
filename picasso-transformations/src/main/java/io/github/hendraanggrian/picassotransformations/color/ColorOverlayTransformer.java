package io.github.hendraanggrian.picassotransformations.color;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.ColorInt;

import io.github.hendraanggrian.picassotransformations.Transformer;
import io.github.hendraanggrian.picassotransformations.internal.PaintBuilder;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ColorOverlayTransformer extends Transformer {

    public static final String TAG = "overlay";
    public static final String NAME_COLOR = "color";

    private int color;

    public ColorOverlayTransformer(@ColorInt int color) {
        this.color = color;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        final Bitmap target = createDefaultBitmap(source);
        new Canvas(target).drawBitmap(source, 0, 0, new PaintBuilder(Paint.ANTI_ALIAS_FLAG)
                .colorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP))
                .build());
        source.recycle();
        return target;
    }

    @Override
    public String key() {
        return Key.fromTag(TAG).put(NAME_COLOR, color).toString();
    }
}