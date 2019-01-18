package net.brigs.crm.HttpClient.client;

import android.support.annotation.NonNull;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Client {


    @NonNull
    public String setPost(String uri, String email, String password) throws Exception {

        OkHttpClient client = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(uri)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();


    }
}

