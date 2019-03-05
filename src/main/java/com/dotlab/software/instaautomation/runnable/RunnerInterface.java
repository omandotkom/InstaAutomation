/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.runnable;

/**
 *
 * @author omandotkom
 */
public interface RunnerInterface extends LoggerInterface{
    public void onRunnerDone();
    public void onRunnerStart();
    }
