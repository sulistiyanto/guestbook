/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import configure.SHA1Utility;
import implementSQL.implementAccount;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Login;

/**
 *
 * @author sulistiyanto
 */
public class interAccount extends implementAccount {

    Login login = new Login();

    //insert data
    public void insertAccount(TextField txtUsername, PasswordField txtPassword, ComboBox comboLevel) {
        try {
            login.setLoginName(txtUsername.getText());
            String crypto = SHA1Utility.getSHA1(txtPassword.getText().trim());
            login.setLoginPassword(crypto);
            login.setLoginLevel((String) comboLevel.getValue());
            insertSQL(login);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            System.out.println("");
        }
    }

    //list table
    public void listTableAccount(TableColumn colUsername, TableColumn colPassword, TableColumn colLevel) {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("loginName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("loginPassword"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("loginLevel"));
    }

    public void refreshAccount(TableView tableAccount, ObservableList listAccount) {
        listAccount = listLogin();
        tableAccount.setItems(listAccount);
    }
    
    //delete data
    public void deleteAccount(TableView tableAccount) {
        try {
            Login l = (Login) tableAccount.getSelectionModel().getSelectedItem();
            deleteAccount(l.getLoginName());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //update
    public void updateAccount(TextField txtUsername, PasswordField txtPassword, ComboBox comboLevel) {
        try {
            login.setLoginName(txtUsername.getText());
            String crypto = SHA1Utility.getSHA1(txtPassword.getText().trim());
            login.setLoginPassword(crypto);
            login.setLoginLevel((String) comboLevel.getValue());
            updateAccount(login);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            System.out.println(e);
        }
    }
    
    //search
    public void search(TableView tableAccount, ObservableList listAccount, TextField txtSearch) {
        listAccount = searchAccount(txtSearch);
        tableAccount.setItems(listAccount);
    }
}
