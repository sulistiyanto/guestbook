/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.AutoGuestId;
import configure.Export;
import configure.PopUpMenu;
import configure.configScene;
import implementSQL.ConnectionDB;
import interfaces.interGuest;
import interfaces.sql.interGuestSQL;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.imageio.ImageIO;
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
public class GuestController extends interGuest implements Initializable {

    ConnectionDB connectionDB = new ConnectionDB();
    interGuestSQL guest = new interGuestSQL();
    AutoGuestId autoId = new AutoGuestId();
    PopUpMenu popUp = new PopUpMenu();
    Export export = new Export();

    private Boolean statusSaveOrUpdate = false;
    String imageString, imageString1, imageString2, folder, bookName;
    private Integer statusAction, onKlik, onSelect;
    JasperReport jasperReport;
    JasperDesign jasperDesign;
    JasperPrint jasperPrint;
    Map<String, Object> param = new HashMap<>();

    @FXML
    private HBox boxLoading;
    @FXML
    private Label labelLoadingStatus, bookId, lblTotal;
    @FXML
    private ProgressBar progressBarLoading;
    @FXML
    private ImageView image1, image2;
    @FXML
    private RadioButton rdMale, rdFemale;
    @FXML
    private ComboBox<String> comboBookName;
    @FXML
    private TextField txtGuestId, txtGuestName, txtGuestPhone, txtGuestJob, txtBookName, txtSearch;
    @FXML
    private TextArea txtGuestAddress;
    @FXML
    private TableView<Book> tableBook;
    @FXML
    private TableColumn<Book, String> colBookName, colBookId;
    private final ObservableList<Book> listBook = FXCollections.observableArrayList();
    @FXML
    private Button btnSave;
    @FXML
    private TableView<Guest> tableGuest;
    @FXML
    private TableColumn<Guest, String> colGuestId, colGuestName, colGuestSex, colGuestJob, colGuestPhone, colGuestAddress;
    @FXML
    private TableColumn colGuestAction;
    private final ObservableList<Guest> listGuest = FXCollections.observableArrayList();
    private Object comboPresence;

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
    private void pressedFemale(KeyEvent event) {
        rdMale.setSelected(false);
        rdFemale.setSelected(true);
    }

    @FXML
    private void actionLoadImage(ActionEvent event) {
        handle();
    }

    @FXML
    private void actionSave(ActionEvent event) {
        try {
            if (txtGuestName.getText().isEmpty()) {
                configScene.createDialog(Alert.AlertType.INFORMATION, "Silahkan isi nama tamu");
                txtGuestName.requestFocus();
            } else if (txtGuestJob.getText().isEmpty()) {
                configScene.createDialog(Alert.AlertType.INFORMATION, "Silahkan isi pekerjaan tamu");
                txtGuestJob.requestFocus();
            } else if (txtGuestAddress.getText().isEmpty()) {
                configScene.createDialog(Alert.AlertType.INFORMATION, "Silahkan isi alamat tamu");
                txtGuestAddress.requestFocus();
            } else if (txtBookName.getText().isEmpty()) {
                configScene.createDialog(Alert.AlertType.INFORMATION, "Silahkan isi buku tamu");
                txtBookName.requestFocus();
            } else {
                if (statusSaveOrUpdate == false) {
                    labelLoadingStatus.setText("Simpan Data . . .");
                    configScene.progressBarLoading(boxLoading, progressBarLoading);
                    insertData(txtGuestId, txtGuestName, rdMale, txtGuestJob, txtGuestPhone, txtGuestAddress, bookId);
                    refreshGuest(tableGuest, listGuest, bookName);
                    combo();
                    if (imageString != null) {
                        NIOCopier nioCopier = new GuestController.NIOCopier(imageString, gambar(txtGuestId.getText()));
                    } else {
                        NIOCopier nioCopier = new GuestController.NIOCopier("C:\\xampp\\htdocs\\guestbook\\images\\default.jpg",
                                gambar(txtGuestId.getText()));
                    }
                    clear();
                } else {
                    labelLoadingStatus.setText("Simpan Data . . .");
                    configScene.progressBarLoading(boxLoading, progressBarLoading);
                    editGuest(txtGuestId, txtGuestName, rdMale, txtGuestJob, txtGuestPhone, txtGuestAddress, bookId);
                    refreshGuest(tableGuest, listGuest, bookName);
                    combo();
                    if (imageString != null) {
                        NIOCopier nioCopier = new GuestController.NIOCopier(imageString, gambar(txtGuestId.getText()));
                    } else {
                        //NIOCopier nioCopier = new GuestController.NIOCopier(imageString2, gambar(txtGuestId.getText()));
                    }
                    clear();
                    statusSaveOrUpdate = false;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    private void keySearch(KeyEvent event) {
        if (txtSearch.textProperty().get().isEmpty()) {
            listTableGuest(colGuestId, colGuestName, colGuestSex, colGuestPhone, colGuestJob,
                    colGuestAddress);
            guest.totalGuest(comboBookName, lblTotal);
            clear();
        }
        search(tableGuest, listGuest, txtSearch, comboBookName);
        guest.totalSearchGuest(comboBookName, lblTotal, txtSearch);
    }

    @FXML
    private void actionRefresh(ActionEvent event) {
        labelLoadingStatus.setText("Segarkan Data . . .");
        configScene.progressBarLoading(boxLoading, progressBarLoading);
        clear();
    }

    @FXML
    private void keyLoadBook(KeyEvent keyEvent) {
        listTableBook(colBookId, colBookName);
        loadColBookName(tableBook, listBook, txtBookName);
    }

    @FXML
    private void clickedTableBook(MouseEvent event) {
        tableBook.setVisible(false);
        btnSave.requestFocus();
        try {
            Book b = tableBook.getSelectionModel().getSelectedItem();
            txtBookName.setText(b.getBookName());
            bookId.setText(b.getBookId());
            tableBook.getSelectionModel().clearSelection();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clickedTableGuest(MouseEvent event) {
        try {
            if (statusAction == 1) {
                configScene.onSelectTable(onKlik, onSelect, tableBook);
                try {
                    Guest g = tableGuest.getSelectionModel().getSelectedItem();
                    txtGuestId.setText(g.getGuestId());
                    txtGuestName.setText(g.getGuestName());
                    txtGuestJob.setText(g.getGuestJob());
                    txtGuestPhone.setText(g.getGuestPhone());
                    txtGuestAddress.setText(g.getGuestAddress());
                    String guestSex = g.getGuestSex();
                    if (guestSex.equals("L")) {
                        rdMale.setSelected(true);
                        rdFemale.setSelected(false);
                    } else {
                        rdFemale.setSelected(true);
                        rdMale.setSelected(false);
                    }
                    String id = g.getBookId();
                    bookId.setText(g.getBookId());
                    imageString1 = "file:///C:\\xampp\\htdocs\\guestbook\\images\\" + g.getGuestId() + ".jpg";
                    imageString2 = "C:\\xampp\\htdocs\\guestbook\\images\\" + g.getGuestId() + ".jpg";
                    Image image = new Image(imageString1);
                    image2.setImage(image);
                    image2.setVisible(true);
                    image1.setVisible(false);
                    image1.setImage(null);
                    String sql = "select book_name from book where book_id= '" + id + "'";
                    connectionDB.rs = connectionDB.st.executeQuery(sql);
                    while (connectionDB.rs.next()) {
                        txtBookName.setText(connectionDB.rs.getString("book_name"));
                    }
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    connectionDB.closed();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void actionExport(ActionEvent event) {
        handleExport();
    }

    /*
     * fungsi tambahan
     */
    //clear text
    private void clear() {
        txtGuestName.setText("");
        txtGuestPhone.setText("");
        txtGuestJob.setText("");
        txtBookName.setText("");
        txtGuestAddress.setText("");
        txtSearch.setText("");
        autoId.GuestId(txtGuestId);
        image2.setVisible(true);
        Image image = new Image("/image/1.jpg");
        image2.setImage(image);
        image1.setVisible(false);
        image1.setImage(null);
        rdMale.setSelected(true);
        rdFemale.setSelected(false);
        imageString = null;
        tableGuest.getSelectionModel().clearSelection();
    }

    private void combo() {
        guest.comboView(comboBookName);
        guest.comboViewAll(comboBookName);
    }

    public class NIOCopier {

        public NIOCopier(String asal, String tujuan) throws IOException {
            FileInputStream inFile = new FileInputStream(asal);
            FileOutputStream outFile = new FileOutputStream(tujuan);
            FileChannel outChannel;
            try (FileChannel inChannel = inFile.getChannel()) {
                outChannel = outFile.getChannel();
                for (ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
                        inChannel.read(buffer) != -1;
                        buffer.clear()) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        outChannel.write(buffer);
                    }
                }
            }
            outChannel.close();
        }
    }

    private void folder() {
        folder = "C:\\xampp\\htdocs\\guestbook\\images";
        new File(folder).mkdir();
    }

    //Menentukan tempat penyimpanan gambar
    private String gambar(String id) {
        return "C:\\xampp\\htdocs\\guestbook\\images" + File.separator + id.trim() + ".jpg";
    }

    private void cetak(String str) {
        System.out.println(str);
    }

    @SuppressWarnings("null")
    private void handle() {
        try {
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            //Show open file dialog
            File file = fileChooser.showOpenDialog(stage);
            String locationImage = file.getParent();
            String nameImage = file.getName();
            imageString = locationImage + File.separator + nameImage;

            if (file != null) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    image1.setImage(image);
                    image1.setVisible(true);
                    image2.setVisible(false);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            } else {
                System.out.println("Batal Pilih . . .");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
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
                    tableGuest.getSelectionModel().select(row);
                    clickedTableGuest(null);
                    deleteGuest(tblView);
                    autoId.GuestId(txtGuestId);
                    refreshGuest(tableGuest, listGuest, bookName);
                    combo();
                    new GuestController().deletefile("C:/xampp/htdocs/guestbook/images/" + txtGuestId.getText() + ".jpg");
                    clear();
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
                    tableGuest.getSelectionModel().select(row);
                    clickedTableGuest(null);
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

    private void deletefile(String fileName) {
        File file = new File(fileName);
        boolean success = file.delete();
        if (!success) {
            System.out.println(fileName + " Deletion failed.");
        } else {
            System.out.println(fileName + " File deleted.");
        }
    }

    //implement button cell
    @SuppressWarnings("Convert2Lambda")
    private void classAction() {
        colGuestAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        colGuestAction.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
            @Override
            public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
                return new ButtonCell(tableGuest);
            }
        });
    }

    //max textfield
    public EventHandler<KeyEvent> maxLength(final Integer i) {
        return (KeyEvent arg0) -> {
            TextField tx = (TextField) arg0.getSource();
            if (tx.getText().length() >= i) {
                arg0.consume();
            }
        };
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
                }
            } else {
                System.out.println("Batal Pilih . . .");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void jasperRep() {
        try {
            String g = comboBookName.getValue();
            System.out.println(g);
            File file1 = new File("C:\\xampp\\htdocs\\guestbook\\report\\Guest.jrxml");
            jasperDesign = JRXmlLoader.load(file1);
            String sql = "SELECT guestbookserver.guest.guest_id, guestbookserver.guest.guest_name, "
                    + "guestbookserver.guest.guest_sex,  guestbookserver.guest.guest_job, "
                    + "guestbookserver.guest.guest_phone, guestbookserver.guest.guest_address, guestbookserver.book.book_id, "
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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connectionDB.connectionDB();
        rdMale.setSelected(true);
        combo();
        autoId.GuestId(txtGuestId);
        Image image = new Image("/image/1.jpg");
        image2.setImage(image);
        listTableGuest(colGuestId, colGuestName, colGuestSex, colGuestPhone, colGuestJob, colGuestAddress);
        bookName = comboBookName.getValue();
        refreshGuest(tableGuest, listGuest, bookName);
        guest.totalGuest(comboBookName, lblTotal);
        classAction();
        txtGuestId.addEventFilter(KeyEvent.KEY_TYPED, maxLength(8));
        txtGuestPhone.addEventFilter(KeyEvent.KEY_TYPED, maxLength(12));
        comboBookName.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            listTableGuest(colGuestId, colGuestName, colGuestSex, colGuestPhone, colGuestJob, colGuestAddress);
            refreshGuest(tableGuest, listGuest, newValue);
            guest.totalGuest(comboBookName, lblTotal);
        });
        txtGuestPhone.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                char ch = txtGuestPhone.getText().charAt(oldValue.intValue());
                if (!(ch >= '0' && ch <= '9')) {
                    txtGuestPhone.setText(txtGuestPhone.getText().substring(0, txtGuestPhone.getText().length() - 1));
                }
            }
        });
    }
}
