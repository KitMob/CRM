package net.brigs.crm;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static junit.framework.TestCase.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RestApiTest {
    private String email;
    private String password;


    @Test
    public void GET_success_false() throws IOException {
        //before
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("https://brigs.top/login");
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
    public void POST_success_false() throws IOException {
        //before
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("https://brigs.top/login");
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        //then
        String excepted = "{\"success\":false,\"message\":\"No hash provided!\"}";
        String actual = "";
        while ((actual = rd.readLine()) != null) {
            assertEquals("success\":false", excepted, actual);
        }


    }
}