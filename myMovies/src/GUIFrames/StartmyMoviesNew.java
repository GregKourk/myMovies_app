package GUIFrames;

import java.util.ArrayList;


public class StartmyMoviesNew {
    
    static ArrayList<String> favoriteList;
    
    public static void StartmyMoviesNew(){
           
        //εμφανιση SplashScreen 
        SplashScreen splash = new SplashScreen(200);
        splash.run();
                 
        // Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame(favoriteList).setVisible(true);
        });
    }
    
}
