/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.runnable;

import com.dotlab.software.instaautomation.Scrapper.Entities.User;
import java.util.ArrayList;

/**
 *
 * @author omandotkom
 */
public interface CustomRunnerInterface extends RunnerInterface{
    void onUserListAdded(ArrayList<User> userList);
    void onUserSuccessfullyFiltered(User user);
    void onUserAnalyzed(User user);
}
