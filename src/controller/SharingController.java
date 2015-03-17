/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.ConfigWifi;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
public class SharingController extends GuestBookController implements Initializable {

    @FXML
    private Button btnOn;
    @FXML
    private Button btnOff;
    @FXML
    private Label lblOn, ON;
    @FXML
    private Label lblOff, OFF;

    String str;
    ConfigWifi wifi = new ConfigWifi();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        readStatus();
        String s = str.trim();
        if (s.equals("on")) {
            lblOn.setVisible(true);
            ON.setVisible(true);
            btnOn.setVisible(true);
            lblOff.setVisible(false);
            OFF.setVisible(false);
            btnOff.setVisible(false);
        } else {
            lblOff.setVisible(true);
            OFF.setVisible(true);
            btnOff.setVisible(true);
            lblOn.setVisible(false);
            ON.setVisible(false);
            btnOn.setVisible(false);
        }
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
            str = "off";
            wifi.saveStatus(str);
        } catch (InterruptedException e1) {
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
            str = "on";
            wifi.saveStatus(str);
        } catch (InterruptedException e1) {
        }
    }

    public void readStatus() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\xampp\\htdocs\\guestbook\\report\\wifi.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            str = sb.toString();
            System.out.println(str);
        } catch (Exception e) {

        }
    }
    
}
