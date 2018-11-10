package com.nearur.roomrent;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ContentResolver resolver;
    ListView listView;
    MyAdapter arrayAdapter;
    ArrayList<Room> arrayList;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resolver=getContentResolver();

        sharedPreferences=getSharedPreferences("Rent",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        first();
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    void first(){
        if(!sharedPreferences.contains("Number")){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setView(R.layout.dialog);
            final Dialog d=builder.create();
            d.show();
            final EditText editTextNum=(EditText)d.findViewById(R.id.editTextNumber);
            final EditText rent=(EditText)d.findViewById(R.id.editTextRent);
            Button btn=(Button)d.findViewById(R.id.buttonSubmit);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num=Integer.parseInt(editTextNum.getText().toString().trim());
                    if(num>0){
                    editor.putInt("Number",num);
                    editor.putInt("Rent",Integer.parseInt(rent.getText().toString().trim()));
                    editor.commit();
                    d.dismiss();
                        insert();
                }else{
                        Toast.makeText(getApplicationContext(),"Invalid Number Of Rooms",Toast.LENGTH_LONG).show();
                    }
                }

            });
        }
    }

    void insert(){
        int number=sharedPreferences.getInt("Number",0);
        Uri x=null;
        for(int i=1;i<=number;i++){
            ContentValues values=new ContentValues();
            values.put(Util.id,i);
            x=resolver.insert(Util.u,values);
        }
        Toast.makeText(getApplicationContext(),"Rooms Inserted: "+x.getLastPathSegment(),Toast.LENGTH_LONG).show();
        init();
    }

    void init() {
        listView = (ListView) findViewById(R.id.listviewrooms);
        arrayList =new ArrayList<>();
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
        Cursor c=resolver.query(Util.u,projection,null,null,null);
        if(c!=null){
            while (c.moveToNext()){
                arrayList.add(new Room(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getBlob(9)));
            }
        }
        arrayAdapter=new MyAdapter(MainActivity.this,R.layout.roomitem,arrayList,false);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               pos=i;
                showDialog();
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent i=new Intent(getApplicationContext(),Deleted.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public Bitmap bit(byte[] a){
        if(a!=null){
            return BitmapFactory.decodeByteArray(a, 0, a.length);
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    public void showDialog(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Please Select");
        String[] items={"View/Update", "Delete"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0:
                        Intent intent=new Intent(getApplicationContext(),Single.class);
                        intent.putExtra("RoomNumber",pos+1);
                        startActivity(intent);
                        break;
                    case 1:
                        AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity.this);
                        builder1.setTitle("Delete PG"+(pos+1));
                        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String w="Id = "+(pos+1);
                                Room r=null;
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

                                Cursor c = resolver.query(Util.u, projection,w, null, null);
                                if (c != null) {
                                    while (c.moveToNext())
                                        r=new Room(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getBlob(9));
                                }
                                c.close();
                                String f=null;
                                ContentValues values=new ContentValues();
                                int v=1;
                                v=sharedPreferences.getInt("Deleted",1);
                                values.put(Util.id,v++);
                                editor.putInt("Deleted",v);
                                editor.commit();
                                values.put(Util.Name,r.name+"\nRoom Number :"+r.id);
                                values.put(Util.Mobile,r.mobile);
                                values.put(Util.Address,r.address);
                                values.put(Util.Aadhar,r.aadhar);
                                values.put(Util.Others,r.others);
                                values.put(Util.DateRent,f);
                                values.put(Util.HireDate,r.hiredate);
                                values.put(Util.Rent,r.rent);
                                values.put(Util.Image,r.img);
                                resolver.insert(Util.u1,values);
                                ContentValues values1=new ContentValues();
                                values1.put(Util.id,(pos+1));
                                values1.put(Util.Name,f);
                                values1.put(Util.Mobile,f);
                                values1.put(Util.Address,f);
                                values1.put(Util.Aadhar,f);
                                values1.put(Util.Others,f);
                                values1.put(Util.DateRent,f);
                                values1.put(Util.HireDate,f);
                                values1.put(Util.Rent,f);
                                values1.put(Util.Image,f);
                                resolver.update(Util.u,values1,w,null);
                                Toast.makeText(MainActivity.this,"Deleted PG ",Toast.LENGTH_LONG).show();
                                init();
                            }
                        });
                        builder1.setMessage("Are you sure to delete ?");
                        builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder1.create().show();
                        break;
                }
            }
        });
        builder.create().show();
    }
}
