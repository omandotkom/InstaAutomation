/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.UI.Login;

import com.dotlab.software.instaautomation.UI.AutoStatic;
import com.dotlab.software.instaautomation.UI.homepage.IntervalGenerator;
import com.dotlab.software.instaautomation.UI.MessagePopup;
import com.dotlab.software.instaautomation.Scrapper.Entities.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.dotlab.software.instaautomation.Settings.ApplicationSettings;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author omandotkom
 */
public class LoginPageController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param url
     */
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button btnMasuk;
    @FXML
    private Label lblStatusKonfigurasi;
    @FXML
    private Button btnBrowseConfig;
    @FXML
    private TextField txtConfigPath;
    @FXML
    private Button btnSettings;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ApplicationSettings settings = new ApplicationSettings();
        //System.out.println("THE FUCKING PATH IS : " + ClassLoader.)
        if (settings.isValidConfigFile(settings.getConfigPath())) {
            try {
                IntervalGenerator.loadIntervals();
        
                btnMasuk.setDisable(false);
            } catch (IOException ex) {
                Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Unable to load config file at " + settings.getConfigPath());
                System.out.println("--ERROR INFO " + ex.getMessage());
            } catch (Exception e) {
                System.out.println("Unable to load config file at " + settings.getConfigPath());
                System.out.println("--ERROR INFO " + e.getMessage());
            }
        } else {
            txtConfigPath.clear();
            btnMasuk.setDisable(true);
        }
        txtConfigPath.setText(settings.getConfigPath());
        if (settings.getUser() != null) {
            usernameTextField.setText(settings.getUser().getUsername());
            passwordTextField.setText(settings.getUser().getPassword());
        }
        btnMasuk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                if (usernameTextField.getText().isEmpty()) {
                    //username empty
                    Alert alert = new Alert(AlertType.ERROR, "Harap isi username instagram");
                    alert.showAndWait();
                } else {
                    if (passwordTextField.getText().isEmpty()) {
                        //password empty
                        Alert alert = new Alert(AlertType.ERROR, "Harap isi password instagram");
                        alert.showAndWait();

                    } else {
                        //Login()
                        btnMasuk.setDisable(true);
                        login();

                    }
                }
            }

        });

        btnBrowseConfig.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                ApplicationSettings settings = new ApplicationSettings();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Pilih File Konfigurasi");
                String path = fileChooser.showOpenDialog(btnBrowseConfig.getScene().getWindow()).getAbsolutePath();
                if (path != null) {
                    if (!path.isEmpty()) {
                        if (settings.isValidConfigFile(path)) {
                            settings.saveConfigPath(path);
                            MessagePopup.show("Berhasil mengatur konfigurasi", MessagePopup.MessageType.Information);
                            txtConfigPath.setText(new ApplicationSettings().getConfigPath());
                        } else {
                            MessagePopup.show("Konfigurasi Gagal", MessagePopup.MessageType.Error);
                        }
                    }
                }
            }
        });
        txtConfigPath.textProperty().addListener((observable, oldValue, newValue) -> {

            if (settings.isValidConfigFile(txtConfigPath.getText())) {
                lblStatusKonfigurasi.setText("Konfigurasi selesai");
                btnMasuk.setDisable(false);

                try {
                    IntervalGenerator.loadIntervals();
                } catch (IOException ex) {
                    Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                lblStatusKonfigurasi.setText("Kesalahan konfigurasi");
                btnMasuk.setDisable(true);

            }
        });

        if (settings.getChromeDriverPath().isEmpty()) {

            btnMasuk.setDisable(true);
            MessagePopup.show("Driver chrome tidak ada.", MessagePopup.MessageType.Error);
            //btnSettings.setVisible(true);

        }

        btnSettings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    btnMasuk.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/ApplicationnSettings.fxml"));

                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setMaximized(false);
                    stage.setScene(new Scene(root));
                    stage.showAndWait();

                } catch (IOException ex) {
                    Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    private void login() {
        User user = new User();
        // WebDriver driver = new ChromeDriver();
        user.setUsername(usernameTextField.getText());
        user.setPassword(passwordTextField.getText());
        new ApplicationSettings().saveUser(user);
        //Automation AUTOMATION = new Automation();

        if (AutoStatic.AUTOMATION.auth(new ApplicationSettings().getUser().getUsername(), new ApplicationSettings().getUser().getPassword())) {
            try {
                AutoStatic.LOGGED_USER = user;
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/ApplicationHomePage.fxml"));
                btnMasuk.getScene().getWindow().hide();
                Stage stage = (Stage) btnMasuk.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
