/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Scrapper;

import org.json.JSONObject;

/**
 *
 * @author omandotkom
 */
public class URLGenerator {

    public static String generateURLforFollowers(String id, boolean firstPage, String endCursor) {
        StringBuilder builder = new StringBuilder();
        builder.append("https://www.instagram.com/graphql/query/?query_hash=56066f031e6239f35a904ac20c9f37d9&variables=");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("include_reel", true);
        jsonObject.put("fetch_mutual", false);
        jsonObject.put("first", 50);

        if (!firstPage) {
            jsonObject.put("after", endCursor);
        }
        builder.append(jsonObject.toString());
        return builder.toString();
    }

    public static String generateURLforFollowing(String id, boolean firstPage, String endCursor) {
        StringBuilder builder = new StringBuilder();
        builder.append("https://www.instagram.com/graphql/query/?query_hash=c56ee0ae1f89cdbd1c89e2bc6b8f3d18&variables=");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("include_reel", true);
        jsonObject.put("fetch_mutual", false);
        jsonObject.put("first", 50);

        if (!firstPage) {
            jsonObject.put("after", endCursor);
        }
        builder.append(jsonObject.toString());
        return builder.toString();

    }

    public static void main(String[] args) {
        System.out.println(generateProfileURL("omanlagidimana"));
    }

    public static String generateAccountA1(String accountUsername) {
        StringBuilder builder = new StringBuilder();
        builder.append("https://www.instagram.com/");
        builder.append(accountUsername);
        builder.append("/?__a=1");
        return builder.toString();
    }

    public static String generateProfileURL(String accountUsername) {
        StringBuilder builder = new StringBuilder();
        builder.append("https://www.instagram.com/");
        builder.append(accountUsername);
        builder.append("/?hl=en");
        return builder.toString();
    }
    
    public static String generateConfigURL(){
    String url = "https://raw.githubusercontent.com/omandotkom/InstaAutomationPublicConfig/master/config.json";
    return url;
    }
}
