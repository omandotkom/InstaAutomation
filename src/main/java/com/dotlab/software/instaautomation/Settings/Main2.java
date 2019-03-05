/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Settings;

import java.io.IOException;
import com.dotlab.software.instaautomation.Scrapper.Entities.User;
import java.util.HashSet;

/**
 *
 * @author omandotkom
 */
public class Main2 {

    public static void main(String[] args) throws InterruptedException, IOException {
        /*   User user = new User();
     user.setUsername("omandotkom.jpg");
     user.setPassword("system3298");
     ApplicationSettings settings = new ApplicationSettings();
     settings.saveUser(user);
     System.out.println("Username is " + settings.getUser().getUsername());
     }
         */
        // new ApplicationSettings().clear();
     /*   if (new ApplicationSettings().isValidConfigFile("/home/omandotkom/Pemrograman/Java/NetBeansProjects/config.json")) {
            System.out.println("valid");
        } else {
            System.out.println("tidal valid");
        };*/
     
     ApplicationSettings settings = new ApplicationSettings();
     int[] res = settings.getConfig().getLikeInterval();
     for (int a : res){
     System.out.println(a);
     }
    }
}
