package io.github.hendraanggrian.picasso.target;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public abstract class MultipleTargeter<Views extends Iterable<? extends View>> extends Targeter<Views> {

    public MultipleTargeter(@NonNull Views views) {
        super(views);
    }

    @Override
    public void initTag(Views target) {
        for (View view : target)
            view.setTag(this);
    }
}