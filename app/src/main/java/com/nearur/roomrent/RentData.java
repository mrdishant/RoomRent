package com.nearur.roomrent;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class RentData extends ContentProvider {

    renthelper help;
    SQLiteDatabase sqLiteDatabase;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long s=sqLiteDatabase.insert(uri.getLastPathSegment(),null,values);
        return Uri.parse("/dummy/"+s);
    }

    @Override
    public boolean onCreate() {
        help=new renthelper(getContext(),Util.Dbname,null,Util.dbversion);
        sqLiteDatabase=help.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return sqLiteDatabase.query(uri.getLastPathSegment(),projection,selection,selectionArgs,null,null,sortOrder);

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
     return sqLiteDatabase.update(uri.getLastPathSegment(),values,selection,selectionArgs);
    }

    class renthelper extends SQLiteOpenHelper{

        public renthelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(Util.query);
            sqLiteDatabase.execSQL(Util.query1);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }
    }
}
