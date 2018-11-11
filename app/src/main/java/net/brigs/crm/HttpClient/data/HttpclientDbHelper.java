package net.brigs.crm.HttpClient.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HttpclientDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "httpclient.db";


    private static final int DATABASE_VERSION = 1;

    public HttpclientDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_LOGIN_TABLE = "CREATE TABLE " + HttpclientContract.LoginData.TABLE_NAME + " ("
                + HttpclientContract.LoginData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HttpclientContract.LoginData.COLUMN_SUCCESS + " INTEGER , "
                + HttpclientContract.LoginData.COLUMN_MESSAGE + " TEXT , "
                + HttpclientContract.LoginData.COLUMN_ID + " TEXT , "
                + HttpclientContract.LoginData.COLUMN_HASH + " TEXT );";

             db.execSQL(SQL_CREATE_LOGIN_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
