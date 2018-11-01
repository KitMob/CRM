package net.brigs.crm;

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

import net.brigs.crm.HttpClient.ShowLog;
import net.brigs.crm.HttpClient.client.AppendLog;
import net.brigs.crm.HttpClient.client.Client;
import net.brigs.crm.HttpClient.parser.JsonParser;
import net.brigs.crm.HttpClient.parser.User;

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


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

        }
    };

    @Override
    public void run() {
        this.pathname = writeFileSD(APPLICATION_LOG_NAME).getAbsolutePath() + APPLICATION_LOG_NAME;

        Log.d(LOG_TAG, "Start trend");
        client = new Client();
        uri = "https://brigs.top/login";


        aut += "\n post to  uri: " + uri +
                "\n email: " + email +
                "\n password: " + password + "\n";

        try {
            String rd = client.setPost(uri, email, password);
            User user = new JsonParser().getUser(String.valueOf(rd));
            success = user.getSuccess();

            aut += "\n answer:" + user.toString();
            logFaille = appendLog.appendLog(aut, pathname);

            if (success) {
                String users = user.getUser();
                Log.d(LOG_TAG, "true \n" + user.toString()); //TODO сохроняит и брать значение их БД
                //  Toast.makeText(this,"Успех: \n" + users,Toast.LENGTH_LONG);
            }
            if (!success) {
                //   Toast.makeText(this,"Не удача не верный логин или пароль \n",Toast.LENGTH_LONG);
                Log.d(LOG_TAG, "false \n" + user.toString());

                startActivity(new Intent(Login.this, ShowLog.class));


            }


        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }


        handler.sendEmptyMessage(0);

    }


    private File writeFileSD(String applicationLogName) {
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
                File sdFile = new File(sdPath, applicationLogName);
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


    }


    @Override
    public void onClick(View v) {
        email = emailLogin.getText().toString();
        password = passwordLogin.getText().toString();

        thread = new Thread(this);
        thread.start();
    }
}
