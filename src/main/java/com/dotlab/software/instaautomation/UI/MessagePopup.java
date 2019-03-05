/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.UI;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author omandotkom
 */
public class MessagePopup {

    public enum MessageType {
        Information,
        Error
    }

    public static void show(String pesan, MessageType type) {
        Alert alert;
        if (type == MessageType.Error) {
            alert = new Alert(AlertType.ERROR, pesan);
            alert.setTitle("Kesalahan");
            alert.showAndWait();
        } else if (type == MessageType.Information) {
            alert = new Alert(AlertType.INFORMATION, pesan);
            alert.showAndWait();
        }

    }
}
