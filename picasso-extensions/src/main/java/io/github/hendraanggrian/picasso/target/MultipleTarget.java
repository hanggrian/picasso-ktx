package io.github.hendraanggrian.picasso.target;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class MultipleTarget<Views extends Iterable<? extends View>> extends BaseTarget<Views> {

    public MultipleTarget(@NonNull Views views) {
        super(views);
    }

    @Override
    public void initTag(Views target) {
        for (View view : target)
            view.setTag(this);
    }
}