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
import com.dotlab.software.instaautomation.filters.CleanerFilter;
import java.util.ArrayList;

/**
 *
 * @author omandotkom
 */
public class RunnableCleaner implements Runnable{
    private boolean running;
    private CleanerFilter engineSetting;

    public RunnableCleaner(CleanerFilter filter) {
        this.engineSetting = filter;
    }

    @Override
    public void run() {
     running = true;
        engineSetting.getCustomRunnerInterface().onRunnerStart();
        engineSetting.getCustomRunnerInterface().logMessage("Memulai operasi pembersihan akun...");
        engineSetting.getCustomRunnerInterface().logMessage("Account : @" + engineSetting.getUser().getUsername());
        if (running) {
            engineSetting.getCustomRunnerInterface().logMessage("Mengumpulkan pengikut...");
            AccountEngine engine = new AccountEngine(engineSetting);
            //engine.setIncludePrivate();
            //engine.setMaxFollower(maxFollower);
            ArrayList<User> userList = engine.getCleanFollowerList();
            engineSetting.getCustomRunnerInterface().onUserListAdded(userList);
            engineSetting.getCustomRunnerInterface().logMessage("Berhasil mengumpulkan " + userList.size() + " pengikut.");
            engineSetting.getCustomRunnerInterface().logMessage("Menunggu....");
            int currentMedia = 1;

            for (int i = 0; i < userList.size(); i++) {

                try {
                    if (running) {

                      if (AutoStatic.AUTOMATION.unfollow(URLGenerator.generateProfileURL(userList.get(i).getUsername()))) {
                            engineSetting.getCustomRunnerInterface().logMessage("Berhasil unfollow " + userList.get(i).getUsername() + "(" + userList.get(i).getFullname() + ") | " + currentMedia + "/" + userList.size());

                        } else {
                            engineSetting.getCustomRunnerInterface().logMessage("Gagal unfollow " + userList.get(i).getUsername());

                        }
                        currentMedia++;
                        long s = 30000;
                        engineSetting.getCustomRunnerInterface().logMessage("Tunggu " + s / 1000 + " detik.");
                        Thread.sleep(s);
                    } else {
                        break;
                    }
                } catch (InterruptedException ex) {
                    //    Logger.getLogger(ApplicationHomePageController.class.getName()).log(Level.SEVERE, null, ex);
                    engineSetting.getCustomRunnerInterface().logMessage(ex.getMessage());
                } catch (Exception e) {
                    engineSetting.getCustomRunnerInterface().logMessage(e.getMessage());
                }
                if (!running) {
                    break;
                }
            }

        }

        engineSetting.getCustomRunnerInterface().onRunnerDone();
    }
}
