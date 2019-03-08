/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.UI.homepage;

import com.dotlab.software.instaautomation.Scrapper.AccountEngine;
import com.dotlab.software.instaautomation.Scrapper.Entities.User;
import com.dotlab.software.instaautomation.UI.AutoStatic;
import com.dotlab.software.instaautomation.UI.MessagePopup;
import com.dotlab.software.instaautomation.runnable.RunnerInterface;
import com.dotlab.software.instaautomation.UI.Shutdown;
import com.dotlab.software.instaautomation.filters.CleanerFilter;
import com.dotlab.software.instaautomation.filters.FollowFilter;
import com.dotlab.software.instaautomation.filters.FollowbyHashtagFilter;
import com.dotlab.software.instaautomation.runnable.CustomRunnerInterface;
import com.dotlab.software.instaautomation.runnable.RunnableCleaner;
import com.dotlab.software.instaautomation.runnable.RunnableFollowByAccount;
import com.dotlab.software.instaautomation.runnable.RunnableFollowByHashtag;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.lang3.StringUtils;

/**
 * FXML Controller class
 *
 * @author omandotkom
 */
public class FollowModuleController implements Initializable {

    private static boolean isEngineRunning = false;
    private RunnableFollowByHashtag runnablefbh;
    private RunnableFollowByAccount runnablefba;

    @FXML
    private MenuItem menuPengaturan;
    @FXML
    private Menu mnKontrolTugas;
    @FXML
    private RadioMenuItem rbThreadShutdown;
    @FXML
    private CheckBox checkFollowAccountAll;
    @FXML
    private ListView<?> listViewAkun;
    @FXML
    private Button btnFollowMulaiAccount;
    @FXML
    private Button btnFollowBerhentiAccount;
    @FXML
    private TextField txtFollowAccount;
    @FXML
    private TextField txtJumlahFollowAccount;
    @FXML
    private TextArea txtFollowByAccountLog;
    @FXML
    private CheckBox checkFollowPrivateAccount;
    @FXML
    private CheckBox checkRequestedAccount;
    @FXML
    private Label totalScraped;

    private static final String FOLLOW_BY_ACCOUNT = "FOLLOW_BY_ACCOUNT";
    private static final String FOLLOW_BY_HASHTAG = "FOLLOW_BY_HASHTAG";
    private static final String CLEANER = "CLEANER";
    @FXML
    private TextField txtFollowHashtag;
    @FXML
    private TextField txtJumlahFollowHashtag;
    @FXML
    private Button btnFollowMulaiHashtag;
    @FXML
    private Button btnFollowBerhentiHashtag;
    @FXML
    private CheckBox checkLikeHashtag;
    @FXML
    private TextArea txtFollowLogHashtag;
    @FXML
    private CheckBox checkSettingMaxFollowerAccount;
    // protected ListProperty<User> listProperty = new SimpleListProperty<>();
    private List<User> userList;
    private ObservableList<User> observableList;
    @FXML
    private ListView<String> listViewAkunCleaner;
    @FXML
    private TextField txtAccountCleaner;
    @FXML
    private CheckBox chkBukanPengikutCleaner;
    @FXML
    private CheckBox chkAkunTidakAktifCleaner;
    @FXML
    private CheckBox chkFollowingMorethanFollowerCleaner;
    @FXML
    private CheckBox chkAkunDenganPengikutMoreThan;
    @FXML
    private TextArea txtLogCleaner;
    @FXML
    private Button btnMulaiCleaner;
    @FXML
    private Button btnBerhentiCleaner;
    @FXML
    private AnchorPane panelCleaner;
    @FXML
    private Tab tabCleaner;
    @FXML
    private Label lblTotalAccountCleaner;
    private int currentAccountAmount = 0;
    ///coba
    private ListProperty<String> listProperty = new SimpleListProperty<>();
    /**
     * Initializes the controller class.
     */
    private List<String> nameList = new ArrayList<>();
    @FXML
    private Label lblTotalAccount;

    private CustomRunnerInterface generateFollowByHashtagEvents() {
        CustomRunnerInterface cri = new CustomRunnerInterface() {
            @Override
            public void onUserListAdded(ArrayList<User> userList) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onUserSuccessfullyFiltered(User user) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onUserAnalyzed(User user) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onRunnerDone() {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                logMessage("Operasi follow by hashtag selesai");
                btnFollowBerhentiHashtag.setDisable(true);
                btnFollowMulaiHashtag.setDisable(false);
                isEngineRunning = false;
                if (rbThreadShutdown.isSelected()) {
                    logMessage("Mematikan komputer dalam 1 menit.");
                    Shutdown.shtdown();
                }

            }

            @Override
            public void onRunnerStart() {
                loggerApp("Pencarian media berdasarkan hashtag " + txtFollowHashtag.getText(), "FOLLOW_BY_HASHTAG");

                isEngineRunning = true;

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onRunnerError(String errorMessage) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                loggerApp("Error : \n" + errorMessage,FOLLOW_BY_HASHTAG);

            }

            @Override
            public void logMessage(String message) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                loggerApp(message, FOLLOW_BY_HASHTAG);

            }

        };
        return cri;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userList = new ArrayList<>();
        listProperty.set(FXCollections.observableArrayList(nameList));
        listViewAkunCleaner.itemsProperty().bind(listProperty);

        // listViewAkun.itemsProperty().bind(observableList);
        btnFollowMulaiHashtag.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (!txtFollowHashtag.getText().isEmpty()) {
                    if (!txtJumlahFollowHashtag.getText().isEmpty()) {
                        //hashtag dan jumlah foto sudah di tentukan
                        try {
                            FollowbyHashtagFilter filter = new FollowbyHashtagFilter();
                            filter.setHashtagInner(txtFollowHashtag.getText());
                            filter.setMaxFollow(Integer.valueOf(txtJumlahFollowHashtag.getText()));
                            filter.setRunnerEvents(generateFollowByHashtagEvents());
                            filter.setIsIncludeLike(checkLikeHashtag.isSelected());
                            //run = new RunnableHashtag(txtHashtag.getText(), Integer.valueOf(txtJumlahFoto.getText()), checkLikeTopPost.isSelected());
                            runnablefbh = new RunnableFollowByHashtag(filter);
                            Thread runner = new Thread(runnablefbh);
                            txtFollowLogHashtag.clear();
                            runner.start();
                            //btnLikeMulai.setText("Berhenti");
                            btnFollowMulaiHashtag.setDisable(true);
                            btnFollowBerhentiHashtag.setDisable(false);
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

    private void loggerApp(String logMessage, String logType) {
        Platform.runLater(() -> {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String time = dtf.format(now);
            switch (logType) {
                case FollowModuleController.FOLLOW_BY_HASHTAG:
                    txtFollowLogHashtag.appendText("(" + time + ") " + logMessage + "\n");
                    break;
                case FollowModuleController.FOLLOW_BY_ACCOUNT:
                    txtFollowByAccountLog.appendText("(" + time + ") " + logMessage + "\n");
                    break;
                case FollowModuleController.CLEANER:
                    txtLogCleaner.appendText("(" + time + ") " + logMessage + "\n");
                    break;
            }
        });
    }

    @FXML
    private void btnFollowBerhentiAction(ActionEvent event) {
        if (runnablefbh != null) {
            if (runnablefbh.isRunning()) {
                runnablefbh.terminate();
                runnablefbh = null;
            } else {
                runnablefbh = null;
            }

        }
    }

    private CustomRunnerInterface FollowMulaiAccountonActionInterface() {
        CustomRunnerInterface intf = new CustomRunnerInterface() {
            @Override
            public void onUserListAdded(ArrayList<User> userList) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                ObservableList<String> items = (ObservableList<String>) listViewAkun.getItems();
                userList.forEach((u) -> {
                    items.add(u.getUsername());
                });
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        totalScraped.setText(String.valueOf(userList.size()));

                    }
                });
            }

            @Override
            public void onUserSuccessfullyFiltered(User user) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                currentAccountAmount++;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lblTotalAccount.setText(String.valueOf(currentAccountAmount) + " | terakhir ditambahkan : " + user.getUsername());

                    }
                });
            }

            @Override
            public void onUserAnalyzed(User user) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onRunnerDone() {
                logMessage("Operasi follow by follower selesai");
                btnFollowBerhentiAccount.setDisable(true);
                btnFollowMulaiAccount.setDisable(false);
                isEngineRunning = false;
                if (rbThreadShutdown.isSelected()) {
                    logMessage("Mematikan komputer dalam 1 menit.");
                    Shutdown.shtdown();
                }
//    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onRunnerStart() {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                isEngineRunning = true;
                currentAccountAmount = 0;
            }

            @Override
            public void onRunnerError(String errorMessage) {
                loggerApp("Error : \n "+ errorMessage, FollowModuleController.FOLLOW_BY_ACCOUNT);
  
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void logMessage(String message) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                loggerApp(message, FollowModuleController.FOLLOW_BY_ACCOUNT);

            }
        };
        return intf;
    }

    @FXML
    private void btnFollowMulaiAccountonAction(ActionEvent event) {
        if (!txtFollowAccount.getText().isEmpty()) {
            if (!checkFollowAccountAll.isSelected() && txtJumlahFollowAccount.getText().isEmpty()) {
                MessagePopup.show("Masukan jumlah maksimal akun yang akan diikuti.", MessagePopup.MessageType.Error);
            } else {

                try {
                    //run = new RunnableHashtag(txtHashtag.getText(), Integer.valueOf(txtJumlahFoto.getText()), checkLikeTopPost.isSelected());
                    FollowFilter engineSetting = new FollowFilter();

                    engineSetting.setIncludePrivateAccount(checkFollowPrivateAccount.isSelected());
                    engineSetting.setJumlahFollow(Integer.valueOf(txtJumlahFollowAccount.getText()));

                    engineSetting.setTargetAccount(txtFollowAccount.getText());
                    engineSetting.setCustomRunnerInterface(FollowMulaiAccountonActionInterface());
                    engineSetting.setCustomRunnerInterface(FollowMulaiAccountonActionInterface());
                    if (checkSettingMaxFollowerAccount.isSelected()) {
                        engineSetting.setMaxFollowerofAnAccount(maxFollower);
                    }
                    runnablefba = new RunnableFollowByAccount(engineSetting);
                    Thread runner = new Thread(runnablefba);

                    loggerApp("Pencarian akun berdasarkan follower " + txtFollowHashtag.getText(), FollowModuleController.FOLLOW_BY_ACCOUNT);
                    txtFollowByAccountLog.clear();
                    runner.start();
                    //btnLikeMulai.setText("Berhenti");
                    btnFollowMulaiAccount.setDisable(true);
                    btnFollowBerhentiAccount.setDisable(false);
                } catch (Exception e) {
                    MessagePopup.show("Kesalahan : " + e.getMessage(), MessagePopup.MessageType.Error);
                }

            }
        } else {
            MessagePopup.show("Tentukan akun target.", MessagePopup.MessageType.Error);

        }

    }

    @FXML
    private void btnFollowBerhentiAccountonAction(ActionEvent event) {

    }

    @FXML
    private void checkFollowAccountAllonAction(ActionEvent event) {

        if (checkFollowAccountAll.isSelected()) {
            txtJumlahFollowAccount.setText("");
            txtJumlahFollowAccount.setDisable(true);
            FollowFilter filter = new FollowFilter();
            filter.setCustomRunnerInterface(this.generateCustomRunnerInterface());

            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        AccountEngine engine = new AccountEngine(filter);
                        User u = new User();
                        u.setUsername(txtFollowAccount.getText());
                        engine.analyzeAccount(u);
                    } catch (Exception ex) {
                        loggerApp(ex.getMessage(),FollowModuleController.FOLLOW_BY_ACCOUNT);
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                        //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                          Dialog dialog = new Dialog(DialogType.ERROR, "Kesalahan", ex.getMessage());
                        dialog.showAndWait();
                            }
                        });
                        
                        
                    }
                }

            };
            Thread runner = new Thread(run);
            runner.start();
        } else {
            txtJumlahFollowAccount.setDisable(false);
        }
    }
    private int maxFollower = -1;

    @FXML
    private void checkSettingMaxFolloweronAction(ActionEvent event) {
        if (checkSettingMaxFollowerAccount.isSelected()) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Pengaturan Daftar Follower");
            dialog.setHeaderText("Masukan jumlah maksimal pengikut akun yang akan dimasukkan ke daftar.");
            dialog.setContentText("Masukan nilai:");

// Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && !result.get().isEmpty()) {
                if (StringUtils.isNumeric(result.get())) {
                    System.out.println("Nilai: " + result.get());
                    maxFollower = Integer.parseInt(result.get());
                } else {
                    maxFollower = -1;
                    MessagePopup.show("Periksa kembali angka yang dimasukkan.", MessagePopup.MessageType.Error);
                    checkSettingMaxFollowerAccount.setSelected(false);
                }
            } else {
                maxFollower = -1;
                checkSettingMaxFollowerAccount.setSelected(false);
            }
        }
    }

    @FXML
    private void tabCleanerSelectionChanged(Event event) {
        txtAccountCleaner.setText(AutoStatic.LOGGED_USER.getUsername());
    }

    private CustomRunnerInterface generateCustomRunnerInterface() {
        CustomRunnerInterface cri = new CustomRunnerInterface() {
            @Override
            public void onUserListAdded(ArrayList<User> userList) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onUserSuccessfullyFiltered(User user) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onUserAnalyzed(User user) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
           txtJumlahFollowAccount.setText(String.valueOf(user.getFollowerCount()));
            }

            @Override
            public void onRunnerDone() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onRunnerStart() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onRunnerError(String errorMessage) {
loggerApp(errorMessage,FollowModuleController.FOLLOW_BY_ACCOUNT);                
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void logMessage(String message) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return cri;
    }

    private CustomRunnerInterface generateCleanerInterface() {
        CustomRunnerInterface ine = new CustomRunnerInterface() {
            @Override
            public void onUserListAdded(ArrayList<User> userList) {
                //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lblTotalAccountCleaner.setText(String.valueOf(userList.size()));
                        ObservableList<String> items = (ObservableList<String>) listViewAkunCleaner.getItems();
                        userList.forEach((u) -> {
                            items.add(u.getUsername());
                        });
                    }
                });
            }

            @Override
            public void onUserSuccessfullyFiltered(User user) {
                //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                userList.add(user);
                nameList.add(user.getUsername());

            }

            @Override
            public void onUserAnalyzed(User user) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onRunnerDone() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onRunnerStart() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onRunnerError(String errorMessage) {
                //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                loggerApp("Error : \n " + errorMessage, FollowModuleController.CLEANER);

            }

            @Override
            public void logMessage(String message) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                loggerApp(message, FollowModuleController.CLEANER);

            }

        };
        return ine;
    }

    @FXML
    private void btnMulaiCleaneronAction(ActionEvent event) {
        CleanerFilter filter = new CleanerFilter();
        filter.setUser(AutoStatic.LOGGED_USER);
        filter.setCustomRunnerInterface(generateCleanerInterface());
        RunnableCleaner cleaner = new RunnableCleaner(filter);
        Thread t = new Thread(cleaner);
        t.start();
        ObservableList<String> listItems = FXCollections.observableArrayList();
        listViewAkunCleaner.setItems(listItems);
    }

    @FXML
    private void btnBerhentiCleaneronAction(ActionEvent event) {
        try {
            AutoStatic.AUTOMATION.unfollow("https://www.instagram.com/chasingclaudia/");
        } catch (Exception ex) {
            Logger.getLogger(FollowModuleController.class.getName()).log(Level.SEVERE, null, ex);
            Dialog dialog = new Dialog(DialogType.ERROR, "Kesalahan", ex.getMessage());
            dialog.showAndWait();
        }
    }

}
