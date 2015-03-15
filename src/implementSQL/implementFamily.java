/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementSQL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Family;
import model.Guest;

/**
 *
 * @author sulistiyanto
 */
public class implementFamily extends ConnectionDB {

    //load table book
    public ObservableList<Guest> searchGuest(TextField txtGuestName, TextField txtBookName) {
        try {
            connectionDB();
            ObservableList<Guest> listSearch = FXCollections.observableArrayList();
            String sql = "select * from guest, book where book.book_id = guest.book_id and "
                    + "guest.guest_name like '" + txtGuestName.getText() + "%' and book.book_name like '" + txtBookName.getText() + "%' order by guest_name asc";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Guest guest = new Guest();
                guest.setGuestId(rs.getString(1));
                guest.setGuestName(rs.getString(2));
                listSearch.add(guest);
            }
            return listSearch;
        } catch (Exception e) {
            System.out.println("list" + e);
            return null;
        } finally {
            closed();
        }
    }

    //insert
    public void insertFamily(Family family) {
        try {
            connectionDB();
            String sql = "insert into family values ('" + family.getFamilyId() + "', '" + family.getFamilyName()
                    + "', '" + family.getFamilySex() + "', '" + family.getFamilyPresence() + "', '" + family.getGuestId() + "')";
            st.executeUpdate(sql);
            closed();
        } catch (Exception e) {
            System.out.println("tidak tersimpan" + e);
        } finally {
            closed();
        }
    }

    //list table
    public ObservableList<Family> listFamily(ComboBox comboBookName) {
        try {
            connectionDB();
            ObservableList<Family> list = FXCollections.observableArrayList();
            rs = st.executeQuery("select f.family_id, f.family_name, f.family_sex, "
                    + "f.family_presence, g.guest_name, g.guest_id, b.book_id from family f, guest g, "
                    + "book b where f.guest_id=g.guest_id and g.book_id=b.book_id and "
                    + "b.book_name ='" + comboBookName.getValue() + "' order by g.guest_name asc");
            while (rs.next()) {
                Family family = new Family();
                family.setFamilyId(rs.getString(1));
                family.setFamilyName(rs.getString(2));
                family.setFamilySex(rs.getString(3));
                family.setFamilyPresence(rs.getString(4));
                family.setGuestName(rs.getString(5));
                family.setGuestId(rs.getString(6));
                family.setBookId(rs.getString(7));
                list.add(family);
            }
            return list;
        } catch (Exception e) {
            System.out.println("list" + e);
            return null;
        } finally {
            closed();
        }
    }

    //Delete
    public void deleteFamily(String family_id) {
        try {
            connectionDB();
            st.executeUpdate("delete from family where family_id='" + family_id + "'");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closed();
        }
    }

    //Update
    public void updateFamily(Family family) {
        try {
            connectionDB();
            String sql = "update family set family_name='" + family.getFamilyName() + "', family_sex='" + family.getFamilySex() 
                    + "', guest_id='" + family.getGuestId() + "' where family_id='" + family.getFamilyId() + "'";
            st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closed();
        }
    }

    //list search table
    public ObservableList<Family> searchFamily(TextField txtGuestName, ComboBox bookName) {
        try {
            connectionDB();
            ObservableList<Family> listSearch = FXCollections.observableArrayList();
            String sql = "select f.family_id, f.family_name, f.family_sex, "
                    + "f.family_presence, g.guest_name, g.guest_id, b.book_id from family f, guest g, "
                    + "book b where f.guest_id=g.guest_id and g.book_id=b.book_id and "
                    + "g.guest_name like '%" + txtGuestName.getText() + "%' "
                    + "and b.book_name ='" + bookName.getValue() + "'order by guest_name asc";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Family family = new Family();
                family.setFamilyId(rs.getString(1));
                family.setFamilyName(rs.getString(2));
                family.setFamilySex(rs.getString(3));
                family.setFamilyPresence(rs.getString(4));
                family.setGuestName(rs.getString(5));
                family.setGuestId(rs.getString(6));
                family.setBookId(rs.getString(7));
                listSearch.add(family);
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
