/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.sql;

import implementSQL.ConnectionDB;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author sulistiyanto
 */
public class interGuestSQL extends ConnectionDB {

    public void comboView(ComboBox comboBox) {
        try {
            connectionDB();
            rs = st.executeQuery("select book_name from book order by book_id desc limit 1");
            while (rs.next()) {
                comboBox.setValue(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("list" + e);
        } finally {
            closed();
        }
    }

    public void comboViewAll(ComboBox comboBox) {
        try {
            connectionDB();
            String sql = "select book_name from book order by book_id desc";
            rs = st.executeQuery(sql);
            ArrayList<String> subnames = new ArrayList<>();
            while (rs.next()) {
                subnames.add(rs.getString(1));
                ObservableList<String> options = FXCollections.observableArrayList(subnames);
                comboBox.setItems(options);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closed();
        }
    }
    
    //total guest
    public void totalGuest(ComboBox comboBox, Label label) {
        int count = 0;
        String bookId = "";
        try {
            connectionDB();
            String sqlBookName = "select book_id from book where book_name='" + comboBox.getValue() + "'";
            rs = st.executeQuery(sqlBookName);
            while (rs.next()) {
                bookId = rs.getString("book_id");
            }
            String sql = ("select count(*) as guest_id from guest where book_id='" + bookId + "'");
            rs = st.executeQuery(sql);
            while (rs.next()) {
                count = rs.getInt("guest_id");
                label.setText("" + count);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closed();
        }
    }

    public void totalSearchGuest(ComboBox comboBox, Label label, TextField txtSearch) {
        int count = 0;
        String bookId = "";
        try {
            connectionDB();
            String sqlBookName = "select book_id from book where book_name='" + comboBox.getValue() + "'";
            rs = st.executeQuery(sqlBookName);
            while (rs.next()) {
                bookId = rs.getString("book_id");
            }
            String sql = ("select count(*) as guest_id from guest where guest_name like  '" + txtSearch.getText()
                    + "%' and book_id like '%" + bookId + "%'");
            rs = st.executeQuery(sql);
            while (rs.next()) {
                count = rs.getInt("guest_id");
                label.setText("" + count);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closed();
        }
    }
}
