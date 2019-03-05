/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Scrapper;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import com.dotlab.software.instaautomation.Automator.Automation;
import com.dotlab.software.instaautomation.UI.AutoStatic;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 *
 * @author omandotkom
 */
public class RequestMaker {

   
    /*public String run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        System.out.println("Making request to " + url);
        Request request = new Request.Builder()
                .url(url)
                
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }*/
 public String run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        System.out.println("Making request to " + url);
        Request request = new Request.Builder()
                .url(url)
                 .addHeader("Connection","keep-alive")
                .addHeader("Cookie", "sessionid=" + AutoStatic.AUTOMATION.getSessionID())
               
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println("Request got feedback--");
            String res = response.body().string();
            System.out.println(res);
            return res;
        }
    }
    public String runWithResponse(Automation auto, String url) {
        return auto.getResponse(url);
    }
    
    
    public static void main(String[] args) throws IOException{
    }
}
