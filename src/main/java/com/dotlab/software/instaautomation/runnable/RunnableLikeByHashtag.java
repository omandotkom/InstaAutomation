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

/**
 *
 * @author omandotkom
 */
public class RunnableLikeByHashtag implements Runnable {

    private boolean running;

    private String hashtagInner;
    private boolean topPost, paused;
    int maxMedia;
    /// private TextField txtLikeLog;
    private RunnerInterface runner;

    public boolean isPaused() {
        return this.paused;
    }

    public void pause() throws InterruptedException {
        this.wait();
        this.paused = true;
        runner.logMessage("Operasi dihentikan sementara.");
        System.out.println("dipause");
    }

    public void resume() {
        this.notify();
        this.paused = false;
        runner.logMessage("Melanjutkan operasi...");

    }

    public RunnableLikeByHashtag(String hashtagParam, int maxNum, boolean topPostParam, RunnerInterface onDo) {
        hashtagInner = hashtagParam;
        maxMedia = maxNum;
        topPost = topPostParam;
        //this.txtLikeLog = log;
        runner = onDo;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void terminate() {
        running = false;
        runner.logMessage("Menghentikan operasi like ...");
    }

    @Override
    public void run() {
        running = true;
        runner.onRunnerStart();
        runner.logMessage("Memulai operasi Like by Hashtag...");
        runner.logMessage("Engine : #" + this.hashtagInner + " | " + this.maxMedia);
        if (running) {
            runner.logMessage("Mengumpulkan media...");
            HashtagEngine engine = new HashtagEngine(hashtagInner, topPost);
            engine.run(maxMedia);
            runner.logMessage("Berhasil mengumpulkan " + engine.getPostList().size() + " media.");
            runner.logMessage("Menunggu....");
            int currentMedia = 1;
            //ArrayList<Post> postList = engine.getPostList();

            for (Post post : engine.getPostList()) {

                try {
                    if (running) {
                        if (AutoStatic.AUTOMATION.like(post.getUrl())) {
                            runner.logMessage("Berhasil menyukai " + post.getShortcode() + " | " + currentMedia + "/" + engine.getPostList().size());
                        } else {
                            runner.logMessage("Gagal menyukai " + post.getUrl());
                        }
                        currentMedia++;
                        Thread.sleep(IntervalGenerator.likeIntervalGenerator());
                        
                    } else {
                        break;
                    }
                } catch (InterruptedException ex) {
                    //    Logger.getLogger(ApplicationHomePageController.class.getName()).log(Level.SEVERE, null, ex);
                    runner.logMessage(ex.getMessage());
                }
            }

        }

        runner.onRunnerDone();

    }
}
