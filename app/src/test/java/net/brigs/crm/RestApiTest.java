package net.brigs.crm;

import net.brigs.crm.HttpClient.Client;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;


public class RestApiTest {
    private Client client = new Client();

    private String email;
    private String password;
    private String uri;


// cod get from http://automation-remarks.com/java-rest-client/


    @Test
    public void GET_success_false() throws IOException {
        //before
        uri = "https://brigs.top/login";

        BufferedReader rd = client.get(uri);

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

        BufferedReader rd = client.setPost(uri, email, password);

        //then
        String excepted = "{\"success\":true,\"user\":{\"id\":\"83\",\"hash\":\"HmeC2iJK7r6mbh1K\"}}";
        String actual = "";
        actual = rd.readLine();
        assertEquals(" POST success\":true", excepted, actual);


    }


}