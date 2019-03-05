/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.UI.homepage;

import com.dotlab.software.instaautomation.Settings.ApplicationSettings;
import com.dotlab.software.instaautomation.UI.AutoStatic;
import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author omandotkom
 */
public class IntervalGenerator {

    private static int[] intervalArrayLike;
    private static int[] intervalArrayFollow;

    public static void loadIntervals() throws IOException {
        intervalArrayLike = AutoStatic.SETTINGS.getConfig().getLikeInterval();
        intervalArrayFollow = AutoStatic.SETTINGS.getConfig().getFollowInterval();
        System.out.println("Successfully loaded intervals : ");
        System.out.println("--LIKE INTERVAL--");
        for (int i : intervalArrayLike) {
            System.out.println("  --" + i);
        }
        System.out.println("--FOLLOW INTERVAL--");

        for (int i : intervalArrayFollow) {
            System.out.println("  --" + i);

        }

    }

    public static int likeIntervalGenerator() {
        Random random = new Random();
        int randomNum = random.nextInt((intervalArrayLike.length - 1) + 1) + 0;
        //int[] intervalArrayLike = {15000, 13000, 6000, 20000, 19000};
        return intervalArrayLike[randomNum];
    }

    public static int followIntervalGenerator() {
        Random random = new Random();
        int randomNum = random.nextInt((intervalArrayFollow.length - 1) + 1) + 0;
        //int[] intervalArrayLike = {15000, 13000, 6000, 20000, 19000};
        return intervalArrayFollow[randomNum];
    }

    public static void main(String[] args) throws IOException {
        AutoStatic.SETTINGS.loadConfigFile();
        loadIntervals();
        LinkedList<Integer> followIntervals = new LinkedList<Integer>();
        for (int i = 0; i < 800; i++) {
            followIntervals.add(followIntervalGenerator());
        }
       
    }

    public static char getRandomChar() {
        String str = "0123456789abcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();
        char c = str.charAt(rnd.nextInt(str.length()));
        return c;
    }
}
