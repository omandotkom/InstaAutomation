/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omandotkom
 */
//NOTE : THIS CLASS IS Experimental !
public class InstaBotSettings {

    private static Properties prop;
    private static InputStream inputStream = InstaBotSettings.class.getResourceAsStream("/config/config.properties");

    public InstaBotSettings() throws IOException {

    }

    public static void load() throws IOException {
        prop = new Properties();
        // FileReader reader = new FileReader(configFile);

        if (inputStream != null) {
            prop.load(inputStream);
            inputStream.close();

        }

    }

    public static void save(String key, String value) throws IOException {
        File configFile = new File(InstaBotSettings.class.getResource("/config/config.properties").getPath());
        FileWriter writer = new FileWriter(configFile);
        prop.setProperty(key, value);
        prop.store(writer, value);
    }

    public static void main(String[] args) {
        try {
            InstaBotSettings.load();
            InstaBotSettings.save("appver", "1 beta");
        } catch (IOException ex) {
            Logger.getLogger(InstaBotSettings.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
