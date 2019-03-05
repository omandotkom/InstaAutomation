/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.UI.homepage.configuration;

import com.dotlab.software.instaautomation.Settings.ApplicationSettings;
import com.dotlab.software.instaautomation.UI.AutoStatic;
import com.dotlab.software.instaautomation.UI.MessagePopup;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author omandotkom
 */
public class LikeModuleConfigurationController implements Initializable {

    @FXML
    private TextField txtIntervalWaktu;
    @FXML
    private TextField txtIdentifier;
    @FXML
    private Button btnSimpan;
    @FXML
    private Button btnTutup;

    /**
     * Initializes the controller class.
     */
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
        try {
            txtIntervalWaktu.setText(AutoStatic.SETTINGS.getConfig().getLikeIntervalString());
            txtIdentifier.setText(AutoStatic.SETTINGS.getConfig().getLikeButton());
        } catch (IOException ex) {
         MessagePopup.show(ex.getMessage(), MessagePopup.MessageType.Error);
        }
    }    

    @FXML
    private void btnSimpanonAction(ActionEvent event) {
        try {
            AutoStatic.SETTINGS.getConfig().setLikeButton(txtIdentifier.getText());
           // AutoStatic.SETTINGS.getConfig().setLikeButton(txtIdentifier.getText());
           if (AutoStatic.SETTINGS.saveConfiguration()){
           MessagePopup.show("Berhasil menyimpan konfigurasi.", MessagePopup.MessageType.Information);
           }
        } catch (IOException ex) {
        MessagePopup.show("Gagal menyimpan konfigurasi.\n--" + ex.getMessage(), MessagePopup.MessageType.Error);
        }
    }

    @FXML
    private void btnTutuponAction(ActionEvent event) {
    }

    @FXML
    private void txtIdentifieronAction(ActionEvent event) {
       
    }
    
}
