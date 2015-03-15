/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.configScene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sulistiyanto
 */
public class PresenceMenuController implements Initializable {
    configScene cs = new configScene();
    @FXML
    private Tab tabGuest;
    @FXML
    private AnchorPane panelLoadGuest;
    @FXML
    private Tab tabFamily;
    @FXML
    private AnchorPane panelLoadFamily;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //load pane Player Di tab 1
        cs.loadAnchorPane(panelLoadGuest, "/view/PresenceGuest.fxml");
        //load pane club Di tab 2
        cs.loadAnchorPane(panelLoadFamily, "/view/PresenceFamily.fxml");
        // TODO
    }    
    
}
