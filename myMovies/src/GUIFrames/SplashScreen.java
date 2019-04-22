/**
 * http://examples.oreilly.com/jswing2/code/ch08/SplashScreen.java
 */
package GUIFrames;

import java.awt.*;
import javax.swing.*;
import myMovies_new.DBUtils;
import static GUIFrames.StartmyMoviesNew.favoriteList;
import java.util.ArrayList;


public class SplashScreen extends JWindow implements Runnable {
    
    private final int duration;
    Thread t;

    public SplashScreen(int d) {
        duration = d;
    }

    public void start() {
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {  

        //Δημιουργία πάνελ με τα ονόματά μας
        JPanel splash = new JPanel();
        splash.setBackground(Color.white);
        setSize(250, 150);
        setLocationRelativeTo(null);

        JLabel msg = new JLabel("          myMovies_new is now starting                     ");
        JLabel msg2 = new JLabel("                                                          ");
        JLabel msg3 = new JLabel("             Κουρκοβής Γρηγόριος                          ");
        JLabel msg4 = new JLabel("              Ρίζος Αποστόλης                             ");
        JLabel msg5 = new JLabel("              Τούσιας Μιχαήλ                              ");
        JLabel msg6 = new JLabel("         3η ομαδική εργασία - ΗΛΕ - 4                     ");

        splash.add(msg);
        splash.add(msg2);
        splash.add(msg3);
        splash.add(msg4);
        splash.add(msg5);
        splash.add(msg6);

        this.setContentPane(splash);

        setVisible(true);
        toFront();
        //Τελος δημιουργιας panel
        
        //δημιουργια λιστας που περιεχει ολες τις FavoriteLists
        try {
            favoriteList =  DBUtils.getFavoriteListsNamesList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Database Problem! Program will exit!" , "Database Problem! Program will exit!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } 

        // Προσωρινή αναμονή
        try {
            Thread.sleep(duration+3000);
        }catch (Exception e) {
        }

        setVisible(false);
        dispose();
    }
}


