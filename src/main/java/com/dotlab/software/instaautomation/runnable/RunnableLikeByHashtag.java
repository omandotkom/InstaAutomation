/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.runnable;

import com.dotlab.software.instaautomation.UI.AutoStatic;
import com.dotlab.software.instaautomation.UI.homepage.IntervalGenerator;
import com.dotlab.software.instaautomation.Scrapper.Entities.Post;
import com.dotlab.software.instaautomation.Scrapper.HashtagEngine;
import com.dotlab.software.instaautomation.filters.LikebyHashtagFilter;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import org.openqa.selenium.WebDriverException;

/**
 *
 * @author omandotkom
 */
public class RunnableLikeByHashtag implements Runnable {

    private boolean running;
    private LikebyHashtagFilter filter;
    //private String this.filter.getHashtag();
    //private boolean this.filter.isTopPost(),
    private boolean paused;
    //int this.filter.getMaxMdia;
    /// private TextField txtLikeLog;
    //private RunnerInterface runner;

    public boolean isPaused() {
        return this.paused;
    }

    public void pause() throws InterruptedException {
        this.wait();
        this.paused = true;
        filter.getLikeEvent().logMessage("Operasi dihentikan sementara.");
        System.out.println("dipause");
    }

    public void resume() {
        this.notify();
        this.paused = false;
        filter.getLikeEvent().logMessage("Melanjutkan operasi...");
    }

    /*public RunnableLikeByHashtag(String hashtagParam, int maxNum, boolean this.filter.isTopPost()Param, RunnerInterface onDo) {
        this.filter.getHashtag() = hashtagParam;
        this.filter.getMaxMdia = maxNum;
        this.filter.isTopPost() = this.filter.isTopPost()Param;
        //this.txtLikeLog = log;
        runner = onDo;
    }*/
    public RunnableLikeByHashtag(LikebyHashtagFilter hs) {

        this.filter = hs;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void terminate() {
        running = false;
        filter.getLikeEvent().logMessage("Menghentikan operasi like ...");
    }

    @Override
    public void run() {
        running = true;
        filter.getLikeEvent().onRunnerStart();
        filter.getLikeEvent().logMessage("Memulai operasi Like by Hashtag...");
        filter.getLikeEvent().logMessage("Engine : #" + this.filter.getHashtag() + " | " + this.filter.getMaxMedia());
        if (running) {
            filter.getLikeEvent().logMessage("Mengumpulkan media...");
            HashtagEngine engine = new HashtagEngine(this.filter.getHashtag(), this.filter.isTopPost());
            engine.run(this.filter.getMaxMedia());
            this.filter.getLikeEvent().logMessage("Berhasil mengumpulkan " + engine.getPostList().size() + " media.");
            this.filter.getLikeEvent().logMessage("Menunggu....");
            int currentMedia = 1;
            //ArrayList<Post> postList = engine.getPostList();

            for (Post post : engine.getPostList()) {

                try {
                    if (running) {
                        try {
                            if (AutoStatic.AUTOMATION.like(post.getUrl())) {
                                this.filter.getLikeEvent().logMessage("Berhasil menyukai " + post.getShortcode() + " | " + currentMedia + "/" + engine.getPostList().size());
                            } else {
                                this.filter.getLikeEvent().logMessage("Gagal menyukai " + post.getUrl());
                            }
                        }catch (org.openqa.selenium.NoSuchElementException ne){
                            System.out.println("Failed to like : " + ne.getMessage());
                            this.filter.getLikeEvent().onRunnerError("Failed to like photo. Error L-1A\nplease renew the like setting or report to omandotkom@gmail.com");
                            //throw (ne);
                        
                        }
                        catch (WebDriverException e) {
                            System.out.println("Failed to like : " + e.getMessage());
                            this.filter.getLikeEvent().onRunnerError(e.getMessage());
                            throw (e);
                            
                        }
                        catch (Exception ex) {
                           this.filter.getLikeEvent().onRunnerError(ex.getMessage());
                            Logger.getLogger(RunnableLikeByHashtag.class.getName()).log(Level.SEVERE, null, ex);
                            //Dialog dialog = new Dialog(DialogType.ERROR, "Kesalahan", ex.getMessage());
                            //dialog.showAndWait();
                        }
                        currentMedia++;
                        Thread.sleep(IntervalGenerator.likeIntervalGenerator());

                    } else {
                        break;
                    }
                } catch (InterruptedException ex) {
                    //    Logger.getLogger(ApplicationHomePageController.class.getName()).log(Level.SEVERE, null, ex);
                    this.filter.getLikeEvent().onRunnerError(ex.getMessage());
                }
            }

        }

        this.filter.getLikeEvent().onRunnerDone();

    }
}
