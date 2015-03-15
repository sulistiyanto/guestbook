/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import implementSQL.implementBook;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Book;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author sulistiyanto
 */
public class interBook extends implementBook{

    Book book = new Book();

    //insert data
    public void insertData(TextField txtId, TextField txtBookName, DatePicker datePicker, TextArea txtInformation) {
        try {
            book.setBookId(txtId.getText());
            book.setBookName(txtBookName.getText());
            book.setBookDate(java.sql.Date.valueOf(datePicker.getValue()));
            book.setBookInformation(txtInformation.getText());
            insertSQL(book);
        } catch (Exception e) {
        }
    }

    public void updateBook(TextField txtId, TextField txtBookName, DatePicker datePicker, TextArea txtInformation) {
        try {
            book.setBookId(txtId.getText());
            book.setBookName(txtBookName.getText());
            book.setBookDate(java.sql.Date.valueOf(datePicker.getValue()));
            book.setBookInformation(txtInformation.getText());
            updateBook(book);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //list table
    public void listTableBook(TableColumn colBookId, TableColumn colBookName, TableColumn colBookDate, TableColumn colBookInformation) {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colBookDate.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        colBookInformation.setCellValueFactory(new PropertyValueFactory<>("bookInformation"));
    }

    public void refreshBook(TableView tableBook, ObservableList listBook) {
        listBook = listBook();
        tableBook.setItems(listBook);
    }

    //delete data
    public void deleteBook(TableView tableBook) {
        try {
            Book b = (Book) tableBook.getSelectionModel().getSelectedItem();
            deleteBook(b.getBookId());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    //search
    public void search(TableView tableBook, ObservableList listBook, TextField txtSearch) {
        listBook = searchBook(txtSearch);
        tableBook.setItems(listBook);
    }
}
