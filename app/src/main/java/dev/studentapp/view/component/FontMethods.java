package dev.studentapp.view.component;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class FontMethods {

    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    private static Typeface getTypeface(Context context, String fontname) {
        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontname, typeface);
        }

        return typeface;
    }

    public static void setTypeface(Context context, TextView textView, String fontname) {
        textView.setTypeface(getTypeface(context, fontname));
    }

    public static void setTypeface(Context context, Button button, String fontname) {
        button.setTypeface(getTypeface(context, fontname));
    }
}