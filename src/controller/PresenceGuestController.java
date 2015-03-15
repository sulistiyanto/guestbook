/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import interfaces.interGuest;
import interfaces.sql.interGuestSQL;
import interfaces.interPresenceGuest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.Book;
import model.Guest;

/**
 * FXML Controller class
 *
 * @author sulistiyanto
 */
public class PresenceGuestController extends interPresenceGuest implements Initializable {
    
    interGuestSQL guest = new interGuestSQL();
    interGuest g = new interGuest();
    
    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<String> comboBookName;
    @FXML
    private Label lblTotal;
    @FXML
    private TableView<Guest> tableGuest;
    @FXML
    private TableColumn<Guest, String> colGuestId, colGuestName, colGuestSex, colGuestPhone, colGuestPresence;
    private final ObservableList<Book> listGuest = FXCollections.observableArrayList();
    String bookName;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        combo();
        bookName = (String) comboBookName.getValue();
        listTableresenceGuest(colGuestId, colGuestName, colGuestSex, colGuestPhone, colGuestPresence);
        refreshGuest(tableGuest, listGuest, bookName);
        comboBookName.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            listTableresenceGuest(colGuestId, colGuestName, colGuestSex, colGuestPhone, colGuestPresence);
            refreshGuest(tableGuest, listGuest, newValue);
            guest.totalGuest(comboBookName, lblTotal);
        });
    }

    @FXML
    private void keySearch(KeyEvent event) {
        if (txtSearch.textProperty().get().isEmpty()) {
            listTableresenceGuest(colGuestId, colGuestName, colGuestSex, colGuestPhone, colGuestPresence);
            guest.totalGuest(comboBookName, lblTotal);
        }
        g.search(tableGuest, listGuest, txtSearch, comboBookName);
        guest.totalSearchGuest(comboBookName, lblTotal, txtSearch);
    }
    
    private void combo() {
        guest.comboView(comboBookName);
        guest.comboViewAll(comboBookName);
    }

}
