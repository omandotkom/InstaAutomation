/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.filters;

import com.dotlab.software.instaautomation.runnable.CustomRunnerInterface;
import com.dotlab.software.instaautomation.runnable.RunnerInterface;

/**
 *
 * @author omandotkom
 */
public class FollowbyHashtagFilter {
    private CustomRunnerInterface runnerEvents;
    private String hashtagInner;
    //private RunnerInterface runner;
    int maxFollow;
    private Boolean isIncludeLike = false;

    public CustomRunnerInterface getRunnerEvents() {
        return runnerEvents;
    }

    public void setRunnerEvents(CustomRunnerInterface runnerEvents) {
        this.runnerEvents = runnerEvents;
    }

    public String getHashtagInner() {
        return hashtagInner;
    }

    public void setHashtagInner(String hashtagInner) {
        this.hashtagInner = hashtagInner;
    }


    public int getMaxFollow() {
        return maxFollow;
    }

    public void setMaxFollow(int maxFollow) {
        this.maxFollow = maxFollow;
    }

    public Boolean getIsIncludeLike() {
        return isIncludeLike;
    }

    public void setIsIncludeLike(Boolean isIncludeLike) {
        this.isIncludeLike = isIncludeLike;
    }
    
    
}
