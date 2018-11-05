package net.brigs.crm.HttpClient.client;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class AppendLog {
    private final String LOG_TAG = "myLogs";
    private Date currentTime = Calendar.getInstance().getTime();


    public File appendLog(String text, String pathname) {

        android.util.Log.d(LOG_TAG, "appendLog ");

        android.util.Log.d(LOG_TAG, "pathname: " + pathname);

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
            buf.append("\n" + currentTime + text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logFile;

    }



}
