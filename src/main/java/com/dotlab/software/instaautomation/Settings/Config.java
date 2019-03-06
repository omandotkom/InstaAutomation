/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Settings;

import org.json.JSONObject;

/**
 *
 * @author omandotkom
 */
public class Config {
/*
    private String validation, version, likeInterval, commentInterval, followInterval;
    private String likeButton;

    public static final String VALIDATION_STRING = "validation";
    public static final String VERSION_STRING = "version";
    public static final String LIKE_INTERVAL_STRING = "likeInterval";
    public static final String COMMENT_INTERVAL_STRING = "commentInterval";
    public static final String DB_PATH_STRING = "dbpath";
    public static final String LIKE_BUTTON = "likeButton";
    public static final String FOLLOW_INTERVAL = "followInterval";

    public String getLikeButton() {
        return likeButton;
    }

    public void setLikeButton(String likeButton) {
        this.likeButton = likeButton;
    }

    public void setFollowInterval(String followInterval) {
        this.followInterval = followInterval;
    }
    private String dbPath;

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setLikeInterval(String likeInterval) {
        this.likeInterval = likeInterval;
    }

    public void setCommentInterval(String commentInterval) {
        this.commentInterval = commentInterval;
    }

    public String getValidation() {
        return validation;
    }

    public int[] getLikeInterval() {
        String[] intvString = this.likeInterval.split(",");
        int[] l = new int[intvString.length];
        for (int i = 0; i < intvString.length; i++) {
            l[i] = Integer.parseInt(intvString[i]) * 1000;
        }

        return l;
    }

    public int[] getFollowInterval() {
        String[] intvString = this.followInterval.split(",");
        int[] l = new int[intvString.length];
        for (int i = 0; i < intvString.length; i++) {
            l[i] = Integer.parseInt(intvString[i]) * 1000;
        }
        return l;
    }

    public String getLikeIntervalString() {
        StringBuilder builder = new StringBuilder();
        for (int num : this.getLikeInterval()) {
            num = num / 1000;
            builder.append(num).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public String getVersion() {
        return version;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Config.VERSION_STRING, version);
        jsonObject.put(Config.VALIDATION_STRING, validation);
        jsonObject.put(Config.LIKE_INTERVAL_STRING, likeInterval);
        jsonObject.put(Config.COMMENT_INTERVAL_STRING, commentInterval);
        jsonObject.put(Config.DB_PATH_STRING,dbPath);
        jsonObject.put(Config.FOLLOW_INTERVAL,followInterval);
        jsonObject.put(Config.LIKE_BUTTON,likeButton);

        return jsonObject;
    }
*/
}
