/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.AutoBookId;
import configure.FormatDate;
import configure.PopUpMenu;
import configure.configScene;
import implementSQL.ConnectionDB;
import interfaces.interBook;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.Admin;
import model.Book;

/**
 * FXML Controller class
 *
 * @author sulistiyanto
 */
public class BookController implements Initializable {

    AutoBookId autoId = new AutoBookId();
    FormatDate formatDate = new FormatDate();
    interBook book = new interBook();
    PopUpMenu popUp = new PopUpMenu();
    Admin admin = new Admin();
    

    private Boolean statusSaveOrUpdate = false;
    private Integer statusAction, onKlik, onSelect;

    @FXML
    private HBox boxLoading;
    @FXML
    private Label labelLoadingStatus;
    @FXML
    private ProgressBar progressBarLoading;
    @FXML
    private TextField txtId, txtBookName, txtSearch;
    @FXML
    private TextArea txtInformation;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Book> tableBook;
    @FXML
    private TableColumn<Book, String> colBookId, colBookName, colBookInformation, colBookDate;
    @FXML
    private TableColumn colAction;
    @FXML
    private final ObservableList<Book> listBook = FXCollections.observableArrayList();

    @FXML
    private void actionRefresh(ActionEvent event) {
        labelLoadingStatus.setText("Segarkan Data . . .");
        configScene.progressBarLoading(boxLoading, progressBarLoading);
        clear();
        book.refreshBook(tableBook, listBook);
        System.out.println("koo" + admin.getUsername());
    }

    @FXML
    private void keySearch(KeyEvent event) {
        if (txtSearch.textProperty().get().isEmpty()) {
            book.listTableBook(colBookId, colBookName, colBookDate, colBookInformation);
            clear();
        }
        book.search(tableBook, listBook, txtSearch);
    }

    @FXML
    private void actionSave(ActionEvent event) {
        try {
            Date date = java.sql.Date.valueOf(datePicker.getValue());
            if (txtBookName.getText().isEmpty()) {
                popUp.contextMenuTextField("Silahkan isi buku tamu", txtBookName);
            } else if (date == null) {
                popUp.contextMenuDate("Silahkan pilih tanggal", datePicker);
            } else {
                if (statusSaveOrUpdate == false) {
                    labelLoadingStatus.setText("Simpan Data . . .");
                    configScene.progressBarLoading(boxLoading, progressBarLoading);
                    book.insertData(txtId, txtBookName, datePicker, txtInformation);
                    book.refreshBook(tableBook, listBook);
                    clear();
                } else {
                    labelLoadingStatus.setText("Simpan Data . . .");
                    configScene.progressBarLoading(boxLoading, progressBarLoading);
                    book.updateBook(txtId, txtBookName, datePicker, txtInformation);
                    book.refreshBook(tableBook, listBook);
                    clear();
                    statusSaveOrUpdate = false;
                }

            }
            popUp.focusedPropertyTextField(txtBookName);
            popUp.focusedPropertyDate(datePicker);
        } catch (Exception e) {
            configScene.createDialog(Alert.AlertType.WARNING, "Tanggal tidak boleh kosong\n" + e.getMessage());
            datePicker.requestFocus();
        }
    }

    @FXML //klik table player
    private void clickedTableBook(MouseEvent event) {
        try {
            if (statusAction == 1) {
                configScene.onSelectTable(onKlik, onSelect, tableBook);
                try {
                    Book b = tableBook.getSelectionModel().getSelectedItem();
                    txtId.setText(b.getBookId());
                    txtBookName.setText(b.getBookName());
                    //convert string to localdate
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String dateInString = b.getDateString();
                    Date date = formatter.parse(dateInString);
                    Instant instant = Instant.ofEpochMilli(date.getTime());
                    LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
                    datePicker.setValue(localDate);
                    txtInformation.setText(b.getBookInformation());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*
     * fungsi tambahan
     */
    //implement button cell
    @SuppressWarnings("Convert2Lambda")
    private void classAction() {
        colAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        colAction.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
            @Override
            public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
                return new ButtonCell(tableBook);
            }
        });
    }

    //button cell
    private class ButtonCell extends TableCell<Object, Boolean> {

        final Hyperlink cellButtonDelete = new Hyperlink("Hapus");
        final Hyperlink cellButtonEdit = new Hyperlink("Ubah");
        final HBox hb = new HBox(cellButtonDelete, cellButtonEdit);

        ButtonCell(final TableView tblView) {
            hb.setSpacing(4);
            cellButtonDelete.setOnAction((ActionEvent t) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Yakin Ingin Dihapus ?");
                alert.initStyle(StageStyle.UTILITY);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        labelLoadingStatus.setText("Hapus Data . . .");
                        configScene.progressBarLoading(boxLoading, progressBarLoading);
                        statusAction = 1;
                        int row = getTableRow().getIndex();
                        tableBook.getSelectionModel().select(row);
                        clickedTableBook(null);
                        book.deleteBook(tblView);
                        autoId.BookId(txtId);
                        book.refreshBook(tableBook, listBook);
                        clear();
                        statusAction = 0;
                        onKlik = 0;

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {

                }
            });

            cellButtonEdit.setOnAction((ActionEvent event) -> {
                try {
                    statusAction = 1;
                    int row = getTableRow().getIndex();
                    tableBook.getSelectionModel().select(row);
                    clickedTableBook(null);
                    statusAction = 0;
                    statusSaveOrUpdate = true;
                } catch (Exception e) {
                    System.out.println(e);
                }

            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(hb);
            } else {
                setGraphic(null);
            }
        }
    }

    //clear text
    private void clear() {
        txtBookName.clear();
        txtInformation.clear();
        txtSearch.clear();
        autoId.BookId(txtId);
        tableBook.getSelectionModel().clearSelection();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoId.BookId(txtId);
        formatDate.datePicker(datePicker);
        datePicker.setValue(LocalDate.now());
        book.listTableBook(colBookId, colBookName, colBookDate, colBookInformation);
        book.refreshBook(tableBook, listBook);
        classAction();
    }

}
