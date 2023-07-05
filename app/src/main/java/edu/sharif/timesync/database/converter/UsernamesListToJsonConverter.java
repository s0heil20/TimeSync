package edu.sharif.timesync.database.converter;

import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;

public class UsernamesListToJsonConverter {

    public static JSONArray usernameListToJson(ArrayList<String> usernames){
        JSONArray usernamesJsonArray = new JSONArray();
        for (String username : usernames) {
            usernamesJsonArray.put(username);
        }
        return usernamesJsonArray;
    }

    public static ArrayList<String> JsonArrayToUsernameList(JSONArray jsonArray){
        ArrayList<String> usernames = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                usernames.add(jsonArray.getString(i));
            } catch (Exception e){
                Log.d("PARSE_BOOK_ID_JSON_ERR", "out of bound exception!");
                e.printStackTrace();
            }
        }
        return usernames;
    }
}
