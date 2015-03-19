/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.Export;
import implementSQL.ConnectionDB;
import interfaces.interGuest;
import interfaces.sql.interGuestSQL;
import interfaces.interPresenceGuest;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Book;
import model.Guest;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author sulistiyanto
 */
public class PresenceGuestController extends interPresenceGuest implements Initializable {

    interGuestSQL guest = new interGuestSQL();
    interGuest g = new interGuest();
    ConnectionDB connectionDB = new ConnectionDB();
    Export export = new Export();

    JasperReport jasperReport;
    JasperDesign jasperDesign;
    JasperPrint jasperPrint;
    Map<String, Object> param = new HashMap<>();

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
     *
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
    private void actionPrint(ActionEvent event) {
        try {
            jasperRep();
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void actionExport(ActionEvent event) {
        handleExport();
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

    private void jasperRep() {
        try {
            String g = comboBookName.getValue();
            File file1 = new File("C:\\xampp\\htdocs\\guestbook\\report\\GuestPresence.jrxml");
            jasperDesign = JRXmlLoader.load(file1);
            String sql = "SELECT guestbookserver.guest.guest_id, guestbookserver.guest.guest_name, "
                    + "guestbookserver.guest.guest_sex, guestbookserver.guest.guest_job, "
                    + "guestbookserver.guest.guest_presence, guestbookserver.book.book_id, "
                    + "guestbookserver.book.book_name, guestbookserver.book.book_date "
                    + "FROM guestbookserver.guest, guestbookserver.book "
                    + "WHERE guestbookserver.guest.book_id=guestbookserver.book.book_id "
                    + "AND guestbookserver.book.book_name = '" + g + "'";
            JRDesignQuery newDesignQuery = new JRDesignQuery();
            newDesignQuery.setText(sql);
            jasperDesign.setQuery(newDesignQuery);
            param.clear();
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport, param, connectionDB.connectionDB());
        } catch (Exception e) {
        }
    }

    //export data
    @SuppressWarnings("null")
    private void handleExport() {
        try {
            jasperRep();
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilterDocx = new FileChooser.ExtensionFilter("Word Document (*.docx)", "*.docx");
            FileChooser.ExtensionFilter extFilterXlsx = new FileChooser.ExtensionFilter("Excel Worlkbook (*.xlsx)", "*.xlsx");
            FileChooser.ExtensionFilter extFilterPdf = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
            FileChooser.ExtensionFilter extFilterHtml = new FileChooser.ExtensionFilter("HTML (*.html)", "*.html");
            fileChooser.getExtensionFilters().addAll(extFilterDocx, extFilterXlsx, extFilterPdf, extFilterHtml);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            //Show open file dialog
            File file = fileChooser.showSaveDialog(stage);
            String locationImage = file.getParent();
            String nameImage = file.getName();
            String reportName = locationImage + File.separator + nameImage;
            String tempPath = file.getCanonicalPath().toLowerCase();
            if (file != null) {
                if (file.exists()) {
                    export.chooseExtention(tempPath, reportName, jasperPrint);
                } else if (!file.exists()) {
                    export.chooseExtention(tempPath, reportName, jasperPrint);
                } else {

                }
            } else {
                System.out.println("Batal Pilih . . .");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
