/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.UI;

import com.dotlab.software.instaautomation.Automator.Automation;
import com.dotlab.software.instaautomation.Scrapper.Entities.User;
import com.dotlab.software.instaautomation.Settings.ApplicationSettings;

/**
 *
 * @author omandotkom
 */
public class AutoStatic {

    /**
     *
     */
    public static Automation AUTOMATION = new Automation();
    public static  User LOGGED_USER = new User();
    public static ApplicationSettings SETTINGS = new ApplicationSettings();
}
