package com.example.imagedisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ExpandedActivity extends AppCompatActivity implements View.OnClickListener{

    // a frame layout is used to center image vertically and horizontally
    private FrameLayout layout;

    // layout dimensions
    private int layoutWidth;
    private int layoutHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded);

        // init layout
        layout = findViewById(R.id.expandedLayout);
        layoutWidth = 0;
        layoutHeight = 0;

        layout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // remove the listener and store the device dimensions
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                layoutWidth = layout.getMeasuredWidth();
                layoutHeight = layout.getMeasuredHeight();

                // add back button to layout
                addButton();
            }
        });
    }

    // add the image to layout
    private void addButton() {
        // add a expanded view of image
        Button backButton = new Button(this);
        BitmapDrawable image = ImageData.img;

        // set ratio to image width over height
        int imageWidth = image.getIntrinsicWidth();
        int imageHeight = image.getIntrinsicHeight();

        // calculate whether the width or the height of the image is bound, and by how much
        double maxRatio = Math.max(1.0 * imageWidth / layoutWidth, 1.0 * imageHeight / layoutHeight);

        // compute final display dimensions
        int displayWidth = (int) (imageWidth / maxRatio);
        int displayHeight = (int) (imageHeight / maxRatio);

        // set up layout so image is centered with the right dimensions
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(displayWidth, displayHeight);
        params.gravity = Gravity.CENTER;
        backButton.setLayoutParams(params);

        // set button background to image and add listener
        backButton.setBackground(image);
        backButton.setOnClickListener(this);

        // add button to layout
        layout.addView(backButton);
    }

    @Override
    public void onClick(View v) {
        // return to main view
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}