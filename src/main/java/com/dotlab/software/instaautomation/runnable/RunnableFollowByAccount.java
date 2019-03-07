/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.runnable;

import com.dotlab.software.instaautomation.Scrapper.AccountEngine;
import com.dotlab.software.instaautomation.Scrapper.Entities.User;
import com.dotlab.software.instaautomation.Scrapper.URLGenerator;
import com.dotlab.software.instaautomation.UI.AutoStatic;
import com.dotlab.software.instaautomation.UI.homepage.IntervalGenerator;
import com.dotlab.software.instaautomation.filters.FollowFilter;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omandotkom
 */
public class RunnableFollowByAccount implements Runnable {

    private boolean running;
    private FollowFilter engineSetting;
    //private String account;
    //private CustomRunnerInterface runner;
    //private int maxFollow;
    //private boolean isIncludePrivate;
    //private int maxFollower=-1;
    //defultnya -1
    /*public void setMaxFollower(int max) {
        this.maxFollower = max;
    }*/

    public RunnableFollowByAccount(FollowFilter engineSetting) {
        this.engineSetting = engineSetting;
       /* runner = onDo;
        this.maxFollow = max;
        this.account = account;*/
    }

    public RunnableFollowByAccount() {
    }

    

    public Boolean isRunning() {
        return this.running;
    }

    public void terminate() {
        running = false;
        engineSetting.getCustomRunnerInterface().logMessage("Menghentikan operasi follow by hashtag ...");
    }

    @Override
    public void run() {
        running = true;
        engineSetting.getCustomRunnerInterface().onRunnerStart();
        engineSetting.getCustomRunnerInterface().logMessage("Memulai operasi Follow by Account...");
        engineSetting.getCustomRunnerInterface().logMessage("Account : @" + engineSetting.getTargetAccount());
        if (running) {
            try {
                engineSetting.getCustomRunnerInterface().logMessage("Mengumpulkan pengikut...");
                AccountEngine engine = new AccountEngine(engineSetting);
                //engine.setIncludePrivate();
                //engine.setMaxFollower(maxFollower);
                ArrayList<User> userList = engine.getFollowerList();
                engineSetting.getCustomRunnerInterface().onUserListAdded(userList);
                engineSetting.getCustomRunnerInterface().logMessage("Berhasil mengumpulkan " + userList.size() + " pengikut.");
                engineSetting.getCustomRunnerInterface().logMessage("Menunggu....");
                int currentMedia = 1;
                
                for (int i = 0; i < userList.size(); i++) {
                    
                    try {
                        if (running) {
                            
                            if (AutoStatic.AUTOMATION.followByUsername(URLGenerator.generateProfileURL(userList.get(i).getUsername()))) {
                                engineSetting.getCustomRunnerInterface().logMessage("Berhasil mengikuti " + userList.get(i).getUsername() + "(" + userList.get(i).getFullname() + ") | " + currentMedia + "/" + userList.size());
                                
                            } else {
                                engineSetting.getCustomRunnerInterface().logMessage("Gagal mengikuti " + userList.get(i).getUsername());
                                
                            }
                            currentMedia++;
                            long s = IntervalGenerator.followIntervalGenerator();
                            engineSetting.getCustomRunnerInterface().logMessage("Tunggu " + s + " detik.");
                            Thread.sleep(s);
                        } else {
                            break;
                        }
                    } catch (InterruptedException ex) {
                        //    Logger.getLogger(ApplicationHomePageController.class.getName()).log(Level.SEVERE, null, ex);
                        engineSetting.getCustomRunnerInterface().logMessage(ex.getMessage());
                        Dialog dialog = new Dialog(DialogType.ERROR, "Kesalahan", ex.getMessage());
                        dialog.showAndWait();
                    } catch (Exception e) {
                        engineSetting.getCustomRunnerInterface().logMessage(e.getMessage());
                        Dialog dialog = new Dialog(DialogType.ERROR, "Kesalahan", e.getMessage());
                        dialog.showAndWait();
                    }
                    if (!running) {
                        break;
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(RunnableFollowByAccount.class.getName()).log(Level.SEVERE, null, ex);
             Dialog dialog = new Dialog(DialogType.ERROR, "Kesalahan", ex.getMessage());
                    dialog.showAndWait();
            }

        }

        engineSetting.getCustomRunnerInterface().onRunnerDone();
    }

    /*
    @Override
    public void run() {
        AccountEngine engine = new AccountEngine("omanlagidimana");
        engine.getFollowerList().forEach((u) -> {
            System.out.println("username : " + u.getUsername());
        });

    }*/
    public class A {
    }

}
