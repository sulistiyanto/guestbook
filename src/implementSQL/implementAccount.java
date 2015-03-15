/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementSQL;

import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import model.Login;

/**
 *
 * @author sulistiyanto
 */
public class implementAccount extends ConnectionDB {

    //insert data
    public void insertSQL(Login login) {
        try {
            connectionDB();
            String sql = "insert into login values ('" + login.getLoginName() + "', '"
                    + login.getLoginPassword() + "', '" + login.getLoginLevel() + "')";
            st.executeUpdate(sql);
            closed();
        } catch (Exception e) {
            System.out.println("tidak tersimpan" + e);
        } finally {
            closed();
        }
    }

    //list table
    public ObservableList<Login> listLogin() {
        try {
            connectionDB();
            ObservableList<Login> list = FXCollections.observableArrayList();
            rs = st.executeQuery("select * from login order by login_name asc");
            while (rs.next()) {
                Login login = new Login();
                login.setLoginName(rs.getString(1));
                login.setLoginPassword(rs.getString(2));
                login.setLoginLevel(rs.getString(3));
                list.add(login);
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
    public void deleteAccount(String loginkName) {
        try {
            connectionDB();
            st.executeUpdate("delete from login where login_name='" + loginkName + "'");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closed();
        }
    }

    //Update
    public void updateAccount(Login login) {
        try {
            connectionDB();
            String sql = "update login set login_password='" + login.getLoginPassword() + "', login_level='"
                    + login.getLoginLevel() + "' where login_name='" + login.getLoginName() + "'";
            st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closed();
        }
    }
    
    //search table
    public ObservableList<Login> searchAccount(TextField txtSearch) {
        try {
            connectionDB();
            ObservableList<Login> listSearch = FXCollections.observableArrayList();
            String sql = "select * from login where login_name like '%" + txtSearch.getText() + "%' order by login_name asc";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Login login = new Login();
                login.setLoginName(rs.getString(1));
                login.setLoginPassword(rs.getString(2));
                login.setLoginLevel(rs.getString(3));
                listSearch.add(login);
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
