package com.prakriti.downloadingimage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myImage = findViewById(R.id.myImage);
        Button btnDownload = findViewById(R.id.btnDownload);

        btnDownload.setOnClickListener(v -> {
            // create object of inner class to dl image
            DownloadImage downloadImage = new DownloadImage(MainActivity.this);
            downloadImage.execute("https://images.techhive.com/images/article/2016/09/android-old-habits-100682662-primary.idge.jpg");
        });
    }


    // inner class for downloading image
        // extends abstract AsyncTask class - perform short background tasks, UI is not blocked
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        // AsyncTask accepts params, progress & result generic types
        // parameter is the url - String
        // progress - % that is downloaded in bg
        // result - image we want - Bitmap

        private ProgressDialog progressDialog;
        private Context context;

        public DownloadImage(Context context) {
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // show msg that download is starting
            progressDialog.setMessage("Downloading Image...");
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // return type is acc to result we specified
            String stringUrl = params[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(stringUrl);
                InputStream inputStream = url.openStream(); // opens the url site
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) { // runs on UI thread
            super.onPostExecute(bitmap);
            // show downloaded image on screen
            myImage.setImageBitmap(bitmap);
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

}