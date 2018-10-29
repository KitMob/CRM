package net.brigs.crm.HttpClient;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import net.brigs.crm.HttpClient.client.AppendLog;
import net.brigs.crm.HttpClient.client.Client;
import net.brigs.crm.HttpClient.client.GetMessageError;
import net.brigs.crm.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements Runnable {

    private final String LOG_TAG = "myLogs";
    private TextView textViewLog;
    private AppendLog appendLog = new AppendLog();
    private String aut;
    private String pathname;


    private Client client;
    private String email;
    private String password;
    private String uri;
    private String logFaille;


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
            textViewLog.append(logFaille);
        }
    };

    @Override
    public void run() {
        this.pathname = writeFileSD() + "/application_log.txt";

        Log.d(LOG_TAG, "Start trend");
        client = new Client();
        uri = "https://brigs.top/login";
        email = "android@mail.com";
        password = "1234";

        aut += "\n post to  uri: " + uri +
                "\n email: " + email +
                "\n password: " + password + "\n";


        writeLog(pathname);

        appendLog.appendLog(aut, pathname);

        logFaille = readFile(new File(writeFileSD(),"application_log.txt")); //TODO


        handler.sendEmptyMessage(0);

    }

    private void writeLog(String pathname) {
        BufferedReader rd = null;
        try {
            rd = client.setPost(uri, email, password);
            String answer = "";
            while ((answer = rd.readLine()) != null) {
                aut += "\n answer" + answer;
            }
        } catch (IOException e) {
            String messageError = new GetMessageError().getMessageError(e);
            aut += messageError;
            Log.e(LOG_TAG, "appendLog " + messageError);
            appendLog.appendLog(messageError, pathname);
            e.printStackTrace();


        }
    }

    private String readFile(File file) {

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        return String.valueOf(text);
    }


    private File writeFileSD() {
        File sdPath = null;
        Log.d(LOG_TAG, "writeFileSD ");

        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            sdPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // TODO

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
