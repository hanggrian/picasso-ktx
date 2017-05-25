package com.hendraanggrian.picasso.commons.target;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
final class ViewGroupUtils {

    @Nullable
    static ViewGroup getParent(@NonNull View view) {
        return view.getParent() instanceof ViewGroup
                ? (ViewGroup) view.getParent()
                : null;
    }

    static void replaceView(@NonNull View currentView, @NonNull View newView) {
        ViewGroup parent = getParent(currentView);
        if (parent != null) {
            int index = parent.indexOfChild(currentView);
            parent.removeView(currentView);
            parent.removeView(newView);
            parent.addView(newView, index);
        }
    }
}