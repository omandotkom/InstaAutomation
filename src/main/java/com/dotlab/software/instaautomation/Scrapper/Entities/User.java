/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Scrapper.Entities;

import com.dotlab.software.instaautomation.UI.homepage.IntervalGenerator;
import com.opencsv.bean.CsvBindByName;
import java.text.Normalizer;

/**
 *
 * @author omandotkom
 */
public class User {
    @CsvBindByName
    private String username;
    
    private String password;
    
    private String name, surename;
    @CsvBindByName
    private boolean isPrivate;
    @CsvBindByName
    private String fullname;
    @CsvBindByName
    private String userID;
    @CsvBindByName
    private boolean isFollowed;
    @CsvBindByName
    private int followerCount;
    @CsvBindByName
    private int followingCount;
    @CsvBindByName
    private int postCount;
    
    //status : followed, removed, etc
    @CsvBindByName
    private String status;
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getPostCount() {
        return postCount;
    }
    
    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }
    private boolean isFollowingBack;

    public boolean isIsFollowingBack() {
        return isFollowingBack;
    }

    public void setIsFollowingBack(boolean isFollowingBack) {
        this.isFollowingBack = isFollowingBack;
    }
    

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }
    
    public boolean isIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(boolean isFollowed) {
        this.isFollowed = isFollowed;
    }

    public User(String username, boolean isPrivate, String fullname, String userID) {
        this.username = username;
        this.isPrivate = isPrivate;
        this.fullname = fullname;
        this.userID = userID;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    public User() {
    }

    public String getCompleteName() {
        return this.name + " " + surename;
    }

    public User(String name, String surename) {
        this.name = name;
        this.surename = surename;
    }

    public String generateUsername() {
//generate username dari name & surename
        String re = surename + IntervalGenerator.getRandomChar() + name + IntervalGenerator.getRandomChar();
        re = Normalizer.normalize(re, Normalizer.Form.NFD);

        return re.toLowerCase();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isBot(){
     //check wether the user is a real user
        //algorithm begins
        byte botLevel = 0;
        
        //jika Following > Follower
        if (getFollowingCount() > getFollowerCount()) {
            botLevel++;
        }
        
        //jika post kosong
        if (getPostCount() ==0) {
            botLevel++;
        }
        //todo : nanti diganti
        return botLevel > 1; //a bot;
    }

}
