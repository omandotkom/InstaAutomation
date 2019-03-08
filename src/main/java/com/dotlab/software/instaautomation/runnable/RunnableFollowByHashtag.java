/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.runnable;

import com.dotlab.software.instaautomation.Scrapper.Entities.Post;
import com.dotlab.software.instaautomation.Scrapper.HashtagEngine;
import com.dotlab.software.instaautomation.UI.AutoStatic;
import com.dotlab.software.instaautomation.UI.homepage.IntervalGenerator;
import com.dotlab.software.instaautomation.filters.FollowbyHashtagFilter;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;

/**
 *
 * @author omandotkom
 */
public class RunnableFollowByHashtag implements Runnable {

    private boolean running;

    private FollowbyHashtagFilter filter;

    public RunnableFollowByHashtag(FollowbyHashtagFilter fil) {
        this.filter = fil;
    }

    public Boolean isRunning() {
        return this.running;
    }

    public void terminate() {
        running = false;
        this.filter.getRunnerEvents().logMessage("Menghentikan operasi follow by hashtag ...");
    }

    @Override
    public void run() {
        running = true;

        this.filter.getRunnerEvents().onRunnerStart();
        this.filter.getRunnerEvents().logMessage("Memulai operasi Follow by Hashtag...");
        this.filter.getRunnerEvents().logMessage("Engine : #" + this.filter.getHashtagInner() + " | " + this.filter.getMaxFollow());
        if (running) {
            this.filter.getRunnerEvents().logMessage("Mengumpulkan media...");
            HashtagEngine engine = new HashtagEngine(filter.getHashtagInner(), false);
            engine.run(filter.getMaxFollow());
            this.filter.getRunnerEvents().logMessage("Berhasil mengumpulkan " + engine.getPostList().size() + " media.");
            this.filter.getRunnerEvents().logMessage("Menunggu....");
            int currentMedia = 1;
            //ArrayList<Post> postList = engine.getPostList();

            for (Post post : engine.getPostList()) {

                try {
                    if (running) {
                        if (filter.getIsIncludeLike()) {
                            Thread.sleep(1000);
                            if (AutoStatic.AUTOMATION.like(post.getUrl())) {
                                this.filter.getRunnerEvents().logMessage("Berhasil menyukai " + post.getShortcode() + " | " + currentMedia + "/" + engine.getPostList().size());
                            }
                        }
                        if (AutoStatic.AUTOMATION.follow(post.getUrl())) {
                            this.filter.getRunnerEvents().logMessage("Berhasil mengikuti " + post.getOwner() + " | " + currentMedia + "/" + engine.getPostList().size());

                        } else {
                            this.filter.getRunnerEvents().logMessage("Gagal mengikuti " + post.getOwner());

                        }
                        currentMedia++;
                        long s = IntervalGenerator.followIntervalGenerator();
                        this.filter.getRunnerEvents().logMessage("Tunggu " + s + " detik.");
                        Thread.sleep(s);
                    } else {
                        break;
                    }
                } catch (NoSuchElementException nse) {
                    this.filter.getRunnerEvents().logMessage("Unable to do operation.");
                    System.out.println(nse.getMessage());
                }
                catch(WebDriverException wbe){
                this.filter.getRunnerEvents().logMessage("Unable to do operation.");
                    System.out.println(wbe.getMessage());
                
                }
                catch (InterruptedException ex) {
                    //    Logger.getLogger(ApplicationHomePageController.class.getName()).log(Level.SEVERE, null, ex);
                    this.filter.getRunnerEvents().logMessage(ex.getMessage());
                    System.out.println(ex.getMessage());
                    //Dialog dialog = new Dialog(DialogType.ERROR, "Kesalahan", ex.getMessage());
                    //dialog.showAndWait();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    this.filter.getRunnerEvents().logMessage(e.getMessage());
                    this.filter.getRunnerEvents().onRunnerError(e.getMessage());
//Dialog dialog = new Dialog(DialogType.ERROR, "Kesalahan", e.getMessage());
                    //dialog.showAndWait();
                }
                if (!running) {
                    break;
                }
            }

        }

        this.filter.getRunnerEvents().onRunnerDone();
    }

}
