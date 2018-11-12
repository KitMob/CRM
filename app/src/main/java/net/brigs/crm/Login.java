package net.brigs.crm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.brigs.crm.HttpClient.ShowLog;
import net.brigs.crm.HttpClient.client.AppendLog;
import net.brigs.crm.HttpClient.client.Client;
import net.brigs.crm.HttpClient.client.parser.JsonParser;
import net.brigs.crm.HttpClient.client.parser.User;
import net.brigs.crm.HttpClient.data.HttpclientContract;
import net.brigs.crm.HttpClient.data.HttpclientDbHelper;
import net.brigs.crm.modules.Dashboard;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Login extends AppCompatActivity implements View.OnClickListener, Runnable {

    public static final String APPLICATION_LOG_NAME = "application_log.txt";
    private final String LOG_TAG = "myLogs";
    private TextView emailLogin, passwordLogin;
    private Button buttonAccept;
    private AppendLog appendLog = new AppendLog();
    private String aut;
    private String pathname;


    private Client client;
    private String email;
    private String password;
    private String uri;
    private File logFaille;
    private boolean success;
    private Thread thread;
    private HttpclientDbHelper httpclientDbHelper;


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



    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String rd = msg.obj.toString();
            User user = null;
            try {
                user = new JsonParser().getLoginAnswer(String.valueOf(rd));

                success = user.getSuccess();
                if (success) {
                    String users = user.getId().toString();
                    Log.d(LOG_TAG, "true \n" + user.toString()); //TODO сохроняит и брать значение их БД
                    Toast.makeText(Login.this,"Успех: \n" + users,Toast.LENGTH_LONG).show();//TODO разобратса с текстом в 9 андроиде
                    startActivity(new Intent(Login.this, Dashboard.class));

                }
                if (!success) {
                    Toast.makeText(Login.this,"Не удача не верный логин или пароль \n",Toast.LENGTH_LONG).show();
                    buttonAccept.setEnabled(true);

                    Log.d(LOG_TAG, "false \n" + user.toString());



                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    };

    @Override
    public void run() {
        this.pathname = writeFileSD().getAbsolutePath() + APPLICATION_LOG_NAME;
        Message message;

        Log.d(LOG_TAG, "Start trend");
        client = new Client();
        uri = "https://brigs.top/login";


        aut += "\n post to  uri: " + uri;

        try {
            String rd = client.setPost(uri, email, password); //TODO сохроняит и брать значение их БД
            User user = new JsonParser().getLoginAnswer(String.valueOf(rd));
            success = user.getSuccess();


            aut += "\n answer:" + user.toString();
            logFaille = appendLog.appendLog(aut, pathname);
            //                Log.d(LOG_TAG, "true \n" + user.toString());

            message = handler.obtainMessage(0,0,0,rd);
            handler.sendMessage(message);


        } catch (Exception e) {
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

            if (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(LOG_TAG, "writeFileSD  PERMISSION_GRANTED");
                if (!sdPath.exists()) {
                    sdPath.mkdirs();
                }
                File sdFile = new File(sdPath, Login.APPLICATION_LOG_NAME);
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
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
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
                        ActivityCompat.requestPermissions(Login.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(Login.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
        }
    }

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
        setContentView(R.layout.activity_login);
        emailLogin = findViewById(R.id.login_edit_text_mail);
        passwordLogin = findViewById(R.id.login_edit_text_password);
        buttonAccept = findViewById(R.id.accept_but);


        buttonAccept.setOnClickListener(this);
        httpclientDbHelper = new HttpclientDbHelper(this);


    }


    @Override
    public void onClick(View v) {
        buttonAccept.setEnabled(false);
        email = emailLogin.getText().toString();
        password = passwordLogin.getText().toString();

        thread = new Thread(this);
        thread.start();
    }
}
