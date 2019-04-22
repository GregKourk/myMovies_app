package myMovies_new;

import model.*;
import javax.persistence.*;
import javax.swing.JOptionPane;
import static myMovies_new.myMoviesNew.numberOfDownloads;


public class UpdateDB {
    
    
    //κλάση που ανανεώει δεδομένα προγράμματος
    public static void UpdateAllDB() {
        clearDB();                  //διαγραφη των δεδομενων των βασεων genres, movies
        updateGenresDB(false);      //Κατεβάζει το json και τοποθετεί μέσα στην βάση τα genres
        updateMoviesDB(false);      //Κατεβάζει το json και τοποθετεί μέσα στην βάση τις movies
        JOptionPane.showMessageDialog(null, "All DB updated", "Updated", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void clearDB() {  //κλαση διαγραφής των δεδομένων των πινάκων
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myMovies_newPU");
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                Query q;
                q = em.createQuery("DELETE FROM Movie");
                q.executeUpdate();
                q = em.createQuery("DELETE FROM Genre");
                q.executeUpdate();
                em.getTransaction().commit();
                
                JOptionPane.showMessageDialog(null, "All DB cleared", "DB Cleared", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Can't Delete DB ", JOptionPane.ERROR_MESSAGE);
                em.getTransaction().rollback();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "DB problem ", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //κλάση που ανανεώνει δεδομένα genres
    public static void updateGenresDB(Boolean showMessageWhenComplete) {
        
        String genresIDData;        //string που αποθηκευεται το json
        
        //κατέβασμα απο String json απο το internet
        try {
            genresIDData = Downloader.downloadAllGenresString();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Internet problem", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //δημιουργία   EntityManager
        EntityManager em;
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myMovies_newPU");
            em = emf.createEntityManager();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "DB problem", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //ανανέωση βάσης 
        System.out.println("Starting Genre DB Update");
        
        int numOfGenres=0;  //μετρητης για τον αριθμο των ειδων που θελουμε
        int index=0;        //δεικτης για να περαστουν και να διαβαστουν ολα τα ειδη που εχουμε
        try {
                while(numOfGenres<3) {      //οσο δεν εχουμε βρει 3 ειδη ταινιων 
                    //δημιουργια entity Genre απο το string που κατεβασαμε
                    //Είσοδος το string json και ένας δεικτης που παει κάθε φορά στο επομενο αντικειμενο
                    Genre genre = GetFromJson.createGenreIDFromJson(genresIDData, index);        
                
                    //αν είναι ένα από τα είδη που θέλουμε
                    if (genre.getGenreId()== 28 || genre.getGenreId()==10749 || genre.getGenreId()==878){       
                        em.getTransaction().begin();    
                        em.persist(genre);              //πέρασμα genre στην βάση
                        em.getTransaction().commit();   
                        numOfGenres++;                  //αυξηση μετρητη για εξοδο απο while (αν εχουμε βρει 3 ειδη που ειναι οσα θελουμε)
                    }
                    
                    //αν εχω βρει και τα 3 ειδη genres εμφανιση μηνυματος ολοκληρωσης
                    if (numOfGenres==2){        
                        System.out.println("Genre DB Updated\n");
                        showMessageWhenComplete=true;
                        if (showMessageWhenComplete == true) {
                            JOptionPane.showMessageDialog(null, "Genre DB updated", "Updated", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    index++;    //αυξηση μετρητη για να παει στο επομενο ειδος ταινιας
                }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "json problem", JOptionPane.ERROR_MESSAGE);
            em.getTransaction().rollback();
        }
    }
    
    //κλάση που ανανεώνει δεδομένα movies
    public static void updateMoviesDB(Boolean showMessageWhenComplete){
        //η διαδικασια θα επαναληφθει 10 φορες καθως σε καθε page κατεβαινουν 20 ταινιες
        //Με αυτο τον τροπο θα κατεβουν 200 ταινιες.
        for(int pageCounter=0; pageCounter<numberOfDownloads; pageCounter++){
            
            String moviesData;      //string που αποθηκευεται το json
            
            //κατέβασμα απο String json απο το internet
            try {
                moviesData = Downloader.downloadAllMoviesString(pageCounter+1);      
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Internet problem", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //δημιουργία   EntityManager
            EntityManager em;
            try {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("myMovies_newPU");
                em = emf.createEntityManager();
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "DB problem", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            //ανανέωση βάσης 
            if (pageCounter==0)
                System.out.println("Starting Movie DB Update");
            int numOfMovies=0;  //μετρητης για τον αριθμο των ταινιων που εχει καθε page
            try {
                    while (numOfMovies<20){
                        
                        //δημιουργια entity Movie απο το string που κατεβασαμε
                        Movie movie = GetFromJson.createMovieIDFromJson(moviesData, numOfMovies);
                    
                        em.getTransaction().begin();                
                        em.persist(movie);                          //πέρασμα movie στην βάση
                        em.getTransaction().commit();               
                    
                        numOfMovies++;
                        
                        //αν ειμαστε στην τελευταια page και στην τελευταια movie εμφανιση μηνυματος ολοκληρωσης
                        if (pageCounter==9 && numOfMovies==20 ){
                            showMessageWhenComplete=true;
                            System.out.println("Movie DB Updated");
                            if (showMessageWhenComplete == true) {
                                JOptionPane.showMessageDialog(null, "Movie DB updated", "Updated", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "json problem", JOptionPane.ERROR_MESSAGE);
                em.getTransaction().rollback();
                GuiUtils.exitProgram();
            }
        }
    }
    
}
