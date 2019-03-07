/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.runnable;

import com.dotlab.software.instaautomation.Scrapper.NameGeneratorEngine;
import com.dotlab.software.instaautomation.UI.AutoStatic;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omandotkom
 */
public class RunnableGenerateName implements Runnable {

    private int num;
    private RunnerInterface runner;
    private String emailPath;

    public RunnableGenerateName(int value, RunnerInterface onDo, String emailPath) {
        this.num = value;
        runner = onDo;
        this.emailPath = emailPath;
    }

    @Override
    public void run() {
        runner.onRunnerStart();
        runner.logMessage("Membuat nama...");
        NameGeneratorEngine engine = new NameGeneratorEngine();
        engine.run(num);
        int index = 0;
        for (String email : NameGeneratorEngine.getEmailListFromFile(emailPath)) {
            try {
                AutoStatic.AUTOMATION.register(engine.getUserList().get(index).getCompleteName(), engine.getUserList().get(index).generateUsername(), email);
                runner.logMessage(engine.getUserList().get(index).getCompleteName() + "|" + engine.getUserList().get(index).generateUsername() + "|" + email);
            } catch (Exception ex) {
                Logger.getLogger(RunnableGenerateName.class.getName()).log(Level.SEVERE, null, ex);
            Dialog dialog = new Dialog(DialogType.ERROR, "Kesalahan", ex.getMessage());
                    dialog.showAndWait();
            }

        }
        runner.onRunnerDone();
    }

}
