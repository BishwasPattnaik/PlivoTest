package com.plivo.SlackApiTest;

import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SlackApiTest {
  @BeforeClass
  public void setup() {
    RestAssured.baseURI = "https://slack.com/api";
  }

  /**
   * @return All the available channel names
   */
  public List<String> listAllNameChannels() {
    Response response = RestAssured.given().auth()
        .oauth2("xoxp-597412334192-601264026326-600310747061-7a51dded6b5cb8d9d4ff301dcbc91c21")
        .when().request("GET", "channels.list");
    List<String> channelNames = Helper.listingAllChannels(response, "name");
    return channelNames;
  }

  /**
   * @return Provides all the ids
   */

  public List<String> listAllIDOfChannels() {
    Response response = RestAssured.given().auth()
        .oauth2("xoxp-597412334192-601264026326-600310747061-7a51dded6b5cb8d9d4ff301dcbc91c21")
        .when().request("GET", "channels.list");
    List<String> channelIDs = Helper.listingAllChannels(response, "id");
    return channelIDs;
  }

  /**
   * @param newName
   *          Name you want to give for a newly created channel
   * @return
   */
  @Parameters({ "newChannelName" })
  @Test
  public String createChannel(String newChannelName) {
    Response response = RestAssured.given().auth()
        .oauth2("xoxp-597412334192-601264026326-600310747061-7a51dded6b5cb8d9d4ff301dcbc91c21")
        .contentType("application/x-www-form-urlencoded").formParam("name", newChannelName).when()
        .request("POST", "channels.create");
    JsonPath jsonpath = response.jsonPath();
    JSONObject obj = new JSONObject(jsonpath.prettify());
    JSONObject channelobj = (JSONObject) obj.get("channel");
    Assert.assertTrue(listAllNameChannels().contains(newChannelName));
    return channelobj.get("name_normalized").toString();
  }

  /**
   * @param ChannelTORename
   *          The channel you want to rename
   * @param newChannelName
   *          The new name you want to provide
   */
  @Parameters({ "ChannelTORename", "newChannelName" })
  @Test
  public void renameChannel(String ChannelTORename, String newChannelName) {
    Response response = RestAssured.given().auth()
        .oauth2("xoxp-597412334192-601264026326-600310747061-7a51dded6b5cb8d9d4ff301dcbc91c21")
        .contentType("application/x-www-form-urlencoded").formParam("channel", ChannelTORename)
        .formParam("name", newChannelName).when().request("POST", "channels.create");
    List<String> allChannels = listAllNameChannels();
    Assert.assertTrue(allChannels.contains(newChannelName.toLowerCase()));
  }

  /**
   * @param channelID
   *          Channel id you want to archieve
   */
  @Parameters({ "channelIDToCheckArchieve" })
  @Test
  public void archiveChannel(String channelIDToCheckArchieve) {
    Response response = RestAssured.given().auth()
        .oauth2("xoxp-597412334192-601264026326-600310747061-7a51dded6b5cb8d9d4ff301dcbc91c21")
        .contentType("application/x-www-form-urlencoded")
        .formParam("channel", channelIDToCheckArchieve).when().request("POST", "channels.create");
  }

  /**
   * @param channelID
   *          The channel id you want to validate the archieve
   */
  @Parameters({ "channelIDToCheckArchieve" })
  @Test
  public void verifyArchieve(String channelIDToCheckArchieve) {
    Response response = RestAssured.given().auth()
        .oauth2("xoxp-597412334192-601264026326-600310747061-7a51dded6b5cb8d9d4ff301dcbc91c21")
        .contentType("application/x-www-form-urlencoded")
        .headers("channel", channelIDToCheckArchieve).when().request("GET", "channels.info");
    JsonPath jsonpath = response.jsonPath();
    JSONObject obj = new JSONObject(jsonpath.prettify());
    Assert.assertEquals(obj.get("error").toString(), "channel_not_found");
  }

  @Parameters({ "channelNameToJoin" })
  @Test
  public void joinChannel(String channelNameToJoin) {
    Response response = RestAssured.given().auth()
        .oauth2("xoxp-597412334192-601264026326-600310747061-7a51dded6b5cb8d9d4ff301dcbc91c21")
        .contentType("application/x-www-form-urlencoded").formParam("name", channelNameToJoin)
        .when().request("POST", "channels.join");
    Assert.assertEquals(response.statusCode(), 200);
  }
}
