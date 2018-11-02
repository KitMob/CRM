package net.brigs.crm.HttpClient.parser;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JsonParser {

    private boolean success;
    private String user;

    public User getUser(String response) throws JSONException{
        JSONParser parser = new JSONParser();
        JSONObject in = null;
        try {
            in = (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        success = (boolean) in.get("success");

        if(success) {
            user = in.get("user").toString();
        }
        return new User(success, user);
    }
}