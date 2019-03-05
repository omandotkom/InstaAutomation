/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Automator;

import com.dotlab.software.instaautomation.UI.AutoStatic;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omandotkom
 */
public class AutomationTester {
    public static void main(String[] args){
        try {
            AutoStatic.AUTOMATION.open("http://www.facebook.com");
            Thread.sleep(3000);
            //AutoStatic.AUTOMATION.newTab();
            Thread.sleep(6000);
            AutoStatic.AUTOMATION.scrollDown();
        } catch (InterruptedException ex) {
            Logger.getLogger(AutomationTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
