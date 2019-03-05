/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.UI;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omandotkom
 */
public class Shutdown {
    public static void shtdown() 

    {

        int sec;

        String operatingSystem = System.getProperty("os.name");

        System.out.println("Name of Operating System:"+operatingSystem);

        if(operatingSystem.equals("Linux"))

        {

            Runtime runtime = Runtime.getRuntime();

         

        
            try {
                Process proc = runtime.exec("shutdown -h -t 60");
            } catch (IOException ex) {
                Logger.getLogger(Shutdown.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.exit(0);

        }

        else

        {

            System.out.println("Unsupported operating system.");

        }

    }
    
}
