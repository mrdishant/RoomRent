package com.nearur.roomrent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends ArrayAdapter<Room> {
    Context context;int resource;ArrayList<Room> objects;boolean deleted;
    public MyAdapter(Context context,int resource,ArrayList<Room> objects,boolean deleted) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
        this.deleted=deleted;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= LayoutInflater.from(context).inflate(resource,parent,false);

        ImageView img=(ImageView)v.findViewById(R.id.imageperson);
        CheckedTextView number=(CheckedTextView)v.findViewById(R.id.checkedTextViewroomnumber);
        TextView txt=(TextView)v.findViewById(R.id.textViewNamePerson);

        Room r=objects.get(position);
        if(!deleted){
            number.setText("Room Number "+r.id);
        }else{
            number.setText("Paying Guest "+r.id);
        }

        txt.setText(r.name);
        if(r.img!=null){
            img.setImageBitmap(bit(r.img));
        }else{
            img.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.splash1));
        }

        return v;
    }
    public Bitmap bit(byte[] a){
        if(a!=null){
            return BitmapFactory.decodeByteArray(a, 0, a.length);
        }
        return null;
    }
}
