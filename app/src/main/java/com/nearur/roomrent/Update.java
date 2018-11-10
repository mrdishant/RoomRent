package com.nearur.roomrent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Update extends AppCompatActivity {

    EditText name,mobile,address,aadhar,others,date,rent;
    ImageView imageView;
    Button submit;
    int id;
    Calendar c;
    Bitmap bitmap=null;
    boolean click=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        init();
        c=Calendar.getInstance();
        submit=(Button)findViewById(R.id.buttonsubmit1);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().length()>3&&address.getText().toString().length()>1&&rent.getText().toString().length()>1&&others.getText().toString().length()>1&&date.getText().toString().length()>1){
                   if(click){
                       if(mobile.getText().toString().length()!=10){
                           Toast.makeText(Update.this,"Please Enter Valid Mobile Number",Toast.LENGTH_LONG).show();
                       }

                       else if(aadhar.getText().toString().length()!=12){
                           Toast.makeText(Update.this,"Please Enter Valid Aadhar Number",Toast.LENGTH_LONG).show();
                       }
                       else{
                           insert();
                           setResult(-7623);
                           finish();
                       }

                   }
                   else {
                       Toast.makeText(getApplicationContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                   }

                }else{
                    Toast.makeText(getApplicationContext(),"Please Fill all Details",Toast.LENGTH_LONG).show();
                }

            }
        });

        final DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                click=true;
                date.setText(i2+"/"+(i1+1)+"/"+i);
                c.set(Calendar.YEAR,i);
                c.set(Calendar.MONTH,i1);
                c.set(Calendar.DAY_OF_MONTH,i2);
            }
        };
        date.setFocusable(false);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int m=calendar.get(Calendar.MONTH);
                int y=calendar.get(Calendar.YEAR);
                DatePickerDialog dialog=new DatePickerDialog(Update.this,d,y,m,day);
                dialog.show();
            }
        });
    }
    void init(){
        name=(EditText)findViewById(R.id.editTextname);
        mobile=(EditText)findViewById(R.id.editTextmobile);
        aadhar=(EditText)findViewById(R.id.editTextaadhar);
        address=(EditText)findViewById(R.id.editTextaddress);
        others=(EditText)findViewById(R.id.editTextmembersothers);
        date=(EditText)findViewById(R.id.editTextdaterent);
        rent=(EditText)findViewById(R.id.editTextrent);
        imageView=(ImageView)findViewById(R.id.imageUpdate);

        Bundle bundle=getIntent().getBundleExtra("data");
        Room r=(Room)bundle.getSerializable("Room");
        id=r.id;
        name.setText(r.name);
        mobile.setText(r.mobile);
        aadhar.setText(r.aadhar);
        address.setText(r.address);
        if(r.hiredate!=null){
            date.setText(r.hiredate);
        }
        others.setText(r.others);
        if(r.rent!=null){
            rent.setText(r.rent);
        }else{
            rent.setText(getSharedPreferences("Rent",MODE_PRIVATE).getInt("Rent",0)+"");
        }
        if(r.img!=null){
            bitmap=bit(r.img);
            imageView.setImageBitmap(bitmap);
        }else{
            imageView.setBackgroundResource(R.drawable.ic_menu_camera);
        }

        imageView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String[] items={"Capture","Gallery"};
                final AlertDialog.Builder builder=new AlertDialog.Builder(Update.this);
                builder.setTitle("Please Select");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, 7567);
                                break;
                            case 1:
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"),7623);
                                break;
                        }
                    }
                });
                builder.create().show();


            }
        });
    }

    void insert(){
        ContentResolver resolver=getContentResolver();
        ContentValues values=new ContentValues();
        values.put(Util.id,id);
        values.put(Util.Name,name.getText().toString());
        values.put(Util.Mobile,mobile.getText().toString());
        values.put(Util.Address,address.getText().toString());
        values.put(Util.Aadhar,aadhar.getText().toString());
        values.put(Util.Others,others.getText().toString());
        int x=c.get(Calendar.MONTH);
        if(x==11){
            c.set(Calendar.MONTH,0);
            c.set(Calendar.YEAR,c.get(Calendar.YEAR)+1);
        }
        String newdate=c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+2)+"/"+c.get(Calendar.YEAR);
        values.put(Util.DateRent,newdate);
        values.put(Util.HireDate,date.getText().toString());
        values.put(Util.Rent,rent.getText().toString());
        values.put(Util.Image,getBytes(bitmap));
        String w="Id = "+id;
        resolver.update(Util.u,values,w,null);
        Intent i=new Intent("a.b.c.d.e");
        i.putExtra("id",id);
        i.putExtra("Date",newdate);
        sendBroadcast(i);
        Toast.makeText(getApplicationContext(),"Room Number : "+id+" Updated",Toast.LENGTH_LONG).show();
    }

    public static byte[] getBytes(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }
        return null;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 7623)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    try
                    {
                        imageView.setBackgroundResource(0);
                        bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        imageView.setImageBitmap(bitmap);

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == 7567 && resultCode == Activity.RESULT_OK) {
            imageView.setBackgroundResource(0);
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }
    public Bitmap bit(byte[] a){
        if(a!=null){
            return BitmapFactory.decodeByteArray(a, 0, a.length);
        }
        return null;
    }
}
