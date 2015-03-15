/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementSQL;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import model.Book;

/**
 *
 * @author sulistiyanto
 */
public class implementBook extends ConnectionDB {

    //insert data
    public void insertSQL(Book book) {
        try {
            connectionDB();
            String sql = "insert into book values ('" + book.getBookId() + "', '"
                    + book.getBookName() + "', '" + book.getBookDate() + "', '"
                    + book.getBookInformation() + "')";
            st.executeUpdate(sql);
            closed();
        } catch (Exception e) {
            System.out.println("tidak tersimpan" + e);
        } finally {
            closed();
        }
    }

    //list table
    public ObservableList<Book> listBook() {
        try {
            connectionDB();
            ObservableList<Book> list = FXCollections.observableArrayList();
            rs = st.executeQuery("select * from book order by book_name asc");
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getString(1));
                book.setBookName(rs.getString(2));

                Date dateInString = rs.getDate(3);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String date = String.valueOf(format.format(dateInString));
                book.setDateString(date);
                book.setBookInformation(rs.getString(4));
                list.add(book);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("list : " + e);
            return null;
        } finally {
            closed();
        }
    }

    //Delete
    public void deleteBook(String bookId) {
        try {
            connectionDB();
            st.executeUpdate("delete from book where book_id='" + bookId + "'");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closed();
        }
    }

    //Update
    public void updateBook(Book book) {
        try {
            connectionDB();
            String sql = "update book set book_name='" + book.getBookName() + "',book_date='"
                    + book.getBookDate() + "', book_information='" + book.getBookInformation()
                    + "' where book_id='" + book.getBookId() + "'";
            st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closed();
        }
    }

    //search table
    public ObservableList<Book> searchBook(TextField txtSearch) {
        try {
            connectionDB();
            ObservableList<Book> listSearch = FXCollections.observableArrayList();
            String sql = "select * from book where book_name like '%" + txtSearch.getText() + "%' order by book_name asc";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getString(1));
                book.setBookName(rs.getString(2));

                Date dateInString = rs.getDate(3);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String date = String.valueOf(format.format(dateInString));
                book.setDateString(date);
                book.setBookInformation(rs.getString(4));
                listSearch.add(book);
            }
            return listSearch;
        } catch (Exception e) {
            System.out.println("list" + e);
            return null;
        } finally {
            closed();
        }
    }
}
