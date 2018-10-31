package net.brigs.crm.HttpClient.parser;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    private boolean success;
    private String user;

    public User getUser(String response) throws JSONException {
        JSONObject userJson = new JSONObject(response);
        success = userJson.getBoolean("success");
        user  = String.valueOf(userJson.names());
        return new User(success, user);
    }
}