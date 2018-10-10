package net.brigs.crm.HttpClient;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import net.brigs.crm.HttpClient.client.Client;
import net.brigs.crm.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements Runnable {

    private final String LOG_TAG = "myLogs";
    private TextView textViewLog;
    private Handler h;
    private String aut;


    private Client client = new Client();
    private String email;
    private String password;
    private String uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);
        aut = " ";


        Thread thread = new Thread(this);
        thread.start();


    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            textViewLog = findViewById(R.id.log_TextView);
            textViewLog.append(aut);
        }
    };

    @Override
    public void run() {
        Log.d(LOG_TAG, "Start trend");
        uri = "https://brigs.top/login";
        email = "android@mail.com";
        password = "1234";

        //TODO log vith data
        aut += "\n post to  uri: " + uri +
                "\n email: " + email +
                "\n password: " + password;


        BufferedReader rd = null;
        try {
            rd = client.setPost(uri, email, password);
            String answer = "";
            while ((answer = rd.readLine()) != null) {
                aut += "\n answer" + answer;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        appendLog(aut);

        handler.sendEmptyMessage(0);

    }


    public void appendLog(String text) {
        Log.d(LOG_TAG, "appendLog ");

        String pathname = writeFileSD() + "/application_log.txt";
        Log.d(LOG_TAG, "pathname: " + pathname);

        File logFile = new File(pathname);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private File writeFileSD() {
        File sdPath = null;
        Log.d(LOG_TAG, "writeFileSD ");

        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            sdPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
        } else {
            // получаем путь к SD
            sdPath = Environment.getExternalStorageDirectory();
            // добавляем свой каталог к пути
            sdPath = new File(sdPath.getAbsolutePath() + "/." + getPackageName() + "/logs/");
            // создаем каталог
            if (!sdPath.exists()) {
                sdPath.mkdirs();
            }
            return sdPath;
        }
        return sdPath;
    }


}
