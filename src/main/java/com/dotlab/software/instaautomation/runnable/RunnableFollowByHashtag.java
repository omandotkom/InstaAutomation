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

/**
 *
 * @author omandotkom
 */
public class RunnableFollowByHashtag implements Runnable {

    private boolean running;

    private String hashtagInner;
    private RunnerInterface runner;
    int maxFollow;
    private Boolean isIncludeLike = false;

    public RunnableFollowByHashtag(String hashtagParam, int maxNum, RunnerInterface onDo, Boolean isLike) {
        this.hashtagInner = hashtagParam;
        this.maxFollow = maxNum;
        runner = onDo;
        this.isIncludeLike = isLike;
    }

    public Boolean isRunning() {
        return this.running;
    }

    public void terminate() {
        running = false;
        runner.logMessage("Menghentikan operasi follow by hashtag ...");
    }

    @Override
    public void run() {
        running = true;
        runner.onRunnerStart();
        runner.logMessage("Memulai operasi Follow by Hashtag...");
        runner.logMessage("Engine : #" + this.hashtagInner + " | " + this.maxFollow);
        if (running) {
            runner.logMessage("Mengumpulkan media...");
            HashtagEngine engine = new HashtagEngine(hashtagInner, false);
            engine.run(maxFollow);
            runner.logMessage("Berhasil mengumpulkan " + engine.getPostList().size() + " media.");
            runner.logMessage("Menunggu....");
            int currentMedia = 1;
            //ArrayList<Post> postList = engine.getPostList();

            for (Post post : engine.getPostList()) {

                try {
                    if (running) {
                        if (isIncludeLike) {
                            Thread.sleep(1000);
                            if (AutoStatic.AUTOMATION.like(post.getUrl())) {
                                runner.logMessage("Berhasil menyukai " + post.getShortcode() + " | " + currentMedia + "/" + engine.getPostList().size());
                            }
                        }
                        if (AutoStatic.AUTOMATION.follow(post.getUrl())) {
                            runner.logMessage("Berhasil mengikuti " + post.getOwner() + " | " + currentMedia + "/" + engine.getPostList().size());

                        } else {
                            runner.logMessage("Gagal mengikuti " + post.getOwner());

                        }
                        currentMedia++;
                        long s = IntervalGenerator.followIntervalGenerator();
                        runner.logMessage("Tunggu " + s + " detik.");
                        Thread.sleep(s);
                    } else {
                        break;
                    }
                } catch (InterruptedException ex) {
                    //    Logger.getLogger(ApplicationHomePageController.class.getName()).log(Level.SEVERE, null, ex);
                    runner.logMessage(ex.getMessage());
                } catch (Exception e) {
                    runner.logMessage(e.getMessage());
                }
                if (!running) {
                    break;
                }
            }

        }

        runner.onRunnerDone();
    }

}
