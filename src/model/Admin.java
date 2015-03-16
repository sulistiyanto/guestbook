/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author sulistiyanto
 */
public class Admin {
    
    String username;
    Boolean wifi;

    public Admin() {
    }

    public Admin(String username, Boolean wifi) {
        this.username = username;
        this.wifi = wifi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }
    
}
