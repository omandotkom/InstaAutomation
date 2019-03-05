/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.UI.homepage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author omandotkom
 */
public class StalkerModuleController implements Initializable {

    @FXML
    private TextField txtAccountIDStalk;
    @FXML
    private TextField txtJumlahFotoStalk;
    @FXML
    private CheckBox checkAllMediaStalk;
    @FXML
    private CheckBox checkLikeStalk;
    @FXML
    private CheckBox checkCommentStalk;
    @FXML
    private TextArea txtStalkLog;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
