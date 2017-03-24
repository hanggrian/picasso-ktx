package io.github.hendraanggrian.picassodemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview_main) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Adapter(this, Item.values()));

        startActivity(new Intent(this, TestActivity.class));
    }

    static class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
        @NonNull private final Context context;
        @NonNull private final Item[] items;

        Adapter(@NonNull Context context, @NonNull Item[] items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(context).inflate(R.layout.recycler_main, parent, false));
        }

        @Override
        public void onBindViewHolder(final Holder holder, int position) {
            holder.textViewTitle.setText(items[position].title);
            holder.textViewSubtitle.setText(items[position].subtitle);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, items[holder.getAdapterPosition()].activityClass));
                }
            });
            holder.imageView.setImageResource(items[position].drawable);
        }

        @Override
        public int getItemCount() {
            return items.length;
        }

        static class Holder extends RecyclerView.ViewHolder {
            @BindView(R.id.textview_main_title) TextView textViewTitle;
            @BindView(R.id.textview_main_subtitle) TextView textViewSubtitle;
            @BindView(R.id.button_main) Button button;
            @BindView(R.id.imageview_main) ImageView imageView;

            Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    private enum Item {
        TRANSFORMATION(
                "Transformation",
                "Alter images with or without Picasso",
                TransformationActivity.class,
                R.drawable.ic_transform_black_48dp
        ),
        TARGET(
                "Target",
                "Wrap ImageView",
                TargetActivity.class,
                R.drawable.ic_adjust_black_48dp
        );

        @NonNull private final String title;
        @NonNull private final String subtitle;
        @NonNull private final Class<? extends Activity> activityClass;
        @DrawableRes private final int drawable;

        Item(@NonNull String title, @NonNull String subtitle, @NonNull Class<? extends Activity> activityClass, @DrawableRes int drawable) {
            this.title = title;
            this.subtitle = subtitle;
            this.activityClass = activityClass;
            this.drawable = drawable;
        }
    }
}