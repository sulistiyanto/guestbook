/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import implementSQL.implementFamily;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author sulistiyanto
 */
public class interPresenceFamily extends implementFamily {

    //list table
    public void listTableFamily(TableColumn colFamilyId, TableColumn colFamilyName, TableColumn colFamilySex,
            TableColumn colFamilyPresence, TableColumn colGuestName) {
        colFamilyId.setCellValueFactory(new PropertyValueFactory<>("familyId"));
        colFamilyName.setCellValueFactory(new PropertyValueFactory<>("familyName"));
        colFamilySex.setCellValueFactory(new PropertyValueFactory<>("familySex"));
        colFamilyPresence.setCellValueFactory(new PropertyValueFactory<>("familyPresence"));
        colGuestName.setCellValueFactory(new PropertyValueFactory<>("guestName"));
    }

    public void refreshFamily(TableView tableFamily, ObservableList listFamily, ComboBox comboBoxName) {
        listFamily = listFamily(comboBoxName);
        tableFamily.setItems(listFamily);
    }
}
