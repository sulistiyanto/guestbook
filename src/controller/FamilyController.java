/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.AutoFamilyId;
import configure.Export;
import configure.PopUpMenu;
import configure.configScene;
import implementSQL.ConnectionDB;
import interfaces.interFamily;
import interfaces.interGuest;
import interfaces.sql.interFamilySQL;
import interfaces.sql.interGuestSQL;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.Book;
import model.Family;
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
public class FamilyController extends interFamily implements Initializable {

    AutoFamilyId autoId = new AutoFamilyId();
    interGuest guest = new interGuest();
    interGuestSQL guestSQL = new interGuestSQL();
    interFamilySQL family = new interFamilySQL();
    PopUpMenu popUp = new PopUpMenu();
    ConnectionDB connectionDB = new ConnectionDB();
    Export export = new Export();

    private Boolean statusSaveOrUpdate = false;
    private Integer statusAction, onKlik, onSelect;
    JasperReport jasperReport;
    JasperDesign jasperDesign;
    JasperPrint jasperPrint;
    Map<String, Object> param = new HashMap<>();

    @FXML
    private ComboBox<String> comboBookName;
    @FXML
    private Label labelLoadingStatus, bookId, lblTotal;
    @FXML
    private ProgressBar progressBarLoading;
    @FXML
    private HBox boxLoading;
    @FXML
    private RadioButton rdMale, rdFemale;
    @FXML
    private TableView<Book> tableBook;
    @FXML
    private TableColumn<Book, String> colBookName, colBookId;
    private final ObservableList<Book> listBook = FXCollections.observableArrayList();
    @FXML
    private TextField txtBookName, txtGuestName, txtGuestId, txtFamilyId, txtFamilyName, txtSearch;
    @FXML
    private TableView<Guest> tableGuest;
    @FXML
    private TableColumn<Guest, String> colGuestName, colGuestId;
    private final ObservableList<Guest> listGuest = FXCollections.observableArrayList();
    @FXML
    private TableView<Family> tableFamily;
    @FXML
    private TableColumn<Family, String> colFamilyName, colFamilyId, colFamilySex, guestName, guestId, fBookId;
    private final ObservableList<Family> listFamily = FXCollections.observableArrayList();
    @FXML
    private TableColumn colFamilyAction;

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
    private void actionRefresh(ActionEvent event) {
        labelLoadingStatus.setText("Segarkan Data . . .");
        configScene.progressBarLoading(boxLoading, progressBarLoading);
        clear();
    }

    @FXML
    private void actionMale(ActionEvent event) {
        rdMale.setSelected(true);
        rdFemale.setSelected(false);
    }

    @FXML
    private void actionFemale(ActionEvent event) {
        rdMale.setSelected(false);
        rdFemale.setSelected(true);
    }

    @FXML
    private void keyLoadBook(KeyEvent keyEvent) {
        if (txtBookName.getText().isEmpty()) {
            txtGuestId.setText("");
            txtGuestName.setText("");
        }
        guest.listTableBook(colBookId, colBookName);
        guest.loadColBookName(tableBook, listBook, txtBookName);
    }

    @FXML
    private void clickedTableBook(MouseEvent event) {
        try {
            tableBook.setVisible(false);
            txtGuestName.requestFocus();
            try {
                Book b = tableBook.getSelectionModel().getSelectedItem();
                txtBookName.setText(b.getBookName());
                bookId.setText(b.getBookId());
                tableBook.getSelectionModel().clearSelection();
            } catch (Exception e) {
                System.out.println(e);
                txtBookName.requestFocus();
            }
        } catch (Exception e) {
            System.out.println("click Table Book" + e);
        }

    }

    @FXML
    private void keyLoadGuest(KeyEvent event) {
        if (txtBookName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Isi nama buku terlebih dulu!");
            alert.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                tableBook.setVisible(false);
                txtGuestName.setText("");
                txtBookName.requestFocus();
            } else {
                tableBook.setVisible(false);
                txtGuestName.setText("");
                txtBookName.requestFocus();
            }
        } else if (txtGuestName.getText().isEmpty()) {
            txtGuestId.clear();
        }
        listTableGuest(colGuestId, colGuestName);
        loadColGuestName(tableGuest, listGuest, txtGuestName, txtGuestId, txtBookName);
    }

    @FXML
    private void clickedTableGuest(MouseEvent event) {
        try {
            tableGuest.setVisible(false);
            txtFamilyName.requestFocus();
            try {
                Guest g = tableGuest.getSelectionModel().getSelectedItem();
                txtGuestName.setText(g.getGuestName());
                txtGuestId.setText(g.getGuestId());
                tableGuest.getSelectionModel().clearSelection();
            } catch (Exception e) {
            }
        } catch (Exception e) {
            System.out.println("click Table Guest" + e);
        }
    }

    @FXML
    private void actionSave(ActionEvent event) {
        if (txtBookName.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi buku tamu", txtBookName);
        } else if (txtGuestName.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi nama tamu", txtGuestName);
        } else if (txtFamilyName.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi nama keluarga", txtFamilyName);
        } else {
            if (statusSaveOrUpdate == false) {
                labelLoadingStatus.setText("Simpan Data . . .");
                configScene.progressBarLoading(boxLoading, progressBarLoading);
                insertData(txtFamilyId, txtFamilyName, rdMale, txtGuestId);
                refreshFamily(tableFamily, listFamily, comboBookName);
                combo();
                Alert alert = new Alert(Alert.AlertType.WARNING, "Apakah ingin tambah keluarga dengan tamu yang sama ?");
                alert.initStyle(StageStyle.UTILITY);
                ButtonType buttonTypeYes = new ButtonType("Ya");
                ButtonType buttonTypeNo = new ButtonType("Tidak");
                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeYes) {
                    autoId.FamilyId(txtFamilyId);
                    txtFamilyName.setText("");
                    txtFamilyName.requestFocus();
                    rdMale.setSelected(true);
                    rdFemale.setSelected(false);
                    txtBookName.setEditable(false);
                    txtGuestName.setEditable(false);
                } else {
                    clear();
                }
            } else {
                labelLoadingStatus.setText("Simpan Data . . .");
                configScene.progressBarLoading(boxLoading, progressBarLoading);
                editGuest(txtFamilyId, txtFamilyName, rdMale, txtGuestId);
                refreshFamily(tableFamily, listFamily, comboBookName);
                combo();
                clear();
                statusSaveOrUpdate = false;
            }
        }
        popUp.focusedPropertyTextField(txtBookName);
        popUp.focusedPropertyTextField(txtFamilyName);
        popUp.focusedPropertyTextField(txtGuestName);
    }

    @FXML
    private void clickedTableFamily(MouseEvent event) {
        try {
            if (statusAction == 1) {
                configScene.onSelectTable(onKlik, onSelect, tableBook);
                try {
                    Family f = tableFamily.getSelectionModel().getSelectedItem();
                    txtFamilyId.setText(f.getFamilyId());
                    txtFamilyName.setText(f.getFamilyName());
                    String familySex = f.getFamilySex();
                    if (familySex.equals("L")) {
                        rdMale.setSelected(true);
                        rdFemale.setSelected(false);
                    } else {
                        rdFemale.setSelected(true);
                        rdMale.setSelected(false);
                    }
                    txtGuestName.setText(f.getGuestName());
                    txtGuestId.setText(f.getGuestId());
                    bookId.setText(f.getBookId());
                    family.loadBookName(bookId, txtBookName);
                    txtBookName.setEditable(false);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void keySearch(KeyEvent event) {
        if (txtSearch.textProperty().get().isEmpty()) {
            listTableFamily(colFamilyId, colFamilyName, colFamilySex, guestName, guestId, fBookId);
            refreshFamily(tableFamily, listFamily, comboBookName);
            clear();
        }
        search(tableFamily, listFamily, txtSearch, comboBookName);
        family.totalSearchFamily(comboBookName, lblTotal, txtSearch);
    }

    //fungsi tambahan
    //implement button cell
    @SuppressWarnings("Convert2Lambda")
    private void classAction() {
        colFamilyAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        colFamilyAction.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
            @Override
            public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
                return new FamilyController.ButtonCell(tableFamily);
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
                    statusAction = 1;
                    int row = getTableRow().getIndex();
                    tableFamily.getSelectionModel().select(row);
                    clickedTableFamily(null);
                    deleteFamily(tblView);
                    autoId.FamilyId(txtFamilyId);
                    refreshFamily(tblView, listFamily, comboBookName);
                    clear();
                    combo();
                    labelLoadingStatus.setText("Hapus Data . . .");
                    configScene.progressBarLoading(boxLoading, progressBarLoading);
                    statusAction = 0;
                    onKlik = 0;
                } else {
                    System.out.println("");
                }
            });
            cellButtonEdit.setOnAction((ActionEvent event) -> {
                try {
                    statusAction = 1;
                    int row = getTableRow().getIndex();
                    tableFamily.getSelectionModel().select(row);
                    clickedTableFamily(null);
                    statusSaveOrUpdate = true;
                    statusAction = 0;
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

    private void clear() {
        autoId.FamilyId(txtFamilyId);
        txtBookName.setText("");
        txtGuestName.setText("");
        txtGuestId.setText("");
        txtFamilyName.setText("");
        txtSearch.setText("");
        rdFemale.setSelected(false);
        rdMale.setSelected(true);
        refreshFamily(tableFamily, listFamily, comboBookName);
        family.totalFamily(comboBookName, lblTotal);
        txtBookName.setEditable(true);
        txtGuestName.setEditable(true);
        tableFamily.getSelectionModel().clearSelection();

    }

    private void combo() {
        guestSQL.comboView(comboBookName);
        guestSQL.comboViewAll(comboBookName);
    }

    private void jasperRep() {
        try {
            String g = comboBookName.getValue();
            File file1 = new File("C:\\xampp\\htdocs\\guestbook\\report\\Family.jrxml");
            jasperDesign = JRXmlLoader.load(file1);
            String sql = "SELECT guestbookserver.family.family_name, guestbookserver.family.family_sex, "
                    + "guestbookserver.guest.guest_id, guestbookserver.guest.guest_name, guestbookserver.book.book_id, "
                    + "guestbookserver.book.book_name, guestbookserver.book.book_date "
                    + "FROM guestbookserver.family, guestbookserver.guest, guestbookserver.book "
                    + "WHERE guestbookserver.guest.book_id=guestbookserver.book.book_id "
                    + "AND guestbookserver.family.guest_id=guestbookserver.guest.guest_id "
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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //GlyphsDude.setIcon(lblSearch, FontAwesomeIcons.SEARCH, "17px");
        rdMale.setSelected(true);
        autoId.FamilyId(txtFamilyId);
        combo();
        listTableFamily(colFamilyId, colFamilyName, colFamilySex, guestName, guestId, fBookId);
        refreshFamily(tableFamily, listFamily, comboBookName);
        classAction();
        family.totalFamily(comboBookName, lblTotal);
        comboBookName.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            listTableFamily(colFamilyId, colFamilyName, colFamilySex, guestName, guestId, fBookId);
            refreshFamily(tableFamily, listFamily, comboBookName);
            family.totalFamily(comboBookName, lblTotal);
        });
    }

}
