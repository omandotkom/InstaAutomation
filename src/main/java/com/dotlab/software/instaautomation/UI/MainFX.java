/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.UI;

import com.dotlab.software.instaautomation.Settings.ApplicationSettings;
import com.dotlab.software.instaautomation.UI.homepage.IntervalGenerator;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author omandotkom
 */
public class MainFX extends Application {

    
    @Override
    public void start(Stage primaryStage) throws IOException, URISyntaxException {
        System.out.println("Starting... ");
        AutoStatic.SETTINGS.loadConfigFile();
//ApplicationSettings settings = new ApplicationSettings();

        //load like interval
        IntervalGenerator.loadIntervals();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginPage.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/LikeModuleConfiguration.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("InstaAutomation V.1.Okt BETA Release");
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
