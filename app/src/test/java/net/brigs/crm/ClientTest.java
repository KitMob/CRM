package net.brigs.crm;

import net.brigs.crm.HttpClient.client.Client;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;


public class ClientTest {
    private Client client = new Client();

    private String email;
    private String password;
    private String uri;


// cod get from http://automation-remarks.com/java-rest-client/


    @Test
    public void POST_success_true() throws IOException {

        //before
        uri = "https://brigs.top/login";
        email = "android@mail.com";
        password = "1234";

        BufferedReader rd = client.setPost(uri, email, password);

        //then
        String excepted = "{\"success\":true,\"user\":{\"id\":\"83\",";
        String actual = "";
        actual = rd.readLine();
        actual = actual.split("\"hash\":")[0];

        assertEquals(" POST success\":true", excepted, actual);


    }

  @Test
    public void POST_success_false() throws IOException {

        //before
        uri = "https://brigs.top/login";
        email = "android@mail.com";
        password = "123";

        BufferedReader rd = client.setPost(uri, email, password);

        //then
        String excepted = "{\"success\":false}";
        String actual = "";
        actual = rd.readLine();
        actual = actual.split("\"hash\":")[0];

        assertEquals(" POST_success_false", excepted, actual);


    }

}