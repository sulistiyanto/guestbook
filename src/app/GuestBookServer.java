/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import configure.ClassStage;
import implementSQL.ChangePass;
import implementSQL.CreateDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Admin;

/**
 *
 * @author sulistiyanto
 */
public class GuestBookServer extends Application {
    private static Object NetworkInterrface;

    ClassStage string = new ClassStage();
    CreateDB c = new CreateDB();
    ChangePass p = new ChangePass();
    Admin admin = new Admin();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(string.URL_MAIN_FXML));
        //Scene scene = new Scene(root);
        ///Maximize
        c.create();
        p.change();
        admin.setWifi(Boolean.FALSE);
        System.out.println(admin.getWifi());
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        Image image = new Image(string.URL_IMAGE);
        stage.getIcons().addAll(image);
        stage.setTitle(string.URL_MAIN_TITLE);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
