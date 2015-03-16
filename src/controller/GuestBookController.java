/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.ClassStage;
import configure.PopUpMenu;
import configure.SHA1Utility;
import configure.configScene;
import implementSQL.ConnectionDB;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Admin;

/**
 * FXML Controller class
 *
 * @author sulistiyanto
 */
public class GuestBookController extends ClassStage implements Initializable {

    ConnectionDB con = new ConnectionDB();
    Admin admin = new Admin();
    Stage primaryStage;
    PopUpMenu popUp = new PopUpMenu();
    String ipAddress1;

    @FXML
    private TextField txtUsername, txtPassword;
    @FXML
    private Label labelAbout, lblAccount, lblIPAddress, lblAdmin;
    @FXML
    private Button buttonSignIn, buttonSignOut, btnBook, btnGuest, btnPresence;

    @FXML
    private void actionLogin(ActionEvent event) {
        if (txtUsername.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi username", txtUsername);
        } else if (txtPassword.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi password", txtPassword);
        } else {
            Login();
        }
        popUp.focusedPropertyTextField(txtUsername);
        popUp.focusedPropertyTextField(txtPassword);
    }

    @FXML
    private void actionLogout(ActionEvent event) {
        btnBook.setVisible(false);
        btnGuest.setVisible(false);
        btnPresence.setVisible(false);
        buttonSignOut.setVisible(false);
        lblAccount.setVisible(false);
        buttonSignIn.setVisible(true);
        txtPassword.setEditable(true);
        txtUsername.setEditable(true);
        txtPassword.setText("");
        txtUsername.setText("");
        lblIPAddress.setText("NOT FOUND");
        lblAdmin.setText("NOT FOUND");
    }

    @FXML
    private void actionGuest(ActionEvent event) {
        stageChild(event, URL_GUEST_FXML, URL_GUEST_TITLE,
                URL_IMAGE, primaryStage);
    }

    @FXML
    private void actionBook(ActionEvent event) {
        stageChild(event, URL_BOOK_FXML, URL_BOOK_TITLE,
                URL_IMAGE, primaryStage);
    }

    @FXML
    private void actionPresence(ActionEvent event) {
        stageChild(event, URL_PRESENCE_FXML, URL_PRESENCE_TITLE, URL_IMAGE, primaryStage);
    }

    @FXML
    private void actionUsername(ActionEvent event) {
        txtPassword.requestFocus();

    }

    @FXML
    private void actionPassword(ActionEvent event) {
        buttonSignIn.requestFocus();
    }

    @FXML
    private void keySignIn(KeyEvent event) {
        if (txtUsername.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi username", txtUsername);
        } else if (txtPassword.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi password", txtPassword);
        } else {
            Login();
        }
        popUp.focusedPropertyTextField(txtUsername);
        popUp.focusedPropertyTextField(txtPassword);
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelAbout.setOnMouseClicked((MouseEvent event) -> {
            stageMouse(event, URL_SETTING_FXML, URL_SETTING_TITLE, URL_IMAGE, primaryStage);
        });
        lblAccount.setOnMouseClicked((MouseEvent event) -> {
            stageMouse(event, URL_ACCOUNT_FXML, URL_ACCOUNT_TITLE, URL_IMAGE, primaryStage);
        });
    }

    private void Login() {
        try {
            String user = txtUsername.getText();
            String pass = SHA1Utility.getSHA1(txtPassword.getText());
            System.out.println(pass);
            String u = "", p = "", l = "Admin", l2 = "";
            con.connectionDB();
            con.rs = con.st.executeQuery("select * from login where login_name='" + user + "' and login_password='" + pass + "' ");
            while (con.rs.next()) {
                u = con.rs.getString(1);
                p = con.rs.getString(2);
            }
            admin.setUsername(u);
            con.connectionDB();
            con.rs = con.st.executeQuery("select login_level from login where login_name='" + user + "' ");
            while (con.rs.next()) {
                l2 = con.rs.getString(1);
            }
            con.rs.close();
            System.out.println(l2);
            if (l2.equals(l) && u.equals(user) && p.equals(pass)) {
                btnBook.setVisible(true);
                btnGuest.setVisible(true);
                btnPresence.setVisible(true);
                buttonSignOut.setVisible(true);
                lblAccount.setVisible(true);
                buttonSignIn.setVisible(false);
                txtUsername.setEditable(false);
                txtPassword.setEditable(false);
                txtPassword.setText("");
                txtUsername.setText("");
                ipAddress();
                lblIPAddress.setText(ipAddress1);
                lblAdmin.setText(admin.getUsername());
            } else {
                configScene.createDialog(Alert.AlertType.INFORMATION, "Maaf username dan password tidak cocok");
            }

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | SQLException ex) {
            System.out.println(ex);
        }
    }

    private void ipAddress() {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {

                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        ipAddress1 = inetAddress.getHostAddress();

                    }
                }
            }
        } catch (SocketException ex) {
            System.out.println(ex);
        }

    }

}
