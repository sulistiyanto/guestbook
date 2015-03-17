/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author sulistiyanto
 */
public class ConfigWifiAdmin {

    public void saveStatus(String str) {
        try {
            File newTextFile = new File("C:\\xampp\\htdocs\\guestbook\\report\\wifi.txt");

            FileWriter fw = new FileWriter(newTextFile);
            fw.write(str);
            fw.close();

        } catch (IOException iox) {
        }
    }
    
}
