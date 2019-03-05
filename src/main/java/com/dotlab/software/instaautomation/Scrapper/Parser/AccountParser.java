/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Scrapper.Parser;

import com.beust.jcommander.internal.Nullable;
import com.dotlab.software.instaautomation.Reporter.CSVReport;
import com.dotlab.software.instaautomation.Scrapper.Entities.Post;
import com.dotlab.software.instaautomation.Scrapper.Entities.User;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author omandotkom
 */
public class AccountParser extends Parser {

    private ArrayList<User> userList;

    public AccountParser(String o) {
        //cleans the string from html tags
        //o = Jsoup.clean(o, Whitelist.none());
        try {
            this.jsonObject = new JSONObject(o);
            userList = new ArrayList<User>();
        } catch (JSONException e) {
            System.err.println("ERROR STARTS HERE");
            System.err.println(e.getMessage());
            System.err.println("STRING PARAMETER");
            System.err.println(o);
        } catch (NullPointerException npe) {
            jsonObject = null;
        }
    }

    public AccountParser() {
    }

    public String getAccountId() {
        return jsonObject.getJSONObject("graphql").getJSONObject("user").getString("id");
    }

    public void parseFollower() {
        if (jsonObject.getString("status").equals("ok")) {
            if (!jsonObject.getJSONObject("data").isNull("user")) {
                has_next_page = jsonObject.getJSONObject("data").getJSONObject("user").getJSONObject("edge_followed_by").getJSONObject("page_info").getBoolean("has_next_page");
                if (has_next_page) {
                    //ambil end cursor
                    this.end_cursor = jsonObject.getJSONObject("data").getJSONObject("user").getJSONObject("edge_followed_by").getJSONObject("page_info").getString("end_cursor");
                }
                jsonObject = jsonObject.getJSONObject("data").getJSONObject("user").getJSONObject("edge_followed_by");
                JSONArray arr = jsonObject.getJSONArray("edges");

                for (int i = 0; i < arr.length() - 1; i++) {
                    JSONObject inner = arr.getJSONObject(i).getJSONObject("node");

                    userList.add(new User(inner.getString("username"), inner.getBoolean("is_private"), inner.getString("full_name"), inner.getString("id")));
                }
            }
        }
    }

    public void parseFollowing() {

        if (jsonObject.getString("status").equals("ok")) {
            if (!jsonObject.getJSONObject("data").isNull("user")) {
                has_next_page = jsonObject.getJSONObject("data").getJSONObject("user").getJSONObject("edge_follow").getJSONObject("page_info").getBoolean("has_next_page");
                if (has_next_page) {
                    //ambil end cursor
                    this.end_cursor = jsonObject.getJSONObject("data").getJSONObject("user").getJSONObject("edge_follow").getJSONObject("page_info").getString("end_cursor");
                }
                jsonObject = jsonObject.getJSONObject("data").getJSONObject("user").getJSONObject("edge_follow");
                JSONArray arr = jsonObject.getJSONArray("edges");

                for (int i = 0; i < arr.length() - 1; i++) {
                    JSONObject inner = arr.getJSONObject(i).getJSONObject("node");

                    userList.add(new User(inner.getString("username"), inner.getBoolean("is_private"), inner.getString("full_name"), inner.getString("id")));
                }
            }
        }
    }

    public User analyzeUser() {
      
        if (jsonObject != null) {
            try {
                jsonObject = jsonObject.getJSONObject("graphql").getJSONObject("user");
                User user = new User();
                user.setUsername(jsonObject.getString("username"));
                user.setPostCount(jsonObject.getJSONObject("edge_owner_to_timeline_media").getInt("count"));
                user.setFollowerCount(jsonObject.getJSONObject("edge_followed_by").getInt("count"));
                user.setIsPrivate(jsonObject.getBoolean("is_private"));
                user.setFollowingCount(jsonObject.getJSONObject("edge_follow").getInt("count"));
                user.setIsFollowingBack(jsonObject.getBoolean("follows_viewer"));
                user.setIsFollowed(jsonObject.getBoolean("followed_by_viewer"));
                return user;
            } catch (JSONException jsoe) {
                System.out.println("Error in analyzeUser :" + jsoe.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    public ArrayList<User> getFollowerList() {
        return this.userList;
    }

    @Override
    public void parse() {
        /*        
        JSONArray obj = null;
        if (jsonObject.has("graphql")) {

            String name = jsonObject.getJSONObject("graphql").getJSONObject("user").getString("full_name");
            System.out.println("Parsing account : " + name);
            obj = jsonObject.getJSONObject("graphql").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media").getJSONArray("edges");
            has_next_page = jsonObject.getJSONObject("graphql").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media").getJSONObject("page_info").getBoolean("has_next_page");
            if (has_next_page) {
                end_cursor = jsonObject.getJSONObject("graphql").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media").getJSONObject("page_info").getString("end_cursor");
            }
        } else {
            obj = jsonObject.getJSONObject("data").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media").getJSONArray("edges");
            has_next_page = jsonObject.getJSONObject("data").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media").getJSONObject("page_info").getBoolean("has_next_page");
            if (has_next_page) {
                end_cursor = jsonObject.getJSONObject("data").getJSONObject("user").getJSONObject("edge_owner_to_timeline_media").getJSONObject("page_info").getString("end_cursor");
            }
        }

        postList = new ArrayList<>();
        Post post = new Post();
        for (int i = 0; i < obj.length(); i++) {
            String shortcode = obj.getJSONObject(i).getJSONObject("node").getString("shortcode");
            post.setShortcode(shortcode);
            postList.add(post);
            System.out.println("Added " + shortcode + " to list (" + postList.size() + ")");
        }
         */
    }

    @Override
    public boolean isHas_next_page() {
        return has_next_page;
    }

    @Override
    public void setHas_next_page() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEnd_cursor() {
        return end_cursor;
    }

    /*public String AccountExtractor(String o) {
        Document doc = Jsoup.parse(o);
        Elements name = doc.select("a.FPmhX");
        return name.get(0).text();

    }*/
    @Override
    public ArrayList<Post> getPostList() {
        return this.postList;
    }

    public ArrayList<User> GeneratedAccount(String x) {
        AccountGeneratorParser pars = new AccountGeneratorParser(x);
        return pars.getAccounts();
    }

    class AccountGeneratorParser {

        private ArrayList<User> userList;
        private JSONArray jsonArr;

        public AccountGeneratorParser(String o) {
            //jsonArr = new JSONArray(o);
            try {
                jsonArr = new JSONArray(o);
                userList = new ArrayList<User>();
                parse();
            } catch (JSONException err) {
                System.err.println(err.getMessage());
            }

        }

        public ArrayList<User> getAccounts() {
            return userList;
        }

        private void parse() {
            jsonArr.forEach(obj -> {
                JSONObject jsn = (JSONObject) obj;
                userList.add(new User(jsn.getString("name"), jsn.getString("surname")));
            });

        }
    }
}
