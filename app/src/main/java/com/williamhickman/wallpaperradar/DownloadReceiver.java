package com.williamhickman.wallpaperradar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by HICKMW01 on 10/2/2015.
 */

public class DownloadReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("wsh", "onReceive()");

    }

}