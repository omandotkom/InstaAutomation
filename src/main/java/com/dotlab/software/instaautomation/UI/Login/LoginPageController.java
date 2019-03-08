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
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;

import javafx.stage.Stage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;

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
    private ProgressBar progressBarLogin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ApplicationSettings settings = new ApplicationSettings();
        //System.out.println("THE FUCKING PATH IS : " + ClassLoader.)
        /* if (settings.isValidConfigFile(settings.getConfigPath())) {
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
        }*/
        //txtConfigPath.setText(settings.getConfigPath());
        if (settings.getUser() != null) {
            usernameTextField.setText(settings.getUser().getUsername());
            passwordTextField.setText(settings.getUser().getPassword());
        }
        btnMasuk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (usernameTextField.getText().isEmpty()) {
                    //username empty
                    Dialog dialog = new Dialog(DialogType.ERROR, "Kesalahan", "Harap isi username instagram");
                    dialog.showAndWait();
                } else {
                    if (passwordTextField.getText().isEmpty()) {
                        //password empty

                        Dialog dialog = new Dialog(DialogType.ERROR, "Kesalahan", "Harap isi password instagram");
                        dialog.showAndWait();
                    } else {
                        //Login()
                        btnMasuk.setDisable(true);
                        Thread loginThread = new Thread(new AuthLogin(createLoginListener()));
                        //login(createLoginListener());
                        loginThread.start();
                    }
                }

            }

        });

//      btnBrowseConfig.setOnAction(new EventHandler<ActionEvent>() {
        //          @Override
        //        public void handle(ActionEvent t) {
        /* ApplicationSettings settings = new ApplicationSettings();
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
         */
        //    }
        //  });
        /* txtConfigPath.textProperty().addListener((observable, oldValue, newValue) -> {

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
        });*/
        try {
            if (settings.getChromeDriverPath().isEmpty()) {

                btnMasuk.setDisable(true);
                MessagePopup.show("Driver chrome tidak ada.", MessagePopup.MessageType.Error);
                //btnSettings.setVisible(true);

            }
        } catch (IOException ex) {
            // Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
            Dialog dialog = new Dialog(DialogType.EXCEPTION, "Kesalahan", ex.getMessage());
            dialog.showAndWait();

        } catch (URISyntaxException ex) {
            Dialog dialog = new Dialog(DialogType.EXCEPTION, "Kesalahan", ex.getMessage());
            dialog.showAndWait();

        }

        /*        btnSettings.setOnAction(new EventHandler<ActionEvent>() {
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
                    Dialog dialog = new Dialog(DialogType.ERROR, "Kesalahan", ex.getMessage());
                    dialog.showAndWait();

                }
            }
        });
         */
    }

    private LoginListener createLoginListener() {
        LoginListener mLoginListener = new LoginListener() {
            @Override
            public void onLoginStarted() {

                //Platform.runLater(r); 
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        usernameTextField.setDisable(true);
                        passwordTextField.setDisable(true);
                        btnMasuk.setDisable(true);
                        progressBarLogin.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
                    }
                });

            }

            @Override
            public void onLoginFailed() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        usernameTextField.setDisable(false);
                        passwordTextField.setDisable(false);
                        btnMasuk.setDisable(false);
                        progressBarLogin.setProgress(0.0f);
                         Dialog dialog = new Dialog(DialogType.EXCEPTION, "Kesalahan", "Please check your internet connection, username or password.");
                dialog.showAndWait();
                    }
                });

            }

            @Override
            public void onLoginSuccess(User user) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        try {
                            usernameTextField.setDisable(false);
                            passwordTextField.setDisable(false);
                            btnMasuk.setDisable(false);
                            progressBarLogin.setProgress(1.0f);
                            AutoStatic.LOGGED_USER = user;
                            Parent root = FXMLLoader.load(getClass().getResource("/fxml/ApplicationHomePage.fxml"));
                            btnMasuk.getScene().getWindow().hide();
                            Stage stage = (Stage) btnMasuk.getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.show();

                        } catch (IOException ex) {
                            //  Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
                            Dialog dialog = new Dialog(DialogType.EXCEPTION, "Kesalahan", "An error occured during login");
                            dialog.showAndWait();

                        }
                    }
                });

            }

        };
        return mLoginListener;
    }

    private interface LoginListener {

        void onLoginStarted();

        void onLoginFailed();

        void onLoginSuccess(User user);
    }

    class AuthLogin implements Runnable {

        private LoginListener list;

        public AuthLogin(LoginListener list) {
            this.list = list;
        }

        private void login() {

            try {
                User user = new User();
                // WebDriver driver = new ChromeDriver();
                user.setUsername(usernameTextField.getText());
                user.setPassword(passwordTextField.getText());
                new ApplicationSettings().saveUser(user);
                //Automation AUTOMATION = new AUTOMATION;
                list.onLoginStarted();
                if (AutoStatic.AUTOMATION.auth(new ApplicationSettings().getUser().getUsername(), new ApplicationSettings().getUser().getPassword())) {
                    list.onLoginSuccess(user);
                }
            } catch (NoSuchElementException nse){
            list.onLoginFailed();
            System.out.println(nse.getMessage());
            }
            catch (WebDriverException wbe){
            list.onLoginFailed();
            System.out.println(wbe.getMessage());
            }
            catch (Exception ex) {
                list.onLoginFailed();
               System.out.println(ex.getMessage());

            }
        }

        @Override
        public void run() {
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            login();
        }

    }

}
