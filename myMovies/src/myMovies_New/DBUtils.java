package myMovies_new;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.*;
import javax.swing.JOptionPane;
import model.*;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;


public class DBUtils {
    
    //μεθοδος που επιστρεφει μια λιστα με τα ονοματα ολων των FavoriteList που υπαρχουν μεσα στην βαση δεδομενων
    public static ArrayList<String> getFavoriteListsNamesList() throws Exception {
        //λιστα με τα ονοματα των FavoriteList
        ArrayList<String> favoritesListsNames = new ArrayList<>();
        
        //λιστα με τις FavoriteList του αποτελεσματος του query
        List<FavoriteList> favoritesLists = new ArrayList<>();
        
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myMovies_newPU");
            EntityManager em = emf.createEntityManager();
         
            TypedQuery<FavoriteList> query = em.createNamedQuery("FavoriteList.findAll", FavoriteList.class);
            favoritesLists = query.getResultList();
            
            //περασμα ονοματος καθε FavoriteList στην λιστα με τα ονοματα
            for(FavoriteList list: favoritesLists){
                favoritesListsNames.add(list.getFavoriteListName());
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Πρόβλημα με τη βάση...", JOptionPane.ERROR_MESSAGE);
        }
        return favoritesListsNames;
    }
    
    //μεθοδος που επιστρεφει μια λιστα με τα ονοματα ολων των Genres που υπαρχουν μεσα στην βαση δεδομενων
    public static ArrayList<String> getGenresNamesList() throws Exception {
        //λιστα με τα ονοματα των Genres
        ArrayList<String> genresNamesList = new ArrayList<>();
        
        //λιστα με τα Genres του αποτελεσματος του query
        List<Genre> genresLists = new ArrayList<>();
        
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myMovies_newPU");
            EntityManager em = emf.createEntityManager();
         
            TypedQuery<Genre> query = em.createNamedQuery("Genre.findAll", Genre.class);
            genresLists = query.getResultList();
            
            //περασμα ονοματος καθε Genre στην λιστα με τα ονοματα
            for(Genre genre: genresLists){
                genresNamesList.add(genre.getGenreName());
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Πρόβλημα με τη βάση...", JOptionPane.ERROR_MESSAGE);
        }
        return genresNamesList;
    }
    
    //μεθοδος που επιστρεφει τις Movies βασει φθινουσας σειρας rating
    public static List<Movie> getMoviesEntitiesByRatingDescList() throws Exception{
        //λιστα με τα ονοματα των Genres σε φθινουσα σειρα rating
        List<Movie> allMoviesEntitiesByRatingDescList = new ArrayList<>(); 
        
        //λιστα με τις 10 καλυτερες Movies μονο
        List<Movie> moviesEntitiesByRatingDescList = new ArrayList<>();          
        
        try{
             EntityManagerFactory emf = Persistence.createEntityManagerFactory("myMovies_newPU");
             EntityManager em = emf.createEntityManager();
             
             TypedQuery<Movie> query = em.createNamedQuery("Movie.findByMovieRatingDesc", Movie.class);
             allMoviesEntitiesByRatingDescList = query.getResultList();
         }
         catch (Exception ex){
             JOptionPane.showMessageDialog(null, ex.getMessage(), "Πρόβλημα με τη βάση...", JOptionPane.ERROR_MESSAGE);
         }
        
        //περασμα των 10 καλυτερων Movies στην λιστα που θα επιστραφει στο προγραμμα
        for(int i=0;i<10;i++){
            moviesEntitiesByRatingDescList.add(allMoviesEntitiesByRatingDescList.get(i));
        }
        return moviesEntitiesByRatingDescList;
    }
    
    //μεθοδος που επιστρεφει ενα αντικειμενο τυπου FavoriteList απο το όνομα της FavoriteList
    public static FavoriteList getFavoriteListEtityFromFavoriteListName(String favoriteListName) throws Exception{
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myMovies_newPU");
            EntityManager em = emf.createEntityManager();
            
            TypedQuery<FavoriteList> query = em.createNamedQuery("FavoriteList.findByFavoriteListName", FavoriteList.class).setParameter("favoriteListName", favoriteListName);
            //για να μην χρησιμοποιήσει query cache και να λάβουμε σίγουρα δεδομένα που μόλις μπήκαν στην βάση
            query.setHint(QueryHints.REFRESH, HintValues.TRUE); 
            FavoriteList list = query.getSingleResult();
            return list;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Πρόβλημα με τη βάση...", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    //μεθοδος που επιστρεφει ενα αντικειμενο τυπου Genre απο το όνομα του Genre
    public static Genre getGenreEntityFromGenreName(String genreName) throws Exception{
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myMovies_newPU");
            EntityManager em = emf.createEntityManager();
            
            TypedQuery<Genre> query = em.createNamedQuery("Genre.findByGenreName", Genre.class).setParameter("genreName", genreName);
            //για να μην χρησιμοποιήσει query cache και να λάβουμε σίγουρα δεδομένα που μόλις μπήκαν στην βάση
            query.setHint(QueryHints.REFRESH, HintValues.TRUE); 
            Genre genre = query.getSingleResult();
            return genre;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Πρόβλημα με τη βάση...", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    //μεθοδος που επιστρεφει ενα αντικειμενο τυπου Movie απο το όνομα της Movie
    public static Movie getMovieEntityFromMovieName(String movieName) throws Exception{
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myMovies_newPU");
            EntityManager em = emf.createEntityManager();
            
            TypedQuery<Movie> query = em.createNamedQuery("Movie.findByMovieName", Movie.class).setParameter("movieName", movieName);
            // για να μην χρησιμοποιήσει query cache και να λάβουμε σίγουρα δεδομένα που μόλις μπήκαν στην βάση
            query.setHint(QueryHints.REFRESH, HintValues.TRUE);  
            Movie movie = query.getSingleResult();
            return movie;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Πρόβλημα με τη βάση...", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    //μεθοδος που επιστρεφει μια λιστα αντικειμενων τυπου Movie βασει Genre και ReleaseDate που εισαγονται ως ορισματα στην μεθοδο
    public static List<Movie> getMoviesEntitiesFromSelectedGenreAndReleaseDate(Genre selectedGenre, String dateInserted) throws ParseException{
        List<Movie> moviesEntitiesFromSelectedGenreAndReleaseDate = new ArrayList<>();
        
        //ο χρηστης δινει μονο χρονιά, οποτε προσθετουμε μηνα και ημερα αρχης και τελους για να γινει η αναζητηση στις ταινιες
        String startDateInserted = dateInserted;
        String endDateInserted = dateInserted;
        
        StringBuilder sb = new StringBuilder();
        sb.append(dateInserted);
        sb.append("-01-01");
        startDateInserted = sb.toString();
        
        sb.delete(0, 20);
        sb.append(dateInserted);
        sb.append("-12-31");
        endDateInserted = sb.toString();
        
        //μετατροπη σε μορφη Date για να γινει η συγκριση με τις ημερομηνιες των ταινιων που ειναι στην βαση
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date startDate, endDate = new Date();
        startDate = dateFormat.parse(startDateInserted);
        endDate = dateFormat.parse(endDateInserted);
        
        try{
             EntityManagerFactory emf = Persistence.createEntityManagerFactory("myMovies_newPU");
             EntityManager em = emf.createEntityManager();
             
             TypedQuery<Movie> query = em.createNamedQuery("Movie.findByGenreAndReleaseDate", Movie.class).setParameter("genreIdFk", selectedGenre).setParameter("startDate", startDate).setParameter("endDate", endDate);
             moviesEntitiesFromSelectedGenreAndReleaseDate = query.getResultList();
         }catch (Exception ex){
             JOptionPane.showMessageDialog(null, ex.getMessage(), "Πρόβλημα με τη βάση...", JOptionPane.ERROR_MESSAGE);
         }
        return moviesEntitiesFromSelectedGenreAndReleaseDate;
    }
  }
     
     
     
     
    

