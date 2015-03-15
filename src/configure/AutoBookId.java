/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configure;

import java.sql.SQLException;
import javafx.scene.control.TextField;
import implementSQL.ConnectionDB;

/**
 *
 * @author sulistiyanto
 */
public class AutoBookId extends ConnectionDB {

    public void BookId(TextField textField) {
        String sql = "select * from book order by book_id desc";
        try {
            connectionDB();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String auto = "" + (Integer.parseInt(rs.getString("book_id").substring(2)) + 1);
                String zero = "";
                if (auto.length() == 1) {
                    zero = "000";
                } else if (auto.length() == 2) {
                    zero = "00";
                } else if (auto.length() == 3) {
                    zero = "0";
                } else if (auto.length() == 4) {
                    zero = "";
                }
                textField.setText("B." + zero + auto);
            } else {
                String kode = "B.0001";
                textField.setText(kode);
            }
            //connectionDB.closed();
        } catch (SQLException | NumberFormatException e) {
            System.out.println("df" + e.getMessage());
        }
    }
}
