package myMovies_new;

import GUIFrames.StartmyMoviesNew;


public class myMoviesNew {
    
    //apiKey
    protected static String apiKey = "577d254593d3f75e3af628820cddf7a2";
    
    //μερος του URL για τα genres
    protected static String myMoviesNewGenresURL = "https://api.themoviedb.org/3/genre/movie/list";
    
    //μερος του URL για τις movies
    protected static String myMoviesNewReleaseDateURL = "https://api.themoviedb.org/3/discover/movie?";
   
    //Ο αριθμος των downloads που θα γινει απο το themoviesb.  
    //Μεγιστος αριθμος pages που μπορουν να "κατεβουν" βασει ορων site ειναι το 1000.
    //Βαζουμε τιμη 10 ωστε να μην υπερφορτωθει η βαση δεδομενων.
    protected static Integer numberOfDownloads = 10;            
    
    
    public static void main(String[] args){
        
        StartmyMoviesNew.StartmyMoviesNew();
  
    }
    
}