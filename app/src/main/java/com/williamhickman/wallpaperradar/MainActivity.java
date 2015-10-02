package com.williamhickman.wallpaperradar;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the Send button */
    public void ButtonClick(View view) {
        // Do something in response to button

        String myURL = "http://api.wunderground.com/api/5038d6c22e55f561/radar/image.gif?centerlat=38.5&centerlon=-91&radius=100&width=280&height=280&newmaps=1";
        ImageView myImage;
        File myFile;



        ((TextView)findViewById(R.id.textView)).setText("1");
        myImage = (ImageView) findViewById(R.id.imageView);

        // create temp file
        try
        {
            myFile = File.createTempFile("radar", ".gif", getBaseContext().getCacheDir());
        }
        catch (IOException e)
        {
            Log.d("WallPaperRadar", "Error: " + e);
        }

        // download radar iamge

            //myURL = new URL("http://api.wunderground.com/api/5038d6c22e55f561/radar/image.gif?centerlat=38.5&centerlon=-91&radius=100&width=280&height=280&newmaps=1");

            DownloadManager.Request myRequest = new DownloadManager.Request(Uri.parse(myURL));
            myRequest.setDescription("Downloading latest radar image");
            myRequest.setTitle("WallPaperRadar Downloading Image");
            myRequest.allowScanningByMediaScanner();
            myRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            myRequest.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "radar.gif");

            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

            manager.enqueue(myRequest);
            ((TextView) findViewById(R.id.textView)).setText("Download Request Queued");


        //((TextView)findViewById(R.id.textView)).setText(R.string.button_pressed);
        //myImage.setImageResource(R.drawable.cyclocross);
    }
}
