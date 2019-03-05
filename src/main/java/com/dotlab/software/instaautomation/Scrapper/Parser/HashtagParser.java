/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Scrapper.Parser;

import com.dotlab.software.instaautomation.Scrapper.Entities.Post;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;

/**
 *
 * @author omandotkom
 */
public class HashtagParser extends Parser {

    private boolean includeTopPost = false;
    private ArrayList<String> ownerList = null;

    public HashtagParser(String o, boolean topPost) {
        this.jsonObject = new JSONObject(o);
        this.includeTopPost = topPost;
    }

    public HashtagParser() {
    }

    @Override
    public void parse() {

        String hashtagName = jsonObject.getJSONObject("graphql").getJSONObject("hashtag").getString("name");
        int totalMedia = jsonObject.getJSONObject("graphql").getJSONObject("hashtag").getJSONObject("edge_hashtag_to_media").getInt("count");
        System.out.println("Parsing result by hashtag " + hashtagName);
        System.out.println("Total media : " + totalMedia);

        postList = new ArrayList<>();
        //ownerList = new ArrayList<>();
        //if include top post

        if (this.includeTopPost) {

            JSONArray objTop = jsonObject.getJSONObject("graphql").getJSONObject("hashtag").getJSONObject("edge_hashtag_to_top_posts").getJSONArray("edges");
            for (int i = 0; i < objTop.length(); i++) {
                Post post = new Post();
                post.setOwner(objTop.getJSONObject(i).getJSONObject("node").getJSONObject("owner").getString("id"));
                post.setShortcode(objTop.getJSONObject(i).getJSONObject("node").getString("shortcode"));
                postList.add(post);

                System.out.println(post.getShortcode() + " (top post)");
            }
        }

        JSONArray obj = jsonObject.getJSONObject("graphql").getJSONObject("hashtag").getJSONObject("edge_hashtag_to_media").getJSONArray("edges");

        for (int i = 0; i < obj.length(); i++) {
            Post post = new Post();
            post.setOwner(obj.getJSONObject(i).getJSONObject("node").getJSONObject("owner").getString("id"));
            post.setShortcode(obj.getJSONObject(i).getJSONObject("node").getString("shortcode"));
            postList.add(post);

        }

        //check nextpage 
        has_next_page = jsonObject.getJSONObject("graphql").getJSONObject("hashtag").getJSONObject("edge_hashtag_to_media").getJSONObject("page_info").getBoolean("has_next_page");
        if (has_next_page) {
            this.end_cursor = jsonObject.getJSONObject("graphql").getJSONObject("hashtag").getJSONObject("edge_hashtag_to_media").getJSONObject("page_info").getString("end_cursor");
        }
    }

    @Override
    public boolean isHas_next_page() {
        return has_next_page;
    }

    public void setHas_next_page(boolean has_next_page) {
        this.has_next_page = has_next_page;
    }

    @Override
    public String getEnd_cursor() {
        return end_cursor;
    }

    public void setEnd_cursor(String end_cursor) {
        this.end_cursor = end_cursor;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getOwnerList() {
        return ownerList;
    }

    @Override
    public void setHas_next_page() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static String simplifyHashtag(String hashtag) {
        hashtag = hashtag.replace(" ", "");
        hashtag = hashtag.replace("#", "");
        return hashtag;
    }

    @Override
    public ArrayList<Post> getPostList() {
        return this.postList;
    }
}
