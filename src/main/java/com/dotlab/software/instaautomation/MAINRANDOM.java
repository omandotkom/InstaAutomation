/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation;

import java.util.Random;

class MAINRANDOM {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println("Random Number:- " + randomNumberInRange(0, 4));
            System.out.println("U+1F44C");
        }
      
    }

    public static int randomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
