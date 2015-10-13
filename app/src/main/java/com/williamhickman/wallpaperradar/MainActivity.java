package com.williamhickman.wallpaperradar;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /*
        Variables
     */
    String class_String_URL = "http://api.wunderground.com/api/5038d6c22e55f561/radar/image.gif?centerlat=38.5&centerlon=-91&radius=150&width=280&height=280&newmaps=1";
    String class_String_Radar = "radar.gif";
    Uri class_Uri_RadarImage;
    String class_String_Radar_Full_Path;
    Long class_Long_DownloadID;

    private BroadcastReceiver class_BroadcastReceiver_ProcessNotification = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            parseDownload(context, intent);
        }
    };

    /////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("wsh", "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        class_String_Radar_Full_Path = getExternalFilesDir(null) + "/" + class_String_Radar;
        class_Uri_RadarImage = Uri.parse("file://" + class_String_Radar_Full_Path);
        Log.d("wsh", "onCreate() using " + class_String_Radar_Full_Path);
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
    /*
        Testing...testing...1...2...3
     */
    public void ButtonClick(View view)
    {
        Log.d("wsh", "ButtonClick()");


        //ImageView myImage;
        //File myFile;
        //Uri myURIImage;

        //Log.d("wsh", "ButtonClick() Setting myImage");
        //((TextView)findViewById(R.id.textView)).setText("1");
        //myImage = (ImageView) findViewById(R.id.imageView);

        // create temp file
        /*
        try
        {
            Log.d("wsh", "ButtonClick() creating temp file myFile");
            myFile = File.createTempFile("radar", ".gif", getBaseContext().getCacheDir());

        }
        catch (IOException e)
        {
            Log.d("WallPaperRadar", "Error: " + e);
        }
        */

        // download radar iamge

        //myURL = new URL("http://api.wunderground.com/api/5038d6c22e55f561/radar/image.gif?centerlat=38.5&centerlon=-91&radius=200&width=280&height=280&newmaps=1");

        DownloadManager.Request myRequest = new DownloadManager.Request(Uri.parse(class_String_URL));

        myRequest.setDescription("Downloading latest radar image");
        myRequest.setTitle("WallPaperRadar Downloading Image");
        myRequest.allowScanningByMediaScanner();

        // How do we hide the download?
        //myRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        myRequest.setVisibleInDownloadsUi(Boolean.FALSE);

        Log.d("wsh", "ButtonClick() saving file as " + "radar.gif");
        Log.d("wsh", "ButtonClick()                 with external files directory being " + getExternalFilesDir(null));  ///storage/090C-1F1A/Android/data/com.williamhickman.wallpaperradar/files
        if (this.isFileExists(class_String_Radar_Full_Path))
        {
            this.myDeleteFile(class_String_Radar_Full_Path);
        }
        //myRequest.setDestinationInExternalFilesDir(MainActivity.this, "", "radar.gif");
        myRequest.setDestinationUri(class_Uri_RadarImage);
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        class_Long_DownloadID = manager.enqueue(myRequest);

        this.registerBroadcastReceiver();

            //myURIImage = Uri.parse(getBaseContext().getExternalFilesDir("radar.gif").toString());
            //myImage.setImageURI(myURIImage);

        /*
        // enable our receiver so that we get notified of download events
        Log.d("wsh", "ButtonClick() enable recevier to catch download broadcasts");
        Log.d("wsh", "ButtonClick() set PackageManager pm");
        PackageManager pm = getApplicationContext().getPackageManager();
        Log.d("wsh", "ButtonClick() set ComponentName componentName");
        ComponentName componentName = new ComponentName(getPackageName(), ".receiverDownload");
        Log.d("wsh", "ButtonClick() " + componentName.toString());
        Log.d("wsh", "ButtonClick() " + componentName.getPackageName());
        Log.d("wsh", "ButtonClick() " + componentName.getClassName());

        Log.d("wsh", "ButtonClick() pm.setComponentEnabledSetting()");
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
*/


        // disable this when we dont' need it!!!
        // call somethign like this when our download is done
        // also, according to Google docs, call it in onPause()
        //pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        //((TextView)findViewById(R.id.textView)).setText(R.string.button_pressed);
        //myImage.setImageResource(R.drawable.cyclocross);

        Log.d("wsh", "ButtonClick() done");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("wsh", "onPause()");
        this.unregisterBroadcastReceiver();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("wsh", "onResume()");
    }

    public void registerBroadcastReceiver()
    {
        Log.d("wsh", "registerBroadcastReceiver()");
        this.registerReceiver(class_BroadcastReceiver_ProcessNotification, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void unregisterBroadcastReceiver()
    {
        Log.d("wsh", "unregisterBroadcastReceiver()");

        try
        {
            this.unregisterReceiver(class_BroadcastReceiver_ProcessNotification);
        }
        catch(IllegalArgumentException e)
        {
            // can we check here for something so that we know we are here because
            // there was no receiver to unregister?  Otherwise we just assume that's the
            // only reason we would be here.
            Log.d("wsh", "unregisterBroadcastReceiver() with exception " + e.toString());
        }
    }


    public void parseDownload(Context context, Intent intent)
    {
        Log.d("wsh", "parseDownload()");

        Long method_Long_DownloadID_From_Intent = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);


        Log.d("wsh", "parseDownload() got id " + method_Long_DownloadID_From_Intent);
        Log.d("wsh", "parseDownload() looking for " + class_Long_DownloadID);
        if (method_Long_DownloadID_From_Intent == class_Long_DownloadID)
        {
            this.unregisterBroadcastReceiver();
            this.setWallpaper();
        }

    }

    public void setWallpaper()
    {
        WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        Uri method_URI_RadarFile = Uri.parse("file://" + class_String_Radar_Full_Path);

        ImageView method_ImageView_Picture = (ImageView) findViewById(R.id.imageView);
        //method_ImageView_Picture.setImageURI(method_URI_RadarFile);


        //myWallpaperManager.setResource();
        //myWallpaperManager.
        //myWallpaperManager.getCropAndSetWallpaperIntent(method_URI_RadarFile);
        //myWallpaperManager.set

        File image = new File(class_String_Radar_Full_Path);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
        bitmap = Bitmap.createBitmap(bitmap);

        // try to draw something on the bitmap

        String method_String_test = DateFormat.getDateTimeInstance().format(new Date());
        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.WHITE);
        // text size in pixels
        paint.setTextSize((int) (14));
        // text shadow
        //paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(method_String_test, 0, method_String_test.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width())/2;
        int y = (bitmap.getHeight() + bounds.height())/2;

        canvas.drawText(method_String_test, x, y, paint);

        try
        {
            myWallpaperManager.setBitmap(bitmap);
        }
        catch (IOException e)
        {
            Log.d("wsh", "set wallpaper failed");
        }

        // set the image in the app too so we can see it for testing
        method_ImageView_Picture.setImageBitmap(bitmap);
    }

    private boolean isFileExists(String filename)
    {
        Log.d("wsh", "isFileExists()");
        File myFile = new File(filename);
        Log.d("wsh", "isFileExists() " + filename + "=>" + myFile.exists());
        return myFile.exists();

    }

    private boolean myDeleteFile(String filename)
    {
        Log.d("wsh", "myDeleteFile()");
        Log.d("wsh", "myDeleteFile() deleting " + filename);
        File folder1 = new File(filename);
        return folder1.delete();

    }
}

