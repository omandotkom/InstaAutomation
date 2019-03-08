/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.UI.homepage;

import com.dotlab.software.instaautomation.Scrapper.Parser.HashtagParser;
import com.dotlab.software.instaautomation.UI.MessagePopup;
import com.dotlab.software.instaautomation.runnable.RunnerInterface;
import com.dotlab.software.instaautomation.UI.Shutdown;
import com.dotlab.software.instaautomation.filters.LikebyHashtagFilter;
import com.dotlab.software.instaautomation.runnable.RunnableLikeByHashtag;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author omandotkom
 */
public class LikeModuleController implements Initializable {

    @FXML
    private TextField txtHashtag;
    @FXML
    private TextField txtJumlahFoto;
    @FXML
    private Button btnLikeMulai;
    @FXML
    private Button btnLikeBerhenti;
    @FXML
    private CheckBox checkLikeTopPost;
    @FXML
    private TextArea txtLikeLog;
    @FXML
    private Menu mnKontrolTugas;
    @FXML
    private RadioMenuItem rbThreadShutdown;

    private RunnableLikeByHashtag runnableHashtag;
    private static boolean isEngineRunning = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtLikeLog.setEditable(false);
        rbThreadShutdown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (rbThreadShutdown.isSelected()) {

                    appLogger("Mengatur shutdown otomatis... (status : " + rbThreadShutdown.isSelected() + ")");
                    MessagePopup.show("Komputer akan dimatikan ketika\noperasi yang berjalan selesai.", MessagePopup.MessageType.Information);
                }
            }
        });
        txtHashtag.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {

                } else {
                    //txtHashtag losts its focus
                    txtHashtag.setText(HashtagParser.simplifyHashtag(txtHashtag.getText()));
                }
            }

        });
        mnKontrolTugas.setOnShowing(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
                if (!isEngineRunning) {
                    rbThreadShutdown.setDisable(true);
                } else {
                    rbThreadShutdown.setDisable(false);
                }
            }
        });

        btnLikeBerhenti.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (runnableHashtag != null) {
                    if (runnableHashtag.isRunning()) {
                        runnableHashtag.terminate();
                        runnableHashtag = null;
                    } else {
                        runnableHashtag = null;
                    }

                }
            }
        });
        btnLikeMulai.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (!txtHashtag.getText().isEmpty()) {
                    if (!txtJumlahFoto.getText().isEmpty()) {
                        //hashtag dan jumlah foto sudah di tentukan
                        try {
                            LikebyHashtagFilter filter = new LikebyHashtagFilter();
                            filter.setHashtag(txtHashtag.getText());
                            filter.setMaxMedia(Integer.valueOf(txtJumlahFoto.getText()));
                            filter.setTopPost(checkLikeTopPost.isSelected());
                            filter.setLikeEvent(generateLikeEventInterface()); //run = new RunnableLikeByHashtag(txtHashtag.getText(), Integer.valueOf(txtJumlahFoto.getText()), checkLikeTopPost.isSelected());
                            runnableHashtag = new RunnableLikeByHashtag(filter);
                            Thread runner = new Thread(runnableHashtag);

                            txtLikeLog.clear();
                            runner.start();
                            //btnLikeMulai.setText("Berhenti");
                            btnLikeMulai.setDisable(true);
                            btnLikeBerhenti.setDisable(false);
                        } catch (NumberFormatException e) {
                            MessagePopup.show("Tentukan angka maksimal foto.", MessagePopup.MessageType.Error);

                        }
                    } else {
                        //jumlah foto kosong
                        MessagePopup.show("Tentukan jumlah maksimal foto.", MessagePopup.MessageType.Error);

                    }
                } else {
                    MessagePopup.show("Tentukan hashtag terlebih dahulu.", MessagePopup.MessageType.Error);
                }
            }

        });
    }

    private RunnerInterface generateLikeEventInterface() {
        RunnerInterface likeInterface = new RunnerInterface() {
            @Override
            public void onRunnerDone() {
                //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                logMessage("Operasi like selesai");
                btnLikeBerhenti.setDisable(true);
                btnLikeMulai.setDisable(false);
                isEngineRunning = false;
                if (rbThreadShutdown.isSelected()) {
                    logMessage("Mematikan komputer dalam 1 menit.");
                    Shutdown.shtdown();
                }
            }

            @Override
            public void onRunnerStart() {
                appLogger("Pencarian media berdasarkan hashtag " + txtHashtag.getText());
                //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                isEngineRunning = true;
            }

            @Override
            public void onRunnerError(String errorMessage) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                appLogger("ERROR : \n" + errorMessage);
            }

            @Override
            public void logMessage(String message) {
                appLogger(message);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return likeInterface;
    }

    private void appLogger(String logMessage) {
        Platform.runLater(() -> {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String time = dtf.format(now);
            txtLikeLog.appendText("(" + time + ") " + logMessage + "\n");

        });
    }
}
