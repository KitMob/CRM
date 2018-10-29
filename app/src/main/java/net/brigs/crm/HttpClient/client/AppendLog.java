package net.brigs.crm.HttpClient.client;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppendLog {
    private final String LOG_TAG = "myLogs";

    public void appendLog(String text, File writeFileSD) {

        android.util.Log.d(LOG_TAG, "appendLog ");

        String pathname = writeFileSD + "/application_log.txt";
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
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
