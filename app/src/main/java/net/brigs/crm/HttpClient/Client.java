package net.brigs.crm.HttpClient;

import android.support.annotation.NonNull;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Client {

    @NonNull
    public BufferedReader get(String uri) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        return new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    }

    @NonNull
    public BufferedReader setPost(String uri, String email, String password) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(uri);
        List nameValuePairs = new ArrayList();
        nameValuePairs.add(new BasicNameValuePair("email", email)); //you can as many name value pair as you want in the list.
        nameValuePairs.add(new BasicNameValuePair("password", password)); //you can as many name value pair as you want in the list.
        System.out.println(nameValuePairs.toString());
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = client.execute(post);
        return new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    }
}

