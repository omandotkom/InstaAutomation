/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Scrapper;

import com.dotlab.software.instaautomation.Scrapper.Entities.Post;
import java.util.ArrayList;
import com.dotlab.software.instaautomation.Automator.Automation;
import com.dotlab.software.instaautomation.UI.AutoStatic;
import com.dotlab.software.instaautomation.runnable.LoggerInterface;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author omandotkom
 */
public abstract class Engine {

    //protected ArrayList<Post> postList = new ArrayList<>();
    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        } catch (Exception e2) {
            return false;
        }

        return true;
    }
    protected ArrayList<Post> postList;

    protected String exec(String url, Automation automation) throws Exception {
        try {
            if (automation != null) {
                return new RequestMaker().runWithResponse(automation, url);
            } else {
                return new RequestMaker().run(url);
            }

        } catch (java.io.IOException IOE) {
            System.err.println(IOE.getMessage());
        }
        return null;
    }

    protected String exec(String url) throws Exception {
        try {
            return new RequestMaker().run(url);
        } catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected String newexec(String url) throws Exception {
        try {
                return new RequestMaker().run(url);
            

        } catch (java.io.IOException IOE) {
            System.err.println(IOE.getMessage());
        }
        return null;
    }

    public ArrayList<Post> getPostList() {
        return postList;
    }

    public abstract void run(int maxPost);

    //for all post
    public void run() {
    }
;
}
