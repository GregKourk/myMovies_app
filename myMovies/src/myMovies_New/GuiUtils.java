package myMovies_new;

import javax.swing.*;

public class GuiUtils {
    
    //μεθοδος που κλεινει το τρεχων παραθυρο και ανοιγει το προηγουμενο
    public static void disposeAndOpenUpperFrame(JFrame currentFrame , JFrame upperFrame ){   //κλείσιμο του παρόντος και εμφάνιση  του upper frame
        int confirm = JOptionPane.showOptionDialog(
             null, "Return to initial menu?", 
             "Confirmation", JOptionPane.YES_NO_OPTION, 
             JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (confirm == 0) {
           currentFrame.dispose();
           upperFrame.setVisible(true);
        }
    }      
      
    //μεθοδος για να εξοδος απο το προγραμμα
    public static void exitProgram() {   // ερώτηση για έξοδο
        int confirm = JOptionPane.showOptionDialog(
            null, "Exit from application?",
            "Exit Confirmation", JOptionPane.YES_NO_OPTION,
             JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (confirm == 0) {
            System.exit(0);
        }
    }
}
