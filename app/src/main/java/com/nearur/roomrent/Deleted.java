package com.nearur.roomrent;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Deleted extends AppCompatActivity {
    ListView listView;
    ArrayList arrayList;
    MyAdapter arrayAdapter;
    ContentResolver resolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleted);
        resolver=getContentResolver();
        init();
    }
    void init() {
        listView = (ListView) findViewById(R.id.listviewdelete);
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
        Cursor c=resolver.query(Util.u1,projection,null,null,null);
        if(c!=null){
            while (c.moveToNext()){
                arrayList.add(new Room(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getBlob(9)));
            }
        }
        arrayAdapter=new MyAdapter(Deleted.this,R.layout.roomitem,arrayList,true);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                show((Room)arrayList.get(i));
            }
        });
    }
    void show(Room room){
        AlertDialog.Builder builder=new AlertDialog.Builder(Deleted.this);
        builder.setMessage("Details are : \n"+room.toString());
        builder.setTitle("Paying Guest "+room.id);
        builder.create().show();
    }
}
