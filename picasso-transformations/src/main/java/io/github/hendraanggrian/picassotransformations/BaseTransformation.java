package io.github.hendraanggrian.picassotransformations;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
abstract class BaseTransformation implements Transformation {

    private static final String JSON_NAME = "name";
    private static final String JSON_VALUES = "values";

    @NonNull
    abstract String name();

    @Nullable
    abstract Collection values();

    @Override
    public String key() {
        return buildKey(name(), values());
    }

    @NonNull
    static String buildKey(@NonNull String name, @Nullable Collection values) {
        try {
            final JSONObject json = new JSONObject();
            json.put(JSON_NAME, name);
            json.put(JSON_VALUES, new JSONArray(values));
            return json.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    static BaseTransformation parse(@NonNull String key) {
        try {
            final JSONObject json = new JSONObject(key);
            switch (json.getString(JSON_NAME)) {
                case CropSquareTransformation.TAG:
                    return new CropSquareTransformation();
                case CropCircleTransformation.TAG:
                    return new CropCircleTransformation();
                case CropRoundedTransformation.TAG:
                    return new CropRoundedTransformation(
                            json.getJSONArray(JSON_VALUES).getInt(0),
                            json.getJSONArray(JSON_VALUES).getInt(1));
                case GrayscaleTransformation.TAG:
                    return new GrayscaleTransformation();
                default:
                    throw new RuntimeException("Invalid key: " + key);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}