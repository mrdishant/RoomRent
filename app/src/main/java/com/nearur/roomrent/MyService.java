package com.nearur.roomrent;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;

public class MyService extends Service {
    public MyService() {
    }

    NotificationManager notificationManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationManager=(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        Intent i=new Intent(getApplicationContext(),RentToday.class);
        int id=intent.getIntExtra("id",0);
        i.putExtra("id",id);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),(id+7623),i,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.drawable.splash1);
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        builder.setContentTitle("Room Rent "+id);
        builder.setContentText("Rent Date Today");
        builder.setContentIntent(pendingIntent);
        builder.setDefaults(Notification.DEFAULT_ALL);
        Notification n=builder.build();
        notificationManager.notify((id+7567),n);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
