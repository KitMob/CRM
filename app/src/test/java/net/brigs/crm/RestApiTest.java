package net.brigs.crm;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class RestApiTest {
    private String email;
    private String password;
    private String uri;

// cod get from http://automation-remarks.com/java-rest-client/


    @Test
    public void GET_success_false() throws IOException {
        //before
        uri = "https://brigs.top/login";

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        //then
        String excepted = "{\"success\":false,\"message\":\"No hash provided!\"}";
        String actual = "";
        while ((actual = rd.readLine()) != null) {
            assertEquals(" GET success\":false", excepted, actual);
        }


    }


    @Test
    public void POST_success_true() throws IOException {

        //before
        uri = "https://brigs.top/login";

        email = "android@mail.com";
        password = "1234";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(uri);
        List nameValuePairs = new ArrayList();
        nameValuePairs.add(new BasicNameValuePair("email", email)); //you can as many name value pair as you want in the list.
        nameValuePairs.add(new BasicNameValuePair("password", password)); //you can as many name value pair as you want in the list.
        System.out.println(nameValuePairs.toString());
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        //then
        String excepted = "{\"success\":true,\"user\":{\"id\":\"83\",\"hash\":\"HmeC2iJK7r6mbh1K\"}}";
        String actual = "";
        actual = rd.readLine();
            assertEquals(" POST success\":true", excepted, actual);



    }
}