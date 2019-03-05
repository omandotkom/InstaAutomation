/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Scrapper;

import com.dotlab.software.instaautomation.Scrapper.Parser.HashtagParser;
import com.dotlab.software.instaautomation.Scrapper.Entities.Post;
import java.util.ArrayList;

/**
 *
 * @author omandotkom
 */
public class HashtagEngine extends Engine {

    private String hashtag;
    private boolean isIncludeTopPost = false;

    public HashtagEngine(String hash, boolean isIncludeTopPostval) {
        this.hashtag = hash;
        this.isIncludeTopPost = isIncludeTopPostval;
        postList = new ArrayList<>();
    }

    @Override
    public void run(int maxPost) {

        String url = "https://www.instagram.com/explore/tags/" + hashtag + "/?__a=1";
        String end_cursor = "";
        HashtagParser parser = new HashtagParser();
        while (parser.isHas_next_page()) {
            String response = exec(url + "&max_id=" + end_cursor);

            // HashtagParser parser = new HashtagParser(response);
            parser = new HashtagParser(response, isIncludeTopPost);
            parser.parse();

            if (parser.isHas_next_page()) {
                end_cursor = parser.getEnd_cursor();
            }

            for (Post post : parser.getPostList()) {
                if (postList.size() == maxPost) {
                    break;
                } else {
                    postList.add(post);
                }
            }
            /*
            for (int i = 0; i < parser.getShortcodeList().size(); i++) {
                Post post = new Post();
                post.setShortcode(parser.getShortcodeList().get(i));
                post.setOwner(parser.getOwnerList().get(i));
                postList.add(post);
                
                if (postList.size() == maxPost) {
                    break;
                }
            }*/
            if (postList.size() == maxPost) {
                break;
            }

        }

        System.out.println("Successfully gather " + postList.size() + " media from " + hashtag + " hashtag");
    }

}
