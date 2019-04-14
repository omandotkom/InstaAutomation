/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.HomePage;

import com.dotlab.software.instaautomation.Scrapper.RequestMaker;
import com.dotlab.software.instaautomation.Scrapper.URLGenerator;
import com.dotlab.software.instaautomation.UI.AutoStatic;
import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

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
    @FXML
    private TextField txtLikeVar;
    @FXML
    private Button btnLikeFetch;
    @FXML
    private ProgressBar progressLike;
    private RequestMaker requestMaker;
    @FXML
    private Button btnLikeClose;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtLikeInterval.setText(AutoStatic.SETTINGS.getLikeInterval());
        txtFollowInterval.setText(AutoStatic.SETTINGS.geetFollowInterval());
        txtLikeVar.setText(AutoStatic.SETTINGS.getLikeButton());
        progressLike.setVisible(false);
        requestMaker = new RequestMaker();
    }

    @FXML
    private void simpanLike(ActionEvent event) {
        AutoStatic.SETTINGS.saveLikeInterval(txtLikeInterval.getText());
        AutoStatic.SETTINGS.saveLikeButton(txtLikeVar.getText());
        Dialog dialog = new Dialog(DialogType.INFORMATION, "Berhasil", "Berhasil memperbarui pengaturan");
        dialog.showAndWait();
    }

    @FXML
    private void simpanFollow(ActionEvent event) {
        AutoStatic.SETTINGS.saveFollowInterval(txtFollowInterval.getText());

    }

    @FXML
    private void btnLikeFetchonAction(ActionEvent event) {
        try {
           
            progressLike.setVisible(true);
            progressLike.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            JSONObject j = new JSONObject(requestMaker.run(URLGenerator.generateConfigURL()));
            txtLikeVar.setText(j.getJSONObject("Like").getString("variabel"));
            progressLike.setProgress(1.0f);
        } catch (Exception ex) {
            Dialog dialog = new Dialog(DialogType.ERROR, "Error", ex.getMessage());
            dialog.showAndWait();
            progressLike.setProgress(1.0f);
        }
    }

    @FXML
    private void btnLikeCloseonAction(ActionEvent event) {
        Stage stage = (Stage) btnLikeClose.getScene().getWindow();
        stage.close();
    }

}
