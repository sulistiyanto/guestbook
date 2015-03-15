/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import interfaces.interFamily;
import interfaces.interPresenceFamily;
import interfaces.sql.interFamilySQL;
import interfaces.sql.interGuestSQL;
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
import model.Family;

/**
 * FXML Controller class
 *
 * @author sulistiyanto
 */
public class PresenceFamilyController extends interPresenceFamily implements Initializable {

    interGuestSQL guest = new interGuestSQL();
    interFamilySQL family = new interFamilySQL();
    interFamily f = new interFamily();

    @FXML
    private ComboBox<String> comboBookName;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lblSearch;
    @FXML
    private TableView<Family> tableFamily;
    @FXML
    private TableColumn<Family, String> colFamilyId, colFamilyName, colFamilySex, colFamilyPresence, colGuestName;
    private final ObservableList<Book> listFamily = FXCollections.observableArrayList();
    @FXML
    private Label lblTotal;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        combo();
        listTableFamily(colFamilyId, colFamilyName, colFamilySex, colFamilyPresence, colGuestName);
        refreshFamily(tableFamily, listFamily, comboBookName);
        comboBookName.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            listTableFamily(colFamilyId, colFamilyName, colFamilySex, colFamilyPresence, colGuestName);
            refreshFamily(tableFamily, listFamily, comboBookName);
            family.totalFamily(comboBookName, lblTotal);
        });
    }

    @FXML
    private void keySearch(KeyEvent event) {
        if (txtSearch.textProperty().get().isEmpty()) {
            listTableFamily(colFamilyId, colFamilyName, colFamilySex, colFamilyPresence, colGuestName);
            refreshFamily(tableFamily, listFamily, comboBookName);
        }
        f.search(tableFamily, listFamily, txtSearch, comboBookName);
        family.totalSearchFamily(comboBookName, lblTotal, txtSearch);
    }

    private void combo() {
        guest.comboView(comboBookName);
        guest.comboViewAll(comboBookName);
    }
}
