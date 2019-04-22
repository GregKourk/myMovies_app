package myMovies_new;

import java.io.StringReader;
import java.util.Date;
import javax.json.*;
import javax.persistence.*;
import javax.swing.JOptionPane;
import model.*;

public class GetFromJson {
    
    // Η μεθοδος αυτή διαβάζει τα κατάλληλά  δεδομένα απο το json και επιστρέφει  Genre object
    public static Genre createGenreIDFromJson(String jsonData, int index)throws Exception{
        
        Genre genreObject = new Genre();
        
        JsonReader reader = Json.createReader(new StringReader(jsonData));
        JsonObject jsonObject = reader.readObject();
       
        JsonArray listJson = jsonObject.getJsonArray("genres");
        JsonObject jsonGenre = (JsonObject) listJson.get(index);
        
        JsonValue jsonID = jsonGenre.get("id");    //Genre ID
        Integer jsonIDString = Integer.parseInt(jsonID.toString());
        
        JsonValue jsonName = jsonGenre.get("name");    //Genre name
        String jsonNameString = jsonName.toString();
        
        genreObject.setGenreId(jsonIDString);
        genreObject.setGenreName(jsonNameString);
          
        return genreObject;
    }
    
    // Η μεθοδος αυτή διαβάζει τα κατάλληλά  δεδομένα απο το json και επιστρέφει  Movie object
    public static Movie createMovieIDFromJson(String jsonData, int index) throws Exception{
        
        Movie movieObject = new Movie();
        
        JsonReader reader = Json.createReader(new StringReader(jsonData));
        JsonObject jsonObject = reader.readObject();
        
        JsonArray listJson = jsonObject.getJsonArray("results");
        JsonObject jsonMovie = (JsonObject) listJson.get(index);
        
        JsonValue jsonID = jsonMovie.get("id");    //Movie ID
        Integer jsonIDString = Integer.parseInt(jsonID.toString());
        
        JsonValue jsonRating = jsonMovie.get("vote_average");    //Movie rating
        Float jsonRatingString = Float.parseFloat(jsonRating.toString());
        
        JsonValue jsonName = jsonMovie.get("title");    //Movie name
        String jsonNameString = jsonName.toString();
        
        //Αναζητηση πρωτου ID απο τα ζητουμενα για καθε movie και δημιουργια αντικειμενου Genre για να περαστει στην αντιστοιχη movie
        JsonArray jsonMovieGenresIds = jsonMovie.getJsonArray("genre_ids");
        int genre_id=0;             //To genre_id που θα κρατησουμε 
        int counterForId=0;         //Μετρητης για να διασχισουμε ολη τη λιστα με τα genres_id καθε ταινιας
        boolean flag=false;         //Flag για να ξερουμε ποτε βρηκαμε το genre_id που θελουμε
        while (counterForId<jsonMovieGenresIds.size() && flag==false){
            Integer jsonMovieGenresIdsString = Integer.parseInt(jsonMovieGenresIds.get(counterForId).toString());
            if (jsonMovieGenresIdsString == 28 || jsonMovieGenresIdsString==10749 || jsonMovieGenresIdsString==878){
                genre_id = jsonMovieGenresIdsString;
                flag=true;
            }
            counterForId++;
        }
        
        //δημιουργία   EntityManager
        EntityManager em = null;
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myMovies_newPU");
            em = emf.createEntityManager();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "DB problem", JOptionPane.ERROR_MESSAGE);
        }
        
        //Βρίσκω το είδος που ανηκει κάθε ταινία και δημιουργώ αντικείμενο τύπου Genre για να περαστει μεσα στο αντικειμενο τυπου Movie
        em.getTransaction().begin();
        TypedQuery<Genre> query = em.createNamedQuery("Genre.findByGenreId", Genre.class).setParameter("genreId", genre_id);
        Genre result = query.getSingleResult();
        Genre wantedGenre = result;
        //Τελος διαδικασις ευρεσης ID
        
        JsonValue jsonOverview = jsonMovie.get("overview");    //Movie overview
        String jsonOverviewString = jsonOverview.toString();
        //επειδη εχω μεγιστο μηκος για overview 500 στην βαση μου,
        //αν το μηκος του overview >500 τοτε κραταω μονο τους 500 πρωτους characters
        if (jsonOverviewString.length()>500){       
             jsonOverviewString = jsonOverviewString.substring(0, 499);
        }
       
        JsonValue jsonDate = jsonMovie.get("release_date");    //Movie release date
        String jsonDateString = jsonDate.toString();
        //Επειδη απο το json η ημερομηνια ειναι String της μορφης "yyyy-MM-dd",
        //για να γινει η μετατροπη θελουμε String της μορφης yyyy-MM-dd,
        //οποτε "διωχνω" τα "αυτακια" απο το αρχικο String
        jsonDateString = jsonDateString.substring(1, jsonDateString.length()-1);    
        
        //μετατροπη String σε Date
        Date dateOfMovie = TempUtils.convertStringToDate(jsonDateString);
        
        movieObject.setMovieId(jsonIDString);
        movieObject.setMovieName(jsonNameString);
        movieObject.setMovieOverview(jsonOverviewString);
        movieObject.setMovieRating(jsonRatingString);
        movieObject.setMovieReleaseDate(dateOfMovie);
        movieObject.setGenreIdFk(wantedGenre);
        
        return movieObject;
    }
}
