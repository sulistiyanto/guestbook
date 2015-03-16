/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import configure.PopUpMenu;
import configure.configScene;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author sulistiyanto
 */
public class DatabaseController implements Initializable {

    @FXML
    private TextField txtImport;
    @FXML
    private TextField txtExport;
    PopUpMenu popUp = new PopUpMenu();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void actionRefresImport(ActionEvent event) {
        txtExport.setText("");
        txtImport.setText("");
    }

    @FXML
    private void actionChooseImport(ActionEvent event) {
        handleImport();

    }

    @FXML
    private void actionImport(ActionEvent event) {
        try {
            if (txtImport.getText().isEmpty()) {
                popUp.contextMenuTextField("Silahkan isi buku tamu", txtImport);
            } else {
                String database = "guestbookserver";
                String user = "root";
                String password = "scorpion";
                String[] kata = new String[]{"c:\\xampp\\mysql\\bin\\mysql", database, "-u" + user, "-p" + password, "-e", " source " + txtImport.getText()};
                Process runtimeProcess = Runtime.getRuntime().exec(kata);
                int process = runtimeProcess.waitFor();
                if (process == 0) {
                    configScene.createDialog(Alert.AlertType.INFORMATION, "Database berhasil di restore/ import ...");
                    txtExport.setText("");
                } else {
                    configScene.createDialog(Alert.AlertType.INFORMATION, "Database gagal di restore/ import ...");
                }
            }
            popUp.focusedPropertyTextField(txtImport);
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void actionRefreshExport(ActionEvent event) {
        txtExport.setText("");
        txtImport.setText("");
    }

    @FXML
    private void actionChooseExport(ActionEvent event) {
        handleExport();
    }

    @FXML
    private void actionExport(ActionEvent event) {
        try {
            if (txtExport.getText().isEmpty()) {
                popUp.contextMenuTextField("Silahkan isi buku tamu", txtExport);
            } else {
                Process menjalankancmd = Runtime.getRuntime().exec("C:\\xampp\\mysql\\bin\\mysqldump -hlocalhost -uroot -pscorpion guestbookserver -r " + txtExport.getText());
                int prosesSukses = menjalankancmd.waitFor();
                System.out.println(prosesSukses);
                if (prosesSukses == 0) {
                    configScene.createDialog(Alert.AlertType.INFORMATION, "Database berhasil di backup/ export ...");
                    txtExport.setText("");
                } else {
                    configScene.createDialog(Alert.AlertType.INFORMATION, "Database gagal di backup/ export ...");
                }
            }
            popUp.focusedPropertyTextField(txtExport);
        } catch (IOException | InterruptedException e) {
            configScene.createDialog(Alert.AlertType.INFORMATION, "Database gagal di backup/ export ..." + e.getMessage());
        }
    }

    @SuppressWarnings("null")
    private void handleExport() {
        try {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilterSQL = new FileChooser.ExtensionFilter("SQL files (*.sql)", "*.sql");
            fileChooser.getExtensionFilters().addAll(extFilterSQL);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            //Show open file dialog
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try {
                    txtExport.setText(file.getParent() + File.separator + file.getName());
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            } else {
                System.out.println("Batal Pilih . . .");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @SuppressWarnings("null")
    private void handleImport() {
        try {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilterSQL = new FileChooser.ExtensionFilter("SQL files (*.sql)", "*.sql");
            fileChooser.getExtensionFilters().addAll(extFilterSQL);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            //Show open file dialog
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                try {
                    txtImport.setText(file.getParent() + File.separator + file.getName());
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            } else {
                System.out.println("Batal Pilih . . .");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
