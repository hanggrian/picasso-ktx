package io.github.hendraanggrian.picasso.target;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class ViewGroupUtils {

    static void replaceView(View currentView, View newView) {
        ViewGroup parent = (ViewGroup) currentView.getParent();
        if (parent != null) {
            int index = parent.indexOfChild(currentView);
            parent.removeView(currentView);
            parent.removeView(newView);
            parent.addView(newView, index);
        }
    }
}