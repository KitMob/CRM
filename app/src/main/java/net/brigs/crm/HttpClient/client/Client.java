package net.brigs.crm.HttpClient.client;

import android.support.annotation.NonNull;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client {


// cod get from http://automation-remarks.com/java-rest-client/

    @NonNull
    public String setPost(String uri, String email, String password) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(uri);
        List nameValuePairs = new ArrayList();//TODO optimization
        nameValuePairs.add(new BasicNameValuePair("email", email)); //you can as many name value pair as you want in the list.
        nameValuePairs.add(new BasicNameValuePair("password", password)); //you can as many name value pair as you want in the list.
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        String response = client.execute(post,new BasicResponseHandler());
        return response;
    }
}

