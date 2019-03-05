/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.UI.homepage;

import com.dotlab.software.instaautomation.Settings.ApplicationSettings;
import com.dotlab.software.instaautomation.Settings.Config;
import com.dotlab.software.instaautomation.UI.MessagePopup;
import com.dotlab.software.instaautomation.runnable.RunnerInterface;
import com.dotlab.software.instaautomation.runnable.RunnableGenerateName;
import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author omandotkom
 */
public class MassiveAccountHomeController implements Initializable {

    @FXML
    private CheckBox checkEmailReady;
    @FXML
    private TextField txtPathEmail;
    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtDefaultPassword;
    @FXML
    private Button btnBrowseUsernames;
    @FXML
    private TextArea txtLog;
    @FXML
    private CheckBox checkAutoName;
    @FXML
    private Button btnMulai;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    private int emailCount;

    @FXML
    private void checkEmailReadyAction(ActionEvent event) {
        if (checkEmailReady.isSelected()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Pilih File Konfigurasi");
            File file = fileChooser.showOpenDialog(checkEmailReady.getScene().getWindow());
            if (file != null) {
                String path = file.getAbsolutePath();
                txtPathEmail.setText(path);
                ApplicationSettings settings = new ApplicationSettings();
                if (settings.isValidEmailList(path)) {
                    lblStatus.setText("Status : Valid");
                    emailCount = settings.getEmailsCount();
                    logger("Jumlah email : " + settings.getEmailsCount());
                } else {
                    MessagePopup.show("Daftar email tidak valid, periksa kesalahan.", MessagePopup.MessageType.Error);
                    checkEmailReady.setSelected(false);
                    lblStatus.setText("Status : Tidak Valid");

                    lblStatus.setStyle("fx-text-fill: red;");
                }
            } else {
                checkEmailReady.setSelected(false);
            }
        }
    }

    private void logger(String logMessage) {
        Platform.runLater(() -> {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String time = dtf.format(now);
            txtLog.appendText("(" + time + ") " + logMessage + "\n");

        });
    }

    @FXML
    private void checkAutoNameAction(ActionEvent event) {
       
    }

    @FXML
    private void btnMulaiAction(ActionEvent event) {
        
     if (checkAutoName.isSelected()) {
            RunnableGenerateName rn = new RunnableGenerateName(emailCount,new RunnerInterface(){
                @Override
                public void onRunnerDone() {
                logger("Selesai membuat akun.");
                
                }

                @Override
                public void onRunnerStart() {
                logger("Memulai operasi membuat akun...");
                }

                @Override
                public void logMessage(String message) {
                logger(message);
                }
            },txtPathEmail.getText());
            Thread runner = new Thread(rn);
            runner.start();
        }
    }

}
