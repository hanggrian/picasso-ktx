package io.github.hendraanggrian.picasso.target;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import io.github.hendraanggrian.picasso.internal.ViewGroupUtils;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public abstract class SingleTarget<View extends android.view.View> extends BaseTarget<View> {

    @NonNull private final ViewGroup placeholderContainer;

    public SingleTarget(@NonNull View view) {
        super(view);
        getTarget().setTag(this);
        placeholderContainer = new FrameLayout(view.getContext());
        placeholderContainer.setLayoutParams(view.getLayoutParams());

        ViewGroup parent = ViewGroupUtils.getParent(view);
        if (parent != null && parent.getLayoutTransition() == null)
            parent.setLayoutTransition(new LayoutTransition());
    }

    @Override
    public void initTag(View target) {
        target.setTag(this);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        super.onBitmapLoaded(bitmap, from);
        ViewGroupUtils.replaceView(placeholderContainer, getTarget());
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        super.onBitmapFailed(errorDrawable);
        ViewGroupUtils.replaceView(placeholderContainer, getTarget());
    }

    @Override
    public final void onPrepareLoad(Drawable placeHolderDrawable) {
        super.onPrepareLoad(placeHolderDrawable);
        if (placeHolderDrawable != null) {
            ImageView imageView = new ImageView(getTarget().getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageDrawable(placeHolderDrawable);
            placeholderContainer.addView(imageView, 0);
        }
        ViewGroupUtils.replaceView(getTarget(), placeholderContainer);
    }

    @NonNull
    public SingleTarget<View> placeholder(@Targets.PlaceholderCode int placeholderCode) {
        return placeholder(Placeholder.valueOf(placeholderCode).toView(getTarget().getContext()));
    }

    @NonNull
    public SingleTarget<View> placeholder(@NonNull android.view.View placeholderView) {
        placeholderContainer.removeAllViews();
        placeholderContainer.addView(placeholderView);
        return this;
    }

    @NonNull
    public SingleTarget<View> disableTransition() {
        ViewGroup parent = ViewGroupUtils.getParent(getTarget());
        if (parent != null)
            parent.setLayoutTransition(null);
        return this;
    }

    @NonNull
    private static ViewGroup getParent(@NonNull android.view.View view) {
        return (ViewGroup) view.getParent();
    }

    private enum Placeholder {
        PROGRESS(0) {
            @NonNull
            @Override
            public android.view.View toView(@NonNull Context context) {
                ProgressBar progressBar = new ProgressBar(context);
                progressBar.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ((FrameLayout.LayoutParams) progressBar.getLayoutParams()).gravity = Gravity.CENTER;
                return progressBar;
            }
        };

        @NonNull
        abstract android.view.View toView(@NonNull Context context);

        private int code;

        Placeholder(int code) {
            this.code = code;
        }

        private static Placeholder valueOf(int code) {
            for (Placeholder placeholder : values())
                if (placeholder.code == code)
                    return placeholder;
            throw new RuntimeException();
        }
    }
}