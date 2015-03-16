/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.configIPWifi;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author sulistiyanto
 */
public class SharingController implements Initializable {

    @FXML
    private Button btnOn;
    @FXML
    private Button btnOff;
    @FXML
    private Label lblOn, ON;
    @FXML
    private Label lblOff, OFF;

    configIPWifi ipWifi = new configIPWifi();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ActionOn(ActionEvent event) throws IOException {

        String fileName = "C:\\xampp\\htdocs\\guestbook\\stop.lnk";
        try {
            Process p = Runtime.getRuntime().exec("cmd /c start " + fileName);
            p.waitFor();
            lblOff.setVisible(true);
            OFF.setVisible(true);
            btnOff.setVisible(true);
            lblOn.setVisible(false);
            ON.setVisible(false);
            btnOn.setVisible(false);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    private void actionOff(ActionEvent event) throws IOException {

        String fileName = "C:\\xampp\\htdocs\\guestbook\\start.lnk";
        try {
            Process p = Runtime.getRuntime().exec("cmd /c start " + fileName);
            p.waitFor();
            lblOn.setVisible(true);
            ON.setVisible(true);
            btnOn.setVisible(true);
            lblOff.setVisible(false);
            OFF.setVisible(false);
            btnOff.setVisible(false);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

}
