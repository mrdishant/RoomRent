package com.nearur.roomrent;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Single extends AppCompatActivity {

    ContentResolver resolver;
    TextView textView,title;
    ImageView imageView;
    Button update;
    Room r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        update=(Button)findViewById(R.id.buttonupdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Update.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("Room",r);
                i.putExtra("data",bundle);
                startActivityForResult(i,7623);
            }
        });
        resolver=getContentResolver();
        textView=(TextView)findViewById(R.id.textViewdetails);
        title=(TextView)findViewById(R.id.title);
        imageView=(ImageView)findViewById(R.id.imageSingle);
        init(getIntent().getIntExtra("RoomNumber",0));
    }
    void init(int id) {
        title.setText("Room Number "+id);
        String [] projection={"Id",
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

        String wh="Id = "+id;
        Cursor c = resolver.query(Util.u, projection,wh, null, null);
        if (c != null) {
            while (c.moveToNext())
                r=new Room(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getBlob(9));
                textView.setText("Details are : \n"+r.toString());
                imageView.setImageBitmap(bit(r.img));
            }
            c.close();
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==7623&&resultCode==-7623){
            init(getIntent().getIntExtra("RoomNumber",0));
        }
    }

    public Bitmap bit(byte[] a){
        if(a!=null){
            return BitmapFactory.decodeByteArray(a, 0, a.length);
        }
        return null;
    }
}
