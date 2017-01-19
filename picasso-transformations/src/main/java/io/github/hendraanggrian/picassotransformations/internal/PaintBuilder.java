package io.github.hendraanggrian.picassotransformations.internal;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class PaintBuilder {

    @Nullable private Integer flag;
    @Nullable private ColorFilter colorFilter;
    @Nullable private Shader shader;

    public PaintBuilder() {
    }

    public PaintBuilder(int flag) {
        this.flag = flag;
    }

    public PaintBuilder colorFilter(@NonNull ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
        return this;
    }

    public PaintBuilder shader(@NonNull Shader shader) {
        this.shader = shader;
        return this;
    }

    public Paint build() {
        final Paint paint = flag != null
                ? new Paint(flag)
                : new Paint();
        if (colorFilter != null)
            paint.setColorFilter(colorFilter);
        if (shader != null)
            paint.setShader(shader);
        return paint;
    }
}