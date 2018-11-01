package net.brigs.crm;


import net.brigs.crm.HttpClient.client.Client;
import net.brigs.crm.HttpClient.parser.JsonParser;
import net.brigs.crm.HttpClient.parser.User;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


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


        String rd = client.setPost(uri, email, password);


        //then
        String excepted = "{\"success\":true,\"user\":{\"id\":\"83\",";
        String actual = "";
        actual = rd.toString();
        actual = actual.split("\"hash\":")[0];

        assertEquals(" POST success\":true", excepted, actual);


    }

    @Test
    public void parser_success_true() throws IOException {

        //before
        uri = "https://brigs.top/login";
        email = "android@mail.com";
        password = "1234";
        User user;


        String rd = client.setPost(uri, email, password);

        try {
            user = new JsonParser().getUser(String.valueOf(rd));


            //then
            boolean actual = user.getSuccess();

            assertTrue("parser_success_true", actual);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void parser_success_false() throws IOException {

        //before
        uri = "https://brigs.top/login";
        email = "android@mail.com";
        password = "123";
        User user;


        String rd = client.setPost(uri, email, password);

        try {
            user = new JsonParser().getUser(String.valueOf(rd));


            //then
            boolean actual = user.getSuccess();

            assertFalse("parser_success_false", actual);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void parser_success_get_users() throws IOException {

        //before
        uri = "https://brigs.top/login";
        email = "android@mail.com";
        password = "1234";
        User user;


        String rd = client.setPost(uri, email, password);

        try {
            user = new JsonParser().getUser(String.valueOf(rd));


            //then
            String excepted = "{\"id\":\"83\",";
            String actual = user.getUser();

            actual = actual.split("\"hash\":")[0];


            assertEquals("parser_success_get_users", excepted, actual);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void POST_success_false() throws IOException {

        //before
        uri = "https://brigs.top/login";
        email = "android@mail.com";
        password = "123";

        String rd = client.setPost(uri, email, password);

        //then
        String excepted = "{\"success\":false}";
        String actual = "";
        actual = rd.toString();
        actual = actual.split("\"hash\":")[0];

        assertEquals(" POST_success_false", excepted, actual);


    }

}