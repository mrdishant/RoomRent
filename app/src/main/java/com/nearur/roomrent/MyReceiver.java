package com.nearur.roomrent;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import java.util.Calendar;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("a.b.c.d.e")) {
            AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent i=new Intent(context,MyService.class);
            int id=intent.getIntExtra("id",0);
            i.putExtra("id",id);
            String date=intent.getStringExtra("Date");
            PendingIntent pendingIntent=PendingIntent.getService(context,id,i,0);
            Calendar c=Calendar.getInstance();
            String[] split=date.split("/");
            c.set(Calendar.DAY_OF_MONTH,Integer.parseInt(split[0].trim()));
            c.set(Calendar.MONTH,(Integer.parseInt(split[1].trim())-1));
            c.set(Calendar.YEAR,Integer.parseInt(split[2].trim()));
            c.set(Calendar.HOUR_OF_DAY,10);
            c.set(Calendar.MINUTE,00);
            c.set(Calendar.SECOND,00);
            alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
            Toast.makeText(context,"Alram set for "+c.getTime()+" Date",Toast.LENGTH_LONG).show();
        }else  if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            ContentResolver resolver=context.getContentResolver();
            String[] p={"Id","DateRent"};
            Cursor c=resolver.query(Util.u,p,null,null,null);
            if(c!=null){
                while (c.moveToNext()){
                    String datee=c.getString(1);
                    if(datee!=null){
                        int id=c.getInt(0);
                        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                        Intent i=new Intent(context,MyService.class);
                        i.putExtra("id",id);
                        PendingIntent pendingIntent=PendingIntent.getService(context,id,i,0);
                        Calendar calendar=Calendar.getInstance();
                        String[] split=datee.split("/");
                        calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(split[0].trim()));
                        calendar.set(Calendar.MONTH,(Integer.parseInt(split[1].trim())-1));
                        calendar.set(Calendar.YEAR,Integer.parseInt(split[2].trim()));
                        calendar.set(Calendar.HOUR_OF_DAY,10);
                        calendar.set(Calendar.MINUTE,00);
                        calendar.set(Calendar.SECOND,00);
                        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                        Toast.makeText(context,"Alram set for "+calendar.getTime()+" Date",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
