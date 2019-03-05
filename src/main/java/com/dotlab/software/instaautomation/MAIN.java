/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation;

import java.util.Random;

/**
 *
 * @author omandotkom
 */
public class MAIN {

    public static void main(String[] args){
        

    }

    private static int randomGenerator() {
        Random random = new Random();
        int randomNum = random.nextInt((4 - 0) + 1) + 0;
        int[] intervalArray = {15000, 13000, 6000, 20000, 19000};

        return intervalArray[randomNum];
    }

}
