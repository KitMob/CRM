package net.brigs.crm.HttpClient.client.parser;


import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonParser {

    private boolean success;
    private JSONObject user;
    private String id;
    private String hash;

    public User getLoginAnswer(String response) throws JSONException {
        JSONParser parser = new JSONParser();
        JSONObject in = null;
        try {
            in = (JSONObject) parser.parse(response);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }


        success = (boolean) in.get("success");

        if (success) {
            user = (JSONObject) in.get("user");
            id = user.get("id").toString();
            hash = user.get("hash").toString();

        }

        return new User(success, id, hash);
    }
}