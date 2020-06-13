package com.example.imagedisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // init vars
    private BitmapDrawable img;
    private LinearLayout layout;
    private int status;
    private TextView tf;
    private int layoutWidth;
    private Context context;
    private Drawable[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set variables
        layout = findViewById(R.id.linearLayout);
        tf = findViewById(R.id.textBox);
        status = 0;
        layoutWidth = 0;
        context = this;
        images = new Drawable[]{
                getResources().getDrawable(R.drawable.goa),
                getResources().getDrawable(R.drawable.programming),
                getResources().getDrawable(R.drawable.terminal),
                getResources().getDrawable(R.drawable.badminton)
        };

        initImages();

        // configure add image button
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // attempt to add new image button when the add button is clicked
                addImage();
            }
        });
    }

    // initialize stored images
    private void initImages() {
        // use view tree observer to get layout width when initialized
        layout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // remove the listener and store the device width
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                layoutWidth = layout.getMeasuredWidth();

                // for every image...
                for (Drawable image : images) {
                    // create a new image button
                    Button newImage = new Button(context);

                    // set ratio to image width over height
                    double imageRatio = 1.0 * image.getIntrinsicWidth() / image.getIntrinsicHeight();

                    // set image width to match its parent's, and height to layout width over ratio
                    newImage.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            (int) (layoutWidth / imageRatio)));

                    // set button background to image
                    newImage.setBackground(image);

                    // add button to layout
                    layout.addView(newImage);
                }
            }
        });
    }

    // add image button to the vertical layout
    private void addImage() {

        // get image from url in a thread since it cannot establish http connection in main thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // connect to url via http
                    String url = "" + tf.getText();
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestProperty("connection", "close");
                    connection.connect();

                    // if connection is successful, store image as drawable and set status code to 1
                    Bitmap bm = BitmapFactory.decodeStream(connection.getInputStream());
                    connection.disconnect();
                    img = new BitmapDrawable(Resources.getSystem(), bm);
                    status = 1;
                } catch (IOException e) {
                    // if an io error is thrown, catch it and set status code to 2
                    status = 2;
                }
            }
        }).start();

        // wait for http connection thread to finish with a 10 second timeout
        int timeout = 0;
        while (status == 0 && timeout < 10000) {
            try {
                Thread.sleep(50);
                timeout += 50;
            } catch (InterruptedException e) {
                // if thread is interrupted, log error
                Log.e("addImage", "thread sleep interrupted");
            }
        }

        // initialize toast message
        String message = "";

        if (status == 1) {
            // if connection was established, add a new button with the new image
            Button newImage = new Button(this);

            // set ratio to image width over height
            double imageRatio = 1.0 * img.getIntrinsicWidth() / img.getIntrinsicHeight();

            // set image width to match its parent's, and height to layout width over ratio
            newImage.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) (layoutWidth / imageRatio)));

            newImage.setBackground(img);
            layout.addView(newImage);

            // update toast message
            message = "Image appended!";
        } else if (status == 2) {
            message = "Invalid URL.";
        } else  if (timeout >= 10000) {
            message = "Application has timed out. Please try again.";
        }

        // display status message to user
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();

        // reset status number
        status = 0;
    }
}