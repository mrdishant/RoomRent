package com.nearur.roomrent;

import android.Manifest;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RentToday extends AppCompatActivity {

    TextView textView;
    Button recieved, call, sms;
    Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_today);
        textView = (TextView) findViewById(R.id.textViewtoday);
        recieved = (Button) findViewById(R.id.buttonrecieved);
        call = (Button) findViewById(R.id.buttoncall);
        sms = (Button) findViewById(R.id.buttonsms);
        init(getIntent().getIntExtra("id", 0));
    }

    void init(final int id) {
        final ContentResolver resolver = getContentResolver();
        String[] projection = {"Id",
                "Name",
                "Mobile",
                "Address",
                "DateRent",
                "HireDate",
                "Others",
                "Aadhar",
                "Rent",
                "Image"
        };

        String wh = "Id = " + id;
        Cursor c = resolver.query(Util.u, projection, wh, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                room = new Room(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getBlob(9));
                textView.setText("Details are : \n" + room.toString());
            }
        }
        c.close();

        recieved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.cancel(id + 7567);
                ContentValues values=new ContentValues();
                String[] split=room.dateofrent.split("/");
                int month=Integer.parseInt(split[1].trim());
                int year=Integer.parseInt(split[2].trim());
                if(month!=11){
                    month++;
                }else{
                    year++;
                    month=0;
                }
                values.put(Util.DateRent,split[0].trim()+"/"+month+"/"+year);
                String w="Id = "+room.id;
                resolver.update(Util.u,values,w,null);
                Intent i=new Intent("a.b.c.d.e");
                i.putExtra("id",id);
                i.putExtra("Date",split[0].trim()+"/"+month+"/"+year);
                sendBroadcast(i);
                finish();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + room.mobile));
                if (ActivityCompat.checkSelfPermission(RentToday.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(RentToday.this,"Please Give Permissions",Toast.LENGTH_LONG).show();
                    return;
                }
                startActivity(i);
                finish();
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("sms:"+room.mobile));
                i.putExtra("sms_body","Hello "+room.name+"\nToday is your Date For Rent Rs"+room.rent+"\nDeposit it as soon as possible"+"\nRegards\nYour's Landlord");
                startActivity(i);
            }
        });
    }
}

