package net.brigs.crm.HttpClient.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.brigs.crm.HttpClient.client.AppendLog;

public class HttpclientDbHelper extends SQLiteOpenHelper {
    String LOG_TAG = "myLogs";
    private AppendLog appendLog;


    private static final String DATABASE_NAME = "httpclient.db";


    private static final int DATABASE_VERSION = 2;

    private String DATABASE_TABLE_Login_Data = HttpclientContract.LoginData.TABLE_NAME;
    private String aut;
    private String getPackageName;

    public HttpclientDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_LOGIN_TABLE = "CREATE TABLE " + HttpclientContract.LoginData.TABLE_NAME + "("
                + HttpclientContract.LoginData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HttpclientContract.LoginData.COLUMN_SUCCESS + " INTEGER ,"
                + HttpclientContract.LoginData.COLUMN_USER_ID + " TEXT ,"
                + HttpclientContract.LoginData.COLUMN_HASH + " TEXT );";

        db.execSQL(SQL_CREATE_LOGIN_TABLE);
     //TODO add log

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        aut += "SQLite: \n Обновляемся с версии " + oldVersion + " на версию " + newVersion;
        Log.w(LOG_TAG, "SQLite: \n Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        //TODO add to Log
        appendLog = new AppendLog();

        db.execSQL("DROP TABLE IF  EXISTS " + DATABASE_TABLE_Login_Data);
        onCreate(db);

    }


}
