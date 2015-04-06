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
import model.Book;
import model.Guest;

/**
 *
 * @author sulistiyanto
 */
public class implementGuest extends ConnectionDB {

    //load table book
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

    //insert
    public void insertGuest(Guest guest) {
        try {
            connectionDB();
            String sql = "insert into guest values ('" + guest.getGuestId() + "', '" + guest.getGuestName()
                    + "', '" + guest.getGuestSex() + "', '" + guest.getGuestJob() + "' , '" + guest.getGuestPhone()
                    + "', '" + guest.getGuestAddress() + "', '" + guest.getGuestFoto() + "', '" + guest.getGuestPresence()
                    + "', '" + guest.getBookId() + "')";
            st.executeUpdate(sql);
            closed();
        } catch (Exception e) {
            System.out.println(guest.getBookId() + "buku id");
            System.out.println("tidak tersimpan" + e.getMessage());
        }
    }

    //list table
    public ObservableList<Guest> listGuest(String bookName) {
        try {
            connectionDB();
            ObservableList<Guest> list = FXCollections.observableArrayList();
            rs = st.executeQuery("select * from guest, book "
                    + "where book.book_id = guest.book_id and book.book_name= '" + bookName + "' "
                    + "order by guest_name asc");
            while (rs.next()) {
                Guest guest = new Guest();
                guest.setGuestId(rs.getString(1));
                guest.setGuestName(rs.getString(2));
                guest.setGuestSex(rs.getString(3));
                guest.setGuestJob(rs.getString(4));
                guest.setGuestPhone(rs.getString(5));
                guest.setGuestAddress(rs.getString(6));
                guest.setGuestPresence(rs.getString(8));
                guest.setBookId(rs.getString(9));
                list.add(guest);
            }
            return list;
        } catch (Exception e) {
            System.out.println("list" + e);
            return null;
        } finally {
            closed();
        }
    }

    //list table Yes
    public ObservableList<Guest> listGuestYes(String bookName) {
        try {
            connectionDB();
            ObservableList<Guest> list = FXCollections.observableArrayList();
            rs = st.executeQuery("select * from guest, book "
                    + "where book.book_id = guest.book_id and guest.guest_presence = 'Ya' and book.book_name= '" + bookName + "' "
                    + "order by guest_name asc");
            while (rs.next()) {
                Guest guest = new Guest();
                guest.setGuestId(rs.getString(1));
                guest.setGuestName(rs.getString(2));
                guest.setGuestSex(rs.getString(3));
                guest.setGuestJob(rs.getString(4));
                guest.setGuestPhone(rs.getString(5));
                guest.setGuestAddress(rs.getString(6));
                guest.setGuestPresence(rs.getString(8));
                guest.setBookId(rs.getString(9));
                list.add(guest);
            }
            return list;
        } catch (Exception e) {
            System.out.println("list" + e);
            return null;
        } finally {
            closed();
        }
    }
    
    //list table No
    public ObservableList<Guest> listGuestNo(String bookName) {
        try {
            connectionDB();
            ObservableList<Guest> list = FXCollections.observableArrayList();
            rs = st.executeQuery("select * from guest, book "
                    + "where book.book_id = guest.book_id and guest.guest_presence = 'Tidak' and book.book_name= '" + bookName + "' "
                    + "order by guest_name asc");
            while (rs.next()) {
                Guest guest = new Guest();
                guest.setGuestId(rs.getString(1));
                guest.setGuestName(rs.getString(2));
                guest.setGuestSex(rs.getString(3));
                guest.setGuestJob(rs.getString(4));
                guest.setGuestPhone(rs.getString(5));
                guest.setGuestAddress(rs.getString(6));
                guest.setGuestPresence(rs.getString(8));
                guest.setBookId(rs.getString(9));
                list.add(guest);
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
    public void deleteGuest(String guestId) {
        try {
            connectionDB();
            st.executeUpdate("delete from guest where guest_id='" + guestId + "'");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closed();
        }
    }

    //Update
    public void updateBook(Guest guest) {
        try {
            connectionDB();
            String sql = "update guest set guest_name='" + guest.getGuestName() + "', guest_sex='" + guest.getGuestSex()
                    + "', guest_job='" + guest.getGuestJob() + "', guest_phone='" + guest.getGuestPhone()
                    + "', guest_address='" + guest.getGuestAddress() + "', guest_foto='" + guest.getGuestFoto()
                    + "', book_id='" + guest.getBookId() + "' where guest_id='" + guest.getGuestId() + "'";
            st.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closed();
        }
    }

    //list search table
    public ObservableList<Guest> searchGuest(TextField txtGuestName, ComboBox bookName) {
        try {
            connectionDB();
            ObservableList<Guest> listSearch = FXCollections.observableArrayList();
            String sql = "select * from guest, book where book.book_id = guest.book_id and "
                    + "guest.guest_name like '" + txtGuestName.getText() + "%' and book.book_name like '" + bookName.getValue() + "%' order by guest_name asc";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Guest guest = new Guest();
                guest.setGuestId(rs.getString(1));
                guest.setGuestName(rs.getString(2));
                guest.setGuestSex(rs.getString(3));
                guest.setGuestJob(rs.getString(4));
                guest.setGuestPhone(rs.getString(5));
                guest.setGuestAddress(rs.getString(6));
                guest.setGuestPresence(rs.getString(8));
                guest.setBookId(rs.getString(9));
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
    
    //list search table Yes
    public ObservableList<Guest> searchGuestYes(TextField txtGuestName, ComboBox bookName) {
        try {
            connectionDB();
            ObservableList<Guest> listSearch = FXCollections.observableArrayList();
            String sql = "select * from guest, book where book.book_id = guest.book_id and "
                    + "guest.guest_presence= 'Ya' and guest.guest_name like '" + txtGuestName.getText() + "%' and book.book_name like '" + bookName.getValue() + "%' order by guest_name asc";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Guest guest = new Guest();
                guest.setGuestId(rs.getString(1));
                guest.setGuestName(rs.getString(2));
                guest.setGuestSex(rs.getString(3));
                guest.setGuestJob(rs.getString(4));
                guest.setGuestPhone(rs.getString(5));
                guest.setGuestAddress(rs.getString(6));
                guest.setGuestPresence(rs.getString(8));
                guest.setBookId(rs.getString(9));
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
    
    //list search table Yes
    public ObservableList<Guest> searchGuestNo(TextField txtGuestName, ComboBox bookName) {
        try {
            connectionDB();
            ObservableList<Guest> listSearch = FXCollections.observableArrayList();
            String sql = "select * from guest, book where book.book_id = guest.book_id and "
                    + "guest.guest_presence='Tidak' and guest.guest_name like '" + txtGuestName.getText() + "%' and book.book_name like '" + bookName.getValue() + "%' order by guest_name asc";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Guest guest = new Guest();
                guest.setGuestId(rs.getString(1));
                guest.setGuestName(rs.getString(2));
                guest.setGuestSex(rs.getString(3));
                guest.setGuestJob(rs.getString(4));
                guest.setGuestPhone(rs.getString(5));
                guest.setGuestAddress(rs.getString(6));
                guest.setGuestPresence(rs.getString(8));
                guest.setBookId(rs.getString(9));
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
}
