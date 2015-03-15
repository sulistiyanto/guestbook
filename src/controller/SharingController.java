/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.configIPWifi;
import java.io.BufferedReader;
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
    private void ActionOn(ActionEvent event) {
        lblOff.setVisible(true);
        OFF.setVisible(true);
        btnOff.setVisible(true);
        lblOn.setVisible(false);
        ON.setVisible(false);
        btnOn.setVisible(false);
    }

    @FXML
    private void actionOff(ActionEvent event) {
         try {
                Runtime rt = Runtime.getRuntime();
                //Process pr = rt.exec("cmd /c dir");
                Process pr = rt.exec("C:\\xampp\\htdocs\\guestbook\\start.exe");
 
                BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
 
                String line=null;
 
                while((line=input.readLine()) != null) {
                    System.out.println(line);
                }
 
                int exitVal = pr.waitFor();
                System.out.println("Exited with error code "+exitVal);
 
            } catch(IOException | InterruptedException e) {
                System.out.println(e.toString());
            }
        lblOn.setVisible(true);
        ON.setVisible(true);
        btnOn.setVisible(true);
        lblOff.setVisible(false);
        OFF.setVisible(false);
        btnOff.setVisible(false);
        //ipWifi.IP();
        //ipWifi.wifi();
    }

}
