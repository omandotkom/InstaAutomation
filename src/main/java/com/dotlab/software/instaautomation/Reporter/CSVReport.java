/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Reporter;

import com.dotlab.software.instaautomation.Scrapper.Entities.User;
import com.dotlab.software.instaautomation.UI.MainFX;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author omandotkom
 */
public abstract class CSVReport {


    public static void main(String[] args) {
        System.out.println(getCurrentTimeUsingCalendar());
    }
    
    
    private static String getCurrentTimeUsingCalendar() {

        Calendar cal = Calendar.getInstance();

        Date date = cal.getTime();

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        
        String formattedDate = sdf.format(date);

        
        return formattedDate;
    }

    public static String getJarPath() throws IOException, URISyntaxException {
        return new File(MainFX.class.getProtectionDomain().getCodeSource().getLocation()
                .toURI()).getParent();
    }

    public static class ReportType {

        public static final int LIKE_POST = 1;
        public static final int FOLLOW_BY_ACCOUNT = 2;
    }

    public String generateFileName(int reportType, String target) throws IOException, URISyntaxException {
        StringBuilder filename = new StringBuilder(getJarPath() + "/Report-");
        switch (reportType) {
            case ReportType.FOLLOW_BY_ACCOUNT:
                filename.append("FollowByAccount-@" + target + "-" + getCurrentTimeUsingCalendar());
                break;
            case ReportType.LIKE_POST:
                filename.append("LikePost-" + getCurrentTimeUsingCalendar());
                break;
            default:
                filename.append("null");
        }
        filename.append(".csv");
        return filename.toString();
    }

    public abstract void print(List<User> data, int reportType);
}
