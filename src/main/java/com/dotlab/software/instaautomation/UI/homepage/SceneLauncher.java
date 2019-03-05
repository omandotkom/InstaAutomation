/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.UI.homepage;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author omandotkom
 */
public class SceneLauncher {

    private URL URL;
    private String title;
    public SceneLauncher(URL fxmlPath, String title) {
        this.URL = fxmlPath;
        this.title = title;
        
    }

    public void show() throws IOException {
                 Parent root = FXMLLoader.load(URL);
                 
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setMaximized(false);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
    }
}
