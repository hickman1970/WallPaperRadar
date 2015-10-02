package com.williamhickman.wallpaperradar;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
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
        Log.d("wsh", "onCreate()");
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

        Log.d("wsh", "ButtonClick()");

        String myURL = "http://api.wunderground.com/api/5038d6c22e55f561/radar/image.gif?centerlat=38.5&centerlon=-91&radius=100&width=280&height=280&newmaps=1";
        ImageView myImage;
        File myFile;
        Uri myURIImage;

        Log.d("wsh", "ButtonClick() Setting myImage");
        ((TextView)findViewById(R.id.textView)).setText("1");
        myImage = (ImageView) findViewById(R.id.imageView);

        // create temp file
        try
        {
            Log.d("wsh", "ButtonClick() creating temp file myFile");
            myFile = File.createTempFile("radar", ".gif", getBaseContext().getCacheDir());

        }
        catch (IOException e)
        {
            Log.d("WallPaperRadar", "Error: " + e);
        }

        // download radar iamge

        //myURL = new URL("http://api.wunderground.com/api/5038d6c22e55f561/radar/image.gif?centerlat=38.5&centerlon=-91&radius=100&width=280&height=280&newmaps=1");

            Log.d("wsh", "ButtonClick() creating download manager request myRequest");
            DownloadManager.Request myRequest = new DownloadManager.Request(Uri.parse(myURL));

            Log.d("wsh", "ButtonClick() setting properties of myRequest");
            myRequest.setDescription("Downloading latest radar image");
            myRequest.setTitle("WallPaperRadar Downloading Image");
            myRequest.allowScanningByMediaScanner();
            //myRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        myRequest.setVisibleInDownloadsUi(Boolean.FALSE);
            Log.d("wsh", "ButtonClick() - using DIRECTORY_DOWNLOADS");
            myRequest.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_DOWNLOADS, "radar.gif");

            Log.d("wsh", "ButtonClick() creating DownloadManger manager");
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

            Log.d("wsh", "ButtonClick() queueing DownloadManager request");
            manager.enqueue(myRequest);

            Log.d("wsh", "ButtonClick() downloading???");
            myURIImage = Uri.parse(getBaseContext().getExternalFilesDir("radar.gif").toString());
            myImage.setImageURI(myURIImage);

        // enable our receiver so that we get notified of download events
        Log.d("wsh", "ButtonClick() turn on recevier");
        PackageManager pm = getApplicationContext().getPackageManager();
        ComponentName componentName = new ComponentName("com.williamhickman.wallpaperradar", ".DownloadReceiver");
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        // disable this when we dont' need it!!!
        // call somethign like this when our download is done
        //pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        //((TextView)findViewById(R.id.textView)).setText(R.string.button_pressed);
        //myImage.setImageResource(R.drawable.cyclocross);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("wsh", "onResume()");
    }

    public void DownloadReceiver()
    {

        Log.d("wsh", "receiverDownloadComplete()");

    }

}

