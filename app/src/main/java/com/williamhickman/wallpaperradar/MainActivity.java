package com.williamhickman.wallpaperradar;

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

        final int BUFFER_SIZE = 4096;

        ImageView myImage;
        File myFile;
        URL myURL;

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
        try
        {
            myURL = new URL("http://api.wunderground.com/api/5038d6c22e55f561/radar/image.gif?centerlat=38.5&centerlon=-91&radius=100&width=280&height=280&newmaps=1");

            // download code came from http://www.codejava.net/java-se/networking/use-httpurlconnection-to-download-file-from-an-http-url

            HttpURLConnection httpConn = (HttpURLConnection) myURL.openConnection();
            //ResponseCode = httpConn.getResponseCode();

            // always check HTTP response code first
            //if (responseCode == HttpURLConnection.HTTP_OK)
            //{
                int contentLength = httpConn.getContentLength();

                // opens input stream from the HTTP connection
                //InputStream inputStream = httpConn.getInputStream();
                //String saveFilePath = getBaseContext().getCacheDir() + File.separator + "radar.gif";

                // opens an output stream to save into file
                //FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                /*
                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
*/
                //outputStream.close();
                //inputStream.close();
            //}

        }
        catch (IOException e)
        {
            Log.d("WallPaperRadar", "Error: " + e);
        }

        ((TextView)findViewById(R.id.textView)).setText("Set URL");

        //

        ((TextView)findViewById(R.id.textView)).setText("Created temp file");

        //((TextView)findViewById(R.id.textView)).setText(R.string.button_pressed);
        //myImage.setImageResource(R.drawable.cyclocross);
    }
}
