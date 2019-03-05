/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.filters;

import com.dotlab.software.instaautomation.Scrapper.Entities.User;
import com.dotlab.software.instaautomation.runnable.CustomRunnerInterface;

/**
 *
 * @author omandotkom
 */
public class CleanerFilter {
    private User user;
    private boolean nonFollower;
    private boolean inactiveAccount;
    private boolean followingMoreThanFollower;
    private int accountWithFollowerMoreThan;
    private CustomRunnerInterface runner;
    public User getUser() {
        return user;
    }

    public CustomRunnerInterface getCustomRunnerInterface() {
        return runner;
    }

    public void setCustomRunnerInterface(CustomRunnerInterface runner) {
        this.runner = runner;
    }
    

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isNonFollower() {
        return nonFollower;
    }

    public void setNonFollower(boolean nonFollower) {
        this.nonFollower = nonFollower;
    }

    public boolean isInactiveAccount() {
        return inactiveAccount;
    }

    public void setInactiveAccount(boolean inactiveAccount) {
        this.inactiveAccount = inactiveAccount;
    }

    public boolean isFollowingMoreThanFollower() {
        return followingMoreThanFollower;
    }

    public void setFollowingMoreThanFollower(boolean followingMoreThanFollower) {
        this.followingMoreThanFollower = followingMoreThanFollower;
    }

    public int getAccountWithFollowerMoreThan() {
        return accountWithFollowerMoreThan;
    }

    public void setAccountWithFollowerMoreThan(int accountWithFollowerMoreThan) {
        this.accountWithFollowerMoreThan = accountWithFollowerMoreThan;
    }
    
    
}
