package com.example.imagedisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // views
    private LinearLayout layout;
    private TextView urlField;
    private ArrayList<Button> buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set variables
        layout = findViewById(R.id.linearLayout);
        urlField = findViewById(R.id.textBox);
        buttonList = new ArrayList<Button>();

        // initialize preset and user added images
        initImages();

        // configure add image button
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
    }

    // PRIVATE METHODS
    // initialize stored images
    private void initImages() {
        // use view tree observer to get layout width when initialized
        layout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // remove the listener and store the device width
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ImageData.layoutWidth = layout.getMeasuredWidth();

                // preset images
                Drawable[] images = {
                        getResources().getDrawable(R.drawable.goa),
                        getResources().getDrawable(R.drawable.terminal),
                        getResources().getDrawable(R.drawable.colours),
                        getResources().getDrawable(R.drawable.badminton),
                        getResources().getDrawable(R.drawable.tall)
                };

                // add every preset image to layout
                for (Drawable image : images) {
                    addImage(image);
                }

                // add every user added image to layout
                for (Drawable image : ImageData.visImages) {
                    addImage(image);
                }
            }
        });
    }

    // add image to linear layout
    private void addImage(Drawable image) {
        // add a new button with the new image
        Button newImage = new Button(this);

        // set ratio to image width over height
        double imageRatio = 1.0 * image.getIntrinsicWidth() / image.getIntrinsicHeight();

        // set image width to match its parent's, and height to layout width over ratio
        newImage.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (ImageData.layoutWidth / imageRatio)));

        // set button background to image and id to counter
        newImage.setBackground(image);
        newImage.setId(ImageData.imageIdCounter);
        ImageData.imageIdCounter++;
        newImage.setOnClickListener(this);

        // add button to layout and button list
        layout.addView(newImage);
        buttonList.add(newImage);
    }

    // PUBLIC METHODS
    // add image button to the vertical layout
    public void loadUrlImage() {
        // get image from url in a thread since it cannot establish http connection in main thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // connect to url via http
                    HttpURLConnection connection = (HttpURLConnection)
                            new URL("" + urlField.getText()).openConnection();
                    connection.setRequestProperty("connection", "close");
                    connection.connect();

                    // if connection is successful, store image as drawable and set status code to 1
                    Bitmap bm = BitmapFactory.decodeStream(connection.getInputStream());
                    connection.disconnect();
                    ImageData.img = new BitmapDrawable(Resources.getSystem(), bm);
                    ImageData.status = 1;

                    // store successful image to visited images list
                    ImageData.visImages.add(ImageData.img);

                } catch (IOException e) {
                    // if an io error is thrown, catch it and set status code to 2
                    ImageData.status = 2;
                }
            }
        }).start();

        // wait for http connection thread to finish
        while (ImageData.status == 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // if thread is interrupted, log error
                Log.e("AddImage", "thread sleep interrupted");
            }
        }

        String toastMessage = "";

        // if connection is successfully established
        if (ImageData.status == 1) {
            // add image
            addImage(ImageData.img);

            // set feedback message
            toastMessage = "Image appended!";

            // if an invalid url has been inputted
        } else if (ImageData.status == 2) {
            toastMessage = "Invalid URL.";
        }

        // display status message to user
        Toast toast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT);
        toast.show();

        // reset status number
        ImageData.status = 0;
    }

    // when user taps a button...
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addButton) {
            // establish http connection and retrieve image if the add button is pressed
            loadUrlImage();
        } else {
            // set drawable to the background of the button clicked
            for (Button clicked : buttonList) {
                if (v.getId() == clicked.getId()) {
                    ImageData.img = (BitmapDrawable) clicked.getBackground();
                    break;
                }
            }

            // if an image is clicked, launch the expanded view
            Intent expanded = new Intent(this, ExpandedActivity.class);
            startActivity(expanded);
        }
    }
}