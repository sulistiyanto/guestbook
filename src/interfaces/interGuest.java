/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import configure.configScene;
import implementSQL.implementBook;
import implementSQL.implementGuest;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Guest;

/**
 *
 * @author sulistiyanto
 */
public class interGuest extends implementGuest{

    implementBook implBook = new implementBook();
    Guest guest = new Guest();

    //load table book
    public void listTableBook(TableColumn colBookId, TableColumn colBookName) {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
    }

    public void loadColBookName(TableView tableBook, ObservableList listBook, TextField txtSearch) {
        listBook = implBook.searchBook(txtSearch);
        tableBook.setItems(listBook);
        if (listBook.isEmpty()) {
            tableBook.setVisible(false);
            configScene.createDialog(Alert.AlertType.INFORMATION, "Buku Tamu Tidak Ditemukan");
            txtSearch.clear();
        } else if (txtSearch.getText().isEmpty()) {
            tableBook.setVisible(false);
        } else {
            tableBook.setVisible(true);
        }
    }

    //insert data
    public void insertData(TextField txtGuestId, TextField txtGuestName, RadioButton rdMale,
            TextField txtGuestJob, TextField txtGuestPhone, TextArea txtAddress, Label bookId) {
        try {
            guest.setGuestId(txtGuestId.getText());
            guest.setGuestName(txtGuestName.getText());
            String guestSex;
            if (rdMale.isSelected()) {
                guestSex = "L";
            } else {
                guestSex = "P";
            }
            guest.setGuestSex(guestSex);
            guest.setGuestJob(txtGuestJob.getText());
            guest.setGuestPhone(txtGuestPhone.getText());
            guest.setGuestAddress(txtAddress.getText());
            guest.setGuestPresence("Tidak");
            guest.setBookId(bookId.getText());
            guest.setGuestFoto(txtGuestId.getText() + ".jpg");
            insertGuest(guest);
        } catch (Exception e) {
        }
    }

    //list table
    public void listTableGuest(TableColumn colGuestId, TableColumn colGuestName, TableColumn colGuestSex,
            TableColumn colGuestPhone, TableColumn colGuestJob, TableColumn colGuestAddress) {
        colGuestId.setCellValueFactory(new PropertyValueFactory<>("guestId"));
        colGuestName.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        colGuestSex.setCellValueFactory(new PropertyValueFactory<>("guestSex"));
        colGuestPhone.setCellValueFactory(new PropertyValueFactory<>("guestPhone"));
        colGuestJob.setCellValueFactory(new PropertyValueFactory<>("guestJob"));
        colGuestAddress.setCellValueFactory(new PropertyValueFactory<>("guestAddress"));
    }

    public void refreshGuest(TableView tableGuest, ObservableList listGuest, String bookName) {
        listGuest = listGuest(bookName);
        tableGuest.setItems(listGuest);
    }

    //delete data
    public void deleteGuest(TableView tableGuest) {
        try {
            Guest g = (Guest) tableGuest.getSelectionModel().getSelectedItem();
            deleteGuest(g.getGuestId());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //update data
    public void editGuest(TextField txtGuestId, TextField txtGuestName, RadioButton rdMale,
            TextField txtGuestJob, TextField txtGuestPhone, TextArea txtAddress, Label bookId) {
        try {
            guest.setGuestId(txtGuestId.getText());
            guest.setGuestName(txtGuestName.getText());
            String guestSex;
            if (rdMale.isSelected()) {
                guestSex = "L";
            } else {
                guestSex = "P";
            }
            guest.setGuestSex(guestSex);
            guest.setGuestJob(txtGuestJob.getText());
            guest.setGuestPhone(txtGuestPhone.getText());
            guest.setGuestAddress(txtAddress.getText());
            guest.setBookId(bookId.getText());
            guest.setGuestFoto(txtGuestId.getText() + ".jpg");
            updateBook(guest);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    //search
    public void search(TableView tableGuset, ObservableList listGuest, TextField txtSearch, ComboBox bookName) {
        try {
            listGuest = searchGuest(txtSearch, bookName);
            tableGuset.setItems(listGuest);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    //search
    public void searchYes(TableView tableGuset, ObservableList listGuest, TextField txtSearch, ComboBox bookName) {
        try {
            listGuest = searchGuestYes(txtSearch, bookName);
            tableGuset.setItems(listGuest);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //search
    public void searchNo(TableView tableGuset, ObservableList listGuest, TextField txtSearch, ComboBox bookName) {
        try {
            listGuest = searchGuestNo(txtSearch, bookName);
            tableGuset.setItems(listGuest);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
