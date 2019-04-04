package com.plivo.SlackApiTest;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Helper {
  /**
   * @param response
   *          Provide a restassured response
   * @param key
   *          The json key you want to extract e.g ("id"/"name")
   * @return List of elements you searched for
   */
  public static List<String> listingAllChannels(Response response, String key) {
    List<String> channelNames = new ArrayList<String>();
    JsonPath jsonpath = response.jsonPath();
    JSONObject obj = new JSONObject(jsonpath.prettify());
    JSONArray arr = obj.getJSONArray("channels");
    for (int i = 0; i < arr.length(); i++) {
      String channelnames = arr.getJSONObject(i).get(key).toString();
      channelNames.add(channelnames);
    }
    return channelNames;
  }
}
