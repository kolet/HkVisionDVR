package com.example.andrei.cctv.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExampleLayout extends LinearLayout {

    public ExampleLayout(Context context,AttributeSet attrs){
        super(context,attrs);

        for(int i =0; i< 100; i++){

            LinearLayout childLayout = new LinearLayout(context);

            ImageView img  = new ImageView(context);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            childLayout.addView(img, params);

            params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            TextView text = new TextView(context);
            text.setText("HELLO");
            childLayout.addView(text, params);

            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            this.addView(childLayout,parentParams);
        }
    }
}