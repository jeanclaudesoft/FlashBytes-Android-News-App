package com.claudylab.flashbytes.adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

public class ScaleTransformer implements ViewPager.PageTransformer {
    private Context context;
    private float elevation;

    public ScaleTransformer(Context context) {
        this.context = context;
        elevation = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                12, context.getResources().getDisplayMetrics());
    }



    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1 || position > 1) {

        } else {
            if (position < 0) {
                ((CardView) page).setCardElevation((1 + position) * elevation);
            } else {
                ((CardView) page).setCardElevation((1 - position) * elevation);
            }
        }
    }
}
