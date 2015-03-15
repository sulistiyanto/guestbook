/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.sql;

import implementSQL.ConnectionDB;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author sulistiyanto
 */
public class interFamilySQL extends ConnectionDB {

    //total guest
    public void totalFamily(ComboBox comboBox, Label label) {
        int count = 0;
        String bookId = "";
        try {
            connectionDB();
            String sqlBookName = "select book_id from book where book_name='" + comboBox.getValue() + "'";
            rs = st.executeQuery(sqlBookName);
            while (rs.next()) {
                bookId = rs.getString("book_id");
            }
            String sql = ("select count(*) as family_name from family f, "
                    + "guest g, book b where f.guest_id = g.guest_id and  "
                    + "g.book_id=b.book_id and b.book_id = '" + bookId + "'");
            rs = st.executeQuery(sql);
            while (rs.next()) {
                count = rs.getInt("family_name");
                label.setText("" + count);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closed();
        }
    }

    public void totalSearchFamily(ComboBox comboBox, Label label, TextField txtSearch) {
        int count = 0;
        String bookId = "";
        try {
            connectionDB();
            String sqlBookName = "select book_id from book where book_name='" + comboBox.getValue() + "'";
            rs = st.executeQuery(sqlBookName);
            while (rs.next()) {
                bookId = rs.getString("book_id");
            }
            String sql = ("select count(*) family_name from family f, "
                    + "guest g, book b where f.guest_id = g.guest_id and  "
                    + "g.book_id=b.book_id and b.book_name = '" + comboBox.getValue() + "' and g.guest_name like '" + txtSearch.getText() + "%'");
            rs = st.executeQuery(sql);
            while (rs.next()) {
                count = rs.getInt("family_name");
                label.setText("" + count);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closed();
        }
    }

    public void loadBookName(Label bookId, TextField txtBookName) {
        String bookName = "";
        try {
            connectionDB();
            String sqlBookName = "select book_name from book where book_id='" + bookId.getText() + "'";
            rs = st.executeQuery(sqlBookName);
            while (rs.next()) {
                bookName = rs.getString("book_name");
            }
            txtBookName.setText(bookName);
        } catch (Exception e) {
        } finally {
            closed();
        }
    }
}
