/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Scrapper.Parser;

import com.dotlab.software.instaautomation.Scrapper.Entities.Post;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author omandotkom
 */
public abstract class Parser {
    protected JSONObject jsonObject = null;
    protected boolean has_next_page = true;
    protected String end_cursor = null;
    //protected ArrayList<String> shortcodeList = null;
    protected ArrayList<Post> postList=null;
    
    public abstract void parse();
    public abstract boolean isHas_next_page();
    public abstract void setHas_next_page();
    public abstract String getEnd_cursor();
    public abstract ArrayList<Post> getPostList();
    protected String jsonString;
}
