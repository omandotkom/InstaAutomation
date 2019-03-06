/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.UI.homepage;

import com.dotlab.software.instaautomation.Settings.ApplicationSettings;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author omandotkom
 */
public class ApplicationnSettingsController implements Initializable {

    @FXML
    private TextField driverPath;
    @FXML
    private Button btnBrowse;
    @FXML
    private Button btnClose;
    @FXML
    private TextField txtLikeInterval;
    @FXML
    private Button btnLikeSimpan;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ApplicationSettings settings = new ApplicationSettings();
        txtLikeInterval.setText(settings.getLikeInterval());

        btnBrowse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Pilih Chrome Driver");
                String path = fileChooser.showOpenDialog((Stage) btnBrowse.getScene().getWindow()).getAbsolutePath();
                if (path != null) {
                    if (!path.isEmpty()) {
                        settings.setChromeDriverPath(path);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Berhasil");
                        alert.setHeaderText(null);
                        alert.setContentText("Berhasil mengatur ChromeDriver, silahkan tekan tombol Tutup Aplikasi.");
                        alert.showAndWait();
                        try {
                            driverPath.setText(new ApplicationSettings().getChromeDriverPath());
                        } catch (IOException ex) {
                            Logger.getLogger(ApplicationnSettingsController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (URISyntaxException ex) {
                            Logger.getLogger(ApplicationnSettingsController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            }
        });
        btnClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        try {
            if (!settings.getChromeDriverPath().isEmpty()) {
                driverPath.setText(settings.getChromeDriverPath());
            }
        } catch (IOException ex) {
            Logger.getLogger(ApplicationnSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ApplicationnSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
