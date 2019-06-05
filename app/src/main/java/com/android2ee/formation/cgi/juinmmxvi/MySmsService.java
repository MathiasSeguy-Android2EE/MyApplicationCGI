package com.android2ee.formation.cgi.juinmmxvi;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import androidx.core.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import com.android2ee.formation.cgi.juinmmxvi.view.MainActivity;

public class MySmsService extends Service {
    private static final String TAG = "MySmsService";
    private final int UniqueNotificationId = 131274;
    private final String SMS_RECEIVE_INTENT_NAME="android.provider.Telephony.SMS_RECEIVED";

    public MySmsService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "My SMSService has been called");
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startMainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pdIntent = PendingIntent.getActivity(this, 0, startMainActivity, PendingIntent.FLAG_CANCEL_CURRENT);

        //For exemple rebuild the messages received:
        //Check you receive the right intent
        if (intent.getAction().equals(SMS_RECEIVE_INTENT_NAME)) {
            //Retrieve the bundle that handles the Messages
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                //Retrieve the data store in the SMS
                Object[] pdus = (Object[]) bundle.get("pdus");
                //Declare the associated SMS Messages
                SmsMessage[] smsMessages = new SmsMessage[pdus.length];
                //Rebuild your SMS Messages
                for (int i = 0; i < pdus.length; i++) {
                    smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                //Parse your SMS Message
                SmsMessage currentMessage;
                String body, from;
                for (int i = 0; i < smsMessages.length; i++) {
                    currentMessage = smsMessages[i];
                    body = currentMessage.getDisplayMessageBody();
                    from = currentMessage.getDisplayOriginatingAddress();
                    displayNotif(from,body);
                }
            }
        }
        stopSelf();

        return Service.START_NOT_STICKY;
    }

    PendingIntent pdIntent = null;

    private void displayNotif(String from, String body) {
        //display the notif
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(true)
                .setContentIntent(pdIntent)
                .setContentText(body)
                .setContentTitle("New SMS de :" + from)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setLights(0x99FF0000, 0, 1000)//don't work
                .setNumber(41108)
                .setOngoing(false)
                .setPriority(Integer.MIN_VALUE)
                .setProgress(100, 0, true) //don't work
                .setSmallIcon(R.drawable.ic_notif)
                .setSubText("SubText")
                .setTicker("You received a new SMS from " + from)
                .setVibrate(new long[]{100, 200, 100, 200, 100}) //need permission
                .setWhen(System.currentTimeMillis());

        NotificationManagerCompat notifManager = NotificationManagerCompat.from(this);
        notifManager.notify(UniqueNotificationId, builder.build());
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
