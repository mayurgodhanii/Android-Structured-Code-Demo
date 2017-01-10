package dev.studentapp.view.component.button;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import dev.studentapp.view.component.FontMethods;

/**
 * Created by Nirav Dangi on 16/08/16.
 */
public class ButtonOpenSansBold extends Button {

    public ButtonOpenSansBold(Context context) {
        super(context);
        FontMethods.setTypeface(context,this,"open-sans-bold.ttf");
    }

    public ButtonOpenSansBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontMethods.setTypeface(context,this,"open-sans-bold.ttf");
    }

    public ButtonOpenSansBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FontMethods.setTypeface(context,this,"open-sans-bold.ttf");
    }

    public ButtonOpenSansBold(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        FontMethods.setTypeface(context,this,"open-sans-bold.ttf");
    }
}
