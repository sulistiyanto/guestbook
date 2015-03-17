/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.PopUpMenu;
import configure.SHA1Utility;
import configure.configScene;
import interfaces.interAccount;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.Book;
import model.Login;

/**
 * FXML Controller class
 *
 * @author sulistiyanto
 */
public class AccountController extends interAccount implements Initializable {

    @FXML
    private TextField txtSearch;
    @FXML
    private TableView<Login> tableAccount;
    @FXML
    private TableColumn<Login, String> colUsername, colPassword, colLevel;
    @FXML
    private TableColumn colAction;
    @FXML
    private final ObservableList<Book> listAccount = FXCollections.observableArrayList();
    ObservableList<String> listComboBox = FXCollections.observableArrayList("Admin", "User");
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtKonfirmPass;
    @FXML
    private ComboBox<String> comboLevel;
    @FXML
    private HBox boxLoading;
    @FXML
    private Label labelLoadingStatus;
    @FXML
    private ProgressBar progressBarLoading;

    private Boolean statusSaveOrUpdate = false;
    private Integer statusAction, onKlik, onSelect;
    PopUpMenu popUp = new PopUpMenu();
    String user;
    GuestBookController gbc = new GuestBookController();
    Login l = new Login();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboLevel.setItems(listComboBox);
        listTableAccount(colUsername, colPassword, colLevel);
        refreshAccount(tableAccount, listAccount);
        classAction();
    }

    @FXML
    private void actionSave(ActionEvent event) {
        if (txtUsername.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi username", txtUsername);
        } else if (txtPassword.getText().isEmpty()) {
            popUp.contextMenuTextField("Silahkan isi password", txtPassword);
        } else if (txtKonfirmPass.getText().isEmpty()) {
            popUp.contextMenuTextField("Isi konfirmasi password", txtKonfirmPass);
        } else if (!txtKonfirmPass.getText().equals(txtPassword.getText())) {
            popUp.contextMenuTextField("Password tidak cocok", txtKonfirmPass);
        } else if (comboLevel.getValue() == null) {
            popUp.contextMenuComboBox("Pilih level user", comboLevel);
        } else {
            if (statusSaveOrUpdate == false) {
                labelLoadingStatus.setText("Simpan Data . . .");
                configScene.progressBarLoading(boxLoading, progressBarLoading);
                insertAccount(txtUsername, txtPassword, comboLevel);
                refreshAccount(tableAccount, listAccount);
                clear();
            } else {
                labelLoadingStatus.setText("Simpan Data . . .");
                configScene.progressBarLoading(boxLoading, progressBarLoading);
                updateAccount(txtUsername, txtPassword, comboLevel);
                refreshAccount(tableAccount, listAccount);
                clear();
                statusSaveOrUpdate = false;
            }
        }
        popUp.focusedPropertyTextField(txtUsername);
        popUp.focusedPropertyTextField(txtPassword);
        popUp.focusedPropertyTextField(txtKonfirmPass);
        popUp.focusedPropertyComboBox(comboLevel);
    }

    @FXML
    private void actionRefresh(ActionEvent event) {
        labelLoadingStatus.setText("Segarkan Data . . .");
        configScene.progressBarLoading(boxLoading, progressBarLoading);
        clear();
    }

    @FXML
    private void keySearch(KeyEvent event) {
        if (txtSearch.textProperty().get().isEmpty()) {
            refreshAccount(tableAccount, listAccount);
            clear();
        }
        search(tableAccount, listAccount, txtSearch);
    }

    @FXML
    private void clickedTableAccount(MouseEvent event) {
        try {
            if (statusAction == 1) {
                configScene.onSelectTable(onKlik, onSelect, tableAccount);
                try {
                    Login l = tableAccount.getSelectionModel().getSelectedItem();
                    txtUsername.setText(l.getLoginName());
                    txtPassword.setText(l.getLoginPassword());
                    txtKonfirmPass.setText(l.getLoginPassword());
                    comboLevel.setValue(l.getLoginLevel());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //fungsi tambahan
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
                return new ButtonCell(tableAccount);
            }
        });
    }

    //button cell
    private class ButtonCell extends TableCell<Object, Boolean> {

        final Hyperlink cellButtonDelete = new Hyperlink("Hapus");
        final Hyperlink cellButtonEdit = new Hyperlink("Ubah");
        final HBox hb = new HBox(cellButtonDelete, cellButtonEdit);

        @SuppressWarnings("empty-statement")
        ButtonCell(final TableView tblView) {
            hb.setSpacing(4);
            cellButtonDelete.setOnAction((ActionEvent t) -> {
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Yakin Ingin Dihapus ?");
                    alert.initStyle(StageStyle.UTILITY);;
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        labelLoadingStatus.setText("Hapus Data . . .");
                        configScene.progressBarLoading(boxLoading, progressBarLoading);
                        statusAction = 1;
                        int row = getTableRow().getIndex();
                        tableAccount.getSelectionModel().select(row);
                        clickedTableAccount(null);
                        deleteAccount(tblView);
                        refreshAccount(tableAccount, listAccount);
                        clear();
                        statusAction = 0;
                        onKlik = 0;
                    } else {
                    }

                } catch (Exception e) {
                }
            });

            cellButtonEdit.setOnAction((ActionEvent event) -> {
                try {
                    statusAction = 1;
                    int row = getTableRow().getIndex();
                    tableAccount.getSelectionModel().select(row);
                    clickedTableAccount(null);
                    dialogPassword();
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

    private void clear() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtKonfirmPass.setText("");
        comboLevel.getSelectionModel().clearSelection();
        comboLevel.setItems(listComboBox);
        tableAccount.getSelectionModel().clearSelection();
        txtUsername.setEditable(true);
    }

    private void dialogPassword() {
        try {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Ubah Akun");
            dialog.setHeaderText("Masukan Password anda!");
            // Set the button types.
            ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
            // Add a custom icon.
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/image/ic_launcher.png").toString()));
            // Create the username and password labels and fields.
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            PasswordField password = new PasswordField();
            password.setPromptText("Password");

            grid.add(new Label("Password:"), 0, 1);
            grid.add(password, 1, 1);
            // Enable/Disable login button depending on whether a username was entered.
            Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
            loginButton.setDisable(true);
            // Do some validation (using the Java 8 lambda syntax).
            password.textProperty().addListener((observable, oldValue, newValue) -> {
                loginButton.setDisable(newValue.trim().isEmpty());
            });

            dialog.getDialogPane().setContent(grid);
            // Convert the result to a username-password-pair when the login button is clicked.
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return password.getText();
                } else {
                    clear();
                }
                return null;
            });
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(usernamePassword -> {
                System.out.println(" Password=" + usernamePassword);
            });

            if (result.get().equals(password.getText())) {
                String pass = password.getText();
                String crypto = SHA1Utility.getSHA1(pass);
                if (crypto.equals(txtPassword.getText())) {
                    txtUsername.setEditable(false);
                    txtPassword.requestFocus();
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Informasi");
                    alert.setHeaderText("Look, an Information Dialog");
                    alert.setContentText("Password anda salah!");
                    alert.showAndWait();
                    clear();
                }
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            System.out.println(e);
        }
    }
}
