package net.brigs.crm.HttpClient;

import android.content.Intent;
import android.os.Bundle;
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


public class ShowLog extends AppCompatActivity {

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

        setContentView(R.layout.activity_show_log);

        textViewLog = findViewById(R.id.log_TextView);
        Intent intent = getIntent();

        String file= intent.getStringExtra("File");
        if (file != null) {
            aut = readFile(file);
            textViewLog.append(aut);
        }



    }

    //TODO допелить


    private String readFile(String file) {
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


}
