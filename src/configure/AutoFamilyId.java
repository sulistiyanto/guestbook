/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configure;

import implementSQL.ConnectionDB;
import java.sql.SQLException;
import javafx.scene.control.TextField;

/**
 *
 * @author sulistiyanto
 */
public class AutoFamilyId extends ConnectionDB{

    public void FamilyId(TextField textField) {
        String sql = "select * from family order by family_id desc";
        try {
            connectionDB();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String auto = "" + (Integer.parseInt(rs.getString("family_id").substring(2)) + 1);
                String zero = "";
                if (auto.length() == 1) {
                    zero = "0000000";
                } else if (auto.length() == 2) {
                    zero = "000000";
                } else if (auto.length() == 3) {
                    zero = "00000";
                } else if (auto.length() == 4) {
                    zero = "0000";
                } else if (auto.length() == 5) {
                    zero = "000";
                } else if (auto.length() == 6) {
                    zero = "00";
                } else if (auto.length() == 7) {
                    zero = "0";
                } else if (auto.length() == 8) {
                    zero = "";
                }
                textField.setText("F." + zero + auto);
            } else {
                String kode = "F.00000001";
                textField.setText(kode);
            }
            //connectionDB.closed();
        } catch (SQLException | NumberFormatException e) {
            System.out.println("df" + e.getMessage());
        } finally{
            closed();
        }
    }
}
