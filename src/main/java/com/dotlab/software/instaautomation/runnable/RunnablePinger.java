/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.runnable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;

/**
 *
 * @author omandotkom
 */
public class RunnablePinger implements Runnable {

    private LoggerInterface logger;

    /*public RunnablePinger(LoggerInterface logger) {
        this.logger = logger;
    }*/
    @Override
    public void run() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        String line = "ping www.google.com";
        CommandLine cmdLine = CommandLine.parse(line);
        DefaultExecutor executor = new DefaultExecutor();

        ExecuteWatchdog watchdog = new ExecuteWatchdog(5000);
        executor.setWatchdog(watchdog);
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);

        executor.setStreamHandler(streamHandler);
        System.out.println("output : " + outputStream.toString());

    }

    public static void main(String[] args) throws IOException {
        LogOutputStream stream = new LogOutputStream() {
            @Override
            protected void processLine(String arg0, int arg1) {
                System.out.println("fuck : " + arg0 + " ==> " + arg1);
            }
        };
        String line = "ping www.google.com";
        CommandLine cmdLine = CommandLine.parse(line);
        DefaultExecutor executor = new DefaultExecutor();
        ExecuteStreamHandler hand = (ExecuteStreamHandler) stream;
        ExecuteWatchdog watchdog = new ExecuteWatchdog(5000);
        executor.setWatchdog(watchdog);
        executor.setStreamHandler(hand);
        executor.execute(cmdLine);

    }

}
