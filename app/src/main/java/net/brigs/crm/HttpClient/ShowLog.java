package net.brigs.crm.HttpClient;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class ShowLog extends AppCompatActivity implements Runnable {

    public static final String APPLICATION_LOG_NAME = "application_log.txt";
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

        if (!checkPermission()) {
            openActivity();
        } else {
            if (checkPermission()) {
                requestPermissionAndContinue();
            } else {
                openActivity();
            }
        }


        aut = " ";


    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            textViewLog = findViewById(R.id.log_TextView);
            textViewLog.append(logFaille);
        }
    };

    @Override
    public void run() {
        this.pathname = writeFileSD().getAbsolutePath() + APPLICATION_LOG_NAME;

        Log.d(LOG_TAG, "Start trend");
        client = new Client();
        uri = "https://brigs.top/login";
        email = "android@mail.com";
        password = "1234";

        aut += "\n post to  uri: " + uri +
                "\n email: " + email +
                "\n password: " + password + "\n";


        writeLog(pathname);

        logFaille = readFile(new File(writeFileSD(), APPLICATION_LOG_NAME)); //TODO


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
            appendLog.appendLog(aut, pathname);
            e.printStackTrace();


        }
    }

    private String readFile(File file) {
        Log.d(LOG_TAG, "readFile ");


        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            String messageError = new GetMessageError().getMessageError(e);
            Log.e(LOG_TAG, "readFile " + messageError);
            return messageError;
            //You'll need to add proper error handling here
        }
        Log.d(LOG_TAG, "readFile text: \n" + text);

        return String.valueOf(text);
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

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(LOG_TAG, "writeFileSD  PERMISSION_GRANTED");
                if (!sdPath.exists()) {
                    sdPath.mkdirs();
                }
                File sdFile = new File(sdPath, APPLICATION_LOG_NAME);
                try {
                    sdFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sdPath;
        }

        return sdPath;
    }


    private static final int PERMISSION_REQUEST_CODE = 200;

    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle(getString(R.string.permission_necessary));
                alertBuilder.setMessage(R.string.storage_permission_is_encessary_to_wrote_event);
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(ShowLog.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else
                ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            openActivity();
        }
    }

    //
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openActivity() {
        //add your further process after giving permission or to download images from remote server.
        setContentView(R.layout.activity_main_client);

        Thread thread = new Thread(this);
        thread.start();


    }


}
