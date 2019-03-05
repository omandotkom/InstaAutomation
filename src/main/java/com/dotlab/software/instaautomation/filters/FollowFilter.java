/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.filters;

import com.dotlab.software.instaautomation.runnable.CustomRunnerInterface;

/**
 *
 * @author omandotkom
 */
public class FollowFilter {

    private String targetAccount;
    private String hashtag;
    private int jumlahFollow;
    private int maxFollowerofAnAccount;
    private int maxFollowingofAnAccount;
    private boolean includePrivateAccount;
    private boolean includeLike;
    private boolean detectBot;

    public boolean isDetectBot() {
        return detectBot;
    }

    public void setDetectBot(boolean detectBot) {
        this.detectBot = detectBot;
    }

    public CustomRunnerInterface getCustomRunnerInterface() {
        return customRunnerInterface;
    }

    public void setCustomRunnerInterface(CustomRunnerInterface customRunnerInterface) {
        this.customRunnerInterface = customRunnerInterface;
    }
    private CustomRunnerInterface customRunnerInterface;

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public int getJumlahFollow() {
        return jumlahFollow;
    }

    public void setJumlahFollow(int jumlahFollow) {
        this.jumlahFollow = jumlahFollow;
    }

    public int getMaxFollowerofAnAccount() {
        return maxFollowerofAnAccount;
    }

    public void setMaxFollowerofAnAccount(int maxFollowerofAnAccount) {
        this.maxFollowerofAnAccount = maxFollowerofAnAccount;
        if (this.maxFollowerofAnAccount > 0) {
            customRunnerInterface.logMessage("Maksimal follower dari akun yang akan difollow : " + this.maxFollowerofAnAccount);
        }
    }

    public int getMaxFollowingofAnAccount() {
        return maxFollowingofAnAccount;
    }

    public void setMaxFollowingofAnAccount(int maxFollowingofAnAccount) {
        this.maxFollowingofAnAccount = maxFollowingofAnAccount;
    }

    public boolean isIncludePrivateAccount() {
        return includePrivateAccount;
    }

    public void setIncludePrivateAccount(boolean includePrivateAccount) {
        this.includePrivateAccount = includePrivateAccount;
    }

    public boolean isIncludeLike() {
        return includeLike;
    }

    public void setIncludeLike(boolean includeLike) {
        this.includeLike = includeLike;
    }

}
