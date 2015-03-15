/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author sulistiyanto
 */
public class configIPWifi {

    private static final String CMD = "netsh int ip set address name = \"Local Area Connection\" source = static addr = 192.168.222.3 mask = 255.255.255.0";

    public void IP() {
        try {
            // Run "netsh" Windows command
            Process process = Runtime.getRuntime().exec(CMD);

            // Get input streams
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Read command standard output
            String s;
            System.out.println("Standard output: ");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // Read command errors
            System.out.println("Standard error: ");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void wifi() {
        try {
            System.out.println("-- Setting up WLAN --");
            String netshCommand = "netsh wlan set hostednetwork mode=allow ssid=\"YourSSID\" key=\"12345678\" & exit";
            String[] elevateCommand = new String[]{netshCommand};
            ProcessBuilder pb1 = new ProcessBuilder(elevateCommand);
            Process p1 = pb1.start();
            p1.waitFor();

            System.out.println("-- Starting WLAN --");
            netshCommand = "netsh wlan start hostednetwork & exit";
            elevateCommand = new String[]{netshCommand};
            ProcessBuilder pb2 = new ProcessBuilder(elevateCommand);
            Process p2 = pb2.start();
            p2.waitFor();

            System.out.println("-- Setting up IPv4 interface --");
            netshCommand = "netsh interface ipv4 set address \"Conexión de red inalámbrica\" static 192.168.0.102 255.255.255.0 192.168.0.254 & exit";
            elevateCommand = new String[]{ netshCommand};
            ProcessBuilder pb3 = new ProcessBuilder(elevateCommand);
            Process p3 = pb3.start();
            p3.waitFor();

            System.out.println("-- Getting IPv4 interface dump --");
            netshCommand = "netsh interface ipv4 dump";
            ProcessBuilder pb4 = new ProcessBuilder("cmd.exe", "/c", netshCommand);
            Process p4 = pb4.start();

            System.out.println("-- Printing IPv4 interface dump --");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(p4.getInputStream(), "ISO-8859-1"));
            String output;
            while ((output = bfr.readLine()) != null) {
                System.out.println(output);
            }

        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}
