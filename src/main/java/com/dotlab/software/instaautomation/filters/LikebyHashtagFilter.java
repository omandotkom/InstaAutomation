/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.filters;

import com.dotlab.software.instaautomation.runnable.RunnerInterface;

/**
 *
 * @author omandotkom
 */
public class LikebyHashtagFilter {
    private String hashtag;
    private  RunnerInterface likeEvent;
    private boolean topPost;
    private int maxMedia;

    public int getMaxMedia() {
        return maxMedia;
    }

    public void setMaxMedia(int maxMedia) {
        this.maxMedia = maxMedia;
    }
    
    public boolean isTopPost() {
        return topPost;
    }

    public void setTopPost(boolean topPost) {
        this.topPost = topPost;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public RunnerInterface getLikeEvent() {
        return likeEvent;
    }

    public void setLikeEvent(RunnerInterface likeEvent) {
        this.likeEvent = likeEvent;
    }
    
}
