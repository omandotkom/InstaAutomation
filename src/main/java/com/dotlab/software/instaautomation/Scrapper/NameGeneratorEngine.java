/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Scrapper;

import com.dotlab.software.instaautomation.Scrapper.Entities.User;
import com.dotlab.software.instaautomation.Scrapper.Parser.AccountParser;
import com.dotlab.software.instaautomation.Scrapper.Parser.ConfigParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omandotkom
 */
public class NameGeneratorEngine extends Engine {

    public static ArrayList<String> getEmailListFromFile(String path) {
        ArrayList<String> list = new ArrayList<String>();
        File file = new File(path);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            if (bufferedReader.ready()) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    list.add(line);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NameGeneratorEngine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NameGeneratorEngine.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(NameGeneratorEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    private ArrayList<User> userList;

    public NameGeneratorEngine() {
        userList = new ArrayList<User>();
    }

    @Override
    public void run(int maxPost) {
        try {
            //maxPost = maxNumber of account
            
            String url = "https://uinames.com/api/?amount=" + maxPost;
            String response = exec(url);
            System.out.println(response);
            AccountParser parser = new AccountParser();
            userList = parser.GeneratedAccount(response);
        } catch (Exception ex) {
            Logger.getLogger(NameGeneratorEngine.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<User> getUserList() {
        return userList;
    }

   

}
