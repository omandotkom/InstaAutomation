/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation;

import com.dotlab.software.instaautomation.Automator.Automation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Cookie;

/**
 *
 * @author omandotkom
 */
public class MAINAuthTest {
    public static void main(String[] args){
    Automation automation = new Automation();
        
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        automation.setWebDiver(driver);
        if (automation.auth("quoteesyou", "system3298")){
        System.out.println("Login berhasil");
        }else {
        System.out.println("Login gagal");
        }
        
       
        
    }
}
