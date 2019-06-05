package com.android2ee.formation.cgi.juinmmxvi.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android2ee.formation.cgi.juinmmxvi.MySmsService;

public class MySmsReceiver extends BroadcastReceiver {
    private static final String TAG = "MySmsReceiver";
    public MySmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"MySmsReceiver has been called !!!");
        Intent startSmsServiceIntent= new Intent(context,MySmsService.class);
        startSmsServiceIntent.setAction(intent.getAction());
        startSmsServiceIntent.putExtras(intent);
        context.startService(startSmsServiceIntent);
    }
}
