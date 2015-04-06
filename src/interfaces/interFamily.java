/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import configure.configScene;
import implementSQL.implementFamily;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Family;

/**
 *
 * @author sulistiyanto
 */
public class interFamily extends implementFamily {

    Family family = new Family();

    //load table family
    public void listTableGuest(TableColumn colGuestId, TableColumn colGuestName) {
        colGuestId.setCellValueFactory(new PropertyValueFactory<>("guestId"));
        colGuestName.setCellValueFactory(new PropertyValueFactory<>("guestName"));
    }

    public void loadColGuestName(TableView tableGuest, ObservableList listGuest, TextField txtGuestName,
            TextField txtGuestId, TextField txtBookName) {
        listGuest = searchGuest(txtGuestName, txtBookName);
        tableGuest.setItems(listGuest);
        if (listGuest.isEmpty()) {
            tableGuest.setVisible(false);
            configScene.createDialog(Alert.AlertType.INFORMATION, "Nama Tamu Tidak Ditemukan");
            txtGuestName.clear();
            txtGuestId.clear();
        } else if (txtGuestName.getText().isEmpty()) {
            tableGuest.setVisible(false);
        } else {
            tableGuest.setVisible(true);
        }
    }

    //insert data
    public void insertData(TextField txtFamilyId, TextField txtFamilyName, RadioButton rdMale, TextField txtGuestId) {
        try {
            family.setFamilyId(txtFamilyId.getText());
            family.setFamilyName(txtFamilyName.getText());
            String familySex;
            if (rdMale.isSelected()) {
                familySex = "L";
            } else {
                familySex = "P";
            }
            family.setFamilySex(familySex);
            family.setGuestId(txtGuestId.getText());
            insertFamily(family);
        } catch (Exception e) {
        }
    }

    //list table
    public void listTableFamily(TableColumn colFamilyId, TableColumn colFamilyName, TableColumn colFamilySex,
            TableColumn colGuestName, TableColumn guestId, TableColumn fBookId) {
        colFamilyId.setCellValueFactory(new PropertyValueFactory<>("familyId"));
        colFamilyName.setCellValueFactory(new PropertyValueFactory<>("familyName"));
        colFamilySex.setCellValueFactory(new PropertyValueFactory<>("familySex"));
        colGuestName.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        guestId.setCellValueFactory(new PropertyValueFactory<>("guestId"));
        fBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
    }

    public void refreshFamily(TableView tableFamily, ObservableList listFamily, ComboBox comboBoxName) {
        listFamily = listFamily(comboBoxName);
        tableFamily.setItems(listFamily);
    }

    //delete data
    public void deleteFamily(TableView tableFamily) {
        try {
            Family f = (Family) tableFamily.getSelectionModel().getSelectedItem();
            deleteFamily(f.getFamilyId());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //update data
    public void editGuest(TextField txtFamilyId, TextField txtFamilyName, RadioButton rdMale, TextField txtGuestId) {
        try {
            family.setFamilyId(txtFamilyId.getText());
            family.setFamilyName(txtFamilyName.getText());
            String guestSex;
            if (rdMale.isSelected()) {
                guestSex = "L";
            } else {
                guestSex = "P";
            }
            family.setFamilySex(guestSex);
            family.setGuestId(txtGuestId.getText());
            updateFamily(family);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    //search
    public void search(TableView tableFamily, ObservableList listFamily, TextField txtSearch, ComboBox bookName) {
        try {
            listFamily = searchFamily(txtSearch, bookName);
            tableFamily.setItems(listFamily);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
