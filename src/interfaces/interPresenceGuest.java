/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import implementSQL.implementGuest;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author sulistiyanto
 */
public class interPresenceGuest extends implementGuest{
    
    //list table
    public void listTableresenceGuest(TableColumn colGuestId, TableColumn colGuestName, TableColumn colGuestSex,
            TableColumn colGuestPhone, TableColumn colGuestPresence) {
        colGuestId.setCellValueFactory(new PropertyValueFactory<>("guestId"));
        colGuestName.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        colGuestSex.setCellValueFactory(new PropertyValueFactory<>("guestSex"));
        colGuestPhone.setCellValueFactory(new PropertyValueFactory<>("guestPhone"));
        colGuestPresence.setCellValueFactory(new PropertyValueFactory<>("guestPresence"));
    }
    
    public void refreshGuest(TableView tableGuest, ObservableList listGuest, String bookName) {
        listGuest = listGuest(bookName);
        tableGuest.setItems(listGuest);
    }
}
