/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.HomePage;

import com.dotlab.software.instaautomation.UI.AutoStatic;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author omandotkom
 */
public class ApplicationnSettingsController implements Initializable {

    @FXML
    private TextField txtLikeInterval;
    @FXML
    private Button btnLikeSimpan;
    @FXML
    private AnchorPane cmnt;
    @FXML
    private TextField txtFollowInterval;
    @FXML
    private Button btnFollowSimpan;
    @FXML
    private TextField driverPath;
    @FXML
    private Button btnBrowse;
    @FXML
    private Button btnClose;
    @FXML
    private Tab app;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtLikeInterval.setText(AutoStatic.SETTINGS.getLikeInterval());
        txtFollowInterval.setText(AutoStatic.SETTINGS.geetFollowInterval());
   
    }    

    @FXML
    private void simpanLike(ActionEvent event) {
        AutoStatic.SETTINGS.saveLikeInterval(txtLikeInterval.getText());
    }

    @FXML
    private void simpanFollow(ActionEvent event) {
        AutoStatic.SETTINGS.saveFollowInterval(txtFollowInterval.getText());
                
    }
    
}
