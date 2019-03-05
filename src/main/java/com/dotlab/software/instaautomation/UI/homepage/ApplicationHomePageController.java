/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.UI.homepage;

import com.dotlab.software.instaautomation.UI.AutoStatic;
import com.dotlab.software.instaautomation.UI.MessagePopup;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author omandotkom
 */
public class ApplicationHomePageController implements Initializable {

    @FXML
    private MenuItem menuPengaturan;
    @FXML
    private RadioMenuItem rbThreadShutdown;

    @FXML
    private Menu mnKontrolTugas;
    @FXML
    private Button btnLikeModule;
    @FXML
    private Button btnFollowModule;
    @FXML
    private ImageView imgLogo;
    @FXML
    private Button btnMassiveAccount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //mengantur logo
        Image image = new Image(getClass().getResourceAsStream("/img/applogo.png"));
        if (image != null) {
            imgLogo.setImage(image);
        }
        System.out.println("COOKIENYA " + AutoStatic.AUTOMATION.getDriver().manage().getCookies().toString());

        menuPengaturan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {

                    SceneLauncher sceneLauncher = new SceneLauncher(getClass().getResource("/fxml/ApplicationSettings.fxml"),"Pengaturan");
                    
                    sceneLauncher.show();

                } catch (IOException ex) {
                    Logger.getLogger(ApplicationHomePageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    @FXML
    private void btnLikeModuleAction(ActionEvent event) {
        try {
            SceneLauncher sceneLauncher = new SceneLauncher(getClass().getResource("/fxml/LikeModule.fxml"), "Like Module");
            sceneLauncher.show();

        } catch (IOException ex) {
            MessagePopup.show(ex.getMessage(), MessagePopup.MessageType.Error);
        }
    }

    @FXML
    private void btnFollowModuleAction(ActionEvent event) {
        try {
            SceneLauncher sceneLauncher = new SceneLauncher(getClass().getResource("/fxml/FollowModule.fxml"),"Follow Module");
            sceneLauncher.show();

        } catch (IOException ex) {
            MessagePopup.show(ex.getMessage(), MessagePopup.MessageType.Error);
        }

    }

    @FXML
    private void btnMassiveAccountAction(ActionEvent event) {
    }

}
