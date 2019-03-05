/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Scrapper.Parser;

import com.dotlab.software.instaautomation.Scrapper.Entities.Post;
import com.dotlab.software.instaautomation.Settings.Config;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONException;

/**
 *
 * @author omandotkom
 */
public class ConfigParser extends Parser {

    private Config config = new Config();

    public Config getConfig() {
        return config;
    }
    @Override
    public void parse() {
        this.jsonObject = new JSONObject(this.jsonString);
        config.setVersion(jsonObject.getString(Config.VERSION_STRING));
        config.setValidation(jsonObject.getString(Config.VALIDATION_STRING));
        config.setLikeInterval(jsonObject.getString(Config.LIKE_INTERVAL_STRING));
        config.setCommentInterval(jsonObject.getString(Config.COMMENT_INTERVAL_STRING));
        config.setDbPath(jsonObject.getString(Config.DB_PATH_STRING));
        config.setFollowInterval(jsonObject.getString(Config.FOLLOW_INTERVAL));
        config.setLikeButton(jsonObject.getString(Config.LIKE_BUTTON));
    }

    public JSONObject toJSONObject() {
        return jsonObject;
    }

    public ConfigParser(String js) {
        this.jsonString = js;
        this.parse();
    }

    public boolean isValidJSON() {
        boolean result = false;
        try {
            this.jsonObject = new JSONObject(this.jsonString);
            result = true;
        } catch (JSONException json) {
            System.out.println("JSON is not valid, " + json.getMessage());
            result = false;
        }catch(Exception e ){
            System.out.println("JSON is not valid, " + e.getMessage());
            result = false;
        
        }
        return result;
    }

    @Override
    public boolean isHas_next_page() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setHas_next_page() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEnd_cursor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Post> getPostList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
